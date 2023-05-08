package us.lsi.alg.puzzle.manual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jheaps.AddressableHeap.Handle;
import org.jheaps.tree.FibonacciHeap;

import us.lsi.alg.puzzle.ActionPuzzle;
import us.lsi.alg.puzzle.HeuristicaPuzzle;
import us.lsi.alg.puzzle.VertexPuzzle;
import us.lsi.common.String2;

public class PuzzleAStar {
	
	public static record AStarPuzzle(VertexPuzzle vertex,ActionPuzzle a,VertexPuzzle lastVertex,
			Double distanceToOrigin) {
		public static AStarPuzzle of(VertexPuzzle vertex,ActionPuzzle a,VertexPuzzle lastVertex,
			Double distanceToOrigin) {
			return new AStarPuzzle(vertex, a,lastVertex,distanceToOrigin);
		}
	}
	
	public static PuzzleAStar of() {
		return new PuzzleAStar();
	}
	
	public VertexPuzzle start;
	public VertexPuzzle end;
	
	private Map<VertexPuzzle,Handle<Double,AStarPuzzle>> tree;
	private FibonacciHeap<Double,AStarPuzzle> heap; 
	private Boolean goal;
	private Integer minValue;
	private Long time;
	
	private PuzzleAStar() {
		super();
	}
	
	private List<ActionPuzzle> acciones(VertexPuzzle v) {
		List<ActionPuzzle> ls = new ArrayList<>();
		ActionPuzzle a = this.tree.get(v).getValue().a();
		while (a != null) {
			ls.add(a);
			v = this.tree.get(v).getValue().lastVertex();
			a = this.tree.get(v).getValue().a();
		}
		Collections.reverse(ls);
		return ls;
	}
	
	public List<ActionPuzzle> search(VertexPuzzle start, VertexPuzzle end, Integer minValue) {
		this.time = System.nanoTime();
		this.start = start;
		this.end = end;
		this.minValue = minValue;
		Double distanceToEnd = HeuristicaPuzzle.heuristicaManhattan(start,v->v.equals(end),end);
		AStarPuzzle a = AStarPuzzle.of(start,null,null,0.);
		this.heap = new FibonacciHeap<>();
		Handle<Double, AStarPuzzle> h = this.heap.insert(distanceToEnd,a);
		this.tree = new HashMap<>();
		this.tree.put(start,h);
		this.goal = false;
		List<ActionPuzzle> r = search();
		this.time = System.nanoTime() - this.time;
		return r;
	}
	
	private Boolean forget(VertexPuzzle v) {
		Integer w = HeuristicaPuzzle.heuristicaManhattan(start,va->va.equals(end),end).intValue();	
		Boolean r =  this.minValue != null && w > this.minValue;
		if(r) this.tree.remove(v);
		return r;
	}

	public List<ActionPuzzle> search() {
		VertexPuzzle vertexActual = null;
		while (!heap.isEmpty() && !goal) {
			Handle<Double, AStarPuzzle> h = heap.deleteMin();
			AStarPuzzle dataActual = h.getValue();
			vertexActual = dataActual.vertex();
			if(forget(vertexActual)) continue;
			for (ActionPuzzle a : vertexActual.actions()) {
				VertexPuzzle v = vertexActual.neighbor(a);
				Double newDistance = dataActual.distanceToOrigin()+1.;
				Double newDistanceToEnd = newDistance + 
						HeuristicaPuzzle.heuristicaManhattan(start,va->va.equals(end),end);				
				if (!tree.containsKey(v)) {	
					AStarPuzzle ac = AStarPuzzle.of(v, a, vertexActual, newDistance);
					Handle<Double, AStarPuzzle> nh = heap.insert(newDistanceToEnd,ac);
					tree.put(v,nh);	
				}else if (newDistance < tree.get(v).getValue().distanceToOrigin()) {
					AStarPuzzle dv = AStarPuzzle.of(v, a, vertexActual, newDistance);
					Handle<Double, AStarPuzzle> hv = tree.get(v);
					hv.setValue(dv);
					hv.decreaseKey(newDistanceToEnd);
				}
			}
			this.goal = vertexActual.equals(end);
		}
		return acciones(vertexActual);
	}
	
	public List<VertexPuzzle> solucion(List<ActionPuzzle> acciones) {
		List<VertexPuzzle> r = new ArrayList<>();
		VertexPuzzle v = this.start;
		r.add(v);
		for(ActionPuzzle a:acciones) {
			v = v.neighbor(a);
			r.add(v);
		}
		return r;
	}
	
	

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		VertexPuzzle v1 = VertexPuzzle.of(1, 2, 3, 4, 5, 0, 6, 7, 8);
		VertexPuzzle end = VertexPuzzle.of(8,1,3,4,0,2,7,6,5);
		System.out.printf("_________\n%.0f\n",HeuristicaPuzzle.heuristicaManhattan(v1, null, end));
		PuzzleAStar a = PuzzleAStar.of();
		List<ActionPuzzle> path = a.search(v1, end,11);
		String2.toConsole(a.solucion(path),e->e.toString(),"\n_____________\n");
	}

	

}
