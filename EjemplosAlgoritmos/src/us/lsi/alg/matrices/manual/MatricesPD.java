package us.lsi.alg.matrices.manual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import us.lsi.common.Files2;


public class MatricesPD {
	
	public static record Spmt(Integer a,Integer weight) implements Comparable<Spmt> {
		
		public static Spmt of(Integer a, Integer weight) {
			return new Spmt(a, weight);
		}
		
		@Override
		public int compareTo(Spmt sp) {
			return this.weight.compareTo(sp.weight);
		}
	}
	
	public static record MatrixInf(Integer nf, Integer nc) {
		public static MatrixInf of(Integer nf, Integer nc) {
			return new MatrixInf(nf, nc);
		}
		
		public static Integer getWeight(List<MatrixProblem> ls) {	
			Integer i = ls.get(0).i();
			Integer a = ls.get(0).j();
			Integer j = ls.get(1).j();
			return MatricesPD.matrices.get(i).nf()*MatricesPD.matrices.get(a).nf()*MatricesPD.matrices.get(j-1).nc();
		}
		
	}
	
	public static record MatrixProblem(Integer i, Integer j) {
		
		public static MatrixProblem of(Integer i, Integer j) {
			return new MatrixProblem(i,j);
		}
		
		public List<Integer> actions() {
			return IntStream.range(i+1,j).boxed().collect(Collectors.toList());
		}

		public Boolean isBaseCase() {
			return j-i < 3;
		}

		public Integer baseCaseSolution() {
			Integer r;
			Integer d = j-i;
			switch(d) {
			case 0: r = 0; break;
			case 1: r = 0; break;
			case 2: r = MatricesPD.matrices.get(i).nf()*MatricesPD.matrices.get(i).nc()*MatricesPD.matrices.get(i+1).nf; break;
			default: r = null;	
			}
			return r;
		}

		public List<MatrixProblem> neighbors(Integer a) {
			return Arrays.asList(MatrixProblem.of(i, a),MatrixProblem.of(a, j));
		}

	}
	
	public static void leeFichero(String fichero){
		List<Integer> ls = Files2.streamFromFile(fichero)
				.map(ln->Integer.parseInt(ln))
				.collect(Collectors.toList());
		Integer n = ls.size();
		List<MatrixInf> r = new ArrayList<>();
		for(int i = 0; i<n-1;i++) {
			MatrixInf m = MatrixInf.of(ls.get(i),ls.get(i+1));
			r.add(m);
		}
		MatricesPD.matrices = r;
		MatricesPD.n = r.size();
		System.out.println(r);
	}
	
	public static MatricesPD of(MatrixProblem startVertex) {
		return new MatricesPD(startVertex);
	}
	
	public static List<MatrixInf> matrices;
	public static Integer n;
	public Map<MatrixProblem,Spmt> solutionsTree;
	public static MatrixProblem startVertex;
	
	
	private MatricesPD(MatrixProblem startVertex) {
		MatricesPD.startVertex = startVertex;
		this.solutionsTree = new HashMap<>();
	}
	
	public String search(){
		search(MatricesPD.startVertex);
		return this.solucion(MatricesPD.startVertex);
	}

	public Spmt search(MatrixProblem actual) {
		Spmt r = null;
		if (this.solutionsTree.containsKey(actual)) {
			r = this.solutionsTree.get(actual);
		} else if (actual.isBaseCase()) {
			Integer w = actual.baseCaseSolution();
			if(w!=null) r = Spmt.of(null,w);
			else r = null;
			this.solutionsTree.put(actual, r);
		} else {
			List<Spmt> sps = new ArrayList<>();
			for (Integer a : actual.actions()) {
				List<Spmt> spNeighbors = new ArrayList<>();
				Integer s = 0;
				for (MatrixProblem neighbor : actual.neighbors(a)) {
					Spmt nb = search(neighbor);
					if (nb == null) {spNeighbors = null; break;}
					spNeighbors.add(nb);
					s += nb.weight();
				}
				Spmt spa = null;
				if(spNeighbors != null) {
					spa = Spmt.of(a,s+MatrixInf.getWeight(actual.neighbors(a)));
				}
				sps.add(spa);
			}
			r = sps.stream().filter(s -> s != null).min(Comparator.naturalOrder()).orElse(null);
			this.solutionsTree.put(actual, r);
		}
		return r;
	}
	

	public String solucion(MatrixProblem v) {
		Spmt s = this.solutionsTree.get(v);
		if(s.a() == null) return String.format("(%d,%d)",v.i(),v.j());
		else {
			List<MatrixProblem> vc = v.neighbors(s.a());
			return String.format("(%s,%s)",solucion(vc.get(0)),solucion(vc.get(1)));
		}
	
	}
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MatricesPD.leeFichero("./ficheros/matrices.txt");
		
		MatrixProblem start = MatrixProblem.of(0,MatricesPD.n);
		
		MatricesPD a = MatricesPD.of(start);
		System.out.println(a.search());
	}

	
}
