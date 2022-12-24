package us.lsi.alg.pack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.graphs.virtual.VirtualVertex;

public record PackVertex(Integer index, Map<Integer, Integer> carga) implements VirtualVertex<PackVertex,PackEdge,Integer> {


	public static PackVertex of(Integer index,  Map<Integer, Integer> carga) {
		return new PackVertex(index, carga);
	}
	
	public static Predicate<PackVertex> goal() { 
		return  v->v.index == n; 
	}
	public static PackVertex first() { 
		return PackVertex.of(0, new HashMap<>()); 
	}

	public static Integer n = Data.n;
	
	public Integer nc() {
		return this.carga().keySet().size();
	}
	
	public Integer cMax() {
		return this.carga().isEmpty()?0:this.carga().entrySet().stream()
				.max(Comparator.comparing(e->e.getValue())).get().getKey();
	}
	
	public Integer cMin() {
		return this.carga().isEmpty()?0:this.carga().entrySet().stream()
				.min(Comparator.comparing(e->e.getValue())).get().getKey();
	}

	private Integer volumen(Integer i) {
		return Data.volumenesObjetos.get(i);
	}
	
	private Integer volumenContenedor() {
		return Data.volumenContenedor;
	}
	
	@Override
	public Boolean isValid() {		
		return this.index()>=0 && this.index()<=PackVertex.n && this.carga().getOrDefault(this.index(),0) <= volumenContenedor();
	}
	
	@Override
	public List<Integer> actions() {
		if (this.index.equals(n))
			return new ArrayList<>();
		List<Integer> r = IntStream.range(0, this.nc() + 1).boxed()
				.filter(c -> this.carga().getOrDefault(c, 0) + volumen(this.index()) <= volumenContenedor())
				.collect(Collectors.toList());

		Map<Integer, List<Integer>> s = r.stream()
				.collect(Collectors.groupingBy(c -> this.carga().getOrDefault(c, 0)));

		r = s.values().stream().map(ls -> ls.get(0)).collect(Collectors.toList());

		Collections.sort(r,
				Comparator.comparing(c -> this.carga().getOrDefault(c, 0) + volumen(this.index())).reversed());

		if (this.index.equals(n - 1))
			r = List.of(r.get(0));
		return r;
	}

	@Override
	public PackVertex neighbor(Integer a) {
		Map<Integer,Integer> carga = new HashMap<>(this.carga());
		carga.put(a,this.carga().getOrDefault(a,0)+volumen(this.index()));
		PackVertex r= PackVertex.of(this.index()+1,carga);
		return r;
	}

	@Override
	public PackEdge edge(Integer a) {
		return PackEdge.of(this,neighbor(a),a);
	}
	
	public PackEdge greedyEdge() {
		Integer a = IntStream.range(0,this.nc()+1).boxed()
				.filter(c->carga.getOrDefault(c,0)+volumen(index)<= volumenContenedor())
				.max(Comparator.comparing(c->carga.getOrDefault(c,0)))
				.orElseGet(()->0);
		return PackEdge.of(this,this.neighbor(a),a);
	}
	
	public PackVertex copy() {
		Map<Integer, Integer> carga = new HashMap<>(this.carga);
		return PackVertex.of(this.index, carga);
	}

	@Override
	public String toString() {
		return "PackVertex [index=" + index +  ", carga=" + carga + ", cmax=" + cMax() + ", cmin=" + cMin()
				+ ", nc=" + nc() + "]";
	}

}
