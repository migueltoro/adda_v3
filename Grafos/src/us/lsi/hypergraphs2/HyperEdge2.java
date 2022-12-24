package us.lsi.hypergraphs2;

import java.util.List;

public interface HyperEdge2<V extends HyperVertex2<V, E, A, S>, 
		E extends HyperEdge2<V,E,A,S>, A, S> {
	V source();
	A action();
	Double edgeWeight(List<Double> solutions);
	S solution(List<S> solutions);
	E me();
	
	default List<V> targets() {
		return this.source().neighbors(this.action());
	}
	default Sp<E> edgeWeight() {
		Sp<E> r;
		List<Sp<E>> ls = this.targets().stream().map(v -> v.vertexWeight()).toList();
		if (ls.contains(null))
			return null;
		else {
			Double weight = this.edgeWeight(ls.stream().map(e -> e.weight()).toList());
			r = Sp.of(weight, me());
		}
		return r;
	}
	default S solution() {
		return solution(this.targets().stream().map(v->v.solution()).toList());
	}
}
