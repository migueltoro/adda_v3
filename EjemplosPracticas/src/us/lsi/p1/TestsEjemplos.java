package us.lsi.p1;

import java.util.List;
import java.util.function.Function;

import us.lsi.common.Pair;
import us.lsi.geometria.Punto2D;
import us.lsi.streams.Stream2;

public class TestsEjemplos {
	public static void main(String[] args) {
//		testsEjemplo1();
		testsEjemplo2();
//		testsEjemplo3();
	}

	public static void testsEjemplo1() {
		Function<String, Punto2D> parsePunto = s -> {
			String[] v = s.split(",");
			return Punto2D.of(Double.valueOf(v[0]), Double.valueOf(v[1]));
		};
		String file = "ficheros/p1/ejemplo1.txt";
		List<Punto2D> ls = Stream2.file(file).map(parsePunto).toList();
		// Explicar diferencia entre Stream2.file(_) y Files2.streamFromFile(_)

		System.out.println("1) Solucion Funcional:\n" + Ejemplo1.solucionFuncional(ls));
		System.out.println("2) Solucion Iterativa:\n" + Ejemplo1.solucionIterativa(ls));
		System.out.println("3) Solucion Rec. Final:\n" + Ejemplo1.solucionRecursivaFinal(ls));
		System.out.println(".............................................................................................................................");
	}
	
	public static void testsEjemplo2() {
		String file = "ficheros/p1/ejemplo2.txt";
		List<Pair<Integer,Integer>> ls = Stream2.file(file)
				.map(s -> Pair.parse(s,",",s1->Integer.parseInt(s1),s2->Integer.parseInt(s2)))
				.toList();
		// Explicar diferencia entre Stream2.file(_) y Files2.streamFromFile(_)

		ls.forEach(p -> {
			System.out.println("1) Solucion R. NO Final: " + Ejemplo2.solucionRecursivaNoFinal(p.first(), p.second()));
			System.out.println("2) Solucion R. Final:    " + Ejemplo2.solucionRecursivaFinal(p.first(), p.second()));
			System.out.println("3) Solucion Iterativa:   " + Ejemplo2.solucionIterativa(p.first(), p.second()));
			System.out.println("4) Solucion Funcional:   " + Ejemplo2.solucionFuncional(p.first(), p.second()));
			System.out.println("____________________________________________________");
		});
		System.out.println(".............................................................................................................................");
	}
	
	public static void testsEjemplo3() {
		String file = "ficheros/p1/ejemplo3.txt";
		List<Pair<Integer,Integer>> ls = Stream2.file(file)
				.map(s -> Pair.parse(s,",",s1->Integer.parseInt(s1),s2->Integer.parseInt(s2)))
				.toList();
		// Explicar diferencia entre Stream2.file(_) y Files2.streamFromFile(_)

		ls.forEach(p -> {
			System.out.println("1) Solucion R. Sin Mem.: " + Ejemplo3.solucionRecursivaSinMemoria(p.first(), p.second()));
			System.out.println("2) Solucion R. Con Mem.: " + Ejemplo3.solucionRecursivaConMemoria(p.first(), p.second()));
			System.out.println("3) Solucion Iterativa:   " + Ejemplo3.solucionIterativa(p.first(), p.second()));
			System.out.println("________________________________");
		});
		System.out.println(".............................................................................................................................");
	}

}

