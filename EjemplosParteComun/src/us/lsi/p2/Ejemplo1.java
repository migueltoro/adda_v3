package us.lsi.p2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Files2;
import us.lsi.common.Matrix;
import us.lsi.common.View4;

public class Ejemplo1 { 
	
	public static boolean cumpleEnunciado(Matrix<Integer> m) {
		if(m.area()==1) {
			return true;
		} else {
			return m.corners().get(0) < m.corners().get(3) 
			&& IntStream.range(0, 4).allMatch(i->cumpleEnunciado(m.view(i)));
		}
	}
	
	public static boolean cumpleEnunciado2(Matrix<Integer> m) {
		Boolean r = true;
		if (m.area() > 1) {
			r = m.corners().get(0) < m.corners().get(3);
			View4<Matrix<Integer>> w = m.views4();
			r = r && cumpleEnunciado2(w.a()) 
				  && cumpleEnunciado2(w.b()) 
				  && cumpleEnunciado2(w.c())
				  && cumpleEnunciado2(w.d());
		}
		return r;
	}

	// ==================================== TESTS ====================================
	public static void main(String[] args) {
		test("ficheros/Ejemplo1DatosEntrada1.txt");
		test("ficheros/Ejemplo1DatosEntrada2.txt");
	}
	
	private static void test(String fichero) {
		Matrix<Integer> matriz = Matrix.of(creaArray(fichero));
		matriz.print("Matriz de entrada");
		System.out.println("Es matriz valida? "+cumpleEnunciado(matriz));	
		System.out.println("Es matriz valida? "+cumpleEnunciado2(matriz));
	}
	
	private static Integer[][] creaArray(String fichero) {
		List<String> ls = Files2.linesFromFile(fichero);
		Integer[][] res = new Integer[ls.size()][];
		for(int i=0; i<ls.size(); i++) {
			String[] tokens = ls.get(i).split(" ");
			res[i] = new Integer[tokens.length];
			List<Integer> fila = 
			Arrays.stream(tokens).map(s->Integer.parseInt(s)).collect(Collectors.toList());
			fila.toArray(res[i]);
		}
		return res;
	}
	
}
