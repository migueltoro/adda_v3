package us.lsi.p3_22;

import java.util.ArrayList;
import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.Pair;
import us.lsi.common.Preconditions;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;

public class Ejemplo6 {

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
		case BEmpty<Character> t -> n - i == 0;
		case BLeaf<Character> t -> n - i == 1 && ls.get(i).equals(t.label());
		case BTree<Character> t -> n - i > 0 && ls.get(i).equals(t.label())
				&& (recursivo(t.left(), ls, i+1) || recursivo(t.right(), ls, i+1));
		};
	}
	

	// Ejemplo4
	private static List<Character> stringListToCharList(String s) {

		String letras = s.replace(",", "").replace("[", "").replace("]", "");
		List<Character> res = new ArrayList<>(letras.length());
		for (int i = 0; i < letras.length(); i++)
			res.add(letras.charAt(i));
		return res;
	}

	public static void testsEjemplo4() {

		String file = "ficheros/Ejemplo4DatosEntrada.txt";

		List<Pair<BinaryTree<Character>, List<Character>>> inputs = Files2.streamFromFile(file).map(linea -> {
			String[] aux = linea.split("#");
			Preconditions.checkArgument(aux.length == 2);
			return Pair.of(BinaryTree.parse(aux[0], s -> s.charAt(0)), stringListToCharList(aux[1]));
		}).toList();

		System.out.println(
				"\n.....................................................................................................");
		System.out.println("EJEMPLO 4");
		System.out.println(
				".....................................................................................................\n");

		inputs.stream().forEach(par -> {

			BinaryTree<Character> tree = par.first();
			List<Character> chars = par.second();

			System.out.println("Arbol: " + tree + "\tSecuencia: " + chars + "\t["
					+ Ejemplo6.solucion_recursiva(tree, chars) + "]");

		});
	}

	public static void main(String[] args) {
		testsEjemplo4();
	}
}
