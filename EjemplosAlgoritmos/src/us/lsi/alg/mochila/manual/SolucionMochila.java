package us.lsi.alg.mochila.manual;

import java.util.List;

import us.lsi.alg.mochila.manual.DatosMochila.ObjetoMochila;
import us.lsi.common.Multiset;

public record SolucionMochila(Integer valor, Integer peso, Multiset<ObjetoMochila> objetos) {
	
	public static SolucionMochila of(MochilaProblem p, List<Integer> acciones) {
		Multiset<ObjetoMochila> objetos = Multiset.empty();
		MochilaProblem v = p;
		Integer valor = 0;
		Integer peso = 0;
		for(int i=0; i< acciones.size();i++) {
			Integer a = acciones.get(i);
			if (a > 0) {
				objetos.add(DatosMochila.objeto(i), a);
				valor += a*DatosMochila.valor(i);
				peso += a*DatosMochila.peso(i);
			}
			v = v.vecino(a);
		}
		return new SolucionMochila(valor,peso,objetos);
	}

}
