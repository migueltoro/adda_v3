package us.lsi.hypergraphs;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.common.List2;
import us.lsi.common.Preconditions;
import us.lsi.graphs.alg.DP.Sp;


public class GraphTree<V, E extends SimpleHyperEdge<V,E,A>, A> {
	
	public static <V, E extends SimpleHyperEdge<V, E, A>, A> GraphTree<V, E, A> of(Map<V, Sp<E>> tree,V vertex) {
		return new GraphTree<V, E, A>(tree,vertex);
	}

	private Map<V, Sp<E>> tree;
	private V vertex;

	private GraphTree(Map<V, Sp<E>> tree, V vertex) {
		super();
		this.tree = tree;
		this.vertex = vertex;
	}
	
	public V vertex() {
		return vertex;
	}
	
	public Double weight() {
		return tree.get(this.vertex).weight();
	}
	
	public A action() {	
		A r = null;
		if(tree.get(this.vertex).edge() != null) r = tree.get(this.vertex).edge().action();
		return r;
	}
	
	public List<GraphTree<V, E, A>> neighbords() {
		return tree.get(this.vertex).edge().targets().stream()
				.map(v->GraphTree.of(tree, v))
				.collect(Collectors.toList());
	}
	
	public Boolean isBaseCase() {
		Preconditions.checkNotNull(tree.get(this.vertex),"Es null");
		return tree.get(this.vertex).edge() == null;
	}
	
	public static <V, E extends SimpleHyperEdge<V,E, A>, A> List<GraphTree<V, E, A>> nextLevel(List<GraphTree<V, E, A>> level){
		return level.stream()
				.filter(t->!t.isBaseCase())
				.flatMap(t->t.neighbords().stream())
				.collect(Collectors.toList());
	}
	
	private static <V, E extends SimpleHyperEdge<V, E,A>, A> String string(GraphTree<V, E, A> tree) {
		return String.format("(%s,%s,%.2f)",tree.vertex(),tree.action()==null?"_":tree.action(),tree.weight());
	}
	
	@Override
	public String toString() {
		Integer n = 0;
		String r = String.format("%3d  [%s]",n,string(this));
		List<GraphTree<V, E, A>> children = nextLevel(List2.of(this));		
		while(!children.isEmpty()) {
			n++;
			r = String.format("%s\n%3d  %s",r,n,children.stream().map(t->string(t)).collect(Collectors.joining(",","[","]")));
			children = nextLevel(children);			
		}
		return r;		
	}
	
	public Set<V> vertices(){
		Set<V> r = new HashSet<>();
		r.add(this.vertex());
		List<GraphTree<V, E, A>> children = nextLevel(List2.of(this));		
		while(!children.isEmpty()) {
			Set<V> sl = children.stream().map(t->t.vertex()).collect(Collectors.toSet());
			r.addAll(sl);
			children = nextLevel(children);			
		}
		return r;
	}
	
}
