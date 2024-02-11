package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

import us.lsi.common.Preconditions;
import us.lsi.common.Set2;

public record Binary(Exp left, Exp right, String name) implements Exp {
	
	public static Binary of(Exp left, Exp right, String op) {
		return new Binary(left, right, op);
	}
	
	public Operator operator() {
		Type t1 = left.type();
		Type t2 = right.type();
		OperatorId id = OperatorId.of2(name,t1,t2);
		Operator op = Operators.operators.get(id);
		Preconditions.checkArgument(op != null,String.format("No existe el operador %s",id));
		return op;
	}
	
	public Object value() {
		Operator.Binary op = (Operator.Binary) this.operator();
		return op.function().apply(left.value(),right.value());
	}
	
	public Type type() {
		return this.operator().resultType();
	}
	
	@Override
	public String toString() {
		return String.format("(%s %s %s)", this.left, this.name, this.right);
	}

	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		Integer n = Ast.getIndex(this,map,this.name(),file);
		Integer left = Ast.getIndex(this.left(),map,this.left().name(),file);
		Integer right = Ast.getIndex(this.right(),map,this.right().name(),file);
		Ast.edge(n,left, file);
		Ast.edge(n,right, file);
		this.left().toDot(file, map);
		this.right().toDot(file, map);
	}
	
	@Override
	public Set<Var> vars() {
		return Set2.union(this.left().vars(),this.right().vars());
	}

	@Override
	public Boolean isConst() {
		return this.left().isConst() && this.right().isConst();
	}

	@Override
	public Exp simplify() {
		Exp r;
		if(this.isConst()) r = Const.of(this.value(),this.type());
		else r = this;
		return r;
	}
}
