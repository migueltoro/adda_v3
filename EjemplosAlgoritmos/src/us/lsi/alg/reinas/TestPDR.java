package us.lsi.alg.reinas;

import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleEdgeAction;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestPDR {

	public static void main(String[] args) {
		ReinasVertex.n = 10;	
		ReinasVertex v1 = ReinasVertex.first();
		
		EGraph<ReinasVertex,SimpleEdgeAction<ReinasVertex,Integer>> graph = 
				EGraph.virtual(v1,ReinasVertex.goal(), PathType.Last, Type.All)
				.goalHasSolution(ReinasVertex.goalHasSolution())
				.vertexWeight(v->v.errores().doubleValue())
				.solutionNumber(1000)
				.build();			
		
		DPR<ReinasVertex, SimpleEdgeAction<ReinasVertex, Integer>, SolucionReinas> ms = 
				DPR.of(graph,SolucionReinas::of,null,null,false);
		
		Optional<GraphPath<ReinasVertex, SimpleEdgeAction<ReinasVertex, Integer>>> path = ms.search();
		System.out.println(SolucionReinas.of(path.get()));
		System.out.println("_____________");
//		System.out.println(ms.getSolutions().size());
//		ms.getSolutions().stream().forEach(s->System.out.println(s));
		System.out.println(ms.getSolutions().size());
	}

}
