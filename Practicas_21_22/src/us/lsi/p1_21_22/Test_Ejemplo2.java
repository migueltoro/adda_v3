package us.lsi.p1_21_22;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.common.Preconditions;
import us.lsi.geometria.Punto2D;

public class Test_Ejemplo2 {
	
	public static void Ejemplo2(String fichero) {
		System.out.println(String.format("%50s", "").replace(' ', '#'));
		System.out.println(String.format("#%24s%-24s#", "Ejemplo 2",""));
		System.out.println(String.format("#%38s%-10s#", fichero,""));
		System.out.println(String.format("%50s\n", "").replace(' ', '#'));

		List<Punto2D> lista = Files2.streamFromFile(fichero).<Punto2D>map(s -> {
			String[] ps = s.split(",");
			Preconditions.checkArgument(ps.length == 2);
			return Punto2D.of(Double.parseDouble(ps[0]), Double.parseDouble(ps[1]));
		}).collect(Collectors.<Punto2D>toList());

		System.out.println(String.format("%s", Ejemplo2.ejemplo2(lista)));
		System.out.println(String.format("%50s\n", "").replace(' ', '#'));
	}

	public static void main(String[] args) {
		Ejemplo2("ficheros/p1/Ejemplo2_DatosEntrada.txt");
	}

}
