/*
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
 */
package org.jcm.asm;

public class BytecodeDefinition extends BytecodeDefBase {
    public static final int INSTR_ADD = 1;

    @Override
    public Instruction[] getInstructions() {
        return new Instruction[] {
            null, // <INVALID>
            new Instruction("iadd", REG, REG, REG), // index is the opcode
        };
    }
}
