package us.lsi.alg.festival;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphWalk;

import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class Greedy {
	
	public static GraphPath<FestivalVertex,FestivalEdge> greedy(FestivalVertex start, 
			Graph<FestivalVertex,FestivalEdge> graph) {
		List<FestivalVertex> vertices = new ArrayList<>();
		Double weight = 0.;
		FestivalVertex v = start;		
		while(!v.goal()) {
			Integer a = v.greedyAction();
			FestivalEdge e = v.edge(a);
			v = v.neighbor(a);
			vertices.add(v);
			weight += e.weight();
		} 
		return new GraphWalk<FestivalVertex,FestivalEdge>(graph,vertices,weight);
	}
	
	public static Double heuristic1(FestivalVertex start) {
		Double weight = 0.;
		FestivalVertex v = start;		
		while(!v.goal()) {
			weight += DatosFestival2.costesMin.get(v.i());
			Integer a = v.greedyAction();
			v = v.neighbor(a);
		} 
		return weight;
	}
	
	public static Double heuristic(FestivalVertex v1, Predicate<FestivalVertex> goal, FestivalVertex v2) {
		return heuristic2(v1);
	}
	
	public static Double heuristic2(FestivalVertex start) {
		Double weight = 0.;
		FestivalVertex v = start;		
		while(!v.goal()) {
			Integer a = v.greedyAction();
			FestivalEdge e = v.edge(a);
			v = v.neighbor2(a);
			weight += e.weight();
		} 
		return weight;
	}
	
	public static void test0() {
		DatosFestival.iniDatos("ficheros/festival/DatosEntrada0.txt");
		DatosFestival2.iniDatos();
		System.out.println(DatosFestival2.costes);
		System.out.println(DatosFestival2.aforoMaximoAreas);
		System.out.println(DatosFestival2.cuotasMinimas);
		FestivalVertexI e = FestivalVertexI.of(0, DatosFestival2.aforoMaximoAreas, DatosFestival2.cuotasMinimas);
		System.out.println(e);
		System.out.println(e.greedyAction());
		System.out.println(e.actions());
		System.out.println(e.neighbor(2));
		System.out.println(e.edge(2));
	}
	
	public static void test1() {
		DatosFestival.iniDatos("ficheros/festival/DatosEntrada4.txt");
		DatosFestival2.iniDatos();
		FestivalVertex v1 = FestivalVertex.initial();
		EGraph<FestivalVertex, FestivalEdge> graph = 
				EGraph.virtual(v1)
				.pathType(PathType.Sum)
				.type(Type.Max)
				.build();
		
		GraphPath<FestivalVertex, FestivalEdge> gpm = greedy(v1,graph);
		System.out.println("G1 "+gpm.getWeight());
		Double h = heuristic1(v1);
		System.out.println("H1 "+h);
		h = heuristic2(v1);
		System.out.println("H2 "+h);
	}

	public static void main(String[] args) {
		test1();
	}

}
