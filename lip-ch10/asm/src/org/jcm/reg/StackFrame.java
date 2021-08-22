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

public class StackFrame {
    FunctionSymbol sym; // associated with which function?
    int returnAddress;  // the instruction following the call
    Object[] registers; // registers

    public StackFrame(FunctionSymbol sym, int returnAddress) {
        this.sym = sym;
        this.returnAddress = returnAddress;
        // allocate space for registers; 1 extra for r0 reserved reg
        registers = new Object[sym.getNumArgs() + sym.getNumLocals() + 1];
    }
}
