package us.lsi.p1;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.common.Pair;
import us.lsi.common.Preconditions;
import us.lsi.geometria.Punto2D;

public class Test_Ejemplos {

	public static void main(String[] args) {

		Ejemplo1("ficheros/Ejemplo1_DatosEntrada.txt");
		Ejemplo2("ficheros/Ejemplo2_DatosEntrada.txt");
		Ejemplo3("ficheros/Ejemplo3_DatosEntrada.txt");
		Ejemplo4("ficheros/Ejemplo4_DatosEntrada.txt");

	}

	public static void Ejemplo1(String fichero) {
		System.out.println(String.format("%50s", "").replace(' ', '#'));
		System.out.println(String.format("#%24s%-24s#", "Ejemplo 1", ""));
		System.out.println(String.format("#%38s%-10s#", fichero, ""));
		System.out.println(String.format("%50s\n", "").replace(' ', '#'));

		Function<String, List<String>> ft = cadena -> Arrays.stream(cadena.split(",")).collect(Collectors.toList());
		List<List<String>> input = Files2.streamFromFile(fichero).filter(x -> !x.startsWith("//")).map(ft)
				.collect(Collectors.toList());
		input.stream().forEach(
				linea -> System.out.println(String.format("%s", Ejemplo1.ejemplo1(Integer.parseInt(linea.get(0)),
						Integer.parseInt(linea.get(1)), x -> x + 1, linea.get(4), linea.get(2), linea.get(3)))));

		System.out.println(String.format("%50s\n", "").replace(' ', '#'));

	}

	public static void Ejemplo2(String fichero) {
		System.out.println(String.format("%50s", "").replace(' ', '#'));
		System.out.println(String.format("#%24s%-24s#", "Ejemplo 2",""));
		System.out.println(String.format("#%38s%-10s#", fichero,""));
		System.out.println(String.format("%50s\n", "").replace(' ', '#'));

		List<Punto2D> lista = Files2.streamFromFile("ficheros/Ejemplo2_DatosEntrada.txt").<Punto2D>map(s -> {
			String[] ps = s.split(",");
			Preconditions.checkArgument(ps.length == 2);
			return Punto2D.of(Double.parseDouble(ps[0]), Double.parseDouble(ps[1]));
		}).collect(Collectors.<Punto2D>toList());

		System.out.println(String.format("%s", Ejemplo2.ejemplo2(lista)));
		System.out.println(String.format("%50s\n", "").replace(' ', '#'));
	}

	public static void Ejemplo3(String fichero) {
		System.out.println(String.format("%50s", "").replace(' ', '#'));
		System.out.println(String.format("#%24s%-24s#", "Ejemplo 3",""));
		System.out.println(String.format("#%38s%-10s#", fichero,""));
		System.out.println(String.format("%50s\n", "").replace(' ', '#'));

		Function<String, Pair<String, String>> f = s -> Pair.of(s.split("#")[0], s.split("#")[1]);

		List<Pair<String, String>> input = Files2.streamFromFile(fichero).filter(x -> !x.startsWith("//")).map(f)
				.collect(Collectors.toList());

		input.stream().forEach(p -> System.out.println(Ejemplo3.ejemplo3(p.first(), p.second())));

		System.out.println(String.format("%50s\n", "").replace(' ', '#'));
	}

	public static void Ejemplo4(String fichero) {
		System.out.println(String.format("%50s", "").replace(' ', '#'));
		System.out.println(String.format("#%24s%-24s#", "Ejemplo 4",""));
		System.out.println(String.format("#%38s%-10s#", fichero,""));
		System.out.println(String.format("%50s\n", "").replace(' ', '#'));

		Files2.streamFromFile("ficheros/Ejemplo4_DatosEntrada.txt").<Pair<Long, Integer>>map(s -> {
			String[] ps = s.split(",");
			Preconditions.checkArgument(ps.length == 2);
			return Pair.of(Long.parseLong(ps[0]), Integer.parseInt(ps[1]));
		}).collect(Collectors.<Pair<Long, Integer>>toList())
				.forEach(x -> System.out.println(String.format("%s", Ejemplo4.ejemplo4(x.first(), x.second()))));

		System.out.println(String.format("%50s\n", "").replace(' ', '#'));
	}

}
