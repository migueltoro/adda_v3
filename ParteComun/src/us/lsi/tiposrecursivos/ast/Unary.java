package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

import us.lsi.common.Preconditions;
import us.lsi.common.Set2;

public record Unary(Exp operand, String name) implements Exp {

	public static Unary of(Exp operand, String op) {
		return new Unary(operand, op);
	}
	
	public Operator operator() {
		Type t1 = operand.type();
		OperatorId id = OperatorId.of1(name,t1);
		Operator op = Operators.operators.get(id);
		Preconditions.checkArgument(op != null,String.format("No existe el operador %s",id));
		return op;
	}
	
	public Object value() {
		Preconditions.checkNotNull(operand.value(),String.format("Valor nulo de %s",this.name()));
		Operator.Unary op = (Operator.Unary) this.operator();
		return op.function().apply(operand.value());
	}
	
	public Type type() {
		return this.operator().resultType();
	}
	

	@Override
	public String toString() {
		return String.format("%s(%s)", this.name, this.operand);
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		Integer n = Ast.getIndex(this,map,this.name(),file);
		Integer operand = Ast.getIndex(this.operand(),map,this.operand().name(),file);
		Ast.edge(n,operand, file);
		this.operand().toDot(file, map);
	}
	
	@Override
	public Set<Var> vars() {
		return Set2.of(this.operand.vars());
	}
	
	@Override
	public Boolean isConst() {
		return this.operand().isConst();
	}

	@Override
	public Exp simplify() {
		Exp r;
		if(this.isConst()) r = Const.of(this.value(),this.type());
		else r = this;
		return r;
	}
}
