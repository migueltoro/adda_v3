package us.lsi.graphs.manual.recorridos;

import java.util.Optional;
import java.util.function.Predicate;
import us.lsi.common.TriFunction;
import us.lsi.graphs.manual.Grafo;
import us.lsi.graphs.manual.heaps.Heap;

public class RecorridoAStar<V,E> extends Recorrido<V,E> {
	
	private TriFunction<V,V,Predicate<V>,Double> heuristic;
	private V end;
	private Predicate<V> predicate;
	
	public static <V, E> RecorridoAStar<V, E> of(Grafo<V, E> grafo, 
			TriFunction<V, V, Predicate<V>, Double> heuristic,
			V end, 
			Predicate<V> predicate) {
		return new RecorridoAStar<>(grafo, heuristic, end, predicate);
	}
	
	protected RecorridoAStar(Grafo<V, E> grafo, 
			TriFunction<V, V, Predicate<V>, Double> heuristic, 
			V end,
			Predicate<V> predicate) {
		super(grafo);
		this.heuristic = heuristic;
		this.end = end;
		this.predicate = predicate;
	}
	
	private Double estimatedWeightToEnd(V v, Double w) {
		return w + this.heuristic.apply(v,this.end,this.predicate);
	}
	
	public void traverse(V source) {
        V v = source;
        Heap<V,Double> q = Heap.of();
        q.add(v,0.);
        this.tree.put(v, Data.of(Optional.empty(),0.));
        while (!q.isEmpty()) {
            v = q.remove();
            this.path.add(v);
            Double w = this.tree.get(v).weight();
            for (V neighbor : this.graph.successors(v)) {
                Double nbw = w + this.graph.edgeWeight(v, neighbor); 
                Double ewe = this.estimatedWeightToEnd(neighbor,nbw);
                if (!this.tree.containsKey(neighbor)) {
                    this.tree.put(neighbor, Data.of(Optional.of(v), nbw));
                    q.add(neighbor,ewe);                    
                } else {
                    if (nbw < this.tree.get(neighbor).weight()) {
                        this.tree.put(neighbor, Data.of(Optional.of(v), nbw));
                        q.decrease(neighbor, ewe);
                    }
                }
                if (this.predicate.test(neighbor)) {
                    break;
                }
            }
        }
    }

}
