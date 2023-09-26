package us.lsi.p1_22;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.common.Pair;

public class Test_Ejemplo3 {
	
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


	public static void main(String[] args) {
		Ejemplo3("ficheros/p1/Ejemplo3_DatosEntrada.txt");
	}

}
