package us.lsi.common.heaps;

import java.util.List;

public class TestFibonacci {

	public static void main(String[] args) {
		FibonacciHeap<Integer,Integer> f = FibonacciHeap.of();
		List<Integer> ls = List.of(2,32,-4156,557,3,5,7,8,9,10,-11,12,13,14,15,16,17,18,19,20);
		f.addAll(ls, ls);
		List<Integer> r = f.removeAll();
		System.out.println(r);	

	}

}
