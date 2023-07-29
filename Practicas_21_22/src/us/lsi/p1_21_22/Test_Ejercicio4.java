package us.lsi.p1_21_22;

import java.util.List;
import java.util.Locale;

import us.lsi.common.Files2;

public class Test_Ejercicio4 {
	
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

	public static void main(String[] args) {
			Locale.setDefault(Locale.of("en", "US"));
			Ejercicio4("ficheros/p1/Ejercicio4_DatosEntrada.txt");
	}

}
