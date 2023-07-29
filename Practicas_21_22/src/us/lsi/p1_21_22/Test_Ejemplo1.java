package us.lsi.p1_21_22;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

public class Test_Ejemplo1 {
	
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

	public static void main(String[] args) {
		Ejemplo1("ficheros/p1/Ejemplo1_DatosEntrada.txt");
	}

}
