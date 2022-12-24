package us.lsi.streams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

import us.lsi.common.List2;
import us.lsi.common.Multiset;
import us.lsi.common.MutableType;

public class Collectors2 {
	
	public static <E,B,R> Collector<E,MutableType<B>,R> of(
			Supplier<B> supplier,
			BiFunction<B,E,B> consumer,
			BinaryOperator<B> combiner,
			Function<B,R> finisher){
		return Collector.of(()->MutableType.of(supplier.get()),
				(b,e)->b.setValue(consumer.apply(b.value(),e)),
				(x,y)->MutableType.of(combiner.apply(x.value(),y.value())),
				x->finisher.apply(x.value()));
	}
	
	
	public static <E> Collector<E,Multiset<E>,Multiset<E>> toMultiset() {
		return Collector.of(
				()->Multiset.empty(), 
				(x,e)->x.add(e), 
				(x,y)->Multiset.add(x, y), 
				x->x);
	}
	
	public static <E> Collector<E,List<E>,List<E>> mergeSort(Comparator<? super E> cmp) {
		return Collector.of(
				()->new ArrayList<>(), 
				(x,e)->List2.insertOrdered(x,e,cmp), 
				(x,y)->List2.mergeOrdered(x,y,cmp), 
				x->x);
	}
	
	public static <E extends Comparable<? super E>> Collector<E,List<E>,List<E>> mergeSort() {
		return mergeSort(Comparator.naturalOrder());
	}
	
	public static <E> Collector<E,MutableType<Boolean>,Boolean> all(Predicate<E> p){
		return of(
				()->true, 
				(b,e)->p.test(e), 
				(b1,b2)->b1&&b2, 
				b->b);
	}
	
	public static <E> Collector<E,Set<E>,Integer> countDistinct() {
		return Collector.of(
				()->new HashSet<E>(), 
				(x,e)->x.add(e), 
				(s1,s2)->{Set<E> c = new HashSet<>(s1); c.addAll(s2); return c;}, 
				s->s.size());
	}

}
