package us.lsi.alg.productoscomp;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record EdgeProductos(VertexProductos source, VertexProductos target, Integer action, Double weight)
implements SimpleEdgeAction<VertexProductos,Integer> {

	public static EdgeProductos of(VertexProductos v1, VertexProductos v2, Integer a) {
		double peso = a * DatosProductos.getPrecio(v1.index());
		return new EdgeProductos(v1, v2, a, peso);
	}
}
