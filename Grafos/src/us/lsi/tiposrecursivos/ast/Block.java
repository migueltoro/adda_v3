package us.lsi.tiposrecursivos.ast;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.Attribute;

import us.lsi.common.Preconditions;

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
	public void toGraph(SimpleDirectedGraph<Vertex, DefaultEdge> graph) {
		Preconditions.checkNotNull(graph);
		if(!graph.containsVertex(this)) graph.addVertex(this);
		this.sentences().stream()
			.forEach(v->{if(!graph.containsVertex(v)) graph.addVertex(v);});
		this.sentences().stream().forEach(v->graph.addEdge(this,v));
		this.sentences().stream().forEach(v->v.toGraph(graph));
	}

	@Override
	public String label() {
		return "B";
	}
	
	public Map<String,Attribute> style() {
		return Map.of();
	}

}
