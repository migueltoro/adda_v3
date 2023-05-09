package us.lsi.alg.monedas.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import us.lsi.alg.monedas.DatosMonedas;
import us.lsi.alg.monedas.MonedaVertex;
import us.lsi.alg.monedas.SolucionMonedas;
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
	
	public static MonedaPD of() {
		return new MonedaPD();
	}
	
	private Integer maxValue;
	private MonedaVertex start;
	private Map<MonedaVertex,Spm> memory;
	private SolucionMonedas solucion;
	private Long time;
	
	private MonedaPD() {
		super();
	}
	
	public Long time() {
		return time;
	}

	public SolucionMonedas pd(Integer initialValue,Integer maxValue,SolucionMonedas s) {
		this.time = System.nanoTime();
		this.maxValue = maxValue;
		this.solucion = s;
		this.start = MonedaVertex.of(0,initialValue);
		this.memory = new HashMap<>();
		pd(start,0,memory);
		this.time = System.nanoTime() - this.time;
		return this.solucion();
	}
	
	private Spm pd(MonedaVertex vertex,Integer accumulateValue, Map<MonedaVertex,Spm> memory) {
		Spm r=null;
		if(memory.containsKey(vertex)) {
			r = memory.get(vertex);
		} else if(vertex.index() == DatosMonedas.n) {
			r = null;
			if (MonedaVertex.goalHasSolution().test(vertex)) {
				r = Spm.of(null, 0);
				memory.put(vertex, r);
				if (this.maxValue == null || accumulateValue > this.maxValue) this.maxValue = accumulateValue;
			}
			memory.put(vertex,r);
		} else {
			List<Spm> soluciones = new ArrayList<>();
			for(Integer a:vertex.actions()) {	
				Double cota = accumulateValue + MonedasHeuristica.cota(vertex,a);
				if(this.maxValue != null && cota <= this.maxValue) continue;	
				Integer ac = accumulateValue+a*DatosMonedas.valor(vertex.index());
				Spm s = pd(vertex.neighbor(a),ac,memory);
				if(s!=null) {
					Spm sp = Spm.of(a,s.weight()+a*DatosMonedas.valor(vertex.index()));
					soluciones.add(sp);
				}
			}
			if (!soluciones.isEmpty()) {
				r = soluciones.stream().filter(s -> s != null).max(Comparator.naturalOrder()).orElse(null);
				memory.put(vertex, r);
			}
		}
		return r;
	}
	
	public SolucionMonedas solucion(){
		List<Integer> acciones = new ArrayList<>();
		MonedaVertex v = this.start;
		Spm s = this.memory.get(v);
		if(s==null) return this.solucion;
		while(s.a() != null) {
			acciones.add(s.a());
			v = v.neighbor(s.a());	
			s = this.memory.get(v);
		}
		return SolucionMonedas.of(acciones);
	}
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosMonedas.datosIniciales("ficheros/monedas3.txt", 400);
		String2.toConsole("%s",DatosMonedas.monedas);
		MonedaVertex v1 = MonedaVertex.first();
		SolucionMonedas s = MonedasHeuristica.solucionVoraz(v1);
		String2.toConsole("%s",s);
		MonedaPD a = MonedaPD.of();
		a.pd(DatosMonedas.valorInicial,s!=null?s.peso():null,s);	
		System.out.println(a.time());
		System.out.println(a.solucion());		
	}

	

}
