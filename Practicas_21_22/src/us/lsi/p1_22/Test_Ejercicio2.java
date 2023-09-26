package us.lsi.p1_22;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

public class Test_Ejercicio2 {
	
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
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		Ejercicio2("ficheros/p1/Ejercicio2_DatosEntrada1.txt");
	}

}
