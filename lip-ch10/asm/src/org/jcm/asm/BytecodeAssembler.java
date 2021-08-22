/*
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
 */
package org.jcm.asm;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.jcm.asm.gen.AssemblerParser;

import java.util.*;

/*
 * Subclass the AssemblerParser to actually implement the necessary
 *  symbol table management and code generation functions.
 *
 * Assembler run should yield the following:
 *
 *  1. dataSize: the global data space size
 *  2. code: code memory should be populated with bytecode + operands
 *  3. mainFunction: code memory address to start execution (0 or @main)
 *  4. constPool: non-integer operands not in code memory
 */
public class BytecodeAssembler extends AssemblerParser {
    public static final int INITIAL_CODE_SIZE = 1024;

    protected int dataSize; // Set via .globals
    protected byte[] code = new byte[INITIAL_CODE_SIZE];
    protected FunctionSymbol mainFunction;
    protected List<Object> constPool = new ArrayList<>();
    protected int ip = 0;
    protected Map<String, Integer> instructionOpcodeMapping = new HashMap<>();
    protected Map<String, LabelSymbol> labels = new HashMap<>(); // Label sym table

    public BytecodeAssembler(
        TokenStream lexer,
        BytecodeDefBase.Instruction[] instructions
    ) {
        super(lexer);

        for (int i = 1; i < instructions.length; i++) {
            instructionOpcodeMapping.put(instructions[i].name.toLowerCase(), i);
        }
    }

    public int getDataSize() { return dataSize; }
    public byte[] getMachineCode() { return code; }
    public int getCodeMemorySize() { return ip; }
    public Object[] getConstantPool() { return constPool.toArray(); }
    public FunctionSymbol getMainFunction() { return mainFunction; }

    public static int getInt(byte[] memory, int index) {
        int b1 = memory[index++] & 0xFF; // mask off sign-extended bits
        int b2 = memory[index++] & 0xFF;
        int b3 = memory[index++] & 0xFF;
        int b4 = memory[index] & 0xFF;
        return b1 << (8 * 3) | b2 << (8 * 2) | b3 << (8) | b4;
    }

    public static void writeInt(byte[] bytes, int index, int value) {
        bytes[index] = (byte) ((value >> (8 * 3)) & 0xFF);
        bytes[index + 1] = (byte) ((value >> (8 * 2)) & 0xFF);
        bytes[index + 2] = (byte) ((value >> (8)) & 0xFF);
        bytes[index + 3] = (byte) (value & 0xFF);
    }

    /**
     *  Grammar members used in syntax-directed translation.
     */
    protected void gen(Token instrToken) {
        String instrName = instrToken.getText();
        Integer opcodeI = instructionOpcodeMapping.get(instrName);

        if (opcodeI == null) {
            System.err.println("line " + instrToken.getLine() +
                ": Unknown instruction: " + instrName);

            return;
        }

        int opcode = opcodeI;
        ensureCapacity(ip + 1);
        code[ip++] = (byte) (opcode & 0xFF);
    }

    protected void gen(Token instrToken, Token operandToken) {
        gen(instrToken);
        genOperand(operandToken);
    }

    protected void gen(Token instrToken, Token oToken1, Token oToken2) {
        gen(instrToken, oToken1);
        genOperand(oToken2);
    }

    protected void gen(Token instrToken, Token oToken1, Token oToken2, Token oToken3) {
        gen(instrToken, oToken1, oToken2);
        genOperand(oToken3);
    }

    protected void checkForUnresolvedReferences() {
        for (String name : labels.keySet()) {
            LabelSymbol sym = labels.get(name);

            if (!sym.isDefined)
                System.err.println("unresolved reference: " + name);
        }
    }

    protected void defineFunction(Token idToken, int args, int locals) {
        String name = idToken.getText();
        FunctionSymbol f = new FunctionSymbol(name, args, locals, ip);

        if (name.equals("main"))
            mainFunction = f;

        if (constPool.contains(f)) {
            // f has been ref'd before def'd - backpatch
            constPool.set(constPool.indexOf(f), f);
        } else {
            // save f to the const pool
            getConstantPoolIndex(f);
        }
    }

    protected void defineDataSize(int n) { dataSize = n; }

    protected void defineLabel(Token idToken) {
        String id = idToken.getText();
        LabelSymbol sym = labels.get(id);

        if (sym == null) {
            // add sym to the label symbol table
            LabelSymbol labelSym = new LabelSymbol(id, ip, false);
            labels.put(id, labelSym);
        } else {
            if (sym.isForwardRef) {
                // we have found definition of forward
                sym.isDefined = true;
                sym.address = ip;
                sym.resolveForwardReferences(code);
            } else {
                // redefinition of symbol
                System.err.println("line " + idToken.getLine() +
                    ": redefinition of symbol " + id);
            }
        }
    }

    /**
     *  Helpers for the grammar members.
     */
    protected void ensureCapacity(int index) {
        if (index >= code.length) {
            int newSize = index * 2;
            byte[] bigger = new byte[newSize];
            System.arraycopy(code, 0, bigger, 0, code.length);
            code = bigger;
        }
    }

    protected void genOperand(Token operandToken) {
        String text = operandToken.getText();
        int v = 0;

        switch (operandToken.getType()) {
            case INT:
                v = Integer.parseInt(text);
                break;
            case CHAR:
                v = text.charAt(1);
                break;
            case FLOAT:
                v = getConstantPoolIndex(Float.valueOf(text));
                break;
            case STRING:
                v = getConstantPoolIndex(text);
                break;
            case ID:
                v = getLabelAddress(text);
                break;
            case FUNC:
                v = getFunctionIndex(text);
                break;
            case REG:
                v = getRegisterNumber(operandToken);
                break;
        }

        ensureCapacity(ip+4);  // expand code array if necessary
        writeInt(code, ip, v); // write operand to code byte array
        ip += 4;               // we've written four bytes
    }

    protected int getConstantPoolIndex(Object o) {
        if (constPool.contains(o))
            return constPool.indexOf(o);

        constPool.add(o);

        return constPool.size()-1;
    }

    protected int getLabelAddress(String id) {
        LabelSymbol sym = labels.get(id);

        if (sym == null) {
            // assume it's a forward code reference; record operand address
            sym = new LabelSymbol(id, ip, true);
            sym.isDefined = false;
            labels.put(id, sym); // add to symbol table
        } else {
            if (sym.isForwardRef) {
                // address is unknown - add to forward ref list &
                //  record where in code memory we should patch later
                sym.addForwardReference(ip);
            } else {
                return sym.address;
            }
        }

        return 0; // we don't know the real address yet
    }

    protected int getFunctionIndex(String id) {
        int i = constPool.indexOf(new FunctionSymbol(id));

        if (i >= 0)
            return i;

        // Must be a forward function reference.
        //  Create the constant pool entry - fill in later
        return getConstantPoolIndex(new FunctionSymbol(id));
    }

    protected int getRegisterNumber(Token regToken) {
        // convert "rN" -> N
        String rs = regToken.getText();
        rs = rs.substring(1);

        return Integer.parseInt(rs);
    }
}
