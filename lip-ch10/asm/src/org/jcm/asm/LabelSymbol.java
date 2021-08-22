/*
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
 */
package org.jcm.asm;

import java.util.*;

public class LabelSymbol {
    String name;
    int address;
    boolean isForwardRef = false;
    boolean isDefined = true;
    Vector<Integer> forwardReferences = null;

    public LabelSymbol(String name) {
	    this.name = name;
    }

    public LabelSymbol(String name, int address) {
        this(name);
        this.address = address;
    }

    public LabelSymbol(String name, int address, boolean forward) {
        this(name);
        isForwardRef = forward;

        if (forward)
            addForwardReference(address); // if fwd ref, set address
        else
            this.address = address;
    }

    public void addForwardReference(int address) {
        if (forwardReferences == null)
            forwardReferences = new Vector<>();

        forwardReferences.addElement(address);
    }

    public void resolveForwardReferences(byte[] code) {
        isForwardRef = false;
        Vector<Integer> operandsToPatch = forwardReferences;

        for (int addrToPatch : operandsToPatch) {
            BytecodeAssembler.writeInt(code, addrToPatch, address);
        }
    }

    public String toString() {
        String refs = "";

        if (forwardReferences != null)
            refs = "[refs=" + forwardReferences + "]";

        return name + "@" + address + refs;
    }
}
