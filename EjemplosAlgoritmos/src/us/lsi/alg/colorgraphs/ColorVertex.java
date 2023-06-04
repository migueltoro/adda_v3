package us.lsi.alg.colorgraphs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import us.lsi.common.IntegerSet;
import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;
import us.lsi.streams.Collectors2;


public record ColorVertex(Integer index, Map<Integer,Integer> cav) 
           implements VirtualVertex<ColorVertex,ColorEdge,Integer>{

	public static ColorVertex of(Integer index, Map<Integer, Integer> cav) {
		return new ColorVertex(index, cav);
	}
	
	public static ColorVertex first() {
		return new ColorVertex(0,new HashMap<>());
	}
	
	public static ColorVertex last() {
		return new ColorVertex(DatosColor.n,new HashMap<>());
	}
	
	public static Predicate<ColorVertex> goal() {
		return v -> v.index() == DatosColor.n;
	}
	
	public IntegerSet ca(){
		return this.cav().values().stream().distinct().collect(Collectors2.toIntegerSet());
	}
	
	public Integer nc() {
		return this.ca().size();
	}
	
	public IntegerSet cv(){
		IntegerSet r;
		if (index < DatosColor.n) {
			r = AuxiliaryColor.coloresDeVecinos(index(), DatosColor.graph, cav());
		} else {
			r = IntegerSet.empty();
		}
		return r;
	}

	@Override
	public Boolean isValid() {
		return this.index()>=0 && this.index() <= DatosColor.n;
	}


	@Override
	public List<Integer> actions() {		
		List<Integer> r = List2.difference(DatosColor.colors,this.cv());
		return r;
	}
	
	public Integer greedyAction() {
		List<Integer> r = List2.difference(this.ca(),this.cv());
		if(r.isEmpty()) r = List2.difference(DatosColor.colors,this.cv());
		return List2.randomUnitary(r).get(0);
	}
	
	public ColorEdge greedyEdge() {
		return this.edge(this.greedyAction());
	}


	@Override
	public ColorVertex neighbor(Integer a) {
		Map<Integer,Integer> m = new HashMap<>(this.cav());
		m.put(this.index(),a);
		return ColorVertex.of(this.index()+1,m);
	}


	@Override
	public ColorEdge edge(Integer a) {
		return ColorEdge.of(this,this.neighbor(a), a);
	}
	
}
