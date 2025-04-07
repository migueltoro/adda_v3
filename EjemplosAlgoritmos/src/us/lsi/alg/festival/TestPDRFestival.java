package us.lsi.alg.festival;

import org.jgrapht.GraphPath;
import us.lsi.graphs.alg.PDR;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestPDRFestival {

	
	public static void main(String[] args) {
		DatosFestival.iniDatos("ficheros/festival/DatosEntrada4.txt");
		DatosFestival2.iniDatos();
		FestivalVertex v1 = FestivalVertex.initial();
		EGraph<FestivalVertex, FestivalEdge> graph = 
				EGraph.virtual(v1)
				.pathType(PathType.Sum)
				.type(Type.Min)
				.heuristic(Greedy::heuristic)
				.build();
		
		GraphPath<FestivalVertex, FestivalEdge> path = Greedy.greedy(v1,graph);
		System.out.println("G1 "+path.getWeight());
		PDR<FestivalVertex,FestivalEdge,GraphPath<FestivalVertex, FestivalEdge>> ms = 
				PDR.of(graph);	
		GraphPath<FestivalVertex, FestivalEdge> gp = ms.search().get();
		System.out.println("PDR "+gp.getWeight());
	}
}
