package us.lsi.p2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Matrix;
import us.lsi.common.Set2;

public class Ejercicio1 {
	
	public static Set<String> creaSet(Matrix<Character> m) {
		//Preconditions.checkArgument(es_potencia(2, m), "La matriz debe ser NxN, siendo N=2^m");
		List<String> ls= creaSet(m, List2.empty());
		Set<String> res = Set2.newTreeSet(Comparator.comparing(s->ls.indexOf(s)));
		res.addAll(ls);
		return res;
	}
	
	private static List<String> creaSet(Matrix<Character> m, List<String> ac) {
		if(m.area()>=4) {
			String s = "" + m.corners().get(0) + m.corners().get(1) + 
			m.corners().get(2) + m.corners().get(3);
			ac.add(s);
			IntStream.range(0, 4).forEach(i->creaSet(m.view(i), ac));
		}
		return ac;
	}
	
	// ==================================== TESTS ====================================	
	public static void main(String[] args) {
		test("ficheros/Ejercicio1DatosEntrada1.txt", true);
		test("ficheros/Ejercicio1DatosEntrada2.txt", true);
	}
	
	private static void test(String fichero, boolean mostrar) {
		Matrix<Character> matriz = Matrix.of(creaArray(fichero));
		if(mostrar)
			matriz.print("\n\nMatriz de entrada");

		System.out.println("\n\nConjunto de cadenas obtenido:");
		creaSet(matriz).forEach(s->System.out.print(s+" "));
	}
	
	private static Character[][] creaArray(String fichero) {
		List<String> ls = Files2.linesFromFile(fichero);
		Character[][] res = new Character[ls.size()][];
		for(int i=0; i<ls.size(); i++) {
			String[] tokens = ls.get(i).split(" ");
			res[i] = new Character[tokens.length];
			List<Character> fila = 
			Arrays.stream(tokens).map(s->s.charAt(0)).collect(Collectors.toList());
			fila.toArray(res[i]);
		}
		return res;
	}
	
}
