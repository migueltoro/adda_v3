package us.lsi.alg.monedas.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import us.lsi.common.String2;

public class MonedaPD {
	
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
	public static MonedaProblem start;
	public static Map<MonedaProblem,Spm> memory;
	
	public static SolucionMonedas pd(Integer initialValue) {
		MonedaPD.maxValue = Integer.MIN_VALUE;
		MonedaPD.start = MonedaProblem.of(0,initialValue);
		MonedaPD.memory = new HashMap<>();
		pd(start,0,memory);
		return MonedaPD.solucion();
	}
	
	public static Spm pd(MonedaProblem vertex,Integer accumulateValue, Map<MonedaProblem,Spm> memory) {
		Spm r;
		if(memory.containsKey(vertex)) {
			r = memory.get(vertex);
		} else if(vertex.index() == MonedaProblem.n) {
			r = null;
			if (MonedaProblem.goalHasSolution().test(vertex)) {
				r = Spm.of(null, 0);
				memory.put(vertex, r);
				if (accumulateValue > MonedaPD.maxValue) MonedaPD.maxValue = accumulateValue;
			}
			memory.put(vertex,r);
		} else {
			List<Spm> soluciones = new ArrayList<>();
			for(Integer a:vertex.acciones()) {	
				Double cota = accumulateValue + MonedasHeuristica.cota(vertex,a);
				if(cota <= MonedaPD.maxValue) continue;	
				Integer ac = accumulateValue+a*Moneda.valor(vertex.index());
				Spm s = pd(vertex.vecino(a),ac,memory);
				if(s!=null) {
					Spm sp = Spm.of(a,s.weight()+a*Moneda.valor(vertex.index()));
					soluciones.add(sp);
				}
			}
			r = soluciones.stream().filter(s->s != null).max(Comparator.naturalOrder()).orElse(null);
			memory.put(vertex,r);
		}
		return r;
	}
	
	public static SolucionMonedas solucion(){
		List<Integer> acciones = new ArrayList<>();
		MonedaProblem v = MonedaPD.start;
		Spm s = MonedaPD.memory.get(v);
		while(s.a() != null) {
			acciones.add(s.a());
			v = v.vecino(s.a());	
			s = MonedaPD.memory.get(v);
		}
		return SolucionMonedas.of(acciones);
	}
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaProblem.datosIniciales("ficheros/monedas3.txt", 36);
		String2.toConsole("%s",Moneda.monedas);
//		MonedaProblem v1 = MonedaProblem.of(0, MonedaProblem.valorInicial);
//		SolucionMonedas s = MonedasHeuristica.solucionVoraz(v1);	
		MonedaPD.pd(MonedaProblem.valorInicial);	
		System.out.println(MonedaPD.solucion());		
	}


}
