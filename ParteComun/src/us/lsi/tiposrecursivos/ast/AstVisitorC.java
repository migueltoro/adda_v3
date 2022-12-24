package us.lsi.tiposrecursivos.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Preconditions;
import us.lsi.tiposrecursivos.parsers.ProgramBaseVisitor;
import us.lsi.tiposrecursivos.parsers.ProgramParser;


public class AstVisitorC extends ProgramBaseVisitor<Object> {
	
	public static Map<String,Object> symbolTable = new HashMap<>();

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override 
	public Ast visitProgram(ProgramParser.ProgramContext ctx) { 
		Integer n = ctx.declaration().size();
		IntStream.range(0,n).boxed()
				.map(i->(Declaration)visit(ctx.declaration(i)))
				.collect(Collectors.toList());
		Block block = (Block) visit(ctx.block());
		block = Block.of(block.sentences(),symbolTable);
		return Ast.of(block);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override
	public Declaration visitFunDeclarationSP(ProgramParser.FunDeclarationSPContext ctx) { 
		String id = ctx.id.getText();
		Type type = Type.valueOf(ctx.type.getText());
		FunDeclaration d = FunDeclaration.of(id,type,List.of());
		Preconditions.checkState(!AstVisitorC.symbolTable.containsKey(id),
				String.format("La variable %s ya ha sido declarada",id));
		AstVisitorC.symbolTable.put(id,d);
		return d;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Declaration visitFunDeclaration(ProgramParser.FunDeclarationContext ctx) { 
		String id = ctx.id.getText();
		Type type = Type.valueOf(ctx.type.getText());
		List<ParamDeclaration> parameters = new ArrayList<>();
		if(ctx.formal_parameters() != null) parameters = (List<ParamDeclaration>) visit(ctx.formal_parameters());
		FunDeclaration d = FunDeclaration.of(id,type,parameters);	
		Preconditions.checkState(!AstVisitorC.symbolTable.containsKey(id),
				String.format("La variable %s ya ha sido declarada",id));
		AstVisitorC.symbolTable.put(id,d);
		return d;
	}
	
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Declaration visitVarDeclaration(ProgramParser.VarDeclarationContext ctx) { 
		String id = ctx.id.getText();
		Type type = Type.valueOf(ctx.type.getText());
		Object value = null;
		if(ctx.exp() != null) value = ((Exp)visit(ctx.exp())).value();
		Var r = Var.of(id,type,value);
		Preconditions.checkState(!AstVisitorC.symbolTable.containsKey(id),
				String.format("La variable %s ya ha sido declarada",id));
		AstVisitorC.symbolTable.put(id,r);
		return Var.of(r.name(), r.type(), r.value());
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override 
	public List<ParamDeclaration> visitFormal_parameters(ProgramParser.Formal_parametersContext ctx) { 
		Integer n = ctx.formal_parameter().size();
		List<ParamDeclaration> r = IntStream.range(0, n).boxed()
				.map(i->(ParamDeclaration)visit(ctx.formal_parameter(i)))
				.collect(Collectors.toList());
		return r;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public ParamDeclaration visitFormal_parameter(ProgramParser.Formal_parameterContext ctx) { 
		String id = ctx.id.getText();
		Type type = Type.valueOf(ctx.type.getText());
		return ParamDeclaration.of(id, type);
    }
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	
	@Override 
	public Sentence visitAsignSentence(ProgramParser.AsignSentenceContext ctx) { 
		String idText =ctx.id.getText();
		Preconditions.checkState(AstVisitorC.symbolTable.containsKey(idText), 
				String.format("La variable %s no ha sido declarada",idText));
		Var id = (Var)AstVisitorC.symbolTable.get(idText);
		Exp exp = (Exp) visit(ctx.exp());
		return Assign.of(id,exp); 
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Sentence visitIfSentence(ProgramParser.IfSentenceContext ctx) { 
		Exp guard = (Exp) visit(ctx.exp());
		Block trueBlock = (Block) visit(ctx.trueBlock);
		Block elseBlock = null;
		if(ctx.elseBlock != null) elseBlock = (Block) visit(ctx.elseBlock);
		return IfThenElse.of(guard,trueBlock,elseBlock);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override 
	public Sentence visitWhileSentence(ProgramParser.WhileSentenceContext ctx) { 
		Exp guard = (Exp) visit(ctx.exp());
		Block block = (Block) visit(ctx.block());
		return While.of(guard,block); 
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override 
	public Block visitBlock(ProgramParser.BlockContext ctx) { 
		Integer n = ctx.sentence().size();
		List<Sentence> sentences = IntStream.range(0,n).boxed()
				.map(i->(Sentence)visit(ctx.sentence(i)))
				.collect(Collectors.toList());
		return Block.of(sentences,Map.of());
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override 
	public Exp visitUnaryExpr(ProgramParser.UnaryExprContext ctx) { 
		String op = ctx.op.getText();
		Exp operand = (Exp) visit(ctx.right);
		return Unary.of(operand,op); 
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Exp visitIntExpr(ProgramParser.IntExprContext ctx) { 
		return Const.of(Integer.parseInt(ctx.getText()),Type.Int); 
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Exp visitBinaryExpr(ProgramParser.BinaryExprContext ctx) { 
		String op = ctx.op.getText();
		Exp left = (Exp) visit(ctx.left);
		Exp right = (Exp) visit(ctx.right);
		return Binary.of(left, right,op);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@SuppressWarnings("unchecked")
	@Override public Exp visitCallExpr(ProgramParser.CallExprContext ctx) { 
		String id = ctx.ID().getText();
		List<Exp> parameters = new ArrayList<>();
		if(ctx.real_parameters() != null) parameters = (List<Exp>) visit(ctx.real_parameters());
		Preconditions.checkState(AstVisitorC.symbolTable.containsKey(id), 
				String.format("La función %s no ha sido declarada",id));
		FunDeclaration d = (FunDeclaration)AstVisitorC.symbolTable.get(id);
		return CallFunction.of(id,parameters,d);
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override 
	public Exp visitParenExpr(ProgramParser.ParenExprContext ctx) { 
		return (Exp) visit(ctx.exp());
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public Exp visitDoubleExp(ProgramParser.DoubleExpContext ctx) { 
		return Const.of(Double.parseDouble(ctx.getText()),Type.Double); 
	}
	
	@Override public Exp visitStrExpr(ProgramParser.StrExprContext ctx) { 
		String text = ctx.getText();
		text = text.substring(1,text.length()-1);
		return Const.of(text,Type.String); 
	}
	
	
	@Override public Exp visitBoolExpr(ProgramParser.BoolExprContext ctx) { 
		return Const.of(ctx.getText().equals("true")?true:false,Type.Boolean); 
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override 
	public Exp visitIdExpr(ProgramParser.IdExprContext ctx) { 
		String idText = ctx.id.getText();
		Preconditions.checkState(AstVisitorC.symbolTable.containsKey(idText), 
				String.format("La variable %s no ha sido declarada",idText));
		Var id = (Var)AstVisitorC.symbolTable.get(idText);
		return id;
	}
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public List<Exp> visitReal_parameters(ProgramParser.Real_parametersContext ctx) { 
		Integer n = ctx.exp().size();
		List<Exp> parameters = IntStream.range(0,n).boxed()
				.map(i->(Exp)visit(ctx.exp(i)))
				.collect(Collectors.toList());
		return parameters;
	}
}
