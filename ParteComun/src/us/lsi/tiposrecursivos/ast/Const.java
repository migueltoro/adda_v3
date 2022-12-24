package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.Map;
import java.util.Set;

import us.lsi.common.Set2;

public record Const(String name, Type type, Object value) implements Exp, Operator {

	public static Const of(Object value, Type type) {
		return new Const("",type,value);
	}
	
	public static Const of(String name,Object value,Type type) {
		return new Const(name,type,value);
	}

	@Override
	public String toString() {
		return String.format("%s",this.value);
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		Ast.getIndex(this,map,this.name(),file);
	}
	
	@Override
	public Operator operator() {
		return this;
	}
	
	@Override
	public Set<Var> vars() {
		return Set2.empty();
	}

	@Override
	public OperatorId id() {
		return OperatorId.of0(this.name());
	}

	@Override
	public Type resultType() {
		return type;
	}
	
	@Override
	public Boolean isConst() {
		return true;
	}

	@Override
	public Const simplify() {
		return this;
	}
	
}
