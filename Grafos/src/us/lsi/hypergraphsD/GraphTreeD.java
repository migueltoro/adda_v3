package us.lsi.hypergraphsD;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.hypergraphsD.GraphTreeD.Gtb;
import us.lsi.hypergraphsD.GraphTreeD.Gtr;

public sealed interface GraphTreeD<V extends HyperVertexD<V, E, A, ?>, E extends HyperEdgeD<V, E, A,?>, A> 
		permits Gtb,Gtr{

	public static <V extends HyperVertexD<V, E, A, ?>, E extends HyperEdgeD<V, E, A, ?>, A> 
		GraphTreeD<V, E, A> tb(V v) {
		return new Gtb<V, E, A>(v);
	}
	
	public static <V extends HyperVertexD<V, E, A, ?>, E extends HyperEdgeD<V, E, A, ?>, A> 
		GraphTreeD<V, E, A> tr(V v,A a,List<GraphTreeD<V,E,A>> children) {
		return new Gtr<V, E, A>(v,a,children);
	}
	
	public static <V extends HyperVertexD<V, E, A, ?>, E extends HyperEdgeD<V, E, A, ?>, A, S> 
		GraphTreeD<V, E, A> optimalTree(V v) {
		if (v.isBaseCase()) {
			return new Gtb<V, E, A>(v);
		} else {
			A a = v.sp().edge().action();
			List<GraphTreeD<V,E,A>> children = v.neighbors(a).stream()
					.map(g->GraphTreeD.optimalTree(g))
					.toList();					
			return new Gtr<V, E, A>(v,a,children);
		}
	}
	
	public V vertex();

	public Boolean isLeaf();

	public default Double weight() {
		return this.vertex().weight();
	}

	public List<GraphTreeD<V, E, A>> children(); 
	
	public default Set<V> allVertices() {
		Set<V> s = new HashSet<>();
		s.add(this.vertex());
		this.children().stream().forEach(t->s.addAll(t.allVertices()));
		return s;
	}

	public Set<E> allEdges();

	public static record Gtb<V extends HyperVertexD<V, E, A, ?>, E extends HyperEdgeD<V, E, A, ?>, A>(V vertex) 
	     implements GraphTreeD<V, E, A>{
		public Boolean isLeaf() {
			return true;
		}
		public List<GraphTreeD<V, E, A>> children() {
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
	
	public record Gtr<V extends HyperVertexD<V, E, A, ?>, E extends HyperEdgeD<V, E, A, ?>, A>
				(V vertex, A action, List<GraphTreeD<V,E,A>> children) implements GraphTreeD<V, E, A>{
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
