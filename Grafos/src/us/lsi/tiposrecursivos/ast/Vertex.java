package us.lsi.tiposrecursivos.ast;

import java.util.Map;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.Attribute;

public interface Vertex {
	
	void toGraph(SimpleDirectedGraph<Vertex, DefaultEdge> graph);
	
	default SimpleDirectedGraph<Vertex, DefaultEdge> toGraph() {
		SimpleDirectedGraph<Vertex, DefaultEdge> graph = 
				new SimpleDirectedGraph<>(null,()->new DefaultEdge(), false);
		this.toGraph(graph);
		return graph;
	}
	
	String label();
	
	default Map<String,Attribute> color() {
		return Map.of();
	}
	
	public static Vertex of(String label, Map<String,Attribute> style) {
		return VertexI.of(label, style);
	}
	
	public static record VertexI(Integer id,String label, Map<String,Attribute> style) 
				implements Vertex {
		private static Integer n = 0;
		public static Vertex of(String label, Map<String,Attribute> style) {
			Vertex v =new VertexI(n,label,style);
			VertexI.n = VertexI.n+1;
			return v;
		}
		public void toGraph(SimpleDirectedGraph<Vertex, DefaultEdge> graph) {};
		
	}
}
