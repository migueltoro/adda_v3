package us.lsi.alg.monedas;


import org.jgrapht.GraphPath;
import us.lsi.common.Multiset;

public record SolucionMonedas(Multiset<Moneda> monedas, Integer peso, Integer valor) 
               implements Comparable<SolucionMonedas> {
	
	public static SolucionMonedas of(GraphPath<MonedaVertex,MonedaEdge> path) {	
		Multiset<Moneda> monedas = Multiset.empty();
		Integer peso = 0;
		Integer valor = 0;
		for (MonedaEdge e : path.getEdgeList()) {
			Integer i = e.source().index();
			Moneda m = Moneda.get(i);
			Integer p = Moneda.peso(i);
			Integer v = Moneda.valor(i);
			monedas.add(m, e.action());
			peso = peso + e.action() * p;
			valor = valor + e.action() * v;
		}
		return new SolucionMonedas(monedas, peso,valor);
	}

	@Override
	public int compareTo(SolucionMonedas other) {
		return this.peso.compareTo(other.peso);
	}

}
