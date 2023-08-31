package us.lsi.p3_21_22;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;

/*

	PI3 - Ejercicio 5

	Diseñe un algoritmo que dado un árbol binario de enteros devuelva un Map<Paridad,List<Integer>> que incluya las etiquetas 
	de los nodos que tengan 2 hijos no vacíos, y que cumplan que dicha etiqueta sea mayor que la etiqueta de su hijo izquierdo 
	y menor que la de su hijo derecho, agrupados teniendo en cuenta si son pares o no. Paridad es un enumerado con los valores Par e Impar.  

	Proporcione una solución recursiva, y en los casos que sea posible, también 
	una solución iterativa haciendo uso de los iteradores sobre árboles que se
	proporcionan.
	
	[Recursivo, Iterativo (a elegir entre funcional o imperativo)]
	
 */	

public class Ejercicio5 {

	private static List<BinaryTree<Integer>> inputs; 
	
	private static enum ParE {Par, Impar};
	
	private static ParE paridad(Integer e) {
		ParE res = ParE.Impar;
		if (e%2==0) res = ParE.Par;
		return res;
	}

	
	private static void cargaDatos () {

		inputs = Files2.streamFromFile("ficheros/p3/PI3E5_DatosEntrada.txt")
				.map(linea -> BinaryTree.parse(linea,	p->Integer.parseInt(p)))
				.toList();

	}
	
	private static Map<ParE,List<Integer>> solucion_recursiva (BinaryTree<Integer> tree){
		Map<ParE,List<Integer>> m = new HashMap<ParE,List<Integer>>();
		recursivo (tree,m);
		return m;
	}

	private static void recursivo (BinaryTree<Integer> tree, Map<ParE,List<Integer>> ac) {
		switch (tree) {		
		case BEmpty<Integer> t -> {;} 				
		case BLeaf<Integer> t -> {;} 
		case BTree<Integer> t -> {
			if (check(tree)) updateMap(ac,t.label());
			recursivo(t.left(),ac);
			recursivo(t.right(),ac);
		}
		}
	}
	
	
	private static Map<ParE, List<Integer>> iterativo_funcional(BinaryTree<Integer> tree) {
		return tree.byDepth().filter(t -> check(t))
				.collect(Collectors.groupingBy(t -> paridad(t.optionalLabel().get()),
						Collectors.mapping(t -> t.optionalLabel().get(), Collectors.toList())));
	}
	
	private static Map<ParE,List<Integer>> iterativo_imperativo (BinaryTree<Integer> tree){
		Map<ParE,List<Integer>> res = new HashMap<ParE,List<Integer>>();
		Iterator<BinaryTree<Integer>> it = tree.byDepth().iterator();		
		while (it.hasNext()) {
			BinaryTree<Integer> nxt = it.next();
			if(check(nxt)) updateMap(res,nxt.optionalLabel().get());
		}
		return res;
	}
	
	private static Boolean check (BinaryTree<Integer> tree) {
		return switch(tree) {
		case BEmpty<Integer> t -> false;
		case BLeaf<Integer> t -> false;
		case BTree<Integer> t when (!t.left().isEmpty() && 
									!t.right().isEmpty() && 
									t.label()>t.left().optionalLabel().get() &&
									t.label()<t.right().optionalLabel().get()) -> true;
		case BTree<Integer> t -> false;

		};
	}

	
	private static void updateMap (Map<ParE,List<Integer>> map, Integer e) {
		List<Integer> ls = map.getOrDefault(paridad(e),new ArrayList<>());
		ls.add(e);
		map.put(paridad(e), ls);
	}
	
	public static void main(String[] args) {

		cargaDatos ();
		
		System.out.println("PI3 - Ejercicio 5");

		System.out.println("\nSOLUCIÓN RECURSIVA:\n");	
		inputs.stream()
		.forEach(x->System.out.println(x+":\t"+solucion_recursiva(x)));

		System.out.println("\n\nSOLUCIÓN ITERATIVA FUNCIONAL:\n");	
		inputs.stream()
		.forEach(x->System.out.println(x+":\t"+iterativo_funcional(x)));
		
		System.out.println("\n\nSOLUCIÓN ITERATIVA IMPERATIVA:");	
		inputs.stream()
		.forEach(x->System.out.println(x+":\t"+iterativo_imperativo(x)));
			
	}

}
