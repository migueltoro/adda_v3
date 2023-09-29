package us.lsi.p2;

import java.util.List;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;

public class Ejemplo4 {

	/*
	 * PI3 - Ejemplo 4
	 * 
	 * Implemente una función booleana que, dados un árbol binario de caracteres y
	 * una lista de caracteres, determine si existe un camino en el árbol de la raíz
	 * a una hoja que sea igual a la lista.
	 * 
	 * Resolver de forma recursiva
	 */

	
	public static Boolean solucion_recursiva (BinaryTree<Character> tree,List<Character> list) {
		return recursivo(tree,list,0);	
	}
	
	public static Boolean recursivo(BinaryTree<Character> tree, List<Character> ls, int i) {
		Integer n = ls.size();
		return switch (tree) {
		case BEmpty<Character> t -> false;
		case BLeaf<Character> t -> n - i == 1 && ls.get(i).equals(t.label());
		case BTree<Character> t -> n - i > 0 && ls.get(i).equals(t.label())
				&& (recursivo(t.left(), ls, i+1) || recursivo(t.right(), ls, i+1));
		};
	}

}

