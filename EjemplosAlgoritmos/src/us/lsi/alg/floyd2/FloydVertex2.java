package us.lsi.alg.floyd2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.GraphWalk;

import us.lsi.alg.floyd.FloydVertex;
import us.lsi.common.List2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.alg.DP.Sp;
import us.lsi.hypergraphs2.HyperVertex2;

public record FloydVertex2(Integer i,Integer j,Integer k) 
		implements HyperVertex2<FloydVertex2,FloydEdge2,Boolean,GraphWalk<Integer,SimpleEdge<Integer>>>{

	public static Map<FloydVertex2,Sp<FloydEdge2>> memory = new HashMap<>();
	
	public static FloydVertex2 initial(Integer i,Integer j) {	
		return new FloydVertex2(i,j,0);
	}
	
	public static FloydVertex2 of(Integer i,Integer j,Integer k) {	
		return new FloydVertex2(i,j,k);
	}

	public static Graph<Integer,SimpleEdge<Integer>> graph;
	public static Integer n;
	
	public Boolean isValid() {
		return 0 <= i && i < n && 0 <= j && j < n && 0<= k && j <= n;
	}
	@Override
	public List<Boolean> actions() {
		if(this.isBaseCase()) return List.of();
		if(i == k || k ==j) return List.of(false);
		else return List.of(false,true);
	}
	
	@Override
	public List<FloydVertex2> neighbors(Boolean a) {
		List<FloydVertex2> r=null;
		if(!a) r = List.of(FloydVertex2.of(i,j,k+1)); 
		else r = List.of(FloydVertex2.of(i, k, k+1),FloydVertex2.of(k, j, k+1)); 
		return r;
	}
	
	@Override
	public FloydEdge2 edge(Boolean a) {
		return FloydEdge2.of(this,this.neighbors(a), a);
	}
	
	@Override
	public Boolean isBaseCase() {
		return  k == n; //FloydVertex.graph.containsEdge(this.i,this.j) ;
	}
	
	@Override
	public Double baseCaseWeight() {
		Double r = null;
		if(k ==n && FloydVertex2.graph.containsEdge(this.i, this.j)){
			r = FloydVertex2.graph.getEdge(i, j).weight();
		} else if(k ==n && !FloydVertex2.graph.containsEdge(this.i, this.j)) {
			r = null;
		}
		return r;
	}
	
	public Integer getSource() {
		return this.i;
	}
	
	public Integer getTarget() {
		return j;
	}

	@Override
	public GraphWalk<Integer, SimpleEdge<Integer>> baseCaseSolution() {
		GraphWalk<Integer, SimpleEdge<Integer>> gp = null;
		if(this.i.equals(this.j)) 
			gp = new GraphWalk<>(FloydVertex2.graph,List2.of(i),0.);
		else if(k ==n && FloydVertex2.graph.containsEdge(this.i, this.j)){
			Double w = FloydVertex2.graph.getEdge(i, j).weight();
			List<Integer> ls = List2.of(i,j);
			gp = new GraphWalk<>(FloydVertex2.graph,ls,w);
		} else if(k ==n && !FloydVertex.graph.containsEdge(this.i, this.j)) {
			gp = null;
		}
		return gp;
	}

	@Override
	public FloydVertex2 me() {
		return this;
	}
	
	@Override
	public String toString() {
		return String.format("(%d,%d,%d)",i(),j(),k());
	}
	
}
