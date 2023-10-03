package us.lsi.p5.ej_1;

import java.util.List;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record MulticonjuntoVertex(Integer indice,Integer sr_suma_restante) implements VirtualVertex<MulticonjuntoVertex, MulticonjuntoEdge, Integer> {


	public static Integer n_elementos = DatosMulticonjunto.NUM_E;

	public static MulticonjuntoVertex of(Integer i, Integer sr) {
		return  new MulticonjuntoVertex(i, sr);
	}
	
	public static MulticonjuntoVertex initial() {
		return  new MulticonjuntoVertex(0, DatosMulticonjunto.SUM);
	}
	
	public static Predicate<MulticonjuntoVertex> goal() {
		return  v->v.indice == DatosMulticonjunto.NUM_E;
	}
	
	public static Predicate<MulticonjuntoVertex> goalHasSolution() {
		return  v->v.sr_suma_restante == 0;
	}

//	public static MulticonjuntoVertex copy(MulticonjuntoVertex c) {
//		final MulticonjuntoVertex copia = new MulticonjuntoVertex(c.indice, c.sr_suma_restante);
//		return copia;
//	}
	
	public String toGraph() {
		return String.format("(%d,%d)", this.indice, this.sr_suma_restante);
	}

	// M�todos auxiliares

	public String toString() {
		return String.format("(%d,%d)", this.indice, this.sr_suma_restante);
	}

	// M�todos del grafo

	@Override
	public Boolean isValid() {
		return this.indice >= 0 && this.indice <= DatosMulticonjunto.NUM_E && sr_suma_restante >= 0;
	}

	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		if (this.indice < DatosMulticonjunto.NUM_E) {
			if (this.indice == DatosMulticonjunto.NUM_E - 1) {
				if (this.sr_suma_restante % DatosMulticonjunto.getElemento(this.indice) == 0) {
					Integer max_div = this.accionEntera();
					alternativas.add(max_div);
				}
			} else {
				Integer max_div = this.accionEntera();
				alternativas = List2.rangeList(0, max_div + 1);
			}
		}	
		return alternativas = List2.reverse(alternativas);
	}

	@Override
	public MulticonjuntoVertex neighbor(Integer a) {
		Integer suma_restante_nueva = sr_suma_restante - (DatosMulticonjunto.getElemento(this.indice) * a);
		return MulticonjuntoVertex.of(this.indice + 1, suma_restante_nueva);
	}

	@Override
	public MulticonjuntoEdge edge(Integer a) {
		return MulticonjuntoEdge.of(this, this.neighbor(a), a);
	}
	
	public MulticonjuntoEdge greedyEdge() {
		return edge(accionEntera());
	}

	public Integer accionEntera() {
		return this.sr_suma_restante / DatosMulticonjunto.getElemento(this.indice);
	}
	
	public Double accionReal() {
		return ((double)this.sr_suma_restante)/ DatosMulticonjunto.getElemento(this.indice);
	}

	public static SolucionMulticonjunto getSolucion(GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> path) {
		return MulticonjuntoVertex.getSolucion(path.getEdgeList());
	}

	public static SolucionMulticonjunto getSolucion(List<MulticonjuntoEdge> ls) {

		List<Integer> alternativas = List2.empty();

		for (MulticonjuntoEdge alternativa : ls) {
			alternativas.add(alternativa.action());
		}

		SolucionMulticonjunto s = SolucionMulticonjunto.create(alternativas);

		return s;
	}

}

