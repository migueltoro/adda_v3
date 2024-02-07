package us.lsi.alg.puzzle;

import java.util.List;
import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;


public class TestMainPuzzle {
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		VertexPuzzle v1 = VertexPuzzle.of(1,2,3,4,5,0,6,7,8);
		VertexPuzzle v2 = VertexPuzzle.of(1,2,3,4,0,5,6,7,8);
		VertexPuzzle v3 = VertexPuzzle.of(1,2,3,4,6,5,8,7,0);
		VertexPuzzle v4 = VertexPuzzleI.of(1,2,3,4,5,6,7,8,0);
		VertexPuzzle v5 = VertexPuzzleI.of(3,1,6,0,8,2,4,5,7);
		VertexPuzzle v6 = VertexPuzzleI.of(0,1,2,3,4,5,6,7,8);
		VertexPuzzle v7 = VertexPuzzleI.of(1,2,3,4,5,6,7,8,0);
		VertexPuzzle v8 = VertexPuzzleI.of(8,1,3,4,0,2,7,6,5);
		
		VertexPuzzle e1 = v1;
		VertexPuzzle e2 = v3;
		System.out.println(e1.isSolvable(e2));
		
		EGraph<VertexPuzzle, EdgePuzzle> graph = 
				EGraph.virtual(e1,x->x.equals(e2),PathType.Sum, Type.Min)
				.edgeWeight(x->x.weight())
				.endVertex(e2)
				.heuristic(HeuristicaPuzzle::heuristica)
				.build();		
		
		AStar<VertexPuzzle, EdgePuzzle,?> ms = 
				AStar.ofGreedy(graph);
		
		GraphPath<VertexPuzzle,EdgePuzzle> path = ms.search().orElse(null);
		List<VertexPuzzle> vertices = path.getVertexList();
		for (VertexPuzzle v: vertices) {
			System.out.println(v);
			System.out.println("====================");
		}

	}
}
