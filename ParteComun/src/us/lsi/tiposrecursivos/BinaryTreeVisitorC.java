package us.lsi.tiposrecursivos;


import us.lsi.tiposrecursivos.parsers.BinaryTreeBaseVisitor;
import us.lsi.tiposrecursivos.parsers.BinaryTreeParser;

public class BinaryTreeVisitorC extends BinaryTreeBaseVisitor<Object> {
		
	@Override public Object visitEmptyTree(BinaryTreeParser.EmptyTreeContext ctx) { 
		return BinaryTree.empty(); 
	}
		
	@Override public Object visitLabelTree(BinaryTreeParser.LabelTreeContext ctx) { 
		Object label = visit(ctx.label());
		return BinaryTree.leaf(label); 
	}
		
	@SuppressWarnings("unchecked")
	@Override public Object visitBinaryTree(BinaryTreeParser.BinaryTreeContext ctx) { 
		String label = (String) visit(ctx.label());	
		BinaryTree<String> left = (BinaryTree<String>) visit(ctx.left);
		BinaryTree<String> right = (BinaryTree<String>) visit(ctx.right);
		BinaryTree<String> tree = BinaryTree.binary(label,left,right);
		return tree;
	}
		
	@Override public Object visitIntLabel(BinaryTreeParser.IntLabelContext ctx) { 
		return ctx.getText(); 
	}
		
	@Override public Object visitDoubleLabel(BinaryTreeParser.DoubleLabelContext ctx) { 
		return ctx.getText(); 
	}
		
	@Override public Object visitIdLabel(BinaryTreeParser.IdLabelContext ctx) { 
		return ctx.getText();
	}

}
