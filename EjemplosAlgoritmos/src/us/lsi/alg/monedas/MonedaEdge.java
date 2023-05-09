package us.lsi.alg.monedas;


import us.lsi.graphs.virtual.SimpleEdgeAction;

public record MonedaEdge(MonedaVertex source, MonedaVertex target, Integer action, Double weight) 
           implements SimpleEdgeAction<MonedaVertex,Integer> {

	public static MonedaEdge of(MonedaVertex c1, MonedaVertex c2, Integer action) {
		Double w = (double) (action*DatosMonedas.peso(c1.index()));
		return new MonedaEdge(c1, c2, action, w);
	}

}
