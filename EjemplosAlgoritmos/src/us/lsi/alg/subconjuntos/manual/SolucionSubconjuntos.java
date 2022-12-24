package us.lsi.alg.subconjuntos.manual;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record SolucionSubconjuntos(Double peso, Set<String> conjuntos, Boolean cubre) {

	public static SolucionSubconjuntos of(List<Integer> ls) {
		Set<String> cs = new HashSet<>();
		Double ps = 0.;
		Set<Integer> s = new HashSet<>();
		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i) == 1) {
				cs.add(DatosSubconjuntos.nombre(i));
				ps += DatosSubconjuntos.peso(i);
				s.addAll(DatosSubconjuntos.conjunto(i));
			}
		}
		Boolean c = s.equals(DatosSubconjuntos.universo());
		return new SolucionSubconjuntos(ps,cs,c);
	}

}

