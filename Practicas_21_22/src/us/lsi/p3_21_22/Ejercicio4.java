package us.lsi.p3_21_22;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

	/*

	PI3 - Ejercicio 4

	Diseñe un algoritmo que dado un árbol n-ario de caracteres devuelva un conjunto de
	cadenas de caracteres que contenga todas las cadenas palíndromas que se formen desde
	la raíz a una hoja no vacía.

	Proporcione una solución recursiva, y en los casos que sea posible, también 
	una solución iterativa haciendo uso de los iteradores sobre árboles que se
	proporcionan.
	
	[Recursivo]

	 */

public class Ejercicio4 {
	
	private static List<Tree<Character>> inputs; 

	private static void cargaDatos () {
		inputs = Files2
				.streamFromFile("ficheros/p3/PI3E4_DatosEntrada.txt")
				.map(linea -> Tree.parse(linea,s->s.charAt(0)))
				.toList();
	}
	
	private static Set<String> solucion_recursiva (Tree<Character> tree) {
		return recursivo (tree,new LinkedList<Character>());		
	} 
	
	private static Set<String> recursivo(Tree<Character> tree, List<Character> lc) {
		return switch (tree) {
		case TEmpty<Character> t -> new HashSet<>();
		case TLeaf<Character> t -> {
			List<Character> lc1 = List2.addLast(lc, t.label());
			if (isPalindrome(lc1))
				yield ts(lc1);
			else
				yield new HashSet<>();
		}
		case TNary<Character> t -> {
			List<Character> lc2 = List2.addLast(lc, t.label());
			yield t.elements().stream()
				.map(e -> recursivo(e, lc2))
				.flatMap(x -> x.stream())
				.collect(Collectors.toSet());
		}
		};
	}
	
	private static Set<String> ts(List<Character> word) {
		String r = word.stream()
				.map(x -> x.toString())
				.collect(Collectors.joining(""));
		return Set2.of(r);
	}
	
	private static Boolean isPalindrome(List<Character> word) {
		Integer n = word.size();
		return IntStream.range(0, n).allMatch(i->word.get(i) == word.get(n-1-i));
	}
	
public static void main(String[] args) {
		
		cargaDatos ();
		
		System.out.println("PI3 - Ejercicio 4");
		
		System.out.println("\nSOLUCIÓN RECURSIVA:\n");	
		inputs
		.stream()
		.forEach(x->System.out.println(x+": "+solucion_recursiva(x)));

	}
	
	
}

