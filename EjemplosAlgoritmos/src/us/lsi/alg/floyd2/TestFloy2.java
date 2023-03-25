package us.lsi.alg.floyd2;

import java.util.Locale;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import us.lsi.common.String2;
import us.lsi.common.Union;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.views.IntegerVertexGraphView;
import us.lsi.hypergraphs2.Data;
import us.lsi.hypergraphs2.GraphTree2;

public class TestFloy2 {
	
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
		Locale.setDefault(Locale.of("en", "US"));
		
		SimpleWeightedGraph<Ciudad, Carretera> graph = leeDatos("./ficheros/andalucia.txt");
		IntegerVertexGraphView<Ciudad,Carretera> graph2 = IntegerVertexGraphView.of(graph);
		
		System.out.println(graph);
		System.out.println(graph2);
		
		Integer origen = graph2.getIndex(ciudad(graph,"Sevilla"));
		Integer destino = graph2.getIndex(ciudad(graph,"Almeria"));
		
		FloydVertex2.graph = graph2;
		FloydVertex2.n = graph2.vertexSet().size();
		FloydVertex2 p = FloydVertex2.initial(origen,destino);
		
//		GraphPath<Integer,SimpleEdge<Integer>> gp = p.solution();
		String2.toConsole("21 = "+FloydVertex2.n.toString());
//		String2.toConsole(p.weight().toString());
		String2.toConsole("2 = "+Data.of().memory.toString());
		GraphPath<Integer, SimpleEdge<Integer>> gp = p.solution();
		GraphTree2<FloydVertex2, FloydEdge2, Boolean> t = p.graphTree();
		
		String2.toConsole("3 = "+gp.getVertexList().stream().map(i->graph2.vertex(i)).toList().toString());
		String2.toConsole("4 = "+t.toString());
		SimpleDirectedGraph<Union<FloydVertex2,FloydEdge2>, DefaultEdge> g = 
				Data.graph(Data.<FloydVertex2,FloydEdge2>of().memoryAll);
		
		Data.toDotHypergraph(g, "ficheros/hiperGraph.gv", p);
		Data.toDotAndOr(g, "ficheros/andOrGraph.gv", p);
	}

}
