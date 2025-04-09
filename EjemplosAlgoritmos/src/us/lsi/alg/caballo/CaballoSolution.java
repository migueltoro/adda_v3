package us.lsi.alg.caballo;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.common.Enumerate;
import us.lsi.common.IntPair;
import us.lsi.streams.Stream2;

public class CaballoSolution {
	
	
	public static CaballoSolution of(List<CaballoVertex> vertices) {		
		return new CaballoSolution(vertices);
	}

	private List<CaballoVertex> vertices;
	private Map<IntPair,Integer> position;
	
	private CaballoSolution(List<CaballoVertex> vertices) {
		super();	
		this.vertices = new ArrayList<>(vertices);
		List<Enumerate<CaballoVertex>> p = Stream2.enumerate(this.vertices.stream(),1)
				.collect(Collectors.toList());
		this.position = p.stream()
				.collect(Collectors.toMap(x -> IntPair.of(x.value().row(),x.value().col()), x -> x.counter()));
	}

	@Override
	public String toString() {
		String s = "";
		for (int f = 0; f < CaballoVertex.n; f ++) {
			for (int c = 0; c < CaballoVertex.n; c ++) {
				s += String.format("%3d", position.get(IntPair.of(f,c)));
			}
			s += "\n";
		}
		return s;
	}
		
	public List<CaballoVertex> vertices() {
		return vertices;
	}
	
	public Integer position(Integer row, Integer col) {
		return this.position.get(IntPair.of(row,col));
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	

}
