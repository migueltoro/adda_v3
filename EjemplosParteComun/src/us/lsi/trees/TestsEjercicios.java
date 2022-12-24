package us.lsi.trees;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.common.Pair;
import us.lsi.common.Preconditions;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class TestsEjercicios {

	public static void main(String[] args) {
		testsEjercicio3Binario();
		testsEjercicio3Nario();
		testsEjercicio4Binario();
		testsEjercicio4Nario();

	}

	public static void testsEjercicio3Binario() {
		String file = "ficheros/Ejercicio3DatosEntradaBinario.txt";
		List<Pair<BinaryTree<Character>, Character>> inputsBinary = 
				Files2.streamFromFile(file).map(linea -> {
					String[] aux = linea.split("#");
					Preconditions.checkArgument(aux.length==2);
					return Pair.of(BinaryTree.parse(aux[0], s -> s.charAt(0)), aux[1].charAt(0));
					}
				).collect(Collectors.toList());
		
		System.out.println(".....................................................................................................");
		System.out.println("EJERCICIO 3 - arboles binarios");
		System.out.println(".....................................................................................................\n");

		inputsBinary.stream()
		.forEach(par->{
			BinaryTree<Character> tree = par.first();
			Character ch = par.second();
			
			System.out.println("Arbol: "+tree+
					"\tCaracter: "+ch+"\t["+
					Ejercicio3.solucion_recursiva(tree,ch)+"]");
		});			
		

	}
	
	public static void testsEjercicio3Nario() {
		
		String file2 = "ficheros/Ejercicio3DatosEntradaNario.txt";
		List<Pair<Tree<Character>, Character>> inputsNary = 
				Files2.streamFromFile(file2).map(linea -> {
					String[] aux = linea.split("#");
					Preconditions.checkArgument(aux.length==2);
					return Pair.of(Tree.parse(aux[0], s -> s.charAt(0)), aux[1].charAt(0));
					}
				).toList();
		
		System.out.println(".....................................................................................................");
		System.out.println("EJERCICIO 3 - arboles n-arios");
		System.out.println(".....................................................................................................\n");
		
		inputsNary.stream()
			.forEach(par->{
				Tree<Character> tree = par.first();
				Character ch = par.second();
				
				System.out.println("Arbol: "+tree+
						"\tCaracter: "+ch+"\t["+
						Ejercicio3.solucion_recursiva(tree,ch)+"]");
			});	
		
	}
	
	
	public static void testsEjercicio4Binario() {
		
		String file = "ficheros/Ejercicio4DatosEntradaBinario.txt";
		List<BinaryTree<String>> inputsBinary = 
				Files2.streamFromFile(file)
				.map(linea -> BinaryTree.parse(linea))
				.toList();
		
		System.out.println(".....................................................................................................");
		System.out.println("EJERCICIO 4 - arboles n-arios");
		System.out.println(".....................................................................................................\n");
		inputsBinary.stream()
			.forEach(x->System.out.println(x+": "+Ejercicio4.solucion_recursiva(x)));			

	}
	
	public static void testsEjercicio4Nario() {
		
		String file2 = "ficheros/Ejercicio4DatosEntradaNario.txt";
		List<Tree<String>> inputsNary = 
				Files2.streamFromFile(file2)
				.map(linea -> Tree.parse(linea))
				.toList();
		
		System.out.println(".....................................................................................................");
		System.out.println("EJERCICIO 4 - arboles n-arios");
		System.out.println(".....................................................................................................\n");
		
		
		inputsNary.stream()
		.forEach(x->System.out.println(x+": "+Ejercicio4.solucion_recursiva(x)));	

	}
}
