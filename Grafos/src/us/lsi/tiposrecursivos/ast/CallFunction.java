package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public record CallFunction(String name,List<Exp> parameters, FunDeclaration funDeclaration) implements Exp {
	
	public static CallFunction of(String id, List<Exp> parameters,FunDeclaration funDeclaration) {
		return new CallFunction(id, parameters,funDeclaration);
	}
	
	public Object value() {
		return null;
	}
	
	public Type type() {
		return null;
	}
	
	@Override
	public String toString() {
		String d = this.parameters.stream().map(x->x.toString()).collect(Collectors.joining(","));
		return String.format("%s(%s)",this.name,d);
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object,Integer> map) {
		Integer pn = Ast.getIndex(this,map,this.name(), file);
		for(Exp e:this.parameters()) {
			Integer dn = Ast.getIndex(e,map,e.name(),file);
			Ast.edge(pn, dn, file);
			e.toDot(file, map);
		}
	}

	@Override
	public Operator operator() {
		return null;
	}
	
	@Override
	public Set<Var> vars() {
		return this.parameters().stream().flatMap(e->e.vars().stream()).collect(Collectors.toSet());
	}
	
	@Override
	public Boolean isConst() {
		return this.parameters().stream().allMatch(p->p.isConst());
	}

	@Override
	public Exp simplify() {
		Exp r;
		if(this.isConst()) r = Const.of(this.value(),this.type());
		else r = this;
		return r;
	}
}
