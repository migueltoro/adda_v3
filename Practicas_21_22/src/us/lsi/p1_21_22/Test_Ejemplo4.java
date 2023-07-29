package us.lsi.p1_21_22;

import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.common.Pair;
import us.lsi.common.Preconditions;

public class Test_Ejemplo4 {
	
	public static void Ejemplo4(String fichero) {
		System.out.println(String.format("%50s", "").replace(' ', '#'));
		System.out.println(String.format("#%24s%-24s#", "Ejemplo 4",""));
		System.out.println(String.format("#%38s%-10s#", fichero,""));
		System.out.println(String.format("%50s\n", "").replace(' ', '#'));

		Files2.streamFromFile(fichero).<Pair<Long, Integer>>map(s -> {
			String[] ps = s.split(",");
			Preconditions.checkArgument(ps.length == 2);
			return Pair.of(Long.parseLong(ps[0]), Integer.parseInt(ps[1]));
		}).collect(Collectors.<Pair<Long, Integer>>toList())
				.forEach(x -> System.out.println(String.format("%s", Ejemplo4.ejemplo4(x.first(), x.second()))));

		System.out.println(String.format("%50s\n", "").replace(' ', '#'));
	}

	public static void main(String[] args) {
		Ejemplo4("ficheros/p1/Ejemplo4_DatosEntrada.txt");
	}

}
