package org.jcm.asm;

import org.jcm.asm.gen.AssemblerParser;

public abstract class BytecodeDefBase {
    public static final int REG = AssemblerParser.REG;
    public static final int FUNC = AssemblerParser.FUNC;
    public static final int INT = AssemblerParser.INT;
    public static final int POOL = 1000; // unique imaginary token

    public static class Instruction {
        String name; // E.g., "iadd", "call"
        int[] type = new int[3];
        int n;

        public Instruction(String name) { this(name,0,0,0); n=0; }
        public Instruction(String name, int a) { this(name,a,0,0); n=1; }
        public Instruction(String name, int a, int b) { this(name,a,b,0); n=2; }
        public Instruction(String name, int a, int b, int c) {
            this.name = name;
            type[0] = a;
            type[1] = b;
            type[2] = c;
            n = 3;
        }
    }

    public abstract Instruction[] getInstructions();
}
