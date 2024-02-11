package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.Map;

public record Assign(Exp id, Exp exp) implements Sentence {

	public static Assign of(Exp id, Exp exp) {
		return new Assign(id, exp);
	}
	
	@Override
	public String toString() {
		return String.format("%s = %s", this.id, this.exp);
	}

	@Override
	public String name() {
		return "<==";
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		Integer n = Ast.getIndex(this,map,this.name(),file);
		Integer id = Ast.getIndex(this.id(),map,this.id.name(),file);
		Integer exp = Ast.getIndex(this.exp(),map,this.exp().name(),file);
		Ast.edge(n,id,"left",file);
		Ast.edge(n,exp,"right",file);
		this.id().toDot(file, map);
		this.exp().toDot(file, map);
	}
	
}
