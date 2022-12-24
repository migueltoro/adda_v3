package us.lsi.graphs.views;

import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.GraphType;

public class CompleteGraphView<V, E, G extends Graph<V,E>> implements Graph<V,E> {

	
	public static <V, E, G extends Graph<V,E>> CompleteGraphView<V, E, G> of(
			G graph, 
			Supplier<E> edgeWeightFactory) {
		return new CompleteGraphView<V, E, G>(graph, edgeWeightFactory);
	}
	
	public static Double weight = 1000.;

	private CompleteGraphView(G graph, 
			Supplier<E> edgeWeightFactory) {
		super();
		this.graph = graph;
		this.edgeWeightFactory = edgeWeightFactory;
		this.n= graph.vertexSet().size();
	}

	private G graph;
	private Supplier<E> edgeWeightFactory;
	private final Integer n;

	public Graph<V, E> getGraph() {
		return graph;
	}

	public Supplier<E> getEdgeWeightFactory() {
		return this.edgeWeightFactory;
	}

	public boolean addEdge(V arg0, V arg1, E arg2) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public E addEdge(V arg0, V arg1) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public V addVertex() {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public boolean addVertex(V arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public boolean containsEdge(E e) {
		return graph.containsVertex(this.getEdgeSource(e)) && graph.containsVertex(this.getEdgeSource(e));
	}

	public boolean containsEdge(V v0, V v1) {
		if(v0.equals(v1)) return false;
		return graph.containsVertex(v0) && graph.containsVertex(v1);
	}

	public boolean containsVertex(V v) {
		return graph.containsVertex(v);
	}

	public int degreeOf(V v) {
		return n-1;
	}

	public Set<E> edgeSet() {
		return graph.vertexSet().stream().<E>flatMap(x->edgesOf(x).stream()).collect(Collectors.toSet());
	}

	public Set<E> edgesOf(V v) {
		return graph.vertexSet().stream().filter(x->!x.equals(v)).map(x->getEdge(v,x)).collect(Collectors.toSet());
	}

	public Set<E> getAllEdges(V v0, V v1) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public E getEdge(V v0, V v1) {
		E edge = null;
		if(v0.equals(v1)) return null;
		if (graph.containsEdge(v0, v1))
			edge = graph.getEdge(v0, v1);
		else {
			edge = this.edgeWeightFactory.get();
			this.graph.addEdge(v0, v1, edge);
		}
		return edge;
	}

	public V getEdgeSource(E e) {
		return this.graph.getEdgeSource(e);
	}

	public Supplier<E> getEdgeSupplier() {
		return this.graph.getEdgeSupplier();
	}

	public V getEdgeTarget(E e) {
		return this.graph.getEdgeTarget(e);
	}

	public double getEdgeWeight(E e) {
		return graph.getEdgeWeight(e);
	}

	public GraphType getType() {
		return graph.getType();
	}

	public Supplier<V> getVertexSupplier() {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public int inDegreeOf(V arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public Set<E> incomingEdgesOf(V arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public int outDegreeOf(V arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public Set<E> outgoingEdgesOf(V arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public boolean removeAllEdges(Collection<? extends E> arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public Set<E> removeAllEdges(V arg0, V arg1) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public boolean removeAllVertices(Collection<? extends V> arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public boolean removeEdge(E arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public E removeEdge(V arg0, V arg1) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public boolean removeVertex(V arg0) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public void setEdgeWeight(E arg0, double arg1) {
		throw new IllegalArgumentException("Metodo no permitido");
	}

	public Set<V> vertexSet() {
		return graph.vertexSet();
	}	
	
	@Override
	public String toString() {
		return String.format("%s === %s",this.vertexSet(),this.edgeSet());
	}

}
