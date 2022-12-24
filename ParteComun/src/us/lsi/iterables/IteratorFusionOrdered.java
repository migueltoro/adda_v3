package us.lsi.iterables;

import java.util.Comparator;
import java.util.Iterator;

import us.lsi.common.Comparator2;

public class IteratorFusionOrdered<E> implements Iterator<E>,Iterable<E> {
	
	public static <E> Iterable<E> of(Iterable<E> iteratorA, Iterable<E> iteratorB, Comparator<E> cmp) {
		return new IteratorFusionOrdered<>(iteratorA.iterator(),iteratorB.iterator(),cmp);
	}
	
	private Iterator<E> it1;
	private E e1;
	private Iterator<E> it2;
	private E e2;
	private Comparator<E> cmp;
	
	private IteratorFusionOrdered(Iterator<E> iteratorA, Iterator<E> iteratorB, Comparator<E> cmp) {
		super();
		this.it1 = iteratorA;
		this.it2 = iteratorB;
		this.cmp = cmp;
		this.e1 = null;
		if(it1.hasNext()) e1 = it1.next();
		this.e2 = null;
		if(it2.hasNext()) e2 = it2.next();

	}
	
	@Override
	public Iterator<E> iterator() {
		return this;
	}
	@Override
	public boolean hasNext() {
		return this.e1 != null || this.e2 != null;
	}
	
	@Override
	public E next() {
		E e;
		if(e2==null||e1!=null && Comparator2.isLE(e1,e2,this.cmp)){
			e = e1;
			e1 = null;
				if(it1.hasNext()) e1 = it1.next();
		} else {
			e = e2;
			e2 = null;
				if(it2.hasNext()) e2 = it2.next();
		}		   
		return e;
	}
}
