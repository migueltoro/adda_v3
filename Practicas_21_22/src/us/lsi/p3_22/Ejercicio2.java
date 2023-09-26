package us.lsi.p3_22;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import us.lsi.common.Files2;
import us.lsi.common.Pair;
import us.lsi.common.Preconditions;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;

/*
	PI3 - Ejercicio 2
	
	Dado un árbol binario ordenado de enteros, diseñe un algoritmo que devuelva un
	conjunto con todos los elementos mayores o iguales que uno dado.

	Proporcione una solución recursiva, y en los casos que sea posible, también 
	una solución iterativa haciendo uso de los iteradores sobre árboles que se
	proporcionan.

	[Recursivo]
*/

public class Ejercicio2 {

	private static List<Pair<BinaryTree<Integer>,Integer>> inputs; 

	private static void cargaDatos () {
		inputs = Files2.streamFromFile("ficheros/p3/PI3E2_DatosEntrada.txt")
				.map(linea -> {
					String aux[]= linea.split("#");
					BinaryTree<Integer> t = BinaryTree.parse(aux[0],s->Integer.parseInt(s));
					Preconditions.checkArgument(t.isOrdered(Comparator.naturalOrder()), t+"  No es un árbol binario ordenado");
					Integer o = Integer.parseInt(aux[1]);					
					return Pair.of(t,o);					
				})
				.toList();
	}

	
	private static Set<Integer> solucion_recursiva (BinaryTree<Integer>tree, Integer objective) {	
		Set<Integer> r = new HashSet<>();
		recursivo (tree,objective,r);	
		return r;
	}
	

	private static void recursivo(BinaryTree<Integer>tree, Integer objetive, Set<Integer> res) {
		switch(tree) {
		case BEmpty<Integer> t -> {;}
		case BLeaf<Integer> t when t.label()>=objetive -> res.add(t.label());
		case BLeaf<Integer> t  -> {;}
		case BTree<Integer> t when t.label()>=objetive -> {	
				res.add(t.label());
				recursivo(t.left(),objetive,res);
				recursivo(t.right(),objetive,res);
		}		
		case BTree<Integer> t when t.label()<objetive -> recursivo(t.right(),objetive,res);
		}
	}	
	
	public static void main(String[] args) {

		cargaDatos ();
		
		System.out.println("PI3 - Ejercicio 2");
		
		System.out.println("\nSOLUCIÓN RECURSIVA:\n");	
		inputs
		.stream()
		.forEach(x->System.out.println(x+": "+solucion_recursiva(x.first(),x.second())));

	}

}

