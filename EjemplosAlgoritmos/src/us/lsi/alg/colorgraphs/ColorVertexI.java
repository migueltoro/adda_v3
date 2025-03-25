package us.lsi.alg.colorgraphs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.common.IntegerSet;
import us.lsi.common.List2;


public record ColorVertexI(Integer index, Map<Integer,Integer> cav,IntegerSet ca,
		Integer nc,IntegerSet cv) implements  ColorVertex {

	public static ColorVertex of(Integer index, Map<Integer, Integer> cav) {
		return new ColorVertexI(index, cav);
	}
	/**
	 * Constructor personalizado tomando sólo las propiedades básicas y 
	 * calculando las derivadas. Alternativamente se podría haber hecho en el método
	 * de factoría.
	 */	
	private ColorVertexI(Integer index, Map<Integer, Integer> cav) {		
		this(index,cav,AuxiliaryColor.ca(cav),AuxiliaryColor.nc(cav),AuxiliaryColor.cv(index,cav));
	}
	
	public Map<Integer, Integer> cav() {
		return Map.copyOf(this.cav);
	}
	
	@Override
	public Boolean goal() {
		return this.index() == DatosColor.n;
	}
	
	@Override
	public Boolean goalHasSolution() {
		return true;
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
		return ColorVertexI.of(this.index()+1,m);
	}

	@Override
	public ColorEdge edge(Integer a) {
		return ColorEdge.of(this,this.neighbor(a), a);
	}
	
}
