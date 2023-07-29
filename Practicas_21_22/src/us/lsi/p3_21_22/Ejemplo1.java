package us.lsi.p3_21_22;

import java.util.Iterator;
import java.util.List;

import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;

/*
 	PI3 - Ejemplo 1

	Diseñe un algoritmo que dado un árbol binario de enteros devuelva cierto en caso de
	que para cada nodo que tenga 2 hijos no vacíos se cumpla que su etiqueta es igual a la
	suma de las etiquetas de las raíces de sus 2 hijos.

	Resolver de forma recursiva, y en el caso que sea posible, también de forma iterativa 
	haciendo uso de los iteradores sobre árboles que se proporciona.

	[Recursivo, Iterativo funcional, Iterativo imperativo] 

 */

public class Ejemplo1 {

	private static List<BinaryTree<Integer>> inputs;

	private static void cargaDatos() {
		Ejemplo1.inputs = Files2.streamFromFile("ficheros/p3/Ejemplo1_DatosEntrada.txt")
				.map(linea -> BinaryTree.parse(linea, s -> Integer.parseInt(s))).toList();
	}

	private static Boolean recursivo(BinaryTree<Integer> tree) {
		return switch (tree) {
		case BEmpty<Integer> t -> true;
		case BLeaf<Integer> t-> true;
		case BTree<Integer> t when (!t.left().isEmpty() && !t.right().isEmpty()) -> 
			t.label().equals(t.right().optionalLabel().get()+t.left().optionalLabel().get()) &&
			recursivo(t.left()) && 
			recursivo(t.right());
		case BTree<Integer> t -> false;
		};
	}

	
	private static Boolean pv(BinaryTree<Integer> tree) {
		return switch (tree) {
		case BEmpty<Integer> t -> true;
		case BLeaf<Integer> t-> true;
		case BTree<Integer> t when (!t.left().isEmpty() && !t.right().isEmpty()) -> 
			t.label().equals(t.right().optionalLabel().get()+t.left().optionalLabel().get());	
		case BTree<Integer> t -> false;
		};
	}

	private static Boolean iterativo_funcional(BinaryTree<Integer> tree) {
		return tree.byDeph().allMatch(t -> pv(t));

	}

	private static Boolean iterativo_imperativo(BinaryTree<Integer> tree) {
		Boolean res = true;
		Iterator<BinaryTree<Integer>> it = tree.byDeph().iterator();
		while (res && it.hasNext()) {
			BinaryTree<Integer> nxt = it.next();
			res = pv(nxt);
			if(!res) break;			 
		}
		return res;
	}

	public static void main(String[] args) {

		cargaDatos();
		
		System.out.println("PI3 - Ejemplo 1");

		System.out.println("\nSOLUCIÓN RECURSIVA:\n");
		inputs.stream().forEach(x -> System.out.println(x + " [" + recursivo(x) + "]"));

		System.out.println("\nSOLUCIÓN ITERATIVA FUNCIONAL:\n");
		inputs.stream().forEach(x -> System.out.println(x + " [" + iterativo_funcional(x) + "]"));

		System.out.println("\nSOLUCIÓN ITERATIVA IMPERATIVA:\n");
		inputs.stream().forEach(x -> System.out.println(x + " [" + iterativo_imperativo(x) + "]"));

	}

}
