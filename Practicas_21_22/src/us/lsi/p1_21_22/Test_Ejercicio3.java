package us.lsi.p1_21_22;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

public class Test_Ejercicio3 {
	
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

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		Ejercicio3("ficheros/p1/Ejercicio3_DatosEntrada.txt");
	}

}
