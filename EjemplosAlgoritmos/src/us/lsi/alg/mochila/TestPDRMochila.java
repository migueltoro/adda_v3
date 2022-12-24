package us.lsi.alg.mochila;

import static us.lsi.colors.GraphColors.all;
import static us.lsi.colors.GraphColors.colorIf;
import static us.lsi.colors.GraphColors.styleIf;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;
import us.lsi.path.EGraphPath.PathType;


public class TestPDRMochila {
	
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
//		MochilaVertex.capacidadInicial = 78;
		MochilaVertex.capacidadInicial = 457;
		MochilaVertex e1 = MochilaVertex.initialVertex();
		
		EGraph<MochilaVertex, MochilaEdge> graph = 
				EGraph.virtual(e1,MochilaVertex.goal(), PathType.Sum, Type.Max)
				.greedyEdge(MochilaVertex::greedyEdge)
				.heuristic(MochilaHeuristic::heuristic)
				.build();	
		
		GreedyOnGraph<MochilaVertex, MochilaEdge> rr = GreedyOnGraph.of(graph);
		
		GraphPath<MochilaVertex, MochilaEdge> path = rr.path();	
		Double bv = path.getWeight();
		
		System.out.println("1 = "+bv);
		
		DPR<MochilaVertex, MochilaEdge, SolucionMochila> ms = 
				DPR.of(graph,null,bv,path,true);
		
		
		Optional<GraphPath<MochilaVertex, MochilaEdge>>  sp = ms.search();
		GraphPath<MochilaVertex, MochilaEdge> s1 = sp.get();
		SolucionMochila s = MochilaVertex.getSolucion(s1);
		System.out.println(s);
		
		Predicate<MochilaVertex> pv = v->ms.optimalPath().get().getVertexList().contains(v);
		Predicate<MochilaEdge> pe= e->ms.optimalPath().get().getEdgeList().contains(e);
		
		GraphColors.toDot(ms.outGraph,"ficheros/MochilaPDRGraph.gv",
				v->String.format("(%d,%d)",v.index(),v.capacidadRestante()),
				e->e.action().toString(),
				v->all(colorIf(Color.red,pv.test(v)),styleIf(Style.bold,pv.test(v))),
				e->all(colorIf(Color.red,pe.test(e)),styleIf(Style.bold,pe.test(e)))
				);
	}

}
