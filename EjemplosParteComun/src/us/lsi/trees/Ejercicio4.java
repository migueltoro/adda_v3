package us.lsi.trees;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejercicio4 {
	/*
	PI3 - Ejercicio 4

	Dado un árbol binario de cadena de caracteres, diseñe un algoritmo que devuelva cierto
	si se cumple que, para todo nodo, el número total de vocales contenidas en el subárbol 
	izquierdo es igual al del subárbol derecho. 
	Proporcione una solución también para árboles n-arios entendiendo que en este caso cada subarbol 
	de un árbol tiene el mismo número de vocales.

	Resolver de forma recursiva
	*/
	
	private static record Tupla(Boolean b, Integer n) { 
		public static Tupla of(Boolean b, Integer n) {
			return new Tupla(b, n);
		}
	}
	
	private static int numVocales(String s) {
		return (int) s.toLowerCase().chars()
				.filter(ch -> 'a' == ch || 'e' == ch || 'i' == ch || 'o' == ch || 'u' == ch)
				.count();
	}
	
	// Árboles binarios
	public static Boolean solucion_recursiva(BinaryTree<String> tree) {
		return recursivo(tree).b();
	}
	
	private static Tupla recursivo(BinaryTree<String> tree) {
		return switch(tree) {
			case BEmpty<String> t ->  Tupla.of(true, 0); 
			case BLeaf<String> t ->  Tupla.of(true, numVocales(t.label())); 				
			case BTree<String> t -> {
				Tupla r1 = recursivo(t.left());
				Tupla r2 = recursivo(t.right());
				yield Tupla.of(r1.b() && r2.b() && r1.n()==r2.n(), 
								r1.n()+r2.n()+ numVocales(t.label()));
			}
		};
	}	

	// Árboles n-arios
	public static Boolean solucion_recursiva(Tree<String> tree) {
		return recursivo(tree).b();
	}
	
	private static Tupla recursivo(Tree<String> tree) {
		return switch(tree) {
			case TEmpty<String> t ->  Tupla.of(true, 0); 
			case TLeaf<String> t ->  Tupla.of(true, numVocales(t.label())); 				
			case TNary<String> t -> {
				Integer nv = numVocales(t.label())+t.elements().stream().mapToInt(x->recursivo(x).n).sum();
				Boolean all = t.elements().stream().allMatch(x->recursivo(x).b);
				yield Tupla.of(all,nv);
			}
		};
	}

}
