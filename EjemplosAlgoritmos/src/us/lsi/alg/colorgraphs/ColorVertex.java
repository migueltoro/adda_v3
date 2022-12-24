package us.lsi.alg.colorgraphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.Graph;

import us.lsi.common.List2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.virtual.VirtualVertex;


public record ColorVertex(Integer index, Map<Integer,Integer> cav) 
           implements VirtualVertex<ColorVertex,ColorEdge,Integer>{

	public static ColorVertex of(Integer index, Map<Integer, Integer> cav) {
		return new ColorVertex(index, cav);
	}
	
	public static ColorVertex first() {
		return new ColorVertex(0,new HashMap<>());
	}
	
	public static ColorVertex last() {
		return new ColorVertex(ColorVertex.n,new HashMap<>());
	}
	
	public static Predicate<ColorVertex> goal() {
		return v -> v.index() == ColorVertex.n;
	}

	
	public static void data(Integer m, Graph<Integer,SimpleEdge<Integer>> graph) {
		ColorVertex.m = m;; 
		ColorVertex.n = graph.vertexSet().size(); 
		ColorVertex.graph = graph;
		ColorVertex.colors = List2.rangeList(0, ColorVertex.m);
	}
	

	public Set<Integer> ca(){
		return this.cav().values().stream().distinct().collect(Collectors.toSet());
	}
	
	public Integer nc() {
		return this.ca().size();
	}
	
	public Set<Integer> cv(){
		Set<Integer> r;
		if (index < ColorVertex.n) {
			r = AuxiliaryColor.coloresDeVecinos(index(), ColorVertex.graph, cav());
		} else {
			r = new HashSet<>();
		}
		return r;
	}

	public static Integer m; // número maximo de colores, obtenido previamente mediante un camino voraz
	public static Integer n; // número de vértices
	public static Graph<Integer,SimpleEdge<Integer>> graph;
	public static List<Integer> colors;



	@Override
	public Boolean isValid() {
		return this.index()>=0 && this.index() <= ColorVertex.n;
	}


	@Override
	public List<Integer> actions() {		
		List<Integer> r = List2.difference(ColorVertex.colors,this.cv());
		return r;
	}
	
	public Integer greedyAction() {
		List<Integer> r = List2.difference(this.ca(),this.cv());
		if(r.isEmpty()) r = List2.difference(ColorVertex.colors,this.cv());
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
