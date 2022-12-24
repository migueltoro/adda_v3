package us.lsi.alg.puzzle.manual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jheaps.AddressableHeap.Handle;
import org.jheaps.tree.FibonacciHeap;

import us.lsi.common.String2;


public class PuzzleAStar {
	
	public static record AStarPuzzle(ProblemPuzzle vertex,ActionPuzzle a,ProblemPuzzle lastVertex,
			Double distanceToOrigin) {
		public static AStarPuzzle of(ProblemPuzzle vertex,ActionPuzzle a,ProblemPuzzle lastVertex,
			Double distanceToOrigin) {
			return new AStarPuzzle(vertex, a,lastVertex,distanceToOrigin);
		}
	}
	
	public static PuzzleAStar of(ProblemPuzzle start, ProblemPuzzle end) {
		return new PuzzleAStar(start,end);
	}
	
	public static ProblemPuzzle start;
	public static ProblemPuzzle end;
	
	public Map<ProblemPuzzle,Handle<Double,AStarPuzzle>> tree;
	public FibonacciHeap<Double,AStarPuzzle> heap; 
	public Boolean goal;
	
	private PuzzleAStar(ProblemPuzzle start, ProblemPuzzle end) {
		super();
		PuzzleAStar.start = start;
		PuzzleAStar.end = end;
		Double distanceToEnd = HeuristicaPuzzle.heuristicaManhattan(start,v->v.equals(end),end);
		AStarPuzzle a = AStarPuzzle.of(start,null,null,0.);
		this.heap = new FibonacciHeap<>();
		Handle<Double, AStarPuzzle> h = this.heap.insert(distanceToEnd,a);
		this.tree = new HashMap<>();
		this.tree.put(start,h);
		this.goal = false;
	}
	
	private List<ActionPuzzle> acciones(ProblemPuzzle v) {
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

	public List<ActionPuzzle> search() {
		ProblemPuzzle vertexActual = null;
		while (!heap.isEmpty() && !goal) {
			Handle<Double, AStarPuzzle> h = heap.deleteMin();
			AStarPuzzle dataActual = h.getValue();
			vertexActual = dataActual.vertex();
			for (ActionPuzzle a : vertexActual.actions()) {
				ProblemPuzzle v = vertexActual.neighbor(a);
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
	
	public static List<ProblemPuzzle> solucion(List<ActionPuzzle> acciones) {
		List<ProblemPuzzle> r = new ArrayList<>();
		ProblemPuzzle v = PuzzleAStar.start;
		r.add(v);
		for(ActionPuzzle a:acciones) {
			v = v.neighbor(a);
			r.add(v);
		}
		return r;
	}
	
	

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		ProblemPuzzle v1 = ProblemPuzzle.of(1, 2, 3, 4, 5, 0, 6, 7, 8);
		ProblemPuzzle end = ProblemPuzzle.of(8,1,3,4,0,2,7,6,5);
//		System.out.printf("_________\n%.0f\n",HeuristicaPuzzle.heuristicaManhattan(v1, null, end));
		PuzzleAStar p = PuzzleAStar.of(v1, end);
		List<ActionPuzzle> path = p.search();
		String2.toConsole(PuzzleAStar.solucion(path),e->e.toString(),"\n_____________\n");
	}

	

}
