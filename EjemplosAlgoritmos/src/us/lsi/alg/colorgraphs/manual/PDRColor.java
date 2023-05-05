package us.lsi.alg.colorgraphs.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.alg.colorgraphs.ColorVertex;
import us.lsi.alg.colorgraphs.DatosColor;
import us.lsi.alg.colorgraphs.SolucionColor;

public class PDRColor {
	
	public static record Spm(Integer a,Integer weight) implements Comparable<Spm> {
		
		public static Spm of(Integer a, Integer weight) {
			return new Spm(a, weight);
		}
		
		@Override
		public int compareTo(Spm sp) {
			return this.weight.compareTo(sp.weight);
		}
	}
	
	public static PDRColor of() {
		return new PDRColor();
	}
	
	private static Integer minValue;
	private ColorVertex start;
	private Map<ColorVertex,Spm> memory;
	
	private PDRColor() {
		super();
	}

	public void  pdr(ColorVertex vertex) {
		PDRColor.minValue = DatosColor.m;
		this.start = vertex;
		this.memory = new HashMap<>();
		pdr(start,memory);
	}
	
	public Spm pdr(ColorVertex vertex, Map<ColorVertex,Spm> memory) {
		Spm r= null;
		if(memory.containsKey(vertex)) {
			r = memory.get(vertex);
		} else if(vertex.index() == DatosColor.n) {
			r = Spm.of(null,0);
			memory.put(vertex,r);
			Integer accumulateValue = vertex.nc();
			if(accumulateValue > PDRColor.minValue) PDRColor.minValue = accumulateValue;
		} else {
			List<Spm> soluciones = new ArrayList<>();
			for(Integer a:vertex.actions()) {	
				Integer cota = vertex.neighbor(a).nc();
				if(cota >= PDRColor.minValue) continue;				
				Spm s = pdr(vertex.neighbor(a),memory);
				if(s!=null) {
					Spm sp = Spm.of(a,s.weight());
					soluciones.add(sp);
				}
			}
			if(!soluciones.isEmpty()) {
				r = soluciones.stream().min(Comparator.naturalOrder()).orElse(null);
			 	memory.put(vertex,r);
			}
		}
		return r;
	}
	
	public SolucionColor solucion(){
		List<Integer> acciones = new ArrayList<>();
		ColorVertex v = this.start;
		Spm s = this.memory.get(v);
		while(s.a() != null) {
			acciones.add(s.a());
			v = v.neighbor(s.a());	
			s = this.memory.get(v);
		}
		return SolucionColor.of(acciones);
	}

	public static void main(String[] args) {
		
		DatosColor.data(4,"ficheros/andalucia.txt");
		
		PDRColor p = PDRColor.of();
		
		p.pdr(ColorVertex.first());
		
		System.out.println("BT = "+ p.solucion());

	}

	

}
