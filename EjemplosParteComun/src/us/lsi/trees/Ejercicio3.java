package us.lsi.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.List2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejercicio3 {
	/*
	PI3 - Ejercicio 3
	
	Dados un árbol binario de caracteres y un carácter, diseñe un algoritmo que devuelva
	una lista con todas las cadenas que se forman desde la raíz a una hoja no vacía,
	excluyendo aquellas cadenas que contengan dicho carácter. Proporcione una solución
	también para árboles n-arios.
	
	Resolver de forma recursiva
	*/
	
	
	public static String tostring(List<Character> ac) {
		return ac
		.stream()
		.map(x -> x.toString())
		.collect(Collectors.joining(""));
	}
	
	// Árboles binarios
		public static List<String> solucion_recursiva(BinaryTree<Character> tree, Character ch) {
			return recursivo(tree, ch, new ArrayList<>());
		}
		
		
		private static List<String> recursivo(BinaryTree<Character> tree, Character ch, 
				List<Character> ac){
			return switch(tree) {
			case BEmpty<Character> t -> List2.of(tostring(ac));
			case BLeaf<Character> t when !t.label().equals(ch) ->  List2.of(tostring(ac)+t.label());
			case BLeaf<Character> t when t.label().equals(ch) ->  List2.of();	
			case BTree<Character> t when t.label().equals(ch) ->  List2.of();	
			case BTree<Character> t when !t.label().equals(ch) -> {
				ac.add(t.label());
				List<String> lf = recursivo(t.left(),ch,ac);
				List<String> rf = recursivo(t.right(),ch,ac);
				lf.addAll(rf);
				yield lf.stream().filter(s->s.length()>0).collect(Collectors.toList());
			}
			};									
		}

	// Árboles n-arios	
	public static List<String> solucion_recursiva(Tree<Character> tree, Character ch) {
		return recursivo(tree, ch, new ArrayList<>());
	}
	
	public static List<String> recursivo(Tree<Character> tree, Character ch, List<Character> ac){
		return switch(tree) {
		case TEmpty<Character> t -> List2.of(tostring(ac));
		case TLeaf<Character> t when !t.label().equals(ch) ->  List2.of(tostring(ac)+t.label());
		case TLeaf<Character> t when t.label().equals(ch) ->  List2.of();	
		case TNary<Character> t when t.label().equals(ch) ->  List2.of();	
		case TNary<Character> t when !t.label().equals(ch) -> {
			ac.add(t.label());
			yield t.elements().stream()
				.flatMap(x->recursivo(x,ch,ac).stream())
				.filter(s->s.length()>0)
				.collect(Collectors.toList());
		}
		};									
	}
}
