package us.lsi.alg.colorgraphs;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import us.lsi.graphs.SimpleEdge;
public class AuxiliaryColor {
	
	public static Set<Integer> vecinos(Integer vertex, Graph<Integer,SimpleEdge<Integer>> g){
		return g.edgesOf(vertex).stream().map(e->Graphs.getOppositeVertex(g,e,vertex)).collect(Collectors.toSet());
	}
	
	public static Set<Integer> coloresDeVecinos(Integer vertex, Graph<Integer,SimpleEdge<Integer>> g, Map<Integer,Integer> cav){
		Set<Integer> cv = vecinos(vertex,g)
				.stream()
				.filter(v -> cav.containsKey(v))
				.map(v -> cav.get(v))
				.collect(Collectors.toSet());
		return cv;
	}
	
	public static Boolean check(Graph<Integer,SimpleEdge<Integer>> g, Map<Integer,Integer> cav) {
		Integer n = g.vertexSet().size();
		Boolean r = true;
		for (int index = 0; index < n; index++) {	
			Integer c = cav.get(index);
			Set<Integer> cv = AuxiliaryColor.coloresDeVecinos(index, ColorVertex.graph,cav);
			if(cv.contains(c)){
				r = false;
				break;
			};			
		}
		return r;
	}
	
	public static void show(Graph<Integer,Double> g, Map<Integer,Integer> cav) {
		Integer n = g.vertexSet().size();
		Boolean r = true;
		for (int index = 0; index < n; index++) {	
			Integer c = cav.get(index);
			Set<Integer> cv = AuxiliaryColor.coloresDeVecinos(index, ColorVertex.graph,cav);
			System.out.println(String.format("V = %d, Cv = %d, Vs = %15s, CVs = %15s",
					index,
					c,
					AuxiliaryColor.vecinos(index, ColorVertex.graph),
					cv));
			r = !cv.contains(c);			
		}
		System.out.println(String.format("Result = %s",r));		
	}
	

}
