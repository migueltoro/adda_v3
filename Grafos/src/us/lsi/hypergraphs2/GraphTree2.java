package us.lsi.hypergraphs2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import us.lsi.hypergraphs2.GraphTree2.Gtb;
import us.lsi.hypergraphs2.GraphTree2.Gtr;

public sealed interface GraphTree2<V extends HyperVertex2<V, E, A, ?>, E extends HyperEdge2<V, E, A,?>, A> 
		permits Gtb<V,E,A>,Gtr<V,E,A>{

	public static <V extends HyperVertex2<V, E, A, ?>, E extends HyperEdge2<V, E, A, ?>, A> 
		GraphTree2<V, E, A> tb(V v) {
		return new Gtb<V, E, A>(v);
	}
	
	public static <V extends HyperVertex2<V, E, A, ?>, E extends HyperEdge2<V, E, A, ?>, A> 
		GraphTree2<V, E, A> tr(V v,A a,List<GraphTree2<V,E,A>> children) {
		return new Gtr<V, E, A>(v,a,children);
	}
	
	public static <V extends HyperVertex2<V, E, A, ?>, E extends HyperEdge2<V, E, A, ?>, A, S> 
		GraphTree2<V, E, A> optimalTree(V v) {
		if (v.isBaseCase()) {
			return new Gtb<V, E, A>(v);
		} else {
			A a = v.sp().edge().action();
			List<GraphTree2<V,E,A>> children = v.neighbors(a).stream()
					.map(g->GraphTree2.optimalTree(g))
					.toList();					
			return new Gtr<V, E, A>(v,a,children);
		}
	}
	
	public V vertex();

	public Boolean isLeaf();

	public default Double weight() {
		return this.vertex().weight();
	}

	public List<GraphTree2<V, E, A>> children(); 
	
	public default Set<V> allVertices() {
		Set<V> s = new HashSet<>();
		s.add(this.vertex());
		this.children().stream().forEach(t->s.addAll(t.allVertices()));
		return s;
	}

	public Set<E> allEdges();

	public static record Gtb<V extends HyperVertex2<V, E, A, ?>, E extends HyperEdge2<V, E, A, ?>, A>(V vertex) 
	     implements GraphTree2<V, E, A>{
		public Boolean isLeaf() {
			return true;
		}
		public List<GraphTree2<V, E, A>> children() {
			return List.of();
		}
		@Override
		public String toString() {
			return String.format("(%s)",this.vertex());
		}
		public Set<E> allEdges() {
			return Set.of();
		}
	}
	
	public record Gtr<V extends HyperVertex2<V, E, A, ?>, E extends HyperEdge2<V, E, A, ?>, A>
				(V vertex, A action, List<GraphTree2<V,E,A>> children) implements GraphTree2<V, E, A>{
		public Boolean isLeaf() {
			return false;
		}
		public E edge() {
			return this.vertex().edge(this.action());
		}

		@Override
		public String toString() {
			String lb = String.format("(%s,%s)",this.vertex(),this.action());
			return  lb+this.children().stream()
					.map(g -> g.toString())
					.collect(Collectors.joining(",", "(", ")"));
		}

		public Set<E> allEdges() {
			Set<E> s = new HashSet<>();
			s.add(this.edge());
			this.children().stream().forEach(t -> s.addAll(t.allEdges()));
			return s;
		}
		
	}

}
