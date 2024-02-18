package us.lsi.tiposrecursivos.ast;

import java.io.IOException;
import java.util.Map;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.Attribute;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.tiposrecursivos.parsers.program.ProgramLexer;
import us.lsi.tiposrecursivos.parsers.program.ProgramParser;

public record Ast(Block block) implements Vertex {
	
	private static Integer nError = 0;
	
	public static void printError1(String format,Object...param) {
		String ft = "Error numero %d = "+format;
		System.out.println(String.format(ft,Ast.nError,param[0]));
		Ast.nError++;
	}
	
	public static void printError2(String format,Object...param) {
		String ft = "Error numero %d = "+format;
		System.out.println(String.format(ft,Ast.nError,param[0],param[1]));
		Ast.nError++;
	}
	
	public static Ast of(Block block) {
		return new Ast(block);
	}
	
	public static Ast parse(String file) throws IOException {
		ProgramLexer lexer = new ProgramLexer(CharStreams.fromFileName(file));
		ProgramParser parser = new ProgramParser(new CommonTokenStream(lexer));
	    ParseTree parseTree = parser.program();
	    Ast program =  (Ast) parseTree.accept(new AstVisitorC());
	    return program;
	}
	
	public static Block parseBlock(String file) throws IOException {
		ProgramLexer lexer = new ProgramLexer(CharStreams.fromFileName(file));
		ProgramParser parser = new ProgramParser(new CommonTokenStream(lexer));
	    ParseTree parseTree = parser.block();
	    Block block =  (Block) parseTree.accept(new AstVisitorC());
	    return block;
	}
	

	@Override
	public String toString() {
		String b = this.block()!=null?this.block().toString():"";
		return String.format("Program\n%s",b);
	}
	
	@Override
	public void toGraph(SimpleDirectedGraph<Vertex, DefaultEdge> graph) {
		if(!graph.containsVertex(this)) graph.addVertex(this);
		if(!graph.containsVertex(this.block())) graph.addVertex(this.block());
		graph.addEdge(this,this.block());
		this.block().toGraph(graph);
	}

	@Override
	public String label() {
		return "P";
	}
	
	public Map<String,Attribute> color() {
		return GraphColors.color(Color.blue);
	}
	
	public static void main(String[] args) throws IOException {
		Ast.printError1("La variable %s no está declarada", "Pepe");
	}
}
