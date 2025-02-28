package us.lsi.tsp;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.IntegerVertexGraphView;

public class DatosTspAG implements PermutationData<List<Ciudad>>{
	
	public static IntegerVertexGraphView<Ciudad,Carretera> graph;
	public static Integer n;
	
	public DatosTspAG(String fichero) throws IOException {
		tsp(fichero);
	}
	
	public static void tsp(String fichero) throws IOException {	
		
		SimpleWeightedGraph<Ciudad,Carretera> g =  
				GraphsReader.newGraph(fichero,
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::km);	
		graph = IntegerVertexGraphView.of(g);
		n = graph.vertexSet().size();	
	}

	@Override
	public Integer size() {
		return DatosTspAG.n;
	}
	
	private static Double edgeCost(Integer i, Integer j) {
		Double r = 10000.;
		if (DatosTspAG.graph.containsEdge(i,j)) {
			r = DatosTspAG.graph.getEdgeWeight(i,j);
		}
		return r;
	}

	@Override
	public Double fitnessFunction(List<Integer> cr) {
		return -IntStream.range(0,n).boxed()
				.mapToDouble(i->edgeCost(cr.get(i),cr.get((i+1)%n)))
				.sum();
	}
	

	@Override
	public List<Ciudad> solution(List<Integer> cr) {
		return cr.stream().map(i->graph.vertex(i)).collect(Collectors.toList());
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.Permutation;
	}
}
