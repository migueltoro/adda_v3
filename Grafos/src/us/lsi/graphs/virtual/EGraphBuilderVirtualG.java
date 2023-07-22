package us.lsi.graphs.virtual;

import java.util.function.Predicate;

import org.jgrapht.Graph;

import us.lsi.common.Preconditions;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class EGraphBuilderVirtualG<V, E> extends EGraphBuilderVirtual<VirtualVertexG<V, E>, VirtualEdgeG<V, E>> {

	private Graph<V,E> graph;

	public EGraphBuilderVirtualG(Graph<V,E> graph, V startVertex, Predicate<V> goal, PathType pathType, Type type) {	
		super(VirtualVertexG.of(startVertex, graph),v->goal.test(v.vertex()), pathType, type);		
		this.graph = graph;
		Preconditions.checkArgument(graph.getType().isSimple(),"El grafo debe ser simple");
	}
	
	public Graph<V, E> graph() {
		return graph;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
