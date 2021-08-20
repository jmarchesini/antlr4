/*
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
 */
package org.jcm.asm;

public class FunctionSymbol {
    String name;
    int numArgs;
    int numLocals;
    int address;

    public FunctionSymbol(String name) { this.name = name; }

    public FunctionSymbol(String name, int numArgs, int numLocals, int address) {
        this.name = name;
        this.numArgs = numArgs;
        this.numLocals = numLocals;
        this.address = address;
    }

    @Override
    public int hashCode() { return name.hashCode(); }

    @Override
    public boolean equals(Object o) {
        return o instanceof FunctionSymbol && name.equals(((FunctionSymbol)o).name);
    }

    @Override
    public String toString() {
        return "FunctionSymbol{" +
               "name='" + name + '\'' +
               ", args=" + numArgs +
               ", locals=" + numLocals +
               ", address=" + address +
               '}';
    }

    public String getName() { return name; }

    public int getNumArgs() { return numArgs; }

    public int getNumLocals() { return numLocals; }

    public int getAddress() { return address; }
}
