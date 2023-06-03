package us.lsi.alg.productos_y_componentes;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import us.lsi.graphs.virtual.VirtualVertex;

public record VertexProductos(Integer index,Integer tpR,Integer teR) 
    	implements VirtualVertex<VertexProductos,EdgeProductos,Integer> {

	public static VertexProductos of(Integer index,Integer tpR,Integer teR) {
	   return new VertexProductos(index,tpR,teR);	
	}
	
	public static VertexProductos initial() {
		return VertexProductos.of(0,DatosProductos.getTotalProd(),DatosProductos.getTotalElab());
	}

	public static Predicate<VertexProductos> goal() {
		return v-> v.index() == DatosProductos.getNumProductos();
	}	
	
	public EdgeProductos greedyEdge() {
		return edge(DatosProductos.maxActionInt(DatosProductos.getProducto(index),this.tpR,this.teR));
	}
	
	@Override
	public List<Integer> actions() {
		if (this.index() == DatosProductos.getNumProductos()) return List.of();
		List<Integer> r = IntStream.range(0, DatosProductos.getMaxUnidades(this.index()) + 1).boxed()
				.filter(i -> this.tpR() >= i * DatosProductos.getProducto(this.index()).tp())
				.filter(i -> this.teR() >= i * DatosProductos.getProducto(this.index()).te())
				.toList();
		List<Integer> r2 = new ArrayList<>(r);
		Collections.reverse(r2);
		return r2;
	}

	@Override
	public VertexProductos neighbor(Integer a) {
		Integer tpR2 = tpR - a*DatosProductos.getProducto(this.index()).tp();
        Integer teR2 = teR - a*DatosProductos.getProducto(this.index()).te();
		return VertexProductos.of(index+1,tpR2,teR2);
	}

	@Override
	public EdgeProductos edge(Integer a) {
		return EdgeProductos.of(this,this.neighbor(a),a);
	}
	
	public String toGraph() {
		return String.format("%d,%d,%d",this.index(),this.tpR,this.teR());
	}
}
