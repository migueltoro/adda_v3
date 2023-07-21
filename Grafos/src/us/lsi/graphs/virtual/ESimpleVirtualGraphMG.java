package us.lsi.graphs.virtual;


import org.jgrapht.graph.DirectedMultigraph;

public class ESimpleVirtualGraphMG<V, E> extends ESimpleVirtualGraph<VirtualVertexMG<V,E>,VirtualEdgeMG<V,E>> {
	
	private DirectedMultigraph<V,E> graph;
	
	public ESimpleVirtualGraphMG(EGraphBuilderVirtualMG<V, E> builder) {
		super(builder);
		this.graph = builder.graph();
	}

	public DirectedMultigraph<V, E> graph() {
		return graph;
	}


}
