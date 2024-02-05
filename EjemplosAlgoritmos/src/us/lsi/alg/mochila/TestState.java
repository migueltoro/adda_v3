package us.lsi.alg.mochila;

import java.util.Locale;

import us.lsi.mochila.datos.DatosMochila;
import us.lsi.path.EGraphPath.PathType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.graphs.alg.BT.State;
import us.lsi.graphs.alg.BT.StatePath;


public class TestState {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosMochila.iniDatos("ficheros/mochila/objetosMochila.txt");
		MochilaVertex.capacidadInicial = 78;
		MochilaVertex v1 = MochilaVertex.initialVertex();
//		MochilaVertex v2 = MochilaVertex.lastVertex();
		
		EGraph<MochilaVertex, MochilaEdge> graph = 
				EGraph.virtual(v1,MochilaVertex.goal(), PathType.Sum, Type.Max)
				.greedyEdge(MochilaVertex::greedyEdge)
				.heuristic(MochilaHeuristic::heuristic1)
				.build();
		
		
		State<MochilaVertex,MochilaEdge> initialState = StatePath.of(graph,MochilaVertex.goal(),null);
		System.out.println(initialState);
		MochilaEdge e1 = initialState.getActualVertex().edge(2);
		initialState.forward(e1);
		System.out.println(initialState);
		MochilaEdge e2 = initialState.getActualVertex().edge(2);
		initialState.forward(e2);
		System.out.println(initialState);
		initialState.back(e2);
		System.out.println(initialState);
		initialState.back(e1);
		System.out.println(initialState);
		State<MochilaVertex, MochilaEdge> f = StatePath.of(graph, MochilaVertex.goal(), null);
		System.out.println(f);
	}

}
