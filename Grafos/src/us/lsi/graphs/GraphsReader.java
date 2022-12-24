package us.lsi.graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;

import org.jgrapht.Graph;

/**
 * <p>
 * Clase adecuada para construir un grafo a partir de la información en un fichero
 * 
 * El fichero debe tener una estructura como la siguiente. 
 * 
 * Cada línea en la sección de vértices representa un vértice. 
 * El primer campo debe ser un identificador único para el mismo
 * Los otros campos propiedades
 * 
 * Cada línea de la sección de aristas representa una arista
 * Los dos primeros campos son los identificadores de los vértices extremos
 * Los otros campos propiedades
 * </p>
 * 
 * #VERTEX# <br>
 * s1,Sevilla <br>
 * s2,Córdoba <br>
 * s3,Cádiz <br>
 * s4,Málaga <br>
 * #EDGE# <br>
 * s1,s2,180. <br>
 * s1,s3,120. <br>
 * s2,s4,140. <br>
 * s4,s1,210. <br>
 *
 * 
 * 
 * @author Miguel Toro
 *
 */
public class GraphsReader {

	private static String[] eliminaBlancos(String[] s) {
		String[] r = new String[s.length];
		for (int i = 0; i < r.length; i++) {
			r[i] = s[i].trim();
		}
		return r;
	}
	/**
	 * @param file Fichero con la información de entrada
	 * @param vf Factoría para construir los vértices a partir de las líneas del fichero previamente tokenizada
	 * @param ef Factoría para construir las aristas a partir de las líneas del fichero previamente tokenizada
	 * @param creator Creador del grafo  
	 * @return Grafo sin peso con la información del fichero
	 * @param <V> El tipo de los vértices
	 * @param <E> El tipo de las aristas
	 * @param <G> El tipo del Grafo
	 * @throws IllegalArgumentException Si no encuentra el fichero o si el fichero tiene una estructura no adecuada
	 */
	public static <V, E, G extends Graph<V,E>> G newGraph(
			String file,
			Function<String[],V> vf, 
			Function<String[],E> ef,
			Supplier<G> creator) {
		return newGraph(file,vf,ef,creator,null);	
	}
	
	/**
	 * @pre El grafo pasado con parámetro debe ser con peso y ew 
	 * debe ser distinto de null
	 * @param file Fichero con la información de entrada
	 * @param vf Factoría para construir los vértices a partir de las líneas del fichero previamente tokenizada
	 * @param ef Factoría para construir las aristas a partir de las líneas del fichero previamente tokenizada
	 * @param creator Grafo de entrada 
	 * @param ew Función que proporciona el peso de la arista. Debe ser distinto de null si el grafo es 
	 * de tipo WeightedGraph&lt;V,E&gt;. Si es null no se usa. 
	 * @return Grafo con la información del fichero
	 * @param <V> El tipo de los vértices
	 * @param <E> El tipo de las aristas
	 * @param <G> El tipo del grafo
	 * @throws IllegalArgumentException Si no encuentra el fichero o si el fichero tiene una estructura no adecuada
	 */
	public static <V, E, G extends Graph<V,E>> G newGraph(
			String file,
			Function<String[],V> vf, 
			Function<String[],E> ef,
			Supplier<G> creator,
			Function<E,Double> ew) {

		Map<String, V> idVertices = new HashMap<>();
		G ret = creator.get();
		Scanner sc = null;
		try {
			sc = new Scanner(new File(file));
		} catch (FileNotFoundException e1) {
			throw new IllegalArgumentException("Fichero " + file + " no encontrado");
		}

		List<String> filas = new ArrayList<>();

		while (sc.hasNext()) {
			filas.add(sc.nextLine());
		}

		sc.close();
		
		try {
			List<String> vertices = filas.subList(1, filas.indexOf("#EDGE#"));
			List<String> aristas = filas.subList(filas.indexOf("#EDGE#") + 1, filas.size());

			for (String verticeStr : vertices) {
				String[] vertice = eliminaBlancos(verticeStr.split(","));

				if (vertice.length == 0) {
					throw new IllegalArgumentException(
							"El fichero de entrada de vertices no es correcto. Linea: "
									+ verticeStr);
				}
				V vertex = vf.apply(vertice);
				ret.addVertex(vertex);
				idVertices.put(vertice[0], vertex);
			}
			
			for (String aristaStr : aristas) {
				String[] arista = eliminaBlancos(aristaStr.split(","));
				
				if (arista.length < 2)
					throw new IllegalArgumentException("El número de vértices de la arista no es correcto");

				if (arista.length >= 2) {
					E edge = ef.apply(arista);
					ret.addEdge(idVertices.get(arista[0]),idVertices.get(arista[1]), edge);
					if(ew!= null) {
						ret.setEdgeWeight(edge, ew.apply(edge));
					}
				}
			}

			return ret;

		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException(
					"La composición del fichero no es correcta");
		}
	}
}
