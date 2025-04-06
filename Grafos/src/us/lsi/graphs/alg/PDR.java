package us.lsi.graphs.alg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath;

import java.util.Optional;
import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.GraphWalk;
import org.jgrapht.graph.SimpleDirectedGraph;

public class PDR<V, E, S> {
	
	public static <V, E, S> PDR<V, E, S> of(EGraph<V, E> graph) {
		return new PDR<V, E, S>(graph,null,false);
	}
	
	public static <V, E, S> PDR<V, E, S> of(EGraph<V, E> graph, 
			Function<GraphPath<V, E>, S> fsolution, 
			Boolean withGraph) {
		return new PDR<V, E, S>(graph,fsolution,withGraph);
	}

	private EGraph<V, E> graph;
	private Comparator<Sp<E>> comparatorSp;
	public Map<V, Sp<E>> solutionsTree;
	private List<V> actualPath;
	public Set<S> solutions;
	protected Function<GraphPath<V,E>,S> fsolution;
	public SimpleDirectedGraph<V, E> outGraph;
	private Boolean withGraph = false;
	private Type type;
	public Boolean stop = false;

	PDR(EGraph<V, E> g, Function<GraphPath<V, E>, S> fsolution, Boolean withGraph) {
		this.graph = g;
		this.comparatorSp = this.graph.type() == EGraph.Type.Min?Comparator.naturalOrder():Comparator.reverseOrder();
		this.type = g.type();
		this.solutionsTree = new HashMap<>();
		this.actualPath = new ArrayList<>();
		this.withGraph = withGraph;
		this.fsolution = fsolution;		
	}
	

	protected void update(V actual, Double accumulateValue) {
		if (graph.goalHasSolution().test(actual)) {
			switch(this.type) {
			case All:
				S s = fsolution.apply(pathToOrigin(actual,accumulateValue));
				this.solutions.add(s);
				if (this.solutions.size() >= this.graph.solutionNumber()) this.stop = true;
				break;
			case One:
				s = fsolution.apply(pathToOrigin(actual,accumulateValue));
				this.solutions.add(s);
				this.stop = true;
				break;
			case Min:
			case Max:
			}
		}
	}
	
	public Set<S> getSolutions(){
		return this.solutions;
	}
	
	public void iniciaGraph() {
		if(this.withGraph) outGraph = Graphs2.simpleDirectedGraph();
	}
	
	private void addGraph(V v, E edge) {
		if(withGraph) {
			V v2 = Graphs.getOppositeVertex(graph,edge,v);
			if(!this.outGraph.containsVertex(v)) this.outGraph.addVertex(v);
			if(!this.outGraph.containsVertex(v2)) this.outGraph.addVertex(v2);
			if(!this.outGraph.containsEdge(edge)) this.outGraph.addEdge(v, v2, edge);
		}
	}
	
	public SimpleDirectedGraph<V,E> outGraph() {
		return this.outGraph;
	}

	public Optional<GraphPath<V, E>> search() {
		iniciaGraph();
		this.solutionsTree = new HashMap<>();
		Sp<E> r = search(graph.startVertex(),0., null);	
		if(r == null) return Optional.empty();
		return pathFrom(graph.startVertex());
	}
	
	private Sp<E> search(V actual, Double accumulateValue, E edgeToOrigin) {
		this.actualPath.add(actual);
		Sp<E> r = null;
		if(this.solutionsTree.containsKey(actual)) {
			r = this.solutionsTree.get(actual);
		} else if (graph.goal().test(actual)) {
			if (graph.goalHasSolution().test(actual)) {
				r = Sp.of(graph.goalSolutionValue(actual), null);
				this.solutionsTree.put(actual, r);
				update(actual,accumulateValue);			
			} else {
				r = null;
				this.solutionsTree.put(actual, r);
			}		
		} else {
			List<Sp<E>> rs = new ArrayList<>();	
			for (E edge : graph.edgesListOf(actual)) {					
				V v = Graphs.getOppositeVertex(graph,edge,actual);
				Double ac = this.graph.add(actual,accumulateValue,edge,edgeToOrigin); 
				Sp<E> s = search(v,ac,edge);
				if (s!=null) {
					Double spv = this.graph.fromNeighbordSolutionValue(actual,s.weight,edge,edgeToOrigin);	
					Sp<E> sp = Sp.of(spv,edge);
					rs.add(sp);
				}
				addGraph(actual, edge);
			}
			if (!rs.isEmpty()) {
				r = rs.stream().filter(s -> s != null).min(this.comparatorSp).orElse(null);
				if(r != null)
					this.solutionsTree.put(actual, r);
			}
		}
		this.actualPath.remove(actual);
		return r;
	}
	
	private GraphPath<V, E> pathToOrigin(V vertex,Double accumulateValue) {
		return new GraphWalk<>(this.graph,this.actualPath,accumulateValue);
	}

	private Optional<GraphPath<V, E>> pathFrom(V vertex) {	
		if(this.solutionsTree.get(vertex) == null) return Optional.empty();
		E edge = this.solutionsTree.get(vertex).edge;	
		EGraphPath<V,E> ePath = EGraphPath.ofVertex(this.graph,vertex,this.graph.pathType());
		while(edge != null) {
			ePath.add(edge);
			vertex = Graphs.getOppositeVertex(graph,edge,vertex);
			edge = this.solutionsTree.get(vertex).edge;	
		}
		return Optional.of(ePath);
	}
	
	public record Sp<E>(Double weight, E edge) implements Comparable<Sp<E>> {
		
		public static <E> Sp<E> of(Double weight,E edge) {
			return new Sp<>(weight,edge);
		}

		@Override
		public int compareTo(Sp<E> sp) {
			return this.weight.compareTo(sp.weight);
		}

	}
}
