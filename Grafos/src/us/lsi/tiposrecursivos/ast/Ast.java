package us.lsi.tiposrecursivos.ast;

import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import us.lsi.tiposrecursivos.parsers.ProgramLexer;
import us.lsi.tiposrecursivos.parsers.ProgramParser;

public record Ast(Block block) implements Vertex {
	
	public static Ast of(Block block) {
		return new Ast(block);
	}
	
	public static Ast parse(String file) throws IOException {
		ProgramLexer lexer = new ProgramLexer(CharStreams.fromFileName(file));
		System.out.println("_________________");
		ProgramParser parser = new ProgramParser(new CommonTokenStream(lexer));
		System.out.println("_________________");
	    ParseTree parseTree = parser.program();
	    System.out.println("_________________");
//	    Ast program =  (Ast) parseTree.accept(new AstVisitorC());
//	    return program;
		return null;
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
}
