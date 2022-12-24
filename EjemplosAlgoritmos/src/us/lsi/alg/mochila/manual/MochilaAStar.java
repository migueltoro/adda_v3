package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jheaps.AddressableHeap.Handle;
import org.jheaps.tree.FibonacciHeap;

public class MochilaAStar {
	
	public static record AStarMochila(MochilaProblem vertex, Integer a, MochilaProblem lastVertex,
			Double distanceToOrigin) {
		public static AStarMochila of(MochilaProblem vertex, Integer a, MochilaProblem lastVertex,
			Double distanceToOrigin) {
			return new AStarMochila(vertex, a, lastVertex,distanceToOrigin);
		}	
	}
	
	public static MochilaAStar of(MochilaProblem start) {
		return new MochilaAStar(start);
	}
	
	public MochilaProblem start;
	public Map<MochilaProblem,Handle<Double,AStarMochila>> tree;
	public FibonacciHeap<Double,AStarMochila> heap; 
	public Boolean goal;
	public Comparator<Double> cmp;
	
	private MochilaAStar(MochilaProblem start) {
		super();
		this.start = start;
		Double distanceToEnd = Heuristica.heuristica(start);
		AStarMochila a = AStarMochila.of(start, null,null,0.);
		this.cmp = Comparator.reverseOrder();
		this.heap = new FibonacciHeap<>(cmp);
		Handle<Double, AStarMochila> h = this.heap.insert(distanceToEnd,a);
		this.tree = new HashMap<>();
		this.tree.put(start,h);
		this.goal = false;
	}
	
	private List<Integer> acciones(MochilaProblem v) {
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
		MochilaProblem vertexActual = null;
		while (!heap.isEmpty() && !goal) {
			Handle<Double, AStarMochila> ha = heap.deleteMin();
			AStarMochila dataActual = ha.getValue();
			vertexActual = dataActual.vertex();
			for (Integer a : vertexActual.acciones()) {
				MochilaProblem v = vertexActual.vecino(a);
				Double newDistance = dataActual.distanceToOrigin()+a*DatosMochila.valor(vertexActual.index());
				Double newDistanceToEnd = newDistance + Heuristica.heuristica(v);				
				if (!tree.containsKey(v)) {	
					AStarMochila ac = AStarMochila.of(v, a, vertexActual, newDistance);
					Handle<Double, AStarMochila> nh = heap.insert(newDistanceToEnd,ac);
					tree.put(v,nh);	
				}else if (cmp.compare(newDistance, tree.get(v).getValue().distanceToOrigin()) <0 ) {
					AStarMochila ac = AStarMochila.of(v, a, vertexActual, newDistance);
					Handle<Double, AStarMochila> hv = tree.get(v);
					hv.setValue(ac);
					hv.decreaseKey(newDistanceToEnd);
				}
			}
			this.goal = vertexActual.index() == DatosMochila.n;
		}
		return acciones(vertexActual);
	}
	
	public SolucionMochila solucion(List<Integer> acciones) {
		return SolucionMochila.of(start,acciones);
	}
	
	

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.datos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;
		MochilaProblem v1 = MochilaProblem.of(0, DatosMochila.capacidadInicial);
		MochilaAStar a = MochilaAStar.of(v1);
		List<Integer> ls = a.search();
		SolucionMochila s = a.solucion(ls);
		System.out.println(s);
	}

	

}
