package us.lsi.alg.subconjuntos.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SubconjuntosPD {
	
	public static record Sps(Integer a, Double weight) implements Comparable<Sps> {

		public static Sps of(Integer a, Double weight) {
			return new Sps(a, weight);
		}

		@Override
		public int compareTo(Sps sp) {
			return this.weight.compareTo(sp.weight);
		}
	}
	
	public static Double minValue = Double.MAX_VALUE;
	public static SubconjuntosProblem start;
	public static Map<SubconjuntosProblem,Sps> memory;
	
	public static SolucionSubconjuntos pd() {
		SubconjuntosPD.minValue = Double.MAX_VALUE;
		SubconjuntosPD.start = SubconjuntosProblem.initial();
		SubconjuntosPD.memory = new HashMap<>();
		pd(start,0.,memory);
		return SubconjuntosPD.solucion();
	}
	
	public static SolucionSubconjuntos pd(SolucionSubconjuntos s) {
		SubconjuntosPD.minValue = s.peso();
		SubconjuntosPD.start = SubconjuntosProblem.initial();
		SubconjuntosPD.memory = new HashMap<>();
		pd(start,0.,memory);
		if(SubconjuntosPD.memory.get(start) != null) return solucion();
		else return s;
	}
	
	public static Sps pd(SubconjuntosProblem vertex,Double accumulateValue, Map<SubconjuntosProblem,Sps> memory) {
		Sps r;
		if(memory.containsKey(vertex)) {
			r = memory.get(vertex);
		} else if(vertex.indice() == DatosSubconjuntos.NUM_SC) {
			r = Sps.of(null,0.);
			memory.put(vertex,r);
			if(accumulateValue < SubconjuntosPD.minValue) SubconjuntosPD.minValue = accumulateValue;
		} else {
			List<Sps> soluciones = new ArrayList<>();
			for(Integer a:vertex.actions()) {	
				Double cota = accumulateValue + SubconjuntosHeuristic.cota(vertex,a);
				if(cota >= SubconjuntosPD.minValue) continue;				
				Sps s = pd(vertex.neighbor(a),accumulateValue+a*DatosSubconjuntos.peso(vertex.indice()),memory);
				if(s!=null) {
					Sps sp = Sps.of(a,s.weight()+a*DatosSubconjuntos.peso(vertex.indice()));
					soluciones.add(sp);
				}
			}
			r = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
			if(r!=null) memory.put(vertex,r);
		}
		return r;
	}
	
	public static SolucionSubconjuntos solucion(){
		List<Integer> acciones = new ArrayList<>();
		SubconjuntosProblem v = SubconjuntosPD.start;
		Sps s = SubconjuntosPD.memory.get(v);
		while(s.a() != null) {
			acciones.add(s.a());
			v = v.neighbor(s.a());	
			s = SubconjuntosPD.memory.get(v);
		}
		return SolucionSubconjuntos.of(acciones);
	}
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		for (Integer id_fichero = 1; id_fichero < 3; id_fichero++) {
			DatosSubconjuntos.iniDatos("ficheros/subconjuntos" + id_fichero + ".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
//			DatosSubconjuntos.toConsole();
			
			SolucionSubconjuntos mv = SubconjuntosHeuristic.solucionVoraz(SubconjuntosProblem.initial(),DatosSubconjuntos.NUM_SC);
			SolucionSubconjuntos s = SubconjuntosPD.pd(mv);
			System.out.println(s);
		}
	}


}
