package us.lsi.alg.typ.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import us.lsi.alg.typ.DatosTyP;
import us.lsi.alg.typ.SolucionTyP;
import us.lsi.alg.typ.TyPVertex;
import us.lsi.alg.typ.TyPVertexI;


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
	
	public static TyPPD of() {
		return new TyPPD();
	}
	
	
	private TyPPD() {
		super();
	}

	private SolucionTyP solucion;
	private TyPVertex start;
	private Map<TyPVertex,Sptp> memory;
	private Long time;
	
	public Long time() {
		return time;
	}
	
	public SolucionTyP pdr(TyPVertex start) {
		this.time = System.nanoTime();
		this.start = start;
		this.start = TyPVertex.first();
		this.memory = new HashMap<>();
		pd(start,0,memory);
		this.time = System.nanoTime() - this.time;
		if(memory.get(start) != null) return this.solucion();
		else return this.solucion;
	}
	
	private Sptp pd(TyPVertex vertex,Integer accumulateValue, Map<TyPVertex,Sptp> memory) {
		Sptp r=null;
		if(memory.containsKey(vertex)) {
			r = memory.get(vertex);
		} else if(vertex.index() == DatosTyP.n) {
			r = Sptp.of(null,vertex.maxCarga().intValue());
			memory.put(vertex,r);
		} else {
			List<Sptp> soluciones = new ArrayList<>();
			for (Integer a : vertex.actions()) {
				TyPVertex vecino = vertex.neighbor(a);
				Sptp s = pd(vecino, vecino.maxCarga().intValue(), memory);
				if (s != null) {
					Sptp sp = Sptp.of(a, s.weight());
					soluciones.add(sp);
				}
			}
			r = soluciones.stream().min(Comparator.naturalOrder()).orElse(null);
			memory.put(vertex, r);
		}
		return r;
	}
	
	public SolucionTyP solucion(){
		List<Integer> acciones = new ArrayList<>();
		TyPVertex v = this.start;
		Sptp s = this.memory.get(v);
		while(s.a() != null) {
			acciones.add(s.a());
			v = v.neighbor(s.a());	
			s = this.memory.get(v);
		}
		return SolucionTyP.of(v,acciones);
	}
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosTyP.datos("ficheros/tareas.txt",5);
		DatosTyP.toConsole();
		TyPVertexI v1 = TyPVertexI.first();
		SolucionTyP s = Heuristica.solucionVoraz(v1);
		System.out.println("Voraz = "+s);
		TyPPD a = TyPPD.of();
		a.pdr(v1);
		System.out.println("1 = "+a.solucion());
	}

}
