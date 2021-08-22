/*
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
*/
package org.jcm.reg;

import org.jcm.asm.FunctionSymbol;
import org.jcm.asm.InterpreterBase;
import org.jcm.asm.StructSpace;

/* A simple register-based interpreter */
public class RegInterpreter extends InterpreterBase {

    StackFrame[] calls = new StackFrame[DEFAULT_CALL_STACK_SIZE];

    public static void main(String[] args) throws Exception {
        RegInterpreter ri = new RegInterpreter();
        BytecodeDefinition bcDef = new BytecodeDefinition();
        ri.run(args, ri, bcDef);
    }

    @Override
    protected void exec() {
        // simulate "call main()" - set up stack and start at addr 0
        if (mainFunction == null)
            mainFunction = new FunctionSymbol("main", 0, 0, 0);

        StackFrame f = new StackFrame(mainFunction, ip);
        calls[++fp] = f;
        ip = mainFunction.getAddress();
        cpu();
    }

    /* Interpreter's fetch-decode-execute cycle */
    private void cpu() {
        int i, j, k, addr, fieldIndex;
        short opcode = code[ip];

        while (opcode != BytecodeDefinition.INSTR_HALT && ip < codeSize) {
            if (trace)
                trace();

            ip++; //jump to next instruction or first byte of operand

            Object[] r = calls[fp].registers; // shortcut to current registers

            switch (opcode) {
                case BytecodeDefinition.INSTR_IADD:
                    i = getRegOperand();
                    j = getRegOperand();
                    k = getRegOperand();
                    r[k] = ((Integer) r[i]) + ((Integer) r[j]);
                    break;
                // ...
                case BytecodeDefinition.INSTR_ISUB:
                    i = getRegOperand();
                    j = getRegOperand();
                    k = getRegOperand();
                    r[k] = ((Integer) r[i]) - ((Integer) r[j]);
                    break;
                case BytecodeDefinition.INSTR_IMUL:
                    i = getRegOperand();
                    j = getRegOperand();
                    k = getRegOperand();
                    r[k] = ((Integer) r[i]) * ((Integer) r[j]);
                    break;
                case BytecodeDefinition.INSTR_ILT:
                    i = getRegOperand();
                    j = getRegOperand();
                    k = getRegOperand();
                    r[k] = (Integer) r[i] < (Integer) r[j];
                    break;
                case BytecodeDefinition.INSTR_IEQ:
                    i = getRegOperand();
                    j = getRegOperand();
                    k = getRegOperand();
                    r[k] = ((Integer) r[i]).intValue() == ((Integer) r[j]).intValue();
                    break;
                case BytecodeDefinition.INSTR_FADD:
                    i = getRegOperand();
                    j = getRegOperand();
                    k = getRegOperand();
                    r[k] = ((Float) r[i]) + ((Float) r[j]);
                    break;
                case BytecodeDefinition.INSTR_FSUB:
                    i = getRegOperand();
                    j = getRegOperand();
                    k = getRegOperand();
                    r[k] = ((Float) r[i]) - ((Float) r[j]);
                    break;
                case BytecodeDefinition.INSTR_FMUL:
                    i = getRegOperand();
                    j = getRegOperand();
                    k = getRegOperand();
                    r[k] = ((Float) r[i]) * ((Float) r[j]);
                    break;
                case BytecodeDefinition.INSTR_FLT:
                    i = getRegOperand();
                    j = getRegOperand();
                    k = getRegOperand();
                    r[k] = (Float) r[i] < (Float) r[j];
                    break;
                case BytecodeDefinition.INSTR_FEQ:
                    i = getRegOperand();
                    j = getRegOperand();
                    k = getRegOperand();
                    r[k] = ((Float) r[i]).intValue() == ((Float) r[j]).intValue();
                    break;
                case BytecodeDefinition.INSTR_ITOF:
                    i = getRegOperand();
                    j = getRegOperand();
                    r[j] = (float) ((Integer) r[i]);
                    break;
                case BytecodeDefinition.INSTR_CALL:
                    int funcStringIndex = getIntOperand();
                    int baseRegisterIndex = getRegOperand();
                    call(funcStringIndex, baseRegisterIndex);
                    break;
                case BytecodeDefinition.INSTR_RET:
                    StackFrame f = calls[fp--]; // pop stack frame
                    calls[fp].registers[0] = f.registers[0];
                    ip = f.returnAddress;
                    break;
                case BytecodeDefinition.INSTR_BR:
                    ip = getIntOperand();
                    break;
                case BytecodeDefinition.INSTR_BRT:
                    i = getRegOperand();
                    addr = getIntOperand();
                    Boolean bv = (Boolean) r[i];
                    if (bv) ip = addr;
                    break;
                case BytecodeDefinition.INSTR_BRF:
                    i = getRegOperand();
                    addr = getIntOperand();
                    Boolean bv2 = (Boolean) r[i];
                    if (!bv2) ip = addr;
                    break;
                case BytecodeDefinition.INSTR_CCONST:
                    i = getRegOperand();
                    r[i] = (char) getIntOperand();
                    break;
                case BytecodeDefinition.INSTR_ICONST:
                    i = getRegOperand();
                    r[i] = getIntOperand();
                    break;
                case BytecodeDefinition.INSTR_FCONST:
                case BytecodeDefinition.INSTR_SCONST:
                    i = getRegOperand();
                    int constIndex = getIntOperand();
                    r[i] = constPool[constIndex];
                    break;
                case BytecodeDefinition.INSTR_GLOAD:
                    i = getRegOperand();
                    addr = getIntOperand();
                    r[i] = globals[addr];
                    break;
                case BytecodeDefinition.INSTR_GSTORE:
                    i = getRegOperand();
                    addr = getIntOperand();
                    globals[addr] = r[i];
                    break;
                case BytecodeDefinition.INSTR_FLOAD:
                    i = getRegOperand();
                    j = getRegOperand();
                    fieldIndex = getRegOperand();
                    r[i] = ((StructSpace) r[j]).getFields()[fieldIndex];
                    break;
                case BytecodeDefinition.INSTR_FSTORE:
                    i = getRegOperand();
                    j = getRegOperand();
                    fieldIndex = getRegOperand();
                    ((StructSpace) r[j]).getFields()[fieldIndex] = r[i];
                    break;
                case BytecodeDefinition.INSTR_MOVE:
                    i = getRegOperand();
                    j = getRegOperand();
                    r[j] = r[i];
                    break;
                case BytecodeDefinition.INSTR_PRINT:
                    i = getRegOperand();
                    System.out.println(r[i]);
                    break;
                case BytecodeDefinition.INSTR_STRUCT:
                    i = getRegOperand();
                    int nfields = getIntOperand();
                    r[i] = new StructSpace(nfields);
                    break;
                case BytecodeDefinition.INSTR_NULL:
                    i = getRegOperand();
                    r[i] = null;
                    break;
                default:
                    throw new Error("invalid opcode: " + opcode + " at ip=" + (ip - 1));
            }
            opcode = code[ip];
        }
    }

    private void call(int functionConstPoolIndex, int baseRegisterIndex) {
        FunctionSymbol fs = (FunctionSymbol) constPool[functionConstPoolIndex];
        StackFrame f = new StackFrame(fs, ip);
        StackFrame callingFrame = calls[fp];
        calls[++fp] = f;

        // move args, leaving room for r0
        if (fs.getNumArgs() >= 0)
            System.arraycopy(callingFrame.registers, baseRegisterIndex, f.registers, 1, fs.getNumArgs());

        ip = fs.getAddress(); // branch to function
    }

    private int getRegOperand() { return getIntOperand(); }

    private void trace() {
        disasm.disassembleInstruction(ip);
        Object[] r = calls[fp].registers;

        if (r.length > 0) {
            System.out.print("\t" + calls[fp].sym.getName() + ".registers=[");

            for (int i = 0; i < r.length; i++) {
                if (i == 1)
                    System.out.print(" |");

                if (i == calls[fp].sym.getNumArgs() + 1 && i == 1)
                    System.out.print("|");
                else if (i == calls[fp].sym.getNumArgs() + 1)
                    System.out.print(" |");

                System.out.print(" ");

                if (r[i] == null)
                    System.out.print("?");
                else
                    System.out.print(r[i]);
            }

            System.out.print(" ]");
        }

        if (fp >= 0) {
            System.out.print("  calls=[");

            for (int i = 0; i <= fp; i++) {
                System.out.print(" " + calls[i].sym.getName());
            }

            System.out.print(" ]");
        }

        System.out.println();
    }
}
