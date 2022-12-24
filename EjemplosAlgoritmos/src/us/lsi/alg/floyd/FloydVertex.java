package us.lsi.alg.floyd;

import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.GraphWalk;

import us.lsi.common.List2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.alg.DP.Sp;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.VirtualHyperVertex;
import us.lsi.path.EGraphPath;



public record FloydVertex(Integer i,Integer j,Integer k) implements VirtualHyperVertex<FloydVertex,FloydEdge,Boolean>{

	
	public static FloydVertex initial(Integer i,Integer j) {	
		return new FloydVertex(i,j,0);
	}
	
	public static FloydVertex of(Integer i,Integer j,Integer k) {	
		return new FloydVertex(i,j,k);
	}

	public static Graph<Integer,SimpleEdge<Integer>> graph;
	public static Integer n;
	
	
	public FloydVertex neigbord(Integer i, Integer j, Integer k){
		return new FloydVertex(i,j,k);
	}
	
	@Override
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
	public List<FloydVertex> neighbors(Boolean a) {
		List<FloydVertex> r=null;
		if(!a) r = List.of(this.neigbord(i,j,k+1)); 
		else r = List.of(this.neigbord(i, k, k+1),this.neigbord(k, j, k+1)); 
		return r;
	}
	
	@Override
	public FloydEdge edge(Boolean a) {
		return FloydEdge.of(this,this.neighbors(a), a);
	}
	
	@Override
	public Boolean isBaseCase() {
		return  k == n; //FloydVertex.graph.containsEdge(this.i,this.j) ;
	}
	
	@Override
	public Double baseCaseSolution() {
		Double r = null;
		if(k ==n && FloydVertex.graph.containsEdge(this.i, this.j)){
			Double w = FloydVertex.graph.getEdge(i, j).weight();
			r = w;
		} else if(k ==n && !FloydVertex.graph.containsEdge(this.i, this.j)) {
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

	public static GraphWalk<Integer,SimpleEdge<Integer>> solution(GraphTree<FloydVertex,FloydEdge,Boolean> tree){
		GraphWalk<Integer,SimpleEdge<Integer>> gp = null;		
		if(tree.isBaseCase()) {
			Integer origen = tree.vertex().i;
			Integer destino = tree.vertex().j;
			List<Integer> ls = List2.of(origen,destino);
			gp = new GraphWalk<>(FloydVertex.graph,ls,tree.weight());
		} else if(!tree.action()){
			gp = solution(tree.neighbords().get(0));
		} else {
			GraphWalk<Integer,SimpleEdge<Integer>> gp1 = solution(tree.neighbords().get(0));
			GraphWalk<Integer,SimpleEdge<Integer>> gp2 = solution(tree.neighbords().get(1));
			gp = gp1.concat(gp2,g->EGraphPath.weight(g));
		}
		return gp;
	}
	
	public static GraphWalk<Integer,SimpleEdge<Integer>> solution(Map<FloydVertex, Sp<FloydEdge>> tree,FloydVertex vertex){
		GraphWalk<Integer,SimpleEdge<Integer>> gp = null;
		Sp<FloydEdge> s = tree.get(vertex);
		if(s.edge() == null) {
			Integer origen = vertex.i;
			Integer destino = vertex.j;
			List<Integer> ls = List2.of(origen,destino);
			gp = new GraphWalk<>(FloydVertex.graph,ls,s.weight());
		} else if(!s.edge().action()){
			gp = solution(tree,s.edge().targets().get(0));
		} else {
			GraphWalk<Integer,SimpleEdge<Integer>> gp1 = solution(tree,s.edge().targets().get(0));
			GraphWalk<Integer,SimpleEdge<Integer>> gp2 = solution(tree,s.edge().targets().get(1));
			gp = gp1.concat(gp2,g->EGraphPath.weight(g));
		}
		return gp;
	}

}
