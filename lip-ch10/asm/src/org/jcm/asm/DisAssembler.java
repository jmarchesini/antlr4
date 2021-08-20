/*
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
 */
package org.jcm.asm;

import java.util.List;
import java.util.ArrayList;

public class DisAssembler {
    byte[] code;
    int codeSize;
    protected Object[] constPool;
    BytecodeDefBase bcDef;

    public DisAssembler(byte[] code,
                        int codeSize,
                        Object[] constPool,
                        BytecodeDefBase bcDef)
    {
        this.code = code;
        this.codeSize = codeSize;
        this.constPool = constPool;
        this.bcDef = bcDef;
    }

    public void disassemble() {
        System.out.println("Disassembly:");
        int i=0;
        while (i<codeSize) {
            i = disassembleInstruction(i);
            System.out.println();
        }
        System.out.println();
    }

    public int disassembleInstruction(int ip)
    {
        int opcode = code[ip];
        BytecodeDefinition.Instruction I =
            bcDef.getInstructions()[opcode];
        String instrName = I.name;
        System.out.printf("%04d:\t%-11s", ip, instrName);
        ip++;
        if ( I.n==0 ) {
            System.out.print("  ");
            return ip;
        }
        List<String> operands = new ArrayList<>();
        for (int i=0; i<I.n; i++) {
            int opnd = BytecodeAssembler.getInt(code, ip);
            ip += 4;
            switch ( I.type[i] ) {
                case BytecodeDefinition.REG :
                    operands.add("r"+opnd);
                    break;
                case BytecodeDefinition.FUNC :
                case BytecodeDefinition.POOL :
                    operands.add(showConstPoolOperand(opnd));
                    break;
                case BytecodeDefinition.INT :
                    operands.add(String.valueOf(opnd));
                    break;
            }
        }
        for (int i = 0; i < operands.size(); i++) {
            String s = operands.get(i);
            if ( i>0 ) System.out.print(", ");
            System.out.print(s);
        }
        return ip;
    }

    private String showConstPoolOperand(int poolIndex) {
        StringBuilder buf = new StringBuilder();
        buf.append("#");
        buf.append(poolIndex);
        String s = constPool[poolIndex].toString();
        if ( constPool[poolIndex] instanceof String ) s='"'+s+'"';
        else if ( constPool[poolIndex] instanceof FunctionSymbol ) {
            FunctionSymbol fs = (FunctionSymbol)constPool[poolIndex];
            s= fs.name+"()@"+fs.address;
        }
        buf.append(":");
        buf.append(s);
        return buf.toString();
    }
}
