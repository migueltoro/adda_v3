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
	
	public static MochilaPD of() {
		return new MochilaPD();
	}
	
	private Integer maxValue;
	private MochilaProblem start;
	private Map<MochilaProblem,Spm> memory;
	private SolucionMochila solucion;
	private Long time;
	
	private MochilaPD() {
		super();
	}
	
	public Long time() {
		return time;
	}
	
	public SolucionMochila pd(Integer initialCapacity, Integer maxValue, SolucionMochila s) {
		this.time = System.nanoTime();
		this.maxValue = maxValue;
		this.start = MochilaProblem.of(0,initialCapacity);
		this.solucion = s;
		this.memory = new HashMap<>();
		pd(start,0,memory);
		SolucionMochila r = this.solucion();
		this.time = System.nanoTime() - this.time;
		return r;
	}
	
	private Spm pd(MochilaProblem vertex,Integer accumulateValue, Map<MochilaProblem,Spm> memory) {
		Spm r;
		if(memory.containsKey(vertex)) {
			r = memory.get(vertex);
		} else if(vertex.index() == DatosMochila.n) {
			r = Spm.of(null,0);
			memory.put(vertex,r);
			if(accumulateValue > this.maxValue) this.maxValue = accumulateValue;
		} else {
			List<Spm> soluciones = new ArrayList<>();
			for(Integer a:vertex.acciones()) {	
				Double cota = accumulateValue + Heuristica.cota(vertex,a);
				if(cota <= this.maxValue) continue;				
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
	
	public SolucionMochila solucion(){
		List<Integer> acciones = new ArrayList<>();
		MochilaProblem v = this.start;
		Spm s = this.memory.get(v);
		if(s == null) return this.solucion;
		while(s.a() != null) {
			acciones.add(s.a());
			v = v.vecino(s.a());	
			s = this.memory.get(v);
		}
		return SolucionMochila.of(this.start,acciones);
	}
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosMochila.datos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;
		MochilaProblem v1 = MochilaProblem.of(0, DatosMochila.capacidadInicial);
		SolucionMochila s = Heuristica.solucionVoraz(v1);	
		MochilaPD a = MochilaPD.of();
		a.pd(DatosMochila.capacidadInicial,Integer.MIN_VALUE,null);	
		System.out.println(a.time + " === "+  a.solucion());	
		a.pd(78,s.valor(),s);	
		System.out.println(a.time + " === "+  a.solucion());	
	}


}
