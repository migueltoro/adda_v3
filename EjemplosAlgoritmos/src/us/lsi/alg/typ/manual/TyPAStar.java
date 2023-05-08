package us.lsi.alg.typ.manual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jheaps.AddressableHeap.Handle;
import org.jheaps.tree.FibonacciHeap;

import us.lsi.alg.typ.DatosTyP;
import us.lsi.alg.typ.SolucionTyP;
import us.lsi.alg.typ.TyPVertex;

public class TyPAStar {
	
	public static record AStarTyP(TyPVertex vertex, Integer a, TyPVertex lastVertex,
			Integer distanceToOrigin) {
		public static AStarTyP of(TyPVertex vertex, Integer a, TyPVertex lastVertex,
			Integer distanceToOrigin) {
			return new AStarTyP(vertex, a, lastVertex,distanceToOrigin);
		}	
	}
	
	public static TyPAStar of() {
		return new TyPAStar();
	}
	
	private Map<TyPVertex,Handle<Double,AStarTyP>> tree;
	private FibonacciHeap<Double,AStarTyP> heap; 
	private Boolean goal;
	private Integer minValue;
	private SolucionTyP solucion;
	private Long time;
	
	private TyPAStar() {
		super();
	}
	
	private List<Integer> acciones(TyPVertex v) {
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
	
	public Long time() {
		return time;
	}
	
	public SolucionTyP solucion() {
		return solucion;
	}
	
	public SolucionTyP search(TyPVertex start,Integer minValue, SolucionTyP s) {
		this.time = System.nanoTime();
		Double distanceToEnd = (double)Heuristica.heuristica(start);
		AStarTyP a = AStarTyP.of(start, null,null,0);
		this.heap = new FibonacciHeap<>();
		Handle<Double, AStarTyP> h = this.heap.insert(distanceToEnd,a);
		this.tree = new HashMap<>();
		this.tree.put(start,h);
		this.goal = false;
		this.minValue = minValue;
		this.solucion = s;
		List<Integer> r = search();
		this.time = System.nanoTime() - this.time;
		if(r==null) return this.solucion;
		return SolucionTyP.of(start, r);		
	}
	
	private Boolean forget(TyPVertex v) {
		Integer w = v.maxCarga().intValue();
		Boolean r = this.minValue!=null && w >= this.minValue;
		if(r) this.tree.remove(v);
		return r;
	}

	private List<Integer> search() {
		TyPVertex vertexActual = null;
		while (!heap.isEmpty() && !goal) {
			Handle<Double, AStarTyP> ha = heap.deleteMin();
			AStarTyP dataActual = ha.getValue();
			vertexActual = dataActual.vertex();
			if(forget(vertexActual)) continue;
			for (Integer a : vertexActual.actions()) {
				TyPVertex v = vertexActual.neighbor(a);
				Integer newDistance = v.maxCarga().intValue();
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
		if(!this.goal) return null;
		return acciones(vertexActual);
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosTyP.datos("ficheros/tareas.txt",5);
		TyPVertex e1 = TyPVertex.first();
		SolucionTyP sv = Heuristica.solucionVoraz(e1);
		TyPAStar a = of();
		SolucionTyP s = a.search(e1,sv.getMaxCarga().intValue(),sv);
		System.out.println(a.time());
		System.out.println(s);
	}

}
