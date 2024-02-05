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

import us.lsi.alg.mochila.MochilaVertex;
import us.lsi.alg.mochila.SolucionMochila;
import us.lsi.mochila.datos.DatosMochila;

public class MochilaAStar {
	
	public static record AStarMochila(MochilaVertex vertex, Integer a, MochilaVertex lastVertex,
			Double distanceToOrigin) {
		public static AStarMochila of(MochilaVertex vertex, Integer a, MochilaVertex lastVertex,
			Double distanceToOrigin) {
			return new AStarMochila(vertex, a, lastVertex,distanceToOrigin);
		}	
	}
	
	public static MochilaAStar of() {
		return new MochilaAStar();
	}
	
	public Long time() {
		return time;
	}
	
	private MochilaVertex start;
	private Integer maxValue;
	private SolucionMochila solucion;
	private Map<MochilaVertex,Handle<Double,AStarMochila>> tree;
	private FibonacciHeap<Double,AStarMochila> heap; 
	private Boolean goal;
	private Comparator<Double> cmp;
	private Long time;
	
	private MochilaAStar() {
		super();
	}
	
	private List<Integer> acciones(MochilaVertex v) {
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

	public SolucionMochila search(MochilaVertex start, Integer maxValue, SolucionMochila s) {
		this.time = System.nanoTime();
		this.start = start;
		this.maxValue = maxValue;
		this.solucion = s;
		Double distanceToEnd = Heuristica.heuristica(start);
		AStarMochila a = AStarMochila.of(start, null,null,0.);
		this.cmp = Comparator.reverseOrder();
		this.heap = new FibonacciHeap<>(cmp);
		Handle<Double, AStarMochila> h = this.heap.insert(distanceToEnd,a);
		this.tree = new HashMap<>();
		this.tree.put(start,h);
		this.goal = false;
		List<Integer> r = search();
		if(r==null) return this.solucion;
		this.time = System.nanoTime() - this.time;
		return SolucionMochila.of(start, r);
	}

	public List<Integer> search() {		
		MochilaVertex vertexActual = null;
		while (!heap.isEmpty() && !goal) {
			Handle<Double, AStarMochila> ha = heap.deleteMin();
			AStarMochila dataActual = ha.getValue();
			vertexActual = dataActual.vertex();
			if(this.maxValue != null &&  Heuristica.heuristica(vertexActual) <= this.maxValue) continue;
			for (Integer a : vertexActual.actions()) {
				MochilaVertex v = vertexActual.neighbor(a);
				Double newDistance = dataActual.distanceToOrigin()+a*DatosMochila.getValor(vertexActual.index());
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
		if(!this.goal) return null;
		return acciones(vertexActual);
	}
	
	public SolucionMochila solucion(List<Integer> acciones) {
		return SolucionMochila.of(start,acciones);
	}
	
	

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosMochila.iniDatos("ficheros/mochila/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;
		MochilaVertex v1 = MochilaVertex.of(0, DatosMochila.capacidadInicial);
		SolucionMochila sv = Heuristica.solucionVoraz(v1);
		MochilaAStar a = MochilaAStar.of();
		SolucionMochila s = a.search(v1,null,null);
		System.out.println(a.time() + "  === "+s);
		s = a.search(v1,sv.valor(),s);
		System.out.println(a.time() + "  === "+s);
	}

	

}
