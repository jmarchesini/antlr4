/*
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
 */
package org.jcm.stack;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.jcm.asm.BytecodeAssembler;
import org.jcm.asm.DisAssembler;
import org.jcm.asm.FunctionSymbol;
import org.jcm.asm.gen.AssemblerLexer;

import java.io.InputStream;
import java.io.FileInputStream;

/**
 * A simple stack-based interpreter - pattern 27
 */
public class StackInterpreter {
    public static final int DEFAULT_OPERAND_STACK_SIZE = 100;
    public static final int DEFAULT_CALL_STACK_SIZE = 1000;

    DisAssembler disasm;
    boolean trace = false;

    byte[] code;        // byte-addressable code memory
    int ip;             // instruction pointer register
    int codeSize;       // size of code memory

    Object[] globals;   // global variable space
    Object[] constPool; // the constant pool

    /* Operand stack, grows upwards */
    Object[] operands = new Object[DEFAULT_OPERAND_STACK_SIZE];
    int sp = -1;        // stack pointer register
	
    /* Stack of stack frames, grows upwards */
    StackFrame[] calls = new StackFrame[DEFAULT_CALL_STACK_SIZE];
    int fp = -1;        // frame pointer register

    FunctionSymbol mainFunction;

    public static void main(String[] args) throws Exception {
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

        StackInterpreter stackInterpreter = new StackInterpreter();
        boolean hasErrors = load(stackInterpreter, input);

        if (!hasErrors) {
            stackInterpreter.trace = trace;
            stackInterpreter.exec();

            if (disassemble)
                stackInterpreter.disassemble();
            if (dump)
                stackInterpreter.coredump();
        }
    }

    public static boolean load(StackInterpreter interp, InputStream input) throws Exception {
        boolean hasErrors;

        try (input) {
            BytecodeDefinition bcDef = new BytecodeDefinition();
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

    /* Execute the bytecodes in code memory starting at mainAddr */
    public void exec() {
        // simulate "call main()" - set up stack and start at addr 0
        if (mainFunction == null)
            mainFunction = new FunctionSymbol("main", 0, 0, 0);

        StackFrame mainFrame = new StackFrame(mainFunction, -1);
        calls[++fp] = mainFrame;
        ip = mainFunction.getAddress();

        cpu();
    }

    /* Interpreter's fetch-decode-execute cycle */
    protected void cpu() {
        Object v;
        int a, b;
        float e, f;
        int addr;

        short opcode = code[ip];

        while (opcode != BytecodeDefinition.INSTR_HALT && ip < codeSize) {
            if (trace)
                trace();

            ip++; //jump to next instruction or first byte of operand

            switch (opcode) {
                case BytecodeDefinition.INSTR_IADD:
                    a = (Integer) operands[sp - 1]; // 1st operand 1 below top
                    b = (Integer) operands[sp];     // 2nd operand at top of stack
                    sp -= 2;                        // pop both operands
                    operands[++sp] = a + b;         // push result
                    break;
                case BytecodeDefinition.INSTR_ISUB:
                    a = (Integer) operands[sp - 1];
                    b = (Integer) operands[sp];
                    sp -= 2;
                    operands[++sp] = a - b;
                    break;
                case BytecodeDefinition.INSTR_IMUL:
                    a = (Integer) operands[sp - 1];
                    b = (Integer) operands[sp];
                    sp -= 2;
                    operands[++sp] = a * b;
                    break;
                case BytecodeDefinition.INSTR_ILT:
                    a = (Integer) operands[sp - 1];
                    b = (Integer) operands[sp];
                    sp -= 2;
                    operands[++sp] = a < b;
                    break;
                case BytecodeDefinition.INSTR_IEQ:
                    a = (Integer) operands[sp - 1];
                    b = (Integer) operands[sp];
                    sp -= 2;
                    operands[++sp] = a == b;
                    break;
                case BytecodeDefinition.INSTR_FADD:
                    e = (Float) operands[sp - 1];
                    f = (Float) operands[sp];
                    sp -= 2;
                    operands[++sp] = e + f;
                    break;
                case BytecodeDefinition.INSTR_FSUB:
                    e = (Float) operands[sp - 1];
                    f = (Float) operands[sp];
                    sp -= 2;
                    operands[++sp] = e - f;
                    break;
                case BytecodeDefinition.INSTR_FMUL:
                    e = (Float) operands[sp - 1];
                    f = (Float) operands[sp];
                    sp -= 2;
                    operands[++sp] = e * f;
                    break;
                case BytecodeDefinition.INSTR_FLT:
                    e = (Float) operands[sp - 1];
                    f = (Float) operands[sp];
                    sp -= 2;
                    operands[++sp] = e < f;
                    break;
                case BytecodeDefinition.INSTR_FEQ:
                    e = (Float) operands[sp - 1];
                    f = (Float) operands[sp];
                    sp -= 2;
                    operands[++sp] = e == f;
                    break;
                case BytecodeDefinition.INSTR_ITOF:
                    a = (Integer) operands[sp--];
                    operands[++sp] = (float) a;
                    break;
                case BytecodeDefinition.INSTR_CALL:
                    int funcIndexInConstPool = getIntOperand();
                    call(funcIndexInConstPool);
                    break;
                case BytecodeDefinition.INSTR_RET:  // result is on op stack
                    StackFrame fr = calls[fp--];    // pop stack frame
                    ip = fr.returnAddress;          // branch to ret addr
                    break;
                case BytecodeDefinition.INSTR_BR:
                    ip = getIntOperand();
                    break;
                case BytecodeDefinition.INSTR_BRT:
                    addr = getIntOperand();
                    if (operands[sp--].equals(true)) ip = addr;
                    break;
                case BytecodeDefinition.INSTR_BRF:
                    addr = getIntOperand();
                    if (operands[sp--].equals(false)) ip = addr;
                    break;
                case BytecodeDefinition.INSTR_CCONST:
                    operands[++sp] = (char) getIntOperand(); // push operand
                    break;
                case BytecodeDefinition.INSTR_ICONST:
                    operands[++sp] = getIntOperand();
                    break;
                case BytecodeDefinition.INSTR_FCONST:
                case BytecodeDefinition.INSTR_SCONST:
                    int constPoolIndex = getIntOperand();
                    operands[++sp] = constPool[constPoolIndex];
                    break;
                case BytecodeDefinition.INSTR_LOAD:  // load from call stack
                    addr = getIntOperand();
                    operands[++sp] = calls[fp].locals[addr];
                    break;
                case BytecodeDefinition.INSTR_GLOAD: // load from global memory
                    addr = getIntOperand();
                    operands[++sp] = globals[addr];
                    break;
                case BytecodeDefinition.INSTR_FLOAD: // load from struct field
                    StructSpace struct = (StructSpace) operands[sp--];
                    int fieldOffset = getIntOperand();
                    operands[++sp] = struct.fields[fieldOffset];
                    break;
                case BytecodeDefinition.INSTR_STORE:
                    addr = getIntOperand();
                    calls[fp].locals[addr] = operands[sp--];
                    break;
                case BytecodeDefinition.INSTR_GSTORE:
                    addr = getIntOperand();
                    globals[addr] = operands[sp--];
                    break;
                case BytecodeDefinition.INSTR_FSTORE:
                    struct = (StructSpace) operands[sp--];
                    v = operands[sp--];
                    fieldOffset = getIntOperand();
                    struct.fields[fieldOffset] = v;
                    break;
                case BytecodeDefinition.INSTR_PRINT:
                    System.out.println(operands[sp--]);
                    break;
                case BytecodeDefinition.INSTR_STRUCT:
                    int numFields = getIntOperand();
                    operands[++sp] = new StructSpace(numFields);
                    break;
                case BytecodeDefinition.INSTR_NULL:
                    operands[++sp] = null;
                    break;
                case BytecodeDefinition.INSTR_POP:
                    --sp;
                    break;
                default:
                    throw new Error("invalid opcode: " + opcode + " at ip=" + (ip - 1));
            }
            opcode = code[ip];
        }
    }

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

    protected void call(int functionConstPoolIndex) {
        FunctionSymbol funSym = (FunctionSymbol) constPool[functionConstPoolIndex];
        StackFrame frame = new StackFrame(funSym, ip);

        calls[++fp] = frame; // push new stack frame for parameters and locals

        // move args from operand stack to top frame on call stack
        for (int a = funSym.getNumArgs() - 1; a >= 0; a--) {
            frame.locals[a] = operands[sp--];
        }

        ip = funSym.getAddress(); // branch to function
    }

    protected void disassemble() { disasm.disassemble(); }

    protected void trace() {
        disasm.disassembleInstruction(ip);

        System.out.print("\tstack=[");

        for (int i = 0; i <= sp; i++) {
            Object o = operands[i];
            System.out.print(" " + o);
        }

        System.out.print(" ]");

        if (fp >= 0) {
            System.out.print(", calls=[");

            for (int i = 0; i <= fp; i++) {
                System.out.print(" " + calls[i].sym.getName());
            }

            System.out.print(" ]");
        }

        System.out.println();
    }

    protected void coredump() {
        if (constPool.length > 0)
            dumpConstantPool();

        if (globals.length > 0)
            dumpDataMemory();

        dumpCodeMemory();
    }

    protected void dumpConstantPool() {
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

    protected void dumpDataMemory() {
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

    public void dumpCodeMemory() {
        System.out.println("Code memory:");

        for (int i = 0; code != null && i < codeSize; i++) {
            if (i % 8 == 0 && i != 0) System.out.println();
            if (i % 8 == 0) System.out.printf("%04d:", i);
            System.out.printf(" %3d", ((int) code[i]));
        }

        System.out.println();
    }
}
