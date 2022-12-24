package us.lsi.alg.floyd.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.views.IntegerVertexGraphView;

public class FloydPD {
	
	public static record Spf(Boolean a,Double weight) implements Comparable<Spf> {
		
		public static Spf of(Boolean a, Double weight) {
			return new Spf(a, weight);
		}
		
		@Override
		public int compareTo(Spf sp) {
			return this.weight.compareTo(sp.weight);
		}
	}
	
//	public static enum ActionFloyd{Yes, No};
	
	public static record FloydProblem(Integer i,Integer j,Integer k) {
		
		public static FloydProblem of(Integer i,Integer j) {
			return new FloydProblem(i,j,0);
		}
		
		public static FloydProblem of(Integer i,Integer j,Integer k) {
			return new FloydProblem(i,j,k);
		}
		
		public List<Boolean> actions() {
			if(this.isBaseCase()) return List.of();
			if(i == k || k ==j) return List.of(false);
			else return List.of(false,true);
		}
		
		
		public List<FloydProblem> neighbors(Boolean a) {
			List<FloydProblem> r=null;
			if(!a) r = List.of(FloydProblem.of(i,j,k+1)); 
			else r = List.of(FloydProblem.of(i,k,k+1),FloydProblem.of(k, j, k+1)); 
			return r;
		}
		
		public Boolean isBaseCase() {
			return  k == n; //FloydVertex.graph.containsEdge(this.i,this.j) ;
		}
		
		public Double baseCaseSolution() {
			Double r = null;
			if(k ==n && FloydPD.graph.containsEdge(this.i, this.j)){
				Double w = FloydPD.graph.getEdge(i, j).weight();
				r = w;
			} else if(k ==n && !FloydPD.graph.containsEdge(this.i, this.j)) {
				r = null;
			}
			return r;
		}
	}
	
	public static SimpleWeightedGraph<Ciudad, Carretera> leeDatos(String fichero) {
		SimpleWeightedGraph<Ciudad, Carretera> graph = GraphsReader.newGraph(fichero, 
				Ciudad::ofFormat, 
				Carretera::ofFormat,
				Graphs2::simpleWeightedGraph, 
				Carretera::km);
		return graph;
	}
	
	public static Graph<Integer,SimpleEdge<Integer>> graph;
	public static Integer n;
	public Map<FloydProblem,Spf> solutionsTree;
	public static FloydProblem startVertex;
	
	public static FloydPD of(FloydProblem startVertex) {
		return new FloydPD(startVertex);
	}
	
	private FloydPD(FloydProblem startVertex) {
		FloydPD.startVertex = startVertex;
		this.solutionsTree = new HashMap<>();
	}
	
	public List<Integer> search(){
		search(FloydPD.startVertex);
		System.out.println(this.solutionsTree);
		return this.solucion(FloydPD.startVertex);
	}

	public Spf search(FloydProblem actual) {
		Spf r = null;
		if (this.solutionsTree.containsKey(actual)) {
			r = this.solutionsTree.get(actual);
		} else if (actual.isBaseCase()) {
			Double w = actual.baseCaseSolution();
			if(w!=null) r = Spf.of(null,w);
			else r = null;
			this.solutionsTree.put(actual, r);
		} else {
			List<Spf> sps = new ArrayList<>();
			for (Boolean a:actual.actions()) {
				List<Spf> spNeighbors = new ArrayList<>();
				Double s = 0.;
				for (FloydProblem neighbor : actual.neighbors(a)) {
					Spf nb = search(neighbor);
					if (nb == null) {spNeighbors = null; break;}
					spNeighbors.add(nb);
					s+=nb.weight();
				}
				Spf spa = null;
				if(spNeighbors != null) {
					spa = Spf.of(a,s);
				}
				sps.add(spa);
			}
			r = sps.stream().filter(s -> s != null).min(Comparator.naturalOrder()).orElse(null);
			this.solutionsTree.put(actual, r);
		}
		return r;
	}
	
	public List<Integer> solucion(FloydProblem p) {
		Spf s = this.solutionsTree.get(p);
		if(s.a() == null) {
			List<Integer> r = new ArrayList<>();
			r.add(p.i());
			r.add(p.j());
			return r;
		}
		else {
			List<FloydProblem> vc = p.neighbors(s.a());
			List<Integer> ls0 = solucion(vc.get(0));
			if(s.a()) {
				List<Integer> ls1 = solucion(vc.get(1));
				ls0.remove(ls0.size() - 1);
				ls0.addAll(ls1);
			}
			return ls0;
		}
	}
	
	public static Ciudad ciudad(Graph<Ciudad,Carretera> graph, String nombre) {
		return graph.vertexSet().stream().filter(c->c.nombre().equals(nombre)).findFirst().get();
	}
	
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		
		SimpleWeightedGraph<Ciudad, Carretera> g = leeDatos("./ficheros/andalucia.txt");
		IntegerVertexGraphView<Ciudad, Carretera> g2 = IntegerVertexGraphView.of(g);
		 
		FloydPD.graph = g2;
		FloydPD.n = g2.vertexSet().size();
		
		System.out.println(g);
		System.out.println(FloydPD.graph);
		
		Integer origen = g2.getIndex(ciudad(g,"Sevilla"));
		Integer destino = g2.getIndex(ciudad(g,"Almeria"));
		
		FloydProblem start = FloydProblem.of(origen,destino);
		
		FloydPD a = FloydPD.of(start);
		
		List<Integer> ciudades = a.search();
		
		System.out.println(ciudades.stream().map(i->g2.vertex(i).nombre()).toList());
	}

}
