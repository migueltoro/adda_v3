package us.lsi.alg.robots;

import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestAStar {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		RobotVertex v0 = RobotVertex.of();	
		
		EGraph<RobotVertex, RobotEdge> graph = 
				EGraph.virtual(v0,v->v.goal(), PathType.Sum, Type.Max)
				.edgeWeight(e->e.weight())
				.heuristic((v1,p,v2)->3.*(RobotVertex.N-v1.getT()))
				.build();
		
		AStar<RobotVertex,RobotEdge,RobotSolution> ms = AStar.of(graph);
		
		Optional<GraphPath<RobotVertex,RobotEdge>> path = ms.search();
		System.out.println(RobotSolution.of(path.get()));
	
//		GraphColors.toDot(ms.outGraph(),"ficheros/RobotsAStar.gv",
//				v->String.format("%d,%d",v.getX().get(3),v.getT()),
//				e->e.action().toString(),
//				v->GraphColors.colorIf(Color.red,v.goal()),
//				e->GraphColors.colorIf(Color.red,path.get().getEdgeList().contains(e))
//				);
	}

}
