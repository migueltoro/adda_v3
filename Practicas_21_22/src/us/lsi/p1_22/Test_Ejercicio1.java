package us.lsi.p1_22;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

public class Test_Ejercicio1 {
	
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

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		Ejercicio1("ficheros/p1/Ejercicio1_DatosEntrada1.txt");
	}

}
