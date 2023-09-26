package us.lsi.p3_22;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

/*
	PI3 - Ejercicio 1
	
	Diseñe un algoritmo que dado un árbol n-ario de tipo genérico y un predicado sobre
	dicho tipo, devuelva un conjunto que contenga las etiquetas de las hojas de dicho árbol
	que cumplan el predicado.

	Proporcione una solución recursiva, y en los casos que sea posible, también 
	una solución iterativa haciendo uso de los iteradores sobre árboles que se
	proporcionan.
	
	[Recursivo, Iterativo (a elegir entre funcional o imperativo)]

 */

public class Ejercicio1 {

	private static List<Tree<Integer>> inputs; 
	private static final Predicate<Integer> PAR = x-> x%2==0;
	private static final Predicate<Integer> MENOR_QUE_CINCO = x->x<5;

	private static void cargaDatos () {
		inputs = Files2.streamFromFile("ficheros/p3/PI3E1_DatosEntrada.txt")
				.map(linea -> Tree.parse(linea,s->Integer.parseInt(s)))
				.toList();
	}
	
	private static<E> Set<E> solucion_recursiva (Tree<E> tree, Predicate<E> predicate) {
		Set<E> r = new HashSet<>();
		recursivo (tree,predicate,r);	
		return r;
	}
	
	private static<E> void recursivo (Tree<E> tree, Predicate<E> predicate, Set<E> res) {
		switch(tree) {
		case TEmpty<E> t -> {;}
		case TLeaf<E> t when predicate.test(t.label())-> res.add(t.label());
		case TLeaf<E> t -> {;}
		case TNary<E> t ->	t.children().forEach(tc->recursivo(tc, predicate, res));
		}
	}	
	
	private static <E> Boolean pv(Tree<E> tree, Predicate<E> predicate) {
		return switch (tree) {
		case TLeaf<E> t when predicate.test(t.label()) -> true;
		default -> false;
		};
	}
	
	private static <E> Set<E> iterativo_funcional(Tree<E> tree, Predicate<E> predicate) {
		return tree.byLevel()
				.filter(tr -> pv(tr.tree(), predicate))
				.map(t -> t.tree().optionalLabel().get())
				.collect(Collectors.toSet());
	}
	
	private static<E> Set<E> iterativo_imperativo (Tree<E> tree, Predicate<E> predicate) {
		Set<E> res = new HashSet<E>();
		Iterator<Tree<E>> it = tree.byDepth().iterator();		
		while (it.hasNext()) {			
			Tree<E> t = it.next();			
			if (pv(t, predicate)) {
					res.add(t.optionalLabel().get());				
			}		
		}		
		return res;		
	}
	
	public static void main(String[] args) {

		cargaDatos ();
		
		System.out.println("PI3 - Ejercicio 1");

		System.out.println("\nSOLUCIÓN RECURSIVA-PAR:\n");	
		inputs
		.stream()
		.forEach(x->System.out.println(x+": "+solucion_recursiva(x,PAR)));

		System.out.println("\nSOLUCIÓN RECURSIVA-MENOR_QUE_CINCO:\n");	
		inputs
		.stream()
		.forEach(x->System.out.println(x+": "+solucion_recursiva(x,MENOR_QUE_CINCO)));
		
		System.out.println("\nSOLUCIÓN ITERATIVA FUNCIONAL-PAR:\n");	
		inputs
		.stream()
		.forEach(x->System.out.println(x+": "+iterativo_funcional(x,PAR)));

	
		System.out.println("\nSOLUCIÓN ITERATIVA FUNCIONAL-MENOR_QUE_CINCO:\n");	
		inputs.stream()
		.forEach(x->System.out.println(x+": "+iterativo_funcional(x,MENOR_QUE_CINCO)));
		
		System.out.println("\nSOLUCIÓN ITERATIVA IMPERATIVA-PAR:\n");
		inputs.stream()
		.forEach(x->System.out.println(x+": "+iterativo_imperativo(x,PAR)));

	
		System.out.println("\nSOLUCIÓN ITERATIVA IMPERATIVA-MENOR_QUE_CINCO:\n");	
		inputs.stream()
		.forEach(x->System.out.println(x+": "+iterativo_imperativo(x,MENOR_QUE_CINCO)));

	}

}

