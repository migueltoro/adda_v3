package us.lsi.graphs.examples;

import java.util.Locale;
import java.util.Set;

import org.jgrapht.Graph;

import us.lsi.colors.GraphColors;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.alg.DephtSearch;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraphI;
import us.lsi.streams.Stream2;

public class RecorridoProfundidadTest {
	
	public static Ciudad ciudad(Graph<Ciudad,Carretera> graph, String nombre) {
		return graph.vertexSet().stream().filter(c->c.nombre().equals(nombre)).findFirst().get();
	}
	
	public static void main(String[] args) {
		
		Locale.setDefault(new Locale("en", "US"));
		Graph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						Graphs2::simpleWeightedGraph,
						Carretera::km);
		
		graph.addVertex(Ciudad.of("Londres",2000000));
		
		EGraph<Ciudad,Carretera> g = 
				EGraph.ofGraph(graph,ciudad(graph,"Sevilla"),null).edgeWeight(e->e.km()).build();
		
		DephtSearch<Ciudad, Carretera> rp = DephtSearch.of(g,ciudad(graph,"Sevilla"));
		Stream2.findLast(rp.stream());
//		ra.stream().forEach(v->System.out.println(v.getNombre()));
		Set<Carretera> carreteras = rp.edges();
		
		GraphColors.toDot(graph,"ficheros/andalucia.gv",x->x.nombre(),x->x.nombre()+"--"+x.km());
		
		
		System.out.println(carreteras);

	}


}
