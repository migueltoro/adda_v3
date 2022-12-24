package us.lsi.hypergraphs2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.common.Preconditions;

public record GraphTree2<V extends HyperVertex2<V, E, A, S>, 
		E extends HyperEdge2<V, E, A, S>, A, S> (V vertex) {

	public static <V extends HyperVertex2<V, E, A, S>, E extends HyperEdge2<V, E, A, S>, A, S> 
			GraphTree2<V, E, A, S> of(V v) {
		return new GraphTree2<>(v);
	}
	
	public Boolean isLeaf() {
		return this.vertex().isBaseCase();
	}
	
	public A action() {
		Preconditions.checkArgument(!this.isLeaf(),"El un árbol hoja");
		return this.vertex().vertexWeight().edge().action();
	}
	
	public E edge() {
		Preconditions.checkArgument(!this.isLeaf(),"El un árbol hoja");
		return this.vertex().vertexWeight().edge();
	}

	public Double weight() {
		return this.vertex().vertexWeight().weight();
	}

	public S solution() {
		return this.vertex().solution();
	}

	public List<GraphTree2<V, E, A, S>> children() {
		if(this.action() == null ) return List.of();
		return this.vertex()
				.neighbors(this.action())
				.stream()
				.filter(v->!v.isBaseCase())
				.map(v ->GraphTree2.of(v))
				.toList();
	}
	
	public Set<V> allVertices(){
		Set<V> s = new HashSet<>();
		s.add(this.vertex());
		this.children().stream().forEach(t->s.addAll(t.allVertices()));
		return s;
	}
	public Set<E> allEdges(){
		Set<E> s = new HashSet<>();
		s.add(this.edge());
		this.children().stream().forEach(t->s.addAll(t.allEdges()));
		return s;
	}
	public String string() {
		String label;
		if (vertex().isBaseCase())
			label = "[" + vertex().toString() + "]";
		else {
			label = "[" + vertex().toString() + "," + action().toString() + "]";
			label += this.children().stream()
					.map(g -> g.string())
					.collect(Collectors.joining(",", "(", ")"));
		}
		return label;
	}

}
