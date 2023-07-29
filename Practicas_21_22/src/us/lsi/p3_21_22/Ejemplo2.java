package us.lsi.p3_21_22;

import java.util.ArrayList;
import java.util.List;
import us.lsi.common.Files2;
import us.lsi.common.Pair;
import us.lsi.common.Preconditions;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;

/*
	PI3 - Ejemplo 2

	Implemente una función booleana que, dados un árbol binario de caracteres y una lista
	de caracteres, determine si existe un camino en el árbol de la raíz a una hoja que sea igual
	a la lista.

	Resolver de forma recursiva, y en el caso que sea posible, también de forma iterativa 
	haciendo uso de los iteradores sobre árboles que se proporcionan.

	[Recursivo]
 */

public class Ejemplo2 {

	private static List<Pair<BinaryTree<Character>,List<Character>>> inputs; 

	private static void cargaDatos () {

		inputs = Files2.streamFromFile("ficheros/p3/Ejemplo2_DatosEntrada.txt")
				.map(linea -> {					
					String[] aux = linea.split("#");
					Preconditions.checkArgument(aux.length==2);
					return Pair.of(BinaryTree.parse(aux[0], s -> s.charAt(0)), stringListToCharList(aux[1]));
				})
				.toList();
	}

	private static List<Character> stringListToCharList(String s) {
		String letras = s.replace(",", "").replace("[", "").replace("]","");
		List<Character> res = new ArrayList<>(letras.length());
		for(int i=0; i<letras.length(); i++) 
			res.add(letras.charAt(i));
		return res;
	}


	private static Boolean solucion_recursiva (BinaryTree<Character> tree,List<Character> list) {
		return recursivo(tree,list,0);	
	}

	private static Boolean recursivo (BinaryTree<Character> tree, List<Character> list, int i) {
		return switch(tree) {
		case BEmpty<Character> t -> false;
		case BLeaf<Character> t -> i==list.size()-1 && t.label()==list.get(i);
		case BTree<Character> t -> (t.label()==list.get(i)	&& 
					(recursivo(t.left(), list, i+1) || recursivo(t.right(), list, i+1)));
		};
	}
	
	public static void main(String[] args) {

		cargaDatos ();

		System.out.println("PI3 - Ejemplo 2");

		System.out.println("\nSOLUCIÓN RECURSIVA:\n");	
		inputs.stream().forEach(p->
			System.out.println("Árbol: "+p.first()+" Secuencia: "+p.second()+" ["+solucion_recursiva(p.first(),p.second())+"]"));

	}

}

