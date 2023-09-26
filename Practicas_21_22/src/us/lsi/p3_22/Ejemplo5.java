package us.lsi.p3_22;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import us.lsi.common.Files2;
import us.lsi.math.Math2;
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
		List<Boolean> r = new ArrayList<>();
		recursivo (t,p,0,r);
		return r;
	}
	
	
	private static <E> void recursivo(Tree<E> tree, Predicate<E> pred, int nivel, List<Boolean> res) {
		if(res.size() <= nivel) res.add(true);
		switch (tree) {
		case TEmpty<E> t -> {break;}
		case TLeaf<E> t -> {
			Boolean r = pred.test(t.label()) && res.get(nivel);
			res.set(nivel, r); 
		}
		case TNary<E> t -> {
			Boolean r = pred.test(t.label()) && res.get(nivel);
			res.set(nivel, r);
			t.children().forEach(tc -> recursivo(tc, pred, nivel + 1, res));
		}
		};
	}
	
	private static final Predicate<Integer> PAR = x-> x%2==0;
	private static final Predicate<Integer> PRIMO = x-> Math2.esPrimo(x);
	
	// Ejemplo5
		public static void testsEjemplo5() {
			
			String file = "ficheros/trees/Ejemplo5DatosEntrada.txt";
			
			List<Tree<Integer>> inputs = Files2
					.streamFromFile(file)
					.map(linea -> Tree.parse(linea,s->Integer.parseInt(s)))
					.toList();
			
			System.out.println("\n.....................................................................................................");
			System.out.println("EJEMPLO 5");
			System.out.println(".....................................................................................................\n");
			
			System.out.println("\nSOLUCION RECURSIVA-PAR:\n");	
			inputs.stream()
			.forEach(x->System.out.println(x+": "+Ejemplo5.solucion_recursiva(x, PAR)));

			System.out.println("\nSOLUCION RECURSIVA-PRIMOS:\n");	
			inputs.stream()
				.forEach(x->System.out.println(x+": "+Ejemplo5.solucion_recursiva(x, PRIMO)));


		}

	public static void main(String[] args) {
		testsEjemplo5();
	}
	
}
