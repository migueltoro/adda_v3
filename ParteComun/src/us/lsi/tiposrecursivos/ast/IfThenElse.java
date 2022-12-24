package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.Map;

public record IfThenElse(Exp guard,Block trueBlock,Block falseBlock) implements Sentence {
	
	public static IfThenElse of(Exp guard, Block trueBlock, Block falseBlock) {
		return new IfThenElse(guard, trueBlock, falseBlock);
	}
	
	@Override
	public String toString() {
		return String.format("if (%s) {\n%s} else {\n%s}\n", this.guard, this.trueBlock,this.falseBlock);
	}
	
	@Override
	public String name() {
		return "ifThenElse";
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object, Integer> map) {
		Integer n = Ast.getIndex(this,map,this.name(),file);
		Integer guard = Ast.getIndex(this.guard(),map,this.guard().name(),file);
		Integer trueBlock = Ast.getIndex(this.trueBlock().sentences().get(0),map,
				this.trueBlock().sentences().get(0).name(),file);
		Integer falseBlock = Ast.getIndex(this.falseBlock().sentences().get(0),map,
				this.falseBlock().sentences().get(0).name(),file);
		Ast.edge(n,guard,"Guarda",file);
		Ast.edgeColor(n,trueBlock,"nextTrue","red",file);
		Ast.edgeColor(n,falseBlock,"nextFalse","red",file);
		this.guard().toDot(file,map);
		this.trueBlock().toDot(file,map);
		this.falseBlock().toDot(file,map);
	}

}
