package us.lsi.hypergraphs2;


import java.util.List;

public interface HyperVertex2<V extends HyperVertex2<V,E,A,S>, E extends HyperEdge2<V,E,A,S>,A,S> {
	
	
	List<A> actions();
	Boolean isBaseCase();
	Double baseCaseWeight();
	S baseCaseSolution();
	V me();
	List<V> neighbors(A a);
	E edge(A a); 
	
	public default Sp<E> data() {
		return Data.<V,E>of().memory.getOrDefault(this.me(), null);
	}
	public default void setData(Sp<E> data) {
		Data.<V,E>of().memory.put(this.me(),data);
	}
	public default void setDataAll(Sp<E> data) {
		Data.<V,E>of().memoryAll.put(this.me(),data);
	}
	
	public default Sp<E> vertexWeight() {
		Sp<E> r;
		if (this.data() != null)
			r = this.data();
		else {
			r = null;
			if (this.isBaseCase()) {
				Double br = baseCaseWeight();
				if (br != null) r = Sp.of(br, null);
			} else if (!this.edgesOf().isEmpty()) {
				r = this.edgesOf().stream()
						.map(e->e.edgeWeight())
						.peek(e->this.setDataAll(e))
						.filter(s -> s != null)	
						.min(Data.<V,E>of().order())
						.orElse(null);
			}
			this.setData(r);
		}
		return r;
	}

	default public S solution() {
		Sp<E> sp = this.vertexWeight();
		S r;
		if (this.isBaseCase())
			r = this.baseCaseSolution();
		else {
			r = sp.edge().solution();
		}
		return r;
	}

	/**
	 * Este m&eacute;todo podr&iacute;a ser sobrescrito en la clase que refine al
	 * tipo
	 * 
	 * @return El conjunto de las aristas hacia los vecinos
	 */
	default public List<E> edgesOf() {
		return this.actions().stream().map(a -> this.edge(a)).toList();
	}
	
	default public List<V> targets(A a) {
		return this.edge(a).targets();
	}

	default GraphTree2<V, E, A, S> graphTree() {
		return GraphTree2.of(this.me());
	}
}
