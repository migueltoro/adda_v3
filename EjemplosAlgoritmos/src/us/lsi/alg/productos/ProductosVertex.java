package us.lsi.alg.productos;


import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.common.List2;
import us.lsi.common.Preconditions;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public class ProductosVertex implements VirtualVertex<ProductosVertex, ProductosEdge, Integer> {

	// Variables
	public Integer indice;
	public Set<String> funcionalidades_restantes;

	// Factorías vacías

	public static ProductosVertex initial() {
		return ProductosVertex.of(0,Set2.of(DatosProductos.getFunciones()));
	}
	
	public static Predicate<ProductosVertex> goal() {
		return v->v.indice == DatosProductos.NUM_PRODUCTOS;
	}

	// Factorías con variables

	public static ProductosVertex of(Integer indice, Set<String> funcionalidades_restantes) {
		Set<String> funcionalidades_restantesC = new HashSet<>(funcionalidades_restantes);
		final ProductosVertex a = new ProductosVertex(indice, funcionalidades_restantesC);
		return a;
	}

	public static ProductosVertex copy(ProductosVertex v_input) {
		return ProductosVertex.of(v_input.indice, v_input.funcionalidades_restantes);
	}

	private ProductosVertex(Integer indice, Set<String> funcionalidades_restantes) {
		this.indice = indice;
		this.funcionalidades_restantes = funcionalidades_restantes;
	}

	// Métodos para el grafo

	

	@Override
	public Boolean isValid() {
		return indice >= 0 && indice <= DatosProductos.getProductos().size();
	}
	
	public Integer geedyAction() {
		return actions().stream().max(Comparator.naturalOrder()).get();
	}
	
	public ProductosEdge greedyEdge() {
		return this.edge(geedyAction());
	}

	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = null;
		if (this.indice.equals(DatosProductos.NUM_PRODUCTOS))
			alternativas = List2.of();
		if (this.indice.equals(DatosProductos.NUM_PRODUCTOS - 1)) {
			if (this.funcionalidades_restantes.isEmpty())
				alternativas = List2.of(0);
			else if (!this.funcionalidades_restantes.isEmpty()
					&& DatosProductos.getFuncionesProducto(this.indice).containsAll(this.funcionalidades_restantes))
				alternativas = List2.of(1);
			else
				alternativas = List2.of();
//			System.out.println(String.format("%s == %s",this, alternativas));
		} else if (this.indice < DatosProductos.NUM_PRODUCTOS - 1) {
			if (this.funcionalidades_restantes.isEmpty())
				alternativas = List2.of(0);
			else if (Set2.intersection(this.funcionalidades_restantes, DatosProductos.getFuncionesProducto(this.indice))
					.isEmpty())
				alternativas = List2.of(0);
			else
				alternativas = List2.of(1, 0);
		}
		Preconditions.checkNotNull(alternativas, String.format("%s", this.toGraph()));
		return alternativas;
	}

	@Override
	public ProductosVertex neighbor(Integer a) {
		ProductosVertex r = null;
		if(a == 0) r = ProductosVertex.of(this.indice+1,this.funcionalidades_restantes);
		else if (a == 1) 
			r = ProductosVertex.of(indice + 1,
		          Set2.difference(this.funcionalidades_restantes,DatosProductos.getFuncionesProducto(this.indice)));
		return r;
	}

	@Override
	public ProductosEdge edge(Integer a) {
		return ProductosEdge.of(this, this.neighbor(a), a);
	}

	// Métodos auxiliares

	public String toString() {
		String nombre = this.indice<DatosProductos.NUM_PRODUCTOS?
				DatosProductos.getProducto(this.indice).nombre():"_";
		return String.format("(Indice: %d, %s, Asignación pendiente: %s)", 
				this.indice,nombre,funcionalidades_restantes);
	}
	
	public String toGraph() {
		String nombre = this.indice < DatosProductos.NUM_PRODUCTOS? 
				DatosProductos.getProducto(this.indice).nombre() : "_";
		return String.format("(%s,%s)",nombre,this.funcionalidades_restantes);
	}

	

	public static SolucionProductos getSolucion(GraphPath<ProductosVertex, ProductosEdge> path) {
		return ProductosVertex.getSolucion(path.getEdgeList());
	}

	public static SolucionProductos getSolucion(List<ProductosEdge> ls) {
		List<Integer> alternativas = List2.empty();
		for (ProductosEdge edge : ls) {
			alternativas.add(edge.action());
		}

		return SolucionProductos.of(alternativas);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcionalidades_restantes == null) ? 0 : funcionalidades_restantes.hashCode());
		result = prime * result + ((indice == null) ? 0 : indice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductosVertex other = (ProductosVertex) obj;
		if (funcionalidades_restantes == null) {
			if (other.funcionalidades_restantes != null)
				return false;
		} else if (!funcionalidades_restantes.equals(other.funcionalidades_restantes))
			return false;
		if (indice == null) {
			if (other.indice != null)
				return false;
		} else if (!indice.equals(other.indice))
			return false;
		return true;
	}
	
	

}

