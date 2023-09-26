package us.lsi.p3_22;


import java.util.List;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Comparator;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;

/*
 	PI3 - Ejercicio 3

	Diseñe un algoritmo que dado un árbol binario de enteros, determine el camino del
	árbol desde la raíz a una hoja no vacía tal que el producto de sus etiquetas sea máximo.

	Proporcione una solución recursiva, y en los casos que sea posible, también 
	una solución iterativa haciendo uso de los iteradores sobre árboles que se
	proporcionan.

	[Recursivo]
 */

public class Ejercicio3 {	

	private static List<BinaryTree<Integer>> inputs; 

	private static void cargaDatos () {
		inputs = Files2.streamFromFile("ficheros/p3/PI3E3_DatosEntrada.txt")
				.map(linea -> BinaryTree.parse(linea,s->Integer.parseInt(s)))
				.toList();
	}
	
	private static Integer p(List<Integer> ls) {
		return ls.stream().reduce(1,(x,y)->x*y);
	}

	private static List<Integer> recursivo (BinaryTree<Integer> tree) {
		return recursivo(tree,new ArrayList<>());
	}
	
	private static List<Integer> recursivo (BinaryTree<Integer> tree, List<Integer> ls) {
		return switch (tree) {
		case BEmpty<Integer> t -> null;
		case BLeaf<Integer> t -> List2.addLast(ls,t.label());
		case BTree<Integer> t -> {
			List<Integer> lft = recursivo(t.left(),List2.addLast(ls,t.label()));
			List<Integer> rgt = recursivo(t.right(),List2.addLast(ls,t.label()));
			yield Stream.of(lft,rgt).filter(x->x != null).max(Comparator.comparing(y->p(y))).orElse(null);
		}
		};
	}
	
	public static void main(String[] args) {

		cargaDatos ();

		System.out.println("PI3 - Ejercicio 3");

		System.out.println("\nSOLUCIÓN RECURSIVA:\n");	
		inputs.stream()
		.forEach(x->System.out.println(x+": "+recursivo(x)));

	}



}

