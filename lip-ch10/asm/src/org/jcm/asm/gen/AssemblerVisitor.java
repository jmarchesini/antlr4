// Generated from /home/jmarchesini/dev/src/github/antlr4/lip-ch10/asm/src/org/jcm/asm/Assembler.g4 by ANTLR 4.9.1
package org.jcm.asm.gen;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AssemblerParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AssemblerVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link AssemblerParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(AssemblerParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssemblerParser#globals}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobals(AssemblerParser.GlobalsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssemblerParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(AssemblerParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssemblerParser#instr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstr(AssemblerParser.InstrContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssemblerParser#operand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperand(AssemblerParser.OperandContext ctx);
	/**
	 * Visit a parse tree produced by {@link AssemblerParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel(AssemblerParser.LabelContext ctx);
}