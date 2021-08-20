package org.jcm.asm;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.jcm.asm.gen.AssemblerLexer;

public class Assembler {

    public static void main(String[] args) throws Exception {
        String inputFile = null;

        if (args.length > 0)
            inputFile = args[0];

        CharStream input = CharStreams.fromFileName(inputFile);
        AssemblerLexer lexer = new AssemblerLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        BytecodeAssembler assembler = new BytecodeAssembler(tokens, BytecodeDefinition.instructions);
        assembler.program();
    }
}
