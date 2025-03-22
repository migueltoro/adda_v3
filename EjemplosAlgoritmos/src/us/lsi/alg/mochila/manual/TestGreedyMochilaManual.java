package us.lsi.alg.mochila.manual;

import java.util.Locale;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.SimpleDirectedGraph;

import us.lsi.alg.mochila.MochilaEdge;
import us.lsi.alg.mochila.MochilaVertex;
import us.lsi.alg.mochila.MochilaVertexI;
import us.lsi.colors.GraphColors;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.path.EGraphPath.PathType;

public class TestGreedyMochilaManual {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosMochila.iniDatos("ficheros/mochila/objetosMochila.txt");
		MochilaVertexI.capacidadInicial = 78;
		MochilaVertex v1 = MochilaVertex.initialVertex();
		EGraph<MochilaVertex, MochilaEdge> graph = 
				EGraph.virtual(v1)
				.pathType(PathType.Sum)
				.type(Type.Max)
				.heuristic(Heuristica::heuristica)
				.build();
		
		GraphPath<MochilaVertex, MochilaEdge> gpm = Heuristica.caminoVoraz(v1,graph);
		System.out.println("M "+gpm.getWeight());
	
		Double r2 = Heuristica.heuristica1(v1);
		System.out.println("H1 "+r2);
		
		GreedyOnGraph<MochilaVertex, MochilaEdge> gs = GreedyOnGraph.of(graph);
		GraphPath<MochilaVertex, MochilaEdge> gp = gs.path();
		System.out.println("G "+gp.getWeight());
		System.out.println("________________");
		System.out.println(gp.getWeight());
		SimpleDirectedGraph<MochilaVertex, MochilaEdge> g = Graphs2.addPathToGraph(gp);
		GraphColors.toDot(g,
				"ficheros/MochilaGreadyPath.gv",
				v->String.format("((%d,%d)",v.index(),v.capacidadRestante()),
				e->e.action().toString());

	}

}
