package us.lsi.alg.caballo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Enumerate;
import us.lsi.streams.Stream2;

public class CaballoSolution {
	
	
	public static CaballoSolution of(List<CaballoVertex> vertices) {
		return new CaballoSolution(vertices);
	}

	private List<CaballoVertex> vertices;
	private List<Enumerate<CaballoVertex>> order;
	
	public static Comparator<Enumerate<CaballoVertex>> comparator = 
			Comparator.<Enumerate<CaballoVertex>>comparingInt(x->x.value().row())
			.thenComparingInt(x->x.value().col());
	
	private CaballoSolution(List<CaballoVertex> vertices) {
		super();
		this.vertices = new ArrayList<>(vertices);
		this.order = Stream2.enumerate(this.vertices.stream(),1)
				.collect(Collectors.toList());
		Collections.sort(this.order, comparator);
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < CaballoVertex.n; i++) {
			s += order.subList(i*CaballoVertex.n, (i+1)*CaballoVertex.n)
					.stream()
					.map(x->String.format("%3d", x.counter()))
					.collect(Collectors.joining(" ")) + "\n";
		}
		return s;
	}
		
	public List<CaballoVertex> vertices() {
		return vertices;
	}
	
	public List<Enumerate<CaballoVertex>> order() {
		return order;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	

}
