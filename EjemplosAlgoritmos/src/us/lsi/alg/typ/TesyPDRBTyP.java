package us.lsi.alg.typ;

import java.util.Locale;

import org.jgrapht.GraphPath;
import us.lsi.graphs.alg.PDRB;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TesyPDRBTyP {
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosTyP.datos("ficheros/tareas.txt",5);
		TyPVertex e1 = TyPVertexI.first();
		
		EGraph<TyPVertex,SimpleEdgeAction<TyPVertex,Integer>> graph = 
				EGraph.virtual(e1)
				.pathType(PathType.Last)
				.type(Type.Min)
				.vertexWeight(v->v.maxCarga())
				.heuristic(Heuristica::heuristic)
				.build();	
		Long p1 = System.nanoTime();
		PDRB<TyPVertex,SimpleEdgeAction<TyPVertex,Integer>,?> ms = PDRB.ofGreedy(graph);
		
		ms.search();
		Long p2 = System.nanoTime();
		GraphPath<TyPVertex,SimpleEdgeAction<TyPVertex,Integer>> s1 = ms.search().get();
		SolucionTyP s = SolucionTyP.of(s1);	
		System.out.println(s);
		System.out.println(p2-p1);
		
	}


}
