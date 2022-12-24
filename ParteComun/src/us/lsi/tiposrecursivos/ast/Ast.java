package us.lsi.tiposrecursivos.ast;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import us.lsi.common.Printers;
import us.lsi.tiposrecursivos.parsers.ProgramLexer;
import us.lsi.tiposrecursivos.parsers.ProgramParser;

public record Ast(Block block) {
	
	public static Ast of(Block block) {
		return new Ast(block);
	}
	
	public static Ast parse(String file) throws IOException {
		ProgramLexer lexer = new ProgramLexer(CharStreams.fromFileName("ficheros/program.txt"));
		ProgramParser parser = new ProgramParser(new CommonTokenStream(lexer));
	    ParseTree parseTree = parser.program();
	    Ast program =  (Ast) parseTree.accept(new AstVisitorC());
	    return program;
	}
	
	public static Integer getIndex(Object e, Map<Object,Integer> map, String label, PrintStream file) {
		Integer r;
		if(map.containsKey(e)) r = map.get(e);
		else {
			r = map.get("maxValue")+1;
			map.put("maxValue",r);
			map.put(e, r);
			Ast.vertex(r,label,file);
		}
		return r;
	}
	
	public static void vertex(Integer n, String text, String color, PrintStream file) {
		String txt = String.format("\"%s\" [label=\"%s\" color=\"%s\"];",n,text);
		file.println(txt);
	}
	
	public static void vertex(Integer n, String text, PrintStream file) {
		String txt = String.format("\"%s\" [label=\"%s\"];",n,text);
		file.println(txt);
	}
	
	public static void edge(Integer v1, Integer v2, PrintStream file) {
		edge(v1,v2,null,file);
	}
	
	public static void edgeColor(Integer v1, Integer v2, String color, PrintStream file) {
		String txt;
		if (color!=null) 
			txt = String.format("\"%s\" -> \"%s\" [color=\"%s\"];", v1, v2, color);
		else
			txt = String.format("\"%s\" -> \"%s\";", v1, v2);
		file.println(txt);
	}
	
	public static void edge(Integer v1, Integer v2, String text,  PrintStream file) {
		String txt;
		if (text!=null) 
			txt = String.format("\"%s\" -> \"%s\" [label=\"%s\"];", v1, v2, text);
		else
			txt = String.format("\"%s\" -> \"%s\";", v1, v2);
		file.println(txt);
	}
	
	public static void edgeColor(Integer v1, Integer v2, String text, String color, PrintStream file) {
		String txt;
		if (text!=null) 
			txt = String.format("\"%s\" -> \"%s\" [label=\"%s\" color=\"%s\"];", v1, v2,text,color);
		else
			txt = String.format("\"%s\" -> \"%s\" [color=\"%s\"];", v1, v2,color);
		file.println(txt);
	}
	
	public void toDot(String file) {
		PrintStream p = Printers.file(file);
		Map<Object,Integer> map = new HashMap<>();
		map.put("maxValue",0);
		String txt = "digraph Program { \n \n"; 
		p.println(txt);
		toDot(p,map);
		p.println("}");
		p.close();
	}
	
	public void toDot(PrintStream file, Map<Object,Integer> map) {
		Integer pn = Ast.getIndex(this,map,"Program", file);
		Block b = this.block();	
		Integer bn = Ast.getIndex(b.sentences().get(0),map,b.sentences().get(0).name(), file);
		Ast.edgeColor(pn, bn,"next","red",file);
		b.toDot(file,map);
	}


	@Override
	public String toString() {
		String b = this.block()!=null?this.block().toString():"";
		return String.format("Program\n%s",b);
	}
}
