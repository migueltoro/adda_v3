package us.lsi.enero_2024.e3;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import us.lsi.graphs.views.SubGraphView;

public class Grafos {

	public static Graph<Usuario, Conexion> subgrafoPopular(Graph<Usuario, Conexion> g, Integer cantidadSeguidores) {
		Predicate<Usuario> seguidoresSuficientes = user -> g.incomingEdgesOf(user).size() >= cantidadSeguidores;
		Predicate<Conexion> todasLasConexiones = conexion -> true;
		return SubGraphView.of(g, seguidoresSuficientes, todasLasConexiones);
	}

	public static Integer distanciaEntreUsuarios(Graph<Usuario, Conexion> g, Usuario A, Usuario B) {
		DijkstraShortestPath<Usuario, Conexion> dijkstra = new DijkstraShortestPath<>(g);
		GraphPath<Usuario, Conexion> path = dijkstra.getPath(A, B);
		if (path == null)
			return -1;
		return path.getVertexList().size() - 1;
	}

	public static Integer cantidadAnuncios(Graph<Usuario, Conexion> g) {
		GreedyColoring<Usuario, Conexion> coloreado = new GreedyColoring<>(g);
		return coloreado.getColoring().getNumberColors();
	}

	public static Set<Usuario> usuariosInfluyentes(Graph<Usuario, Conexion> g, Integer umbralSeguidores,
			Integer umbralSeguidos) {
		ConnectivityInspector<Usuario, Conexion> init_cc = new ConnectivityInspector<>(g);
		Set<Usuario> usuariosInfluyentes = g.vertexSet().stream()
				.filter(usuario -> g.inDegreeOf(usuario) > umbralSeguidores && 
						g.outDegreeOf(usuario) >= umbralSeguidos)
				.collect(Collectors.toSet());
		Set<Usuario> resultado = new HashSet<>();
		for (Usuario usuario : usuariosInfluyentes) {
			Graph<Usuario, Conexion> grafoSinUsuario = SubGraphView.of(g, v -> !v.equals(usuario), e -> true);
			ConnectivityInspector<Usuario, Conexion> sg_cc = new ConnectivityInspector<>(grafoSinUsuario);
			if (sg_cc.connectedSets().size() > init_cc.connectedSets().size()) {
				resultado.add(usuario);
			}
		}
		return resultado;
	}

}
