package us.lsi.p3_21_22;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import us.lsi.common.Files2;
import us.lsi.math.Math2;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;
import us.lsi.tiposrecursivos.Trees.TreeLevel;

/*
  	PI3 - Ejemplo 3
 
	Diseñe un algoritmo que dado un árbol n-ario Tree<E> y un predicado sobre E
	devuelva una lista List<Boolean> de forma que el elemento i-ésimo de la lista será “True”
	si todos los elementos del nivel i cumplen el predicado.

	Resolver de forma recursiva, y en el caso que sea posible, también de forma iterativa 
	haciendo uso de los iteradores sobre árboles que se proporcionan.

	[Recursivo, Iterativo imperativo]	

 */

public class Ejemplo3 {

	private static List<Tree<Integer>> inputs; 	
	private static final Predicate<Integer> PAR = x-> x%2==0;
	private static final Predicate<Integer> PRIMO = x-> Math2.esPrimo(x);

	
	
	private static void cargaDatos () {
		inputs = Files2
				.streamFromFile("ficheros/p3/Ejemplo3_DatosEntrada.txt")
				.map(linea -> Tree.parse(linea,s->Integer.parseInt(s)))
				.toList();
	}
	
	private static<E> List<Boolean> solucion_recursiva (Tree<E> t, Predicate<E> p){
		List<Boolean> r = new ArrayList<>();
		recursivo (t,p,0, r);
		return  r;
	}
		
	private static<E> void recursivo (Tree<E> tree, Predicate<E> p, int nivel, List<Boolean> res) {
		switch(tree) {
		case TEmpty<E> t -> {;}
		case TLeaf<E> t -> actualizaLista(p.test(t.label()), nivel, res);
		case TNary<E> t -> {
			actualizaLista(p.test(t.label()), nivel, res);
			t.children().forEach(x->recursivo(x, p, nivel+1, res));
		}
		}
	}

	private static void actualizaLista(boolean ok, int nivel, List<Boolean> res) {
		if(nivel==res.size()) {
			res.add(ok);
		} else if(res.get(nivel)){
			res.set(nivel, ok);
		}		
	}
	
	private static <E> List<Boolean> iterativo_imperativo(Tree<E> t, Predicate<E> p) {
		List<Boolean> res = new ArrayList<Boolean>();
		Iterator<TreeLevel<E>> it = t.byLevel().iterator();
		while (it.hasNext()) {
			TreeLevel<E> nxt = it.next();
			if (!nxt.tree().isEmpty()) {
				actualizaLista(p.test(nxt.tree().optionalLabel().get()), nxt.level(), res);
			}
		}
		return res;

	}
	
	public static void main(String[] args) {

		cargaDatos();

		System.out.println("PI3 - Ejemplo 3");

		System.out.println("\nSOLUCIÓN RECURSIVA-PAR:\n");
		inputs.stream().forEach(x -> System.out.println(x + ": " + solucion_recursiva(x, PAR)));
		
		System.out.println("\nSOLUCIÓN ITERATIVA-PAR:\n");
		inputs.stream().forEach(x -> System.out.println(x + ": " + iterativo_imperativo(x, PAR)));

		System.out.println("\nSOLUCIÓN RECURSIVA-PRIMOS:\n");
		inputs.stream().forEach(x -> System.out.println(x + ": " + solucion_recursiva(x, PRIMO)));

		System.out.println("\nSOLUCIÓN ITERATIVA-PRIMOS:\n");
		inputs.stream().forEach(x -> System.out.println(x + ": " + iterativo_imperativo(x, PRIMO)));

	}

}

