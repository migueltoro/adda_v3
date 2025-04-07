package us.lsi.alg.colorgraphs;

import org.jgrapht.GraphPath;

import us.lsi.common.List2;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.PDRB;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TesyColorPDRB {
	
public static void main(String[] args) {
		
		DatosColor.data(9,"ficheros/andalucia/andalucia.txt");
		
		ColorVertex e1 = ColorVertex.first();
					
		
		EGraph<ColorVertex, ColorEdge> graph = 
				EGraph.virtual(e1)
				.pathType(PathType.Last)
				.type(Type.Min)
				.vertexWeight(v->v.nc().doubleValue())
				.heuristic((v1,p,v2)->(double) v1.nc())
				.build();
				
		GraphPath<ColorVertex, ColorEdge> p = GreedyOnGraph.of(graph,v->v.greedyEdge()).path();
		Integer m = p.getEndVertex().nc();
		System.out.println("Voraz = "+m);
		Long p1 = System.nanoTime();
		PDRB<ColorVertex, ColorEdge,?> ms = PDRB.ofGreedy(graph);
		
		GraphPath<ColorVertex, ColorEdge> path = ms.search().get();
		Long p2 = System.nanoTime();
		ColorVertex lv = List2.last(path.getVertexList());
		System.out.println("Numero de Colores = "+lv.nc());
		System.out.println(lv.cav());
		System.out.println(p2-p1);

	}

}
