package us.lsi.common.heaps;

import java.util.Comparator;
import java.util.List;

public interface FibonacciHeap<V, P> {
	
	public static <V,P extends Comparable<P>> FibonacciHeap<V,P> of() {
		return new CFibonacciHeap<V, P>(Comparator.naturalOrder());
	}
	
	public static <V, P> FibonacciHeap<V, P> of(Comparator<P> comparator) {
		return new CFibonacciHeap<V, P>(comparator);
	}

	void clear();

	boolean isEmpty();

	Integer size();

	void add(V value, P priority);

	void decrease(V value, P priority);

	V remove();

	void addAll(List<V> values, List<P> priorities);

	List<V> removeAll();

}