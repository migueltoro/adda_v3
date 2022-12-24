package us.lsi.graphs;

import us.lsi.common.Preconditions;

public interface SimpleEdge<V> {
	
	/**
	 * @param v1 Un vértice
	 * @param v2 Un segundo vértice
	 * @param weight El peso de la arista
	 * @param <V> el tipo de los vértices
	 * @return Una arista entre ambos vértices
	 */
	public static <V> SimpleEdge<V> of(V v1, V v2, Double weight) {
		return new SimpleEdgeR<V>(v1, v2, weight);
	}
	
	/**
	 * @param v1 Un vértice
	 * @param v2 Un segundo vértice
	 * @param <V> el tipo de los vértices
	 * @return Una arista entre ambos vértices
	 */
	public static <V> SimpleEdge<V> of(V v1, V v2) {
		return new SimpleEdgeR<V>(v1, v2,1.);
	}
	
	V source();
	V target();
	Double weight();
	
	/**
	 * @param v Un vértice de la arista
	 * @return El otro vértice
	 */

	public  default V otherVertex(V v){
		Preconditions.checkNotNull(v,"El vértice no puede ser null");
		V r = null;
		if(v.equals(this.source())) r = this.target();
		else if(v.equals(this.target())) r = this.source();
		return r;
	}
	
	public static record SimpleEdgeR<V>(V source,V target,Double weight) implements SimpleEdge<V> {}

}
