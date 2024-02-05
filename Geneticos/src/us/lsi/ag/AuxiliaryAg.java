package us.lsi.ag;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.Graph;
import org.jgrapht.alg.util.Pair;

import us.lsi.common.List2;
import us.lsi.common.Multiset;
import us.lsi.common.Preconditions;
import us.lsi.common.Set2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.streams.Collectors2;

public class AuxiliaryAg {
	
	
	public static Double convert(Double d, Double min, Double max) {
		Preconditions.checkArgument(min < max, 
				String.format("E valor de min = %.2f debe ser inferior a max = %.2f", min, max));
		return min + (max-min)*d;
	}
	
	public static Integer convert(Double d, Integer min, Integer max) {
		Preconditions.checkArgument(min < max, 
				String.format("E valor de min = %d debe ser inferior a max = %d", min, max));
		return (int) (min + (max-min)*d);
	}
	
	public static <E> List<E> convert(List<Double> d, List<E> normalSequence) {		
		Preconditions.checkArgument(d.size() == normalSequence.size(), 
				String.format("Los tamaños %d, %d debe ser iguales",d.size(), normalSequence.size()));
		Integer n = d.size();
		return IntStream.range(0, n).boxed()
				.map(i->Pair.of(d.get(i),normalSequence.get(i)))
				.sorted(Comparator.comparing(p->p.getFirst()))
				.map(p->p.getSecond())
				.toList();			
	}
	
	public static Integer convert(Double d, List<Integer> values) {
		Integer index = (int) (values.size()*d);
		return values.get(index);
	}
	
	public static Double distanceToBool(Boolean in) {
		return in?0.:1.;
	}
	
	/**
	 * @param in Valor que tiene que cumplir la condic&oacute;n in &lt; 0.
	 * @return in*in si in no cumple la condic&oacute;n, si la cumple cero
	 */
	public static Double distanceToLeZero(Double in) {
		Double r = 0.;		
		if(in > 0) {
			r = in*in;
		}
		return r;
	}
	
	/**
	 * @param in Valor que tiene que cumplir la condic&oacute;n in &gt; 0.
	 * @return in*in si in no cumple la condic&oacute;n, si la cumple cero
	 */
	public static Double distanceToGeZero(Double in) {
		Double r = 0.;		
		if(in < 0) {
			r = in*in;
		}
		return r;
	}
	
	/**
	 * @param in Valor que tiene que cumplir la condic&oacute;n in = 0.
	 * @return in*in
	 */
	public static Double distanceToEqZero(Double in) {
		return in*in;
	}
	
	/**
	 * @param ls Una lista 
	 * @return Genera el cuadrdao de la diferencia entre el n&uacute;mero de el elementos diferentes y el de la lista
	 */
	public static <E> Boolean allDifferents(List<E> ls) {
		Integer n = ls.size();
		Integer m = ls.stream().collect(Collectors.toSet()).size();
		return n.equals(m);
	}
	
	/**
	 * @param ls Una lista 
	 * @return Genera el cuadrado de la diferencia entre el n&uacute;mero de el elementos diferentes y el de la lista
	 */
	public static <E> Double distanceToAllDifferents(List<E> ls) {
		Integer n = ls.size();
		Integer m = ls.stream().collect(Collectors.toSet()).size();
		return (double)(n-m)*(n-m);
	}
	
	/**
	 * @param ls1 Una lista 
	 * @param ls2 Una lista 
	 * @return true si ls1 es una permta
	 */
	public static <E> Boolean isPermutation(List<E> ls1, List<E> ls2) {
		Multiset<E> m1 = ls1.stream().collect(Collectors2.toMultiset());
		Multiset<E> m2 = ls2.stream().collect(Collectors2.toMultiset());
		return m1.equals(m2);
	}
	
	/**
	 * @param ls Una lista 
	 * @return Genera el cuadrado del cardinal de la diferencia simetrica entre ls1 y ls2
	 */
	public static <E> Double distanceToPermutation(List<E> ls1, List<E> ls2) {
		Multiset<E> m1 = ls1.stream().collect(Collectors2.toMultiset());
		Multiset<E> m2 = ls2.stream().collect(Collectors2.toMultiset());
		Integer n = Multiset.symmetricDifference(m1, m2).size();
		return (double) n*n;
	}
	
	/**
	 * @param ls Una lista 
	 * @return Genera el cuadrado del número de de los elementos false
	 */
	
	public static <E> Double distanceToAllMatch(List<Boolean> ls) {
		Integer m = ls.stream().mapToInt(e->e?0:1).sum();
		return (double)m *m;
	}
	
	public static <E> Boolean equals(List<E> ls1, List<E> ls2) {
		return ls1.equals(ls2);
	}
	
	public static <E> Boolean equals(Set<E> ls1, Set<E> ls2) {
		return ls1.equals(ls2);
	}
	
	public static <E> Double distanceToEquals(List<E> ls1, List<E> ls2) {
		Integer n = List2.symmetricDifference(ls1, ls2).size();
		return (double) n*n;
	}
	
	public static <E> Double distanceToEquals(Set<E> ls1, Set<E> ls2) {
		Integer n = Set2.symmetricDifference(ls1, ls2).size();
		return (double) n*n;
	}
	
	private static List<Integer> vertices(Graph<Integer,SimpleEdge<Integer>> graph, List<SimpleEdge<Integer>> edges){
		Integer n = edges.size();
		List<Integer> vertices = IntStream.range(0,n).boxed()
				.map(i->graph.getEdgeSource(edges.get(i)))
				.collect(Collectors.toList());
		vertices.add(graph.getEdgeTarget(edges.get(n-1)));
		return vertices;
	}
	
	public static Boolean isSimpleOpenPathVertices(Graph<Integer,SimpleEdge<Integer>> graph, List<Integer> vertices){
		Integer n = vertices.size();
		return allDifferents(vertices) &&
				vertices.stream().allMatch(v->graph.containsVertex(v)) &&
				IntStream.range(0,n-1).boxed().allMatch(i->graph.containsEdge(vertices.get(i),vertices.get(i+1)));
	}
	
	public static Boolean isSimpleOpenPathEdges(Graph<Integer,SimpleEdge<Integer>> graph, List<SimpleEdge<Integer>> edges){		
		return isSimpleOpenPathVertices(graph,vertices(graph,edges));
	}
	
	public static Boolean isSimpleClosedPathVertices(Graph<Integer,SimpleEdge<Integer>> graph, List<Integer> vertices){
		Integer n = vertices.size();
		return isSimpleOpenPathVertices(graph,vertices) &&
			   graph.containsEdge(vertices.get(n-1),vertices.get(0));
	}
	
	public static Boolean isSimpleClosedPathEdges(Graph<Integer,SimpleEdge<Integer>> graph, List<SimpleEdge<Integer>> edges){		
		return isSimpleClosedPathVertices(graph,vertices(graph,edges));
	}
	
	public static Double distanceToSimpleOpenPathVertices(Graph<Integer,SimpleEdge<Integer>> graph, List<Integer> vertices){
		Integer n = vertices.size();
		Double d1 = distanceToAllDifferents(vertices);
		Double d2 =	distanceToAllMatch(vertices.stream().map(v->graph.containsVertex(v)).toList());
		Double d3 =	distanceToAllMatch(IntStream.range(0,n-1).boxed()
				.map(i->graph.containsEdge(vertices.get(i),vertices.get((i+1)))).toList());
		return d1+d2+d3;
	}
	
	public static Double distanceToSimpleClosedPathVertices(Graph<Integer,SimpleEdge<Integer>> graph, List<Integer> vertices){
		Integer n = vertices.size();
		return distanceToSimpleOpenPathVertices(graph,vertices)+
				10*distanceToBool(graph.containsEdge(vertices.get(n-1),vertices.get(0)));
	}
	
	public static Double distanceToSimpleOpenPathEdges(Graph<Integer,SimpleEdge<Integer>> graph, List<SimpleEdge<Integer>> edges) {
		List<Integer> vertices = vertices(graph,edges);
		Integer n = vertices.size();
		return distanceToSimpleOpenPathVertices(graph,vertices(graph,edges))
				+ 10*distanceToBool(graph.containsEdge(vertices.get(n-1),vertices.get(0)));
	}
	
	public static void test1() {
		List<Integer> sn = List.of(0,1,1,1,2,3,4,5,6);
		Integer n = sn.size();
		Random r = new Random();
		List<Double> d = r.doubles().limit(n).boxed().toList();
		System.out.println(convert(d,sn));
	}
	
	public static void main(String[] args) {
		
	}

}
