package us.lsi.tiposrecursivos.ast;


import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

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
	public void toGraph(SimpleDirectedGraph<Vertex, DefaultEdge> graph) {
		if(!graph.containsVertex(this)) graph.addVertex(this);
		this.parameters().stream()
			.forEach(v->{if(!graph.containsVertex(v)) graph.addVertex(v);});
		this.parameters().stream().forEach(v->graph.addEdge(this,v));
		this.parameters().stream().forEach(v->v.toGraph(graph));
	}

	@Override
	public String label() {
			return this.id();
	}
}
