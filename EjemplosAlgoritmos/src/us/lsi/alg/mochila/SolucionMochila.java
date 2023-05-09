package us.lsi.alg.mochila;

import java.util.List;


import us.lsi.common.Multiset;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.ObjetoMochila;

public record SolucionMochila(Integer valor, Integer peso, Multiset<ObjetoMochila> objetos) {
	
	public static SolucionMochila of(MochilaVertex p, List<Integer> acciones) {
		Multiset<ObjetoMochila> objetos = Multiset.empty();
		MochilaVertex v = p;
		Integer valor = 0;
		Integer peso = 0;
		for(int i=0; i< acciones.size();i++) {
			Integer a = acciones.get(i);
			if (a > 0) {
				objetos.add(DatosMochila.getObjeto(i), a);
				valor += a*DatosMochila.getValor(i);
				peso += a*DatosMochila.getPeso(i);
			}
			v = v.neighbor(a);
		}
		return new SolucionMochila(valor,peso,objetos);
	}

}
