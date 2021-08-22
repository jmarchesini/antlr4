package org.jcm.asm;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.jcm.asm.gen.AssemblerLexer;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author jmarchesini
 */
abstract public class InterpreterBase {
    public static final int DEFAULT_OPERAND_STACK_SIZE = 100;
    public static final int DEFAULT_CALL_STACK_SIZE = 1000;

    protected DisAssembler disasm;
    protected boolean trace = false;

    protected byte[] code;        // byte-addressable code memory
    protected int ip;             // instruction pointer register
    protected int codeSize;       // size of code memory
    protected Object[] globals;   // global variable space
    protected Object[] constPool; // the constant pool

    /* Operand stack, grows upwards */
    protected Object[] operands = new Object[DEFAULT_OPERAND_STACK_SIZE];
    protected int sp = -1;        // stack pointer register
    protected int fp = -1;        // frame pointer register

    protected FunctionSymbol mainFunction;

    protected abstract void exec();

    /**
     * Pull off 4 bytes starting at ip and return 32-bit signed int value.
     *  Return with ip pointing *after* last byte of operand.  The byte-order
     *  is high byte down to low byte, left to right.
     */
    protected int getIntOperand() {
        int word = BytecodeAssembler.getInt(code, ip);
        ip += 4;

        return word;
    }

    protected void run(
        String[] args,
        InterpreterBase interpreter,
        BytecodeDefBase bcDef
    ) throws Exception {
        boolean trace = false;
        boolean disassemble = false;
        boolean dump = false;
        int i = 0;

        String filename = null;
        InputStream input;

        while (i < args.length) {
            switch (args[i]) {
                case "-trace":
                    trace = true;
                    i++;
                    break;
                case "-dis":
                    disassemble = true;
                    i++;
                    break;
                case "-dump":
                    dump = true;
                    i++;
                    break;
                default:
                    filename = args[i];
                    i++;
                    break;
            }
        }

        if (filename != null)
            input = new FileInputStream(filename);
        else
            input = System.in;

        boolean hasErrors = load(interpreter, input, bcDef);

        if (!hasErrors) {
            this.trace = trace;
            interpreter.exec();

            if (disassemble)
                interpreter.disassemble();
            if (dump)
                interpreter.coreDump();
        }
    }

    private static boolean load(
        InterpreterBase interp,
        InputStream input,
        BytecodeDefBase bcDef
    ) throws Exception {
        boolean hasErrors;

        try (input) {
            CharStream charStream = CharStreams.fromStream(input);
            AssemblerLexer lexer = new AssemblerLexer(charStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            BytecodeAssembler assembler = new BytecodeAssembler(tokens, bcDef.getInstructions());

            assembler.program();
            interp.code = assembler.getMachineCode();
            interp.codeSize = assembler.getCodeMemorySize();
            interp.constPool = assembler.getConstantPool();
            interp.mainFunction = assembler.getMainFunction();
            interp.globals = new Object[assembler.getDataSize()];
            interp.disasm =
                new DisAssembler(interp.code, interp.codeSize, interp.constPool, bcDef);

            hasErrors = assembler.getNumberOfSyntaxErrors() > 0;
        }

        return hasErrors;
    }

    private void disassemble() { disasm.disassemble(); }

    private void coreDump() {
        if (constPool.length > 0)
            dumpConstantPool();

        if (globals.length > 0)
            dumpDataMemory();

        dumpCodeMemory();
    }

    private void dumpConstantPool() {
        System.out.println("Constant pool:");
        int addr = 0;

        for (Object o : constPool) {
            if (o instanceof String)
                System.out.printf("%04d: \"%s\"\n", addr, o);
            else
                System.out.printf("%04d: %s\n", addr, o);

            addr++;
        }

        System.out.println();
    }

    private void dumpDataMemory() {
        System.out.println("Data memory:");
        int addr = 0;

        for (Object o : globals) {
            if (o != null)
                System.out.printf("%04d: %s <%s>\n",
                    addr, o, o.getClass().getSimpleName());
            else
                System.out.printf("%04d: <null>\n", addr);

            addr++;
        }

        System.out.println();
    }

    private void dumpCodeMemory() {
        System.out.println("Code memory:");

        for (int i = 0; code != null && i < codeSize; i++) {
            if (i % 8 == 0 && i != 0) System.out.println();
            if (i % 8 == 0) System.out.printf("%04d:", i);
            System.out.printf(" %3d", ((int) code[i]));
        }

        System.out.println();
    }
}
