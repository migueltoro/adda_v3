package us.lsi.alg.reinas.manual;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import us.lsi.common.List2;

public class ReinasBTRandom {
	
	public static class StateReinas {
		ReinasProblem vertice;
		List<ReinasProblem> vertices;
		
		public StateReinas(ReinasProblem vertice, List<ReinasProblem> vertices) {
			super();
			this.vertice = vertice;
			this.vertices = vertices;
		}

		void forward(Integer a) {
			ReinasProblem nv = vertice.vecino(a);
			this.vertices.add(nv);
			this.vertice = nv;
		}
		
		void back(Integer a) {
			this.vertices.remove(this.vertices.size()-1);
			this.vertice = this.vertices.get(this.vertices.size()-1);		
		}
		
		SolucionReinas solucion() {
			return SolucionReinas.of(this.vertice);
		}
		
	}
	
	public static ReinasProblem start;
	public static StateReinas estado;
	public static Set<SolucionReinas> soluciones;
	public static Integer threshold;
	public static Integer iteraciones;
	
	public static void btm(Integer nr) {
		ReinasProblem.n = nr;
		ReinasBTRandom.iteraciones = 0;
		do {
			ReinasBTRandom.start = ReinasProblem.first();
			List<ReinasProblem> va = new ArrayList<>();
			va.add(ReinasBTRandom.start);
			ReinasBTRandom.estado = new StateReinas(ReinasBTRandom.start,va);
			ReinasBTRandom.soluciones = new HashSet<>();
			ReinasBTRandom.btm();
			ReinasBTRandom.iteraciones++;
		}while(ReinasBTRandom.soluciones.size()==0);
	}
	
	public static void btm() {
		if(ReinasBTRandom.estado.vertice.index() == ReinasProblem.n) {
			SolucionReinas s = ReinasBTRandom.estado.solucion();
			if(s != null) ReinasBTRandom.soluciones.add(s);
		} else {
			List<Integer> alternativas = ReinasBTRandom.estado.vertice.acciones();
			if(ReinasBTRandom.estado.vertice.size() > ReinasBTRandom.threshold) {
				alternativas = List2.randomUnitary(alternativas);
			}
			for(Integer a:alternativas) {	
				ReinasBTRandom.estado.forward(a);
				ReinasBTRandom.btm();  
				ReinasBTRandom.estado.back(a);
			}
		}
	}
	
	public static SolucionReinas solucion() {
		return ReinasBTRandom.soluciones.stream().findFirst().get();
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Integer n = 120;
		ReinasBTRandom.threshold = 15;
		long startTime = System.nanoTime();
		ReinasBTRandom.btm(n);	
		long endTime = System.nanoTime() - startTime;
		System.out.println("1 = "+endTime);
		System.out.println("Iteraciones = "+ReinasBTRandom.iteraciones);
		System.out.println(ReinasBTRandom.solucion());
	}

}
