package us.lsi.alg.typ.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class TyPPD {

	public static record Sptp(Integer a,Integer weight) implements Comparable<Sptp> {
		
		public static Sptp of(Integer a, Integer weight) {
			return new Sptp(a, weight);
		}
		
		@Override
		public int compareTo(Sptp sp) {
			return this.weight.compareTo(sp.weight);
		}
	}
	
	public static Integer minValue = Integer.MAX_VALUE;
	public static TyPProblem start;
	public static Map<TyPProblem,Sptp> memory;
	
	public static SolucionTyP pdr() {
		TyPPD.minValue = Integer.MAX_VALUE;
		TyPPD.start = TyPProblem.first();
		TyPPD.memory = new HashMap<>();
		pd(start,0,memory);
		return TyPPD.solucion();
	}
	
	public static SolucionTyP pdr(SolucionTyP s) {
		TyPPD.minValue = s.maxCarga();
		TyPPD.start = TyPProblem.first();
		TyPPD.memory = new HashMap<>();
		pd(start,0,memory);
		if(memory.get(start) != null) return TyPPD.solucion();
		else return s;
	}
	
	private static Sptp pd(TyPProblem vertex,Integer accumulateValue, Map<TyPProblem,Sptp> memory) {
		Sptp r;
		if(memory.containsKey(vertex)) {
			r = memory.get(vertex);
		} else if(vertex.index() == DatosTyP.n) {
			r = Sptp.of(null,vertex.maxCarga());
			memory.put(vertex,r);
			if(accumulateValue < TyPPD.minValue) {
				TyPPD.minValue = accumulateValue;
			}
		} else {
			List<Sptp> soluciones = new ArrayList<>();
			for(Integer a:vertex.acciones()) {	
				Integer cota = Heuristica.cota(vertex,a);
				if(cota >= TyPPD.minValue) continue;	
				TyPProblem vecino = vertex.vecino(a);
				Sptp s = pd(vecino,vecino.maxCarga(),memory);
				if(s!=null) {
					Sptp sp = Sptp.of(a,s.weight());
					soluciones.add(sp);
				}
			}
			r = soluciones.stream().min(Comparator.naturalOrder()).orElse(null);
			if(r!=null) memory.put(vertex,r);
		}
		return r;
	}
	
	public static SolucionTyP solucion(){
		List<Integer> acciones = new ArrayList<>();
		TyPProblem v = TyPPD.start;
		Sptp s = TyPPD.memory.get(v);
		while(s.a() != null) {
			acciones.add(s.a());
			v = v.vecino(s.a());	
			s = TyPPD.memory.get(v);
		}
		return SolucionTyP.of(TyPPD.start,acciones);
	}
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosTyP.datos("ficheros/tareas.txt",5);
		DatosTyP.toConsole();
		TyPProblem v1 = TyPProblem.first();
		SolucionTyP s = Heuristica.solucionVoraz(v1);	
		long startTime = System.nanoTime();
		System.out.println(TyPPD.pdr());
		long endTime = System.nanoTime() - startTime;
		System.out.println("1 = "+endTime);
		startTime = System.nanoTime();
		System.out.println(TyPPD.pdr(s));
		long endTime2 = System.nanoTime() - startTime;
		System.out.println("2 = "+1.*endTime2/endTime);
	}



}
