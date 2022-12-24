package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MochilaPD {
	
	public static record Spm(Integer a,Integer weight) implements Comparable<Spm> {
		
		public static Spm of(Integer a, Integer weight) {
			return new Spm(a, weight);
		}
		
		@Override
		public int compareTo(Spm sp) {
			return this.weight.compareTo(sp.weight);
		}
	}
	
	public static Integer maxValue = Integer.MIN_VALUE;
	public static MochilaProblem start;
	public static Map<MochilaProblem,Spm> memory;
	
	public static SolucionMochila pd(Integer initialCapacity) {
		MochilaPD.maxValue = Integer.MIN_VALUE;
		MochilaPD.start = MochilaProblem.of(0,initialCapacity);
		MochilaPD.memory = new HashMap<>();
		pd(start,0,memory);
		return MochilaPD.solucion();
	}
	
	public static SolucionMochila pd(Integer initialCapacity, Integer maxValue, SolucionMochila s) {
		MochilaPD.maxValue = maxValue;
		MochilaPD.start = MochilaProblem.of(0,initialCapacity);
		MochilaPD.memory = new HashMap<>();
		pd(start,0,memory);
		if(MochilaPD.memory.get(start) == null) return s;
		else return MochilaPD.solucion();
	}
	
	public static Spm pd(MochilaProblem vertex,Integer accumulateValue, Map<MochilaProblem,Spm> memory) {
		Spm r;
		if(memory.containsKey(vertex)) {
			r = memory.get(vertex);
		} else if(vertex.index() == DatosMochila.n) {
			r = Spm.of(null,0);
			memory.put(vertex,r);
			if(accumulateValue > MochilaPD.maxValue) MochilaPD.maxValue = accumulateValue;
		} else {
			List<Spm> soluciones = new ArrayList<>();
			for(Integer a:vertex.acciones()) {	
				Double cota = accumulateValue + Heuristica.cota(vertex,a);
				if(cota <= MochilaPD.maxValue) continue;				
				Spm s = pd(vertex.vecino(a),accumulateValue+a*DatosMochila.valor(vertex.index()),memory);
				if(s!=null) {
					Spm sp = Spm.of(a,s.weight()+a*DatosMochila.valor(vertex.index()));
					soluciones.add(sp);
				}
			}
			r = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
			if(r!=null) memory.put(vertex,r);
		}
		return r;
	}
	
	public static SolucionMochila solucion(){
		List<Integer> acciones = new ArrayList<>();
		MochilaProblem v = MochilaPD.start;
		Spm s = MochilaPD.memory.get(v);
		while(s.a() != null) {
			acciones.add(s.a());
			v = v.vecino(s.a());	
			s = MochilaPD.memory.get(v);
		}
		return SolucionMochila.of(MochilaPD.start,acciones);
	}
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.datos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;
		MochilaProblem v1 = MochilaProblem.of(0, DatosMochila.capacidadInicial);
		SolucionMochila s = Heuristica.solucionVoraz(v1);	
		MochilaPD.pd(78);	
		System.out.println(MochilaPD.solucion());	
		MochilaPD.pd(78,s.valor(),s);	
		System.out.println(MochilaPD.solucion());	
	}


}
