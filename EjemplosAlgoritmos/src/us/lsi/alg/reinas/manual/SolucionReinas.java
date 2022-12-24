package us.lsi.alg.reinas.manual;

import java.util.List;
import java.util.stream.Collectors;

public record SolucionReinas(Integer errores, List<Integer> filasOcupadas) {

	public static SolucionReinas of(ReinasProblem p) {	
		SolucionReinas r = null;
		Integer errores = SolucionReinas.errores(p);
		if(errores == 0) r = new SolucionReinas(errores,p.fo());
		return r;
	}

	private static Integer errores(ReinasProblem p) {
		Integer error1 = ReinasProblem.n - p.fo().stream().collect(Collectors.toSet()).size();
		Integer error2 = ReinasProblem.n - p.dpo().size();
		Integer error3 = ReinasProblem.n - p.dso().size();
		return error1+error2+error3;
	}
	
}
