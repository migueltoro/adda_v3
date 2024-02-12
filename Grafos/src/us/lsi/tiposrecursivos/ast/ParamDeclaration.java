package us.lsi.tiposrecursivos.ast;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

public record ParamDeclaration(String id, Type type) implements Vertex {
	
	public static ParamDeclaration of(String id, Type type) {
		return new ParamDeclaration(id, type);
	}
	
	@Override
	public String toString() {
		return String.format("%s:%s", this.id,this.type);
	}
	
	@Override
	public void toGraph(SimpleDirectedGraph<Vertex, DefaultEdge> graph) {
		if(!graph.containsVertex(this)) graph.addVertex(this);
	}

	@Override
	public String label() {
		return String.format("%s:%s",this.id(),this.type());
	}

}
