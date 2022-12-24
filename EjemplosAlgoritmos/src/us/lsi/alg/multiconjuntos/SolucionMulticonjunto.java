package us.lsi.alg.multiconjuntos;


import java.util.List;

import us.lsi.common.Multiset;

public record SolucionMulticonjunto(Integer diferencia, Multiset<Integer> ms, List<Integer> solucion) implements Comparable<SolucionMulticonjunto>{
	
	public static SolucionMulticonjunto create(List<Integer> ls) {
		Integer diferencia = DatosMulticonjunto.SUM;
		Multiset<Integer> ms = Multiset.of();
		for(int i=0; i<ls.size(); i++) {
			ms.add(DatosMulticonjunto.getNumeros().get(i), ls.get(i));
			diferencia-=DatosMulticonjunto.getNumeros().get(i)*ls.get(i);
		}
		return new SolucionMulticonjunto(diferencia,ms,ls);
	}
	

	public Integer size() {
		return ms.size();
	}
	
	@Override
	public int compareTo(SolucionMulticonjunto other) {
		return this.size().compareTo(other.size());
	}

	@Override
	public String toString() {
		Integer n = 0;
		String msg = "";
		for (int i = 0; i < solucion.size(); i++) {
			if (solucion.get(i) > 1) {
				msg += DatosMulticonjunto.getNumeros().get(i)+"*"+solucion.get(i)+" + ";
			} else if (solucion.get(i) == 1) {
				msg += DatosMulticonjunto.getNumeros().get(i)+" + ";
			}
			n+=solucion.get(i);
		}
		String aux = diferencia==0? "sol. exacta)":"sol. apox. con dif="+diferencia+")";
		if(msg.isEmpty()) {
			msg = "No se cogi� ning�n n�mero ("+aux+" -> Multiset = "+ms;
		} else {
			msg = msg.substring(0, msg.length() - 2)+" ("+n+" elems; "+aux+" -> Multiset = "+ms;
		}
		return msg;
	}

}

