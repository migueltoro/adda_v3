package us.lsi.alg.investigadores.manual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.jheaps.AddressableHeap.Handle;
import org.jheaps.tree.FibonacciHeap;

import us.lsi.alg.investigadores.DatosInv;
import us.lsi.alg.investigadores.InvVertex;
import us.lsi.alg.investigadores.SolucionInv;


public class  AStarInv {
	
	public static record AStarDataInv(InvVertex vertex, Integer a,InvVertex lastVertex,
			Integer distanceToOrigin) {
		public static AStarDataInv of(InvVertex vertex, Integer a,InvVertex lastVertex,
			Integer distanceToOrigin) {
			return new AStarDataInv(vertex, a, lastVertex,distanceToOrigin);
		}	
	}
	
	public static AStarInv of() {
		return new AStarInv();
	}
	
	private Map<InvVertex,Handle<Integer,AStarDataInv>> tree;
	private FibonacciHeap<Integer,AStarDataInv> heap; 
	private Boolean goal;
	private Comparator<Integer> cmp;
	private Integer maxValue;
	private SolucionInv solucion;
	private Long time;
	
	private AStarInv() {
		super();
	}
	
	private List<Integer> acciones(InvVertex v) {
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
	
	public SolucionInv solucion() {
		return solucion;
	}

	public SolucionInv search(InvVertex start,Integer maxValue, SolucionInv s) {
		this.time = System.nanoTime();
		Integer distanceToEnd = start.fo();
		AStarDataInv a = AStarDataInv.of(start,null,null,0);
		this.cmp = Comparator.reverseOrder();
		this.heap = new FibonacciHeap<>(cmp);
		Handle<Integer, AStarDataInv> h = this.heap.insert(distanceToEnd,a);
		this.tree = new HashMap<>();
		this.tree.put(start,h);
		this.goal = false;
		this.maxValue = maxValue;
		this.solucion = s;
		List<Integer> r = search();		
		this.time = System.nanoTime() - this.time;
		if(r==null) return this.solucion;
		return SolucionInv.of(start,r);
	}

	private Boolean forget(InvVertex v) {
		Integer w = v.fo();
		Boolean r = this.maxValue!=null && this.cmp.compare(w,this.maxValue) >= 0;
		if(r) this.tree.remove(v);
		return r;
	}
	
	private static Predicate<InvVertex> goalHasSolution() {
		return v -> v.index() == DatosInv.na && IntStream.range(0, DatosInv.m).boxed()
				.allMatch(t -> v.esTrabajoAcabado(t) || !v.esTrabajoIniciado(t));
	}
	
	private List<Integer> search() {
		InvVertex vertexActual = null;
		while (!heap.isEmpty() && !goal) {
			Handle<Integer, AStarDataInv> ha = heap.deleteMin();
			AStarDataInv dataActual = ha.getValue();
			vertexActual = dataActual.vertex();	
			if(forget(vertexActual)) continue;
			for (Integer a : vertexActual.actions()) {
				InvVertex v = vertexActual.neighbor(a);
				Integer newDistance = v.fo();
				Integer newDistanceToEnd = v.fo();				
				if (!tree.containsKey(v)) {	
					AStarDataInv ac = AStarDataInv.of(v, a, vertexActual, newDistance);
					Handle<Integer, AStarDataInv> nh = heap.insert(newDistanceToEnd,ac);
					tree.put(v,nh);	
				}else if (cmp.compare(newDistance, tree.get(v).getValue().distanceToOrigin()) <0 ) {
					AStarDataInv ac = AStarDataInv.of(v, a, vertexActual, newDistance);
					Handle<Integer, AStarDataInv> hv = tree.get(v);
					hv.setValue(ac);
					hv.decreaseKey(newDistanceToEnd);
				}
			}
			this.goal = goalHasSolution().test(vertexActual);
		}
		if(!this.goal) return null;
		return acciones(vertexActual);
	}
	

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		DatosInv.iniDatos("ficheros/investigadores/inv3.txt");
		
		AStarInv as = AStarInv.of();
		
		SolucionInv sv = GreedyInv.solucionVoraz(InvVertex.first());
		System.out.println(sv);

		SolucionInv so = as.search(InvVertex.first(),null,null);
		System.out.println(as.time());
		System.out.println(so);
		so = as.search(InvVertex.first(),sv.fo(),sv);
		System.out.println(as.time());
		System.out.println(so);
//		so = as.search(InvVertex.first(),null,null);
//		System.out.println(as.time());
//		
//		System.out.println(so);

	}

}
