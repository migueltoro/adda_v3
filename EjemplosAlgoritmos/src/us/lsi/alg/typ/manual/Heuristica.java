package us.lsi.alg.typ.manual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Heuristica {
	
	

	public static Integer heuristica(TyPProblem v1) {
		return v1.maxCarga();
	}
	
	public static Integer cota(TyPProblem v1, Integer a) {
		TyPProblem v2 = v1.vecino(a);
		return v2.maxCarga();
	}

	public static Integer voraz(TyPProblem v1) {
		TyPProblem v = v1;
		Integer r = 0;
		while (v.index() < DatosTyP.n) {
			Integer a = v.greadyAction();
			r = v.maxCarga();
			v = v.vecino(a);
		}
		return r;
	}
	
	public static SolucionTyP solucionVoraz(TyPProblem v1) {
		TyPProblem v = v1;
		List<Integer> acciones = new ArrayList<>();
		while (v.index() < DatosTyP.n) {
			Integer a = v.greadyAction();
			acciones.add(a);
			v = v.vecino(a);
		}
		return SolucionTyP.of(v1,acciones);
	}
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosTyP.datos("ficheros/tareas.txt",5);
		TyPProblem e1 = TyPProblem.first();
		System.out.println(DatosTyP.tareas);
		Integer c = voraz(e1);
		System.out.println(c);
		System.out.println(solucionVoraz(e1));
		System.out.println(DatosTyP.n+","+DatosTyP.m);
		System.out.println(SolucionTyP.of(e1,Arrays.asList(0, 1, 2, 3, 4, 3, 4, 0, 1, 2, 2, 4)));
	}
}
