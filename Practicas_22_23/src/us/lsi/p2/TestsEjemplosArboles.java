package us.lsi.p2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import us.lsi.common.Files2;
import us.lsi.common.Pair;
import us.lsi.common.Preconditions;
import us.lsi.math.Math2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class TestsEjemplosArboles {
	private static final Predicate<Integer> PAR = x-> x%2==0;
	private static final Predicate<Integer> PRIMO = x-> Math2.esPrimo(x);

	public static void main(String[] args) {
		testsEjemplo4();
		testsEjemplo5();
	}
	
	
	// Ejemplo4
	private static List<Character> stringListToCharList(String s) {
		
		String letras = s.replace(",", "").replace("[", "").replace("]","");
		List<Character> res = new ArrayList<>(letras.length());
		for(int i=0; i<letras.length(); i++) 
			res.add(letras.charAt(i));
		return res;
	}
	
	public static void testsEjemplo4() {
		
		String file = "ficheros/p2/ejemplo4.txt";
		
		List<Pair<BinaryTree<Character>,List<Character>>> inputs =
			Files2.streamFromFile(file).map(linea -> {
				String[] aux = linea.split("#");
				Preconditions.checkArgument(aux.length==2);
				return Pair.of(BinaryTree.parse(aux[0], s -> s.charAt(0)), stringListToCharList(aux[1]));
				}
			).toList();
		
		
		System.out.println("\n.....................................................................................................");
		System.out.println("EJEMPLO 4");
		System.out.println(".....................................................................................................\n");

		inputs.stream().forEach(par -> {
			
			BinaryTree<Character> tree = par.first();
			List<Character> chars = par.second();
			
			System.out.println("Arbol: "+tree+
					"\tSecuencia: "+chars+"\t["+
					Ejemplo4.solucion_recursiva(tree,chars)+"]");
			
		}
		);
	}
	
	
	// Ejemplo5
	public static void testsEjemplo5() {
		
		String file = "ficheros/p2/ejemplo5.txt";
		
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
}

