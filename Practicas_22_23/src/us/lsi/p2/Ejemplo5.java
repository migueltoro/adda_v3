package us.lsi.p2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejemplo5 {
	/*
	PI3 - Ejemplo 5

	Diseñe un algoritmo que dado un árbol n-ario Tree<E> y un predicado sobre E
	devuelva una lista List<Boolean> de forma que el elemento i-ésimo de la lista será “True”
	si todos los elementos del nivel i cumplen el predicado.

	Resolver de forma recursiva
	*/
	

	public static <E> List<Boolean> solucion_recursiva (Tree<E> t, Predicate<E> p){
		return recursivo (t,p,0,new ArrayList<>());
	}
	
	
	private static <E> List<Boolean> recursivo(Tree<E> tree, Predicate<E> pred, int nivel, List<Boolean> res) {
		if(res.size() <= nivel) res.add(true);
		return switch (tree) {
		case TEmpty<E> t -> res;
		case TLeaf<E> t -> {
			Boolean r = pred.test(t.label()) && res.get(nivel);
			res.set(nivel, r); 
			yield res;
		}
		case TNary<E> t -> {
			Boolean r = pred.test(t.label()) && res.get(nivel);
			res.set(nivel, r);
			t.children().forEach(tc -> recursivo(tc, pred, nivel + 1, res));
			yield res;
		}
		default -> res;
		};
	}
	
}

