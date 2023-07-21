package us.lsi.graphs.virtual;

import org.jgrapht.Graph;

public class ESimpleVirtualGraphG<V, E> extends ESimpleVirtualGraph<VirtualVertexG<V,E>,VirtualEdgeG<V,E>> {
	
	private Graph<V,E> graph;
	
	public ESimpleVirtualGraphG(EGraphBuilderVirtualG<V,E> builder) {
		super(builder);
		this.graph = builder.graph();
	}
	
	public Graph<V, E> graph() {
		return graph;
	}

}
