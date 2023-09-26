package us.lsi.p1_22;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

public class Test_Ejercicio5 {
	
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

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		Ejercicio5("ficheros/p1/Ejercicio5_DatosEntrada.txt");
	}

}
