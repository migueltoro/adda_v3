package us.lsi.p3_23;

import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.alg.vertexcover.GreedyVCImpl;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.views.SubGraphView;


public class Ejemplo4 {
	
	// Apartado a)
	public static Set<String> apartadoA (Graph<String,Pasillo> gf) {
		var algA = new GreedyVCImpl<>(gf);
		Set<String> cruces = algA.getVertexCover();		
		return cruces;
	}
		
	
	// Apartado b) y c)
	public static void apartadoB (Graph<String,Pasillo> gf, String file) {
		
		//Apartado b)
		Set<String>cruces = apartadoA(gf);
		Predicate<String> pv = c -> cruces.contains(c);
		Predicate<Pasillo> pe = p -> cruces.contains(gf.getEdgeSource(p)) && cruces.contains(gf.getEdgeTarget(p));
		Graph<String,Pasillo> sgf = SubGraphView.of(gf, pv, pe);
		
		var algB1 = new ConnectivityInspector<>(sgf);
		System.out.println("Numero de equipos necesarios: "+algB1.connectedSets().size());
		
		var algB2 = new KruskalMinimumSpanningTree<>(sgf);
		var tree = algB2.getSpanningTree();
		System.out.println(String.format("Metros de cable necesarios: %.1f", tree.getWeight()));
		
		// Apartado c)
		GraphColors.toDot(gf, "resultados/ejemplo4/" + file + ".gv", c->"", v->"", 
				v -> GraphColors.colorIf(Color.blue, Color.blank, cruces.contains(v)),
				e -> GraphColors.colorIf(Color.blue, Color.blank, tree.getEdges().contains(e)));
		
	}
	
}
