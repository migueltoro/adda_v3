package us.lsi.p3_21_22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;
import us.lsi.tiposrecursivos.Trees.TreeLevel;


/*
	PI3 - Ejemplo 4

	Diseñe un algoritmo que dado un árbol n-ario de tipo genérico E devuelva un
	Map<Integer,List<E>> que incluya una entrada en el map por cada nivel del árbol. 
	Dicha entrada debe tener como clave el nivel y como información asociada una lista con las
	etiquetas de los nodos que se encuentran en dicho nivel y que tienen un número de hijos mayor que 0 y par.

	Resolver de forma recursiva, y en el caso que sea posible, también de forma iterativa 
	haciendo uso de los iteradores sobre árboles que se proporcionan.

	[Recursivo, Iterativo imperativo]	
 */

public class Ejemplo4 {

	private static List<Tree<Integer>> inputs; 	

	private static void cargaDatos () {

		Ejemplo4.inputs = Files2.streamFromFile("ficheros/p3/Ejemplo4_DatosEntrada.txt")
				.map(linea -> Tree.parse(linea,s->Integer.parseInt(s)))
				.toList();
	}

	private static<E> Map<Integer, List<E>> solucion_recursiva (Tree<E> t){
		Map<Integer, List<E>> r = new HashMap<>();
		recursivo(t,0, r);
		return  r;
	}

	private static <E> void recursivo(Tree<E> tree, int nivel, Map<Integer, List<E>> res) {
		switch (tree) {
		case TEmpty<E> t -> {;}
		case TLeaf<E> t -> {;}
		case TNary<E> t when t.elements().size() % 2 == 0 -> {
			actualizaMap(t.label(), nivel, res);
			t.elements().forEach(tc -> recursivo(tc, nivel + 1, res));
		}
		case TNary<E> t -> {
			t.elements().forEach(tc -> recursivo(tc, nivel + 1, res));
		}
		}
	}

	private static<E> void actualizaMap(E e, int nivel, Map<Integer, List<E>> res) {
		List<E> ls = res.getOrDefault(nivel, new ArrayList<>());
		ls.add(e);
		res.put(nivel, ls);
	}
	

	
	private static<E> Map<Integer, List<E>> iterativo_imperativo (Tree<E> tree) {
		Map<Integer, List<E>> res = new HashMap<Integer, List<E>>();
		Iterator<TreeLevel<E>> it = tree.byLevel().iterator();
		while (it.hasNext()) {
			TreeLevel<E> nxt = it.next();
			switch (nxt.tree()) {
			case TEmpty<E> t -> {;}
			case TLeaf<E> t -> {;}
			case TNary<E> t when t.elements().size() % 2 == 0 -> actualizaMap(t.label(), nxt.level(), res);
			case TNary<E> t -> {;}
			}
		}
		return res;
	}
	
	public static void main(String[] args) {

		cargaDatos ();
		
		System.out.println("PI3 - Ejemplo 4");

		System.out.println("\nSOLUCIÓN RECURSIVA:\n");

		inputs.stream()
			.forEach(x->System.out.println(x+": "+solucion_recursiva(x)));	

		System.out.println("\nSOLUCIÓN ITERATIVA:\n");	
		inputs.stream()
			.forEach(x->System.out.println(x+": "+iterativo_imperativo(x)));		

	}

}

