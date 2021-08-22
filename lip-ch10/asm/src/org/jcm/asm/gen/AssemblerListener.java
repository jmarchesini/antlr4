// Generated from /home/jmarchesini/dev/src/github/antlr4/lip-ch10/asm/src/org/jcm/asm/Assembler.g4 by ANTLR 4.9.1
package org.jcm.asm.gen;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link AssemblerParser}.
 */
public interface AssemblerListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link AssemblerParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(AssemblerParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblerParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(AssemblerParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssemblerParser#globals}.
	 * @param ctx the parse tree
	 */
	void enterGlobals(AssemblerParser.GlobalsContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblerParser#globals}.
	 * @param ctx the parse tree
	 */
	void exitGlobals(AssemblerParser.GlobalsContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssemblerParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(AssemblerParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblerParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(AssemblerParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssemblerParser#instr}.
	 * @param ctx the parse tree
	 */
	void enterInstr(AssemblerParser.InstrContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblerParser#instr}.
	 * @param ctx the parse tree
	 */
	void exitInstr(AssemblerParser.InstrContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssemblerParser#operand}.
	 * @param ctx the parse tree
	 */
	void enterOperand(AssemblerParser.OperandContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblerParser#operand}.
	 * @param ctx the parse tree
	 */
	void exitOperand(AssemblerParser.OperandContext ctx);
	/**
	 * Enter a parse tree produced by {@link AssemblerParser#label}.
	 * @param ctx the parse tree
	 */
	void enterLabel(AssemblerParser.LabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link AssemblerParser#label}.
	 * @param ctx the parse tree
	 */
	void exitLabel(AssemblerParser.LabelContext ctx);
}