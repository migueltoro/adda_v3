package us.lsi.alg.floyd;


import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.alg.DP;
import us.lsi.graphs.alg.DP.PDType;
import us.lsi.graphs.views.IntegerVertexGraphView;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.SimpleVirtualHyperGraph;

public class TestFloyd {
	
	public static Ciudad ciudad(Graph<Ciudad,Carretera> graph, String nombre) {
		return graph.vertexSet().stream().filter(c->c.nombre().equals(nombre)).findFirst().get();
	}
	
	public static SimpleWeightedGraph<Ciudad, Carretera> leeDatos(String fichero) {
		SimpleWeightedGraph<Ciudad, Carretera> graph = GraphsReader.newGraph(fichero, 
				Ciudad::ofFormat, 
				Carretera::ofFormat,
				Graphs2::simpleWeightedGraph, 
				Carretera::km);
		return graph;
	}
	
	public static void main(String[] args) {
		
		Locale.setDefault(new Locale("en", "US"));
		
		SimpleWeightedGraph<Ciudad, Carretera> graph = leeDatos("./ficheros/andalucia.txt");
		IntegerVertexGraphView<Ciudad,Carretera> graph2 = IntegerVertexGraphView.of(graph);
		
		System.out.println(graph);
		System.out.println(graph2);
		
		Integer origen = graph2.getIndex(ciudad(graph,"Sevilla"));
		Integer destino = graph2.getIndex(ciudad(graph,"Almeria"));
		
		FloydVertex.graph = graph2;
		FloydVertex.n = graph2.vertexSet().size();
		FloydVertex p = FloydVertex.initial(origen,destino);
		
		SimpleVirtualHyperGraph<FloydVertex,FloydEdge,Boolean> graph3 = 
				SimpleVirtualHyperGraph.simpleVirtualHyperGraph(p);
		
		DP<FloydVertex, FloydEdge, Boolean> a = DP.dynamicProgrammingSearch(graph3,PDType.Min);
		
		a.withGraph = true;
		a.search();
		
		GraphTree<FloydVertex, FloydEdge,Boolean> tree = a.searchTree(p);
//		System.out.println(FloydVertex.solution(tree).getVertexList().stream().collect(Collectors.toList()));
//		System.out.println(tree);
		List<Ciudad> lc = FloydVertex.solution(tree).getVertexList().stream()
				.map(i->graph2.getVertex(i)).collect(Collectors.toList());
		System.out.println(lc);
		
		System.out.println(tree.vertices());
		
		a.toDot("ficheros/floydPD.gv",
				v->String.format("(%d,%d,%d)",v.i(),v.j(),v.k()),
				e->e.action()?"Y":"N",
				tree.vertices());
	}

}
