package us.lsi.alg.typ.manual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jheaps.AddressableHeap.Handle;
import org.jheaps.tree.FibonacciHeap;

public class TyPAStar {
	
	public static record AStarTyP(TyPProblem vertex, Integer a, TyPProblem lastVertex,
			Integer distanceToOrigin) {
		public static AStarTyP of(TyPProblem vertex, Integer a, TyPProblem lastVertex,
			Integer distanceToOrigin) {
			return new AStarTyP(vertex, a, lastVertex,distanceToOrigin);
		}	
	}
	
	public static TyPAStar of(TyPProblem start) {
		return new TyPAStar(start);
	}
	
	public TyPProblem start;
	public Map<TyPProblem,Handle<Double,AStarTyP>> tree;
	public FibonacciHeap<Double,AStarTyP> heap; 
	public Boolean goal;
	
	private TyPAStar(TyPProblem start) {
		super();
		this.start = start;
		Double distanceToEnd = (double)Heuristica.heuristica(start);
		AStarTyP a = AStarTyP.of(start, null,null,0);
		this.heap = new FibonacciHeap<>();
		Handle<Double, AStarTyP> h = this.heap.insert(distanceToEnd,a);
		this.tree = new HashMap<>();
		this.tree.put(start,h);
		this.goal = false;
	}
	
	private List<Integer> acciones(TyPProblem v) {
		List<Integer> ls = new ArrayList<>();
		Integer a = this.tree.get(v).getValue().a();
		while (a != null) {
			ls.add(a);
			v = this.tree.get(v).getValue().lastVertex();
			a = this.tree.get(v).getValue().a();
		}
		Collections.reverse(ls);
		return ls;
	}


	public List<Integer> search() {
		TyPProblem vertexActual = null;
		while (!heap.isEmpty() && !goal) {
			Handle<Double, AStarTyP> ha = heap.deleteMin();
			AStarTyP dataActual = ha.getValue();
			vertexActual = dataActual.vertex();
			for (Integer a : vertexActual.acciones()) {
				TyPProblem v = vertexActual.vecino(a);
				Integer newDistance = v.maxCarga();
				Integer newDistanceToEnd = Heuristica.heuristica(v);				
				if (!tree.containsKey(v)) {	
					AStarTyP ac = AStarTyP.of(v, a, vertexActual, newDistance);
					Handle<Double, AStarTyP> nh = heap.insert((double)newDistanceToEnd,ac);
					tree.put(v,nh);		
				}else if (newDistance < tree.get(v).getValue().distanceToOrigin()) {
					AStarTyP ac = AStarTyP.of(v, a, vertexActual, newDistance);
					Handle<Double, AStarTyP> hv = tree.get(v);
					hv.setValue(ac);
					hv.decreaseKey((double)newDistanceToEnd);
				}
			}
			this.goal = vertexActual.index() == DatosTyP.n;
		}
		return acciones(vertexActual);
	}

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosTyP.datos("ficheros/tareas.txt",5);
		TyPProblem e1 = TyPProblem.first();
		TyPAStar a = of(e1);
		List<Integer> acciones = a.search();
		System.out.println(SolucionTyP.of(e1, acciones));
	}

}
