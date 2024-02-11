package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.Map;

public record While(Exp guard, Block block) implements Sentence {
	
	public static While of(Exp guard, Block block) {
		return new While(guard, block);
	}
	
	@Override
	public String toString() {
		return String.format("while (%s) {\n%s}\n", this.guard, this.block);
	}

	@Override
	public String name() {
		return "while";
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		Integer n = Ast.getIndex(this,map,this.name(),file);
		Integer guard = Ast.getIndex(this.guard(),map,this.guard().name(),file);
		Integer block = Ast.getIndex(this.block().sentences().get(0),map,
				this.block().sentences().get(0).name(),file);
		Ast.edge(n,guard, file);
		Ast.edge(n,block, file);
		this.guard().toDot(file, map);
		this.block().toDot(file, map);
	}

}
