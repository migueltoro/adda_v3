package us.lsi.tiposrecursivos.ast;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record Block(List<Sentence> sentences, Map<String,Object> symbolTable) implements Sentence{
	
	public static Block of(List<Sentence> sentences, Map<String,Object> symbolTable) {
		return new Block(sentences,symbolTable);
	}

	@Override
	public String toString() {
		String s = this.sentences.stream().map(x->x.toString()).collect(Collectors.joining("\n"));
		String d = this.symbolTable==null?"":String.format("Declaraciones = %s\n",this.symbolTable());
		return String.format("%s%s\n",d,s);
	}
	
	@Override
	public String name() {
		return "Bloque";
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object,Integer> map) {
		List<Sentence> dc = this.sentences();
		Integer d0n = Ast.getIndex(dc.get(0),map,dc.get(0).name(),file);
		for(int i=1;i<dc.size();i++) {
			Integer dn = Ast.getIndex(dc.get(i),map,dc.get(i).name(),file);
			Ast.edgeColor(d0n, dn, "next","red",file);
			d0n = dn;
		}
		for(Sentence s:dc)
			s.toDot(file, map);
	}

}
