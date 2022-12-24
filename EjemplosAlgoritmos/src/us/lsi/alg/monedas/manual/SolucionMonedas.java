package us.lsi.alg.monedas.manual;


import java.util.List;
import us.lsi.common.Multiset;

public record SolucionMonedas(Multiset<Moneda> monedas, Integer peso, Integer valor) 
               implements Comparable<SolucionMonedas> {
	
	public static SolucionMonedas of(List<Integer> unidades) {	
		Multiset<Moneda> monedas = Multiset.empty();
		Integer peso = 0;
		Integer valor = 0;
		for (int i =0; i<unidades.size();i++) {
			Moneda m = Moneda.get(i);
			Integer p = Moneda.peso(i);
			Integer v = Moneda.valor(i);
			monedas.add(m, unidades.get(i));
			peso = peso + unidades.get(i) * p;
			valor = valor + unidades.get(i) * v;
		}
		return new SolucionMonedas(monedas, peso,valor);
	}

	@Override
	public int compareTo(SolucionMonedas other) {
		return this.peso.compareTo(other.peso);
	}

}
