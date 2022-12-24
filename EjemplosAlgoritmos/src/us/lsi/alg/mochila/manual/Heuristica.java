package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Heuristica {

	private static record HMochila(Integer index, Double capacidadRestante) {		
		
		public static HMochila of(Integer index, Double capacidadRestante) {
			return new HMochila(index, capacidadRestante);
		}
		
		public Double heuristicAction() {
			return Math.min(this.capacidadRestante()/DatosMochila.peso(this.index()),DatosMochila.numMaxDeUnidades(index));
		}
		
		public HMochila vecino(Double a) {
			return HMochila.of(this.index() + 1, this.capacidadRestante() - a * DatosMochila.peso(this.index()));
		}
		
	}

	
	public static Integer voraz(MochilaProblem v1) {
		MochilaProblem v = v1;
		Integer r = 0;
		while (v.capacidadRestante() > 0 && v.index() < DatosMochila.n) {
			Integer a = v.greedyAction();
			r = r + a * DatosMochila.valor(v.index());
			v = v.vecino(a);
		}
		return r;
	}
	
	public static SolucionMochila solucionVoraz(MochilaProblem v1) {
		List<Integer> acciones = new ArrayList<>();
		MochilaProblem v = v1;
		while (v.capacidadRestante() > 0 && v.index() < DatosMochila.n) {
			Integer a = v.greedyAction();
			acciones.add(a);
			v = v.vecino(a);
		}
		return SolucionMochila.of(v1, acciones);
	}
	
	
	public static Double heuristica(MochilaProblem v1) {
		HMochila v = HMochila.of(v1.index(),v1.capacidadRestante().doubleValue());
		Double r = 0.;
		while (v.capacidadRestante() > 0 && v.index() < DatosMochila.n) {
			Double a = v.heuristicAction();
			r = r + a * DatosMochila.valor(v.index());
			v = v.vecino(a);
		}
		return r;
	}
	
	public static Integer heuristica2(MochilaProblem v1) {
		MochilaProblem v = v1;
		Integer r = 0;
		while (v.capacidadRestante() > 0 && v.index() < DatosMochila.n) {
			Integer a = v.greedyAction()+1;
			r = r + a * DatosMochila.valor(v.index());
			Integer cr = v.capacidadRestante() - a * DatosMochila.peso(v.index());
			v = MochilaProblem.of(v.index() + 1, cr>=0?cr:0);
		}
		return r;
	}
	
	
	public static Double cota(MochilaProblem v1, Integer a) {
		MochilaProblem v2 = v1.vecino(a);
		return a*DatosMochila.valor(v1.index()).doubleValue()+heuristica(v2);
	}
	
	public static Double cota2(MochilaProblem v1, Integer a) {
		MochilaProblem v2 = v1.vecino(a);
		return a*DatosMochila.valor(v1.index()).doubleValue()+heuristica2(v2);
	}
	
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.datos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;
		MochilaProblem v1 = MochilaProblem.of(0, DatosMochila.capacidadInicial);
		Integer r = Heuristica.voraz(v1);
		Double s1 = Heuristica.heuristica(v1);
		Integer s2 = Heuristica.heuristica2(v1);
		SolucionMochila s = Heuristica.solucionVoraz(v1);
		System.out.printf("%d,%.2f,%d\n",r,s1,s2);
		System.out.printf("%s",s);
	}

}

