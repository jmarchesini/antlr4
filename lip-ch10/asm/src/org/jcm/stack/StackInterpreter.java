/*
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
 */
package org.jcm.stack;

import org.jcm.asm.FunctionSymbol;
import org.jcm.asm.InterpreterBase;
import org.jcm.asm.StructSpace;

/**
 * A simple stack-based interpreter - pattern 27
 */
public class StackInterpreter extends InterpreterBase {

    StackFrame[] calls = new StackFrame[DEFAULT_CALL_STACK_SIZE];

    public static void main(String[] args) throws Exception {
        StackInterpreter si = new StackInterpreter();
        BytecodeDefinition bcDef = new BytecodeDefinition();
        si.run(args, si, bcDef);
    }

    @Override
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
    private void cpu() {
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
                    operands[++sp] = struct.getFields()[fieldOffset];
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
                    struct.getFields()[fieldOffset] = v;
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

    private void call(int functionConstPoolIndex) {
        FunctionSymbol funSym = (FunctionSymbol) constPool[functionConstPoolIndex];
        StackFrame frame = new StackFrame(funSym, ip);

        calls[++fp] = frame; // push new stack frame for parameters and locals

        // move args from operand stack to top frame on call stack
        for (int a = funSym.getNumArgs() - 1; a >= 0; a--) {
            frame.locals[a] = operands[sp--];
        }

        ip = funSym.getAddress(); // branch to function
    }

    private void trace() {
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
}
