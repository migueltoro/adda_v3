// Generated from Tree.g4 by ANTLR 4.4
package us.lsi.tiposrecursivos.parsers;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TreeParser}.
 */
public interface TreeListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code intLabel}
	 * labeled alternative in {@link TreeParser#label}.
	 * @param ctx the parse tree
	 */
	void enterIntLabel(@NotNull TreeParser.IntLabelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intLabel}
	 * labeled alternative in {@link TreeParser#label}.
	 * @param ctx the parse tree
	 */
	void exitIntLabel(@NotNull TreeParser.IntLabelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code labelTree}
	 * labeled alternative in {@link TreeParser#nary_tree}.
	 * @param ctx the parse tree
	 */
	void enterLabelTree(@NotNull TreeParser.LabelTreeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code labelTree}
	 * labeled alternative in {@link TreeParser#nary_tree}.
	 * @param ctx the parse tree
	 */
	void exitLabelTree(@NotNull TreeParser.LabelTreeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idLabel}
	 * labeled alternative in {@link TreeParser#label}.
	 * @param ctx the parse tree
	 */
	void enterIdLabel(@NotNull TreeParser.IdLabelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idLabel}
	 * labeled alternative in {@link TreeParser#label}.
	 * @param ctx the parse tree
	 */
	void exitIdLabel(@NotNull TreeParser.IdLabelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code emptyTree}
	 * labeled alternative in {@link TreeParser#nary_tree}.
	 * @param ctx the parse tree
	 */
	void enterEmptyTree(@NotNull TreeParser.EmptyTreeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code emptyTree}
	 * labeled alternative in {@link TreeParser#nary_tree}.
	 * @param ctx the parse tree
	 */
	void exitEmptyTree(@NotNull TreeParser.EmptyTreeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code doubleLabel}
	 * labeled alternative in {@link TreeParser#label}.
	 * @param ctx the parse tree
	 */
	void enterDoubleLabel(@NotNull TreeParser.DoubleLabelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code doubleLabel}
	 * labeled alternative in {@link TreeParser#label}.
	 * @param ctx the parse tree
	 */
	void exitDoubleLabel(@NotNull TreeParser.DoubleLabelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code naryTree}
	 * labeled alternative in {@link TreeParser#nary_tree}.
	 * @param ctx the parse tree
	 */
	void enterNaryTree(@NotNull TreeParser.NaryTreeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code naryTree}
	 * labeled alternative in {@link TreeParser#nary_tree}.
	 * @param ctx the parse tree
	 */
	void exitNaryTree(@NotNull TreeParser.NaryTreeContext ctx);
}