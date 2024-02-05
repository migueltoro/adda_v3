package us.lsi.p3;

import java.util.Locale;

import org.jgrapht.Graph;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;


public class TestEjemplo4 {
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		testEjemplo4("Supermercado1");
		testEjemplo4("Supermercado2");
		testEjemplo4("Supermercado3");
		
	}
	
	public static void testEjemplo4(String file) {
		
		Graph<String, Pasillo> grafo = 
				GraphsReader.newGraph("ficheros/p3/" + file + ".txt",
			v -> v[0], Pasillo::ofFormat, 
			Graphs2::simpleWeightedGraph, 
			Pasillo::mts);
		
		System.out.println("\nArchivo " + file + ".txt \n" + "Datos de entrada: " + grafo);
		
		System.out.println("Apartado A):");
		System.out.println("Las camaras deben colocarse en los siguientes "+
				Ejemplo4.apartadoA(grafo).size()+" cruces:");
		Ejemplo4.apartadoA(grafo).forEach(c->System.out.println("\t- Cruce "+c)); 
		
		System.out.println("Apartado B):");
		Ejemplo4.apartadoB(grafo, file);
		
		System.out.println("Apartado C):");
		System.out.println(file + "C.gv generado en " + "ficheros_generados/ejemplo4");
		
		System.out.println(grafo.edgesOf("C07"));
	}


}
