package us.lsi.alg.colorgraphs.manual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jheaps.AddressableHeap.Handle;
import org.jheaps.tree.FibonacciHeap;

import us.lsi.alg.colorgraphs.ColorVertex;
import us.lsi.alg.colorgraphs.DatosColor;
import us.lsi.alg.colorgraphs.SolucionColor;


public class AStarColor {
	
	public static record AStarDataColor(ColorVertex vertex, Integer a,ColorVertex lastVertex,
			Integer distanceToOrigin) {
		public static AStarDataColor of(ColorVertex vertex, Integer a,ColorVertex lastVertex,
			Integer distanceToOrigin) {
			return new AStarDataColor(vertex, a, lastVertex,distanceToOrigin);
		}	
	}
	
	public static AStarColor of(ColorVertex start) {
		return new AStarColor(start);
	}
	
	private Map<ColorVertex,Handle<Integer,AStarDataColor>> tree;
	private FibonacciHeap<Integer,AStarDataColor> heap; 
	private Boolean goal;
	private Comparator<Integer> cmp;
	private Integer minValue;
	
	private AStarColor(ColorVertex start) {
		super();
		Integer distanceToEnd = start.nc();
		AStarDataColor a = AStarDataColor.of(start,null,null,0);
		this.cmp = Comparator.naturalOrder();
		this.heap = new FibonacciHeap<>(cmp);
		Handle<Integer, AStarDataColor> h = this.heap.insert(distanceToEnd,a);
		this.tree = new HashMap<>();
		this.tree.put(start,h);
		this.goal = false;
		this.minValue = DatosColor.m;
	}
	
	private List<Integer> acciones(ColorVertex v) {
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

	private Boolean forget(ColorVertex v) {
		Integer w = v.nc();
		Boolean r = this.cmp.compare(w,this.minValue) >= 0;
		if(r) this.tree.remove(v);
		return r;
	}

	public List<Integer> search() {
		ColorVertex vertexActual = null;
		while (!heap.isEmpty() && !goal) {
			Handle<Integer, AStarDataColor> ha = heap.deleteMin();
			AStarDataColor dataActual = ha.getValue();
			vertexActual = dataActual.vertex();	
			if(forget(vertexActual)) continue;
			for (Integer a : vertexActual.actions()) {
				ColorVertex v = vertexActual.neighbor(a);
				Integer newDistance = v.nc();
				Integer newDistanceToEnd = v.nc();				
				if (!tree.containsKey(v)) {	
					AStarDataColor ac = AStarDataColor.of(v, a, vertexActual, newDistance);
					Handle<Integer, AStarDataColor> nh = heap.insert(newDistanceToEnd,ac);
					tree.put(v,nh);	
				}else if (cmp.compare(newDistance, tree.get(v).getValue().distanceToOrigin()) <0 ) {
					AStarDataColor ac = AStarDataColor.of(v, a, vertexActual, newDistance);
					Handle<Integer, AStarDataColor> hv = tree.get(v);
					hv.setValue(ac);
					hv.decreaseKey(newDistanceToEnd);
				}
			}
			this.goal = vertexActual.index() == DatosColor.n;
		}
		return acciones(vertexActual);
	}
	

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		DatosColor.data(4,"ficheros/andalucia.txt");
		
		AStarColor as = AStarColor.of(ColorVertex.first());
		
		List<Integer> acciones = as.search();
		
		System.out.println("A* = "+ SolucionColor.of(acciones));

	}
}
