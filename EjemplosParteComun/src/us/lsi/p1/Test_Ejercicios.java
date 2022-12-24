package us.lsi.p1;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


import us.lsi.common.Files2;

public class Test_Ejercicios {

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		Ejercicio1("ficheros/PI1E1_DatosEntrada.txt");
		Ejercicio2("ficheros/PI1E2_DatosEntrada1.txt");
		Ejercicio2("ficheros/PI1E2_DatosEntrada2.txt");
		Ejercicio3("ficheros/PI1E3_DatosEntrada.txt");
		Ejercicio4("ficheros/PI1E4_DatosEntrada.txt");
		Ejercicio5("ficheros/PI1E5_DatosEntrada.txt");
	}

	public static void Ejercicio1(String fichero) {
		System.out.println(String.format("%50s", "").replace(' ', '#'));
		System.out.println(String.format("#%24s%-24s#", "Ejercicio 1",""));
		System.out.println(String.format("#%38s%-10s#", fichero,""));
		System.out.println(String.format("%50s\n", "").replace(' ', '#'));

		Function<String, List<String>> ft = cadena -> Arrays.stream(cadena.split(",")).collect(Collectors.toList());
		List<List<String>> input = Files2.streamFromFile(fichero).filter(x -> !x.startsWith("//")).map(ft)
				.collect(Collectors.toList());

		Predicate<String> vocal_abierta = cadena -> cadena.contains("a") || cadena.contains("e")
				|| cadena.contains("o");
		Function<String, Integer> getLength = cadena -> cadena.length();
		Predicate<Integer> esPar = entero -> entero % 2 == 0;

		// Predicate<String> vocal_cerrada = cadena -> cadena.contains("i") ||
		// cadena.contains("u");

		// Longitud par y contener vocal abierta
		input.stream().forEach(linea -> {
			System.out.println(String.format("%s", Ejercicio1.ejercicio1(linea, vocal_abierta, esPar, getLength)));
		});

		System.out.println("##########################################\n");

	}

	public static void Ejercicio2(String fichero) {
		System.out.println(String.format("%50s", "").replace(' ', '#'));
		System.out.println(String.format("#%24s%-24s#", "Ejercicio 2",""));
		System.out.println(String.format("#%38s%-10s#", fichero,""));
		System.out.println(String.format("%50s\n", "").replace(' ', '#'));
		Function<String, List<String>> ft = cadena -> Arrays.stream(cadena.split(",")).collect(Collectors.toList());
		List<List<String>> input = Files2.streamFromFile(fichero).filter(x -> !x.startsWith("//")).map(ft)
				.collect(Collectors.toList());

		System.out.println(Ejercicio2.ejercicio2(input));
		System.out.println(String.format("%50s\n", "").replace(' ', '#'));

	}

	public static void Ejercicio3(String fichero) {
		System.out.println(String.format("%50s", "").replace(' ', '#'));
		System.out.println(String.format("#%24s%-24s#", "Ejercicio 3",""));
		System.out.println(String.format("#%38s%-10s#", fichero,""));
		System.out.println(String.format("%50s\n", "").replace(' ', '#'));
		Function<String, Integer> stringToInt = input_string -> Integer.parseInt(input_string);
		Function<String, List<Integer>> ft = cadena -> Arrays.stream(cadena.split(","))
				.map(e -> stringToInt.apply(e.trim())).collect(Collectors.toList());
		List<List<Integer>> input = Files2.streamFromFile(fichero).filter(x -> !x.startsWith("//")).map(ft)
				.collect(Collectors.toList());
		input.stream().forEach(
				linea -> System.out.println(String.format("%s", Ejercicio3.ejercicio3(linea.get(0), linea.get(1)))));
		System.out.println(String.format("%50s\n", "").replace(' ', '#'));
	}

	public static void Ejercicio4(String fichero) {
		System.out.println(String.format("%50s", "").replace(' ', '#'));
		System.out.println(String.format("#%24s%-24s#", "Ejercicio 4",""));
		System.out.println(String.format("#%38s%-10s#", fichero,""));
		System.out.println(String.format("%50s\n", "").replace(' ', '#'));
		List<String> file = Files2.linesFromFile(fichero);
		file.stream().filter(x -> !x.startsWith("//")).map(linea -> linea.split(",")).forEach(linea -> {
			String print = String.format("%s",
					Ejercicio4.ejercicio4(Double.parseDouble(linea[0]), Double.parseDouble(linea[1])));
			System.out.println(print);
		});
		System.out.println(String.format("%50s\n", "").replace(' ', '#'));
	}
	
	public static void Ejercicio5(String fichero) {
		System.out.println("##########################################");
		System.out.println("#\tEjercicio 5\t\t\t#");
		System.out.println(String.format("#\t%s\t#", fichero));
		System.out.println("##########################################\n");
		Function<String, Integer> stringToInt = input_string -> Integer.parseInt(input_string);
		Function<String, List<Integer>> ft = cadena -> Arrays.stream(cadena.split(","))
				.map(e -> stringToInt.apply(e.trim())).collect(Collectors.toList());
		List<List<Integer>> input = Files2.streamFromFile(fichero).filter(x -> !x.startsWith("//")).map(ft)
				.collect(Collectors.toList());
		input.stream().forEach(linea -> System.out
				.println(String.format("%s", Ejercicio5.ejercicio5(linea.get(0), linea.get(1), linea.get(2)))));
		System.out.println("##########################################\n");

	}

}
