// Generated from Tree.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link TreeParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface TreeVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code emptyTree}
	 * labeled alternative in {@link TreeParser#nary_tree}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyTree(TreeParser.EmptyTreeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code labelTree}
	 * labeled alternative in {@link TreeParser#nary_tree}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelTree(TreeParser.LabelTreeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code naryTree}
	 * labeled alternative in {@link TreeParser#nary_tree}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNaryTree(TreeParser.NaryTreeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intLabel}
	 * labeled alternative in {@link TreeParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLabel(TreeParser.IntLabelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code doubleLabel}
	 * labeled alternative in {@link TreeParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoubleLabel(TreeParser.DoubleLabelContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idLabel}
	 * labeled alternative in {@link TreeParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdLabel(TreeParser.IdLabelContext ctx);
}