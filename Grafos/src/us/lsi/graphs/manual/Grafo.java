package us.lsi.graphs.manual;

import java.util.Set;

public interface Grafo<V, E> {	
    Set<V> successors(V vertex);
    Double edgeWeight(V sourceVertex, V targetVertex);
    E edge(V sourceVertex, V targetVertex);
    Set<V> vertexSet();
}
