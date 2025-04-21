package us.lsi.graphs.manual.tests;



import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.manual.GraphPath;
import us.lsi.graphs.manual.VirtualGraph;
import us.lsi.graphs.manual.recorridos.Greedy;

public class TestGreedy {
	

	public static void main(String[] args) {
		Mapa m = Mapa.parse("ficheros/ciudades.txt", 
				"ficheros/carreteras.txt", 
				VirtualGraph.GraphType.UNDIRECTED,
				VirtualGraph.TraverseType.FORWARD);
		System.out.println(m);
		Ciudad c1 = m.ciudad("Sevilla");
		Ciudad c2 = m.ciudad("Algeciras");
		Predicate<Ciudad> end = x->x.equals(c2);
		Random r = new Random(System.currentTimeMillis());
		Function<Ciudad, Ciudad> gv = 
				v-> {var ls = m.closestVertices(v); int n = ls.size(); return ls.get(r.nextInt(n));};
		GraphPath<Ciudad, Carretera> pbt = 
				Greedy.greedy(c1,end,m,gv);
		System.out.println("Weight = "+pbt.weight());
		System.out.println("MinPath "+pbt.vertices());

	}
}
