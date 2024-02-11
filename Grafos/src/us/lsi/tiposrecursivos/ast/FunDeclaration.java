package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record FunDeclaration(String id,Type resultType,List<ParamDeclaration> parameters) implements Declaration {

	public static FunDeclaration of(String id,Type resultType,List<ParamDeclaration> parameters) {
		return new FunDeclaration(id, resultType,parameters);
	}
	
	@Override
	public String toString() {
		String d = this.parameters.stream().map(x->x.toString()).collect(Collectors.joining(","));
		return String.format("%s(%s):%s",this.id,d,this.resultType);
	}
	
	@Override
	public String name() {
		return String.format("%s(%s):%s",this.id(),
				this.parameters.stream().map(p->p.type().toString()).collect(Collectors.joining(",")), 
				this.resultType());
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		Ast.getIndex(this,map,this.name(),file);
	}

}
