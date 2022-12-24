package us.lsi.p1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.geometria.Punto2D;

public class Ejemplo2 {

	public static String ejemplo2(List<Punto2D> l) {
		String res = "";
		res += String.format("Entrada: %s\n", l);
		res += String.format("%-30s%s\n", "1. Iterativa (while): ", ejemplo2_iterativo(l));
		res += String.format("%-30s%s\n", "2. Recursivo: ", ejemplo2_recursivo(l));
		res += String.format("%-30s%s\n", "3. Funcional: ", ejemplo2_funcional(l));
		return res;
	}

	public static Map<Punto2D.Cuadrante, Double> ejemplo2_funcional(List<Punto2D> ls) {
		return ls.stream()
				.collect(Collectors.groupingBy(Punto2D::getCuadrante,
						Collectors.reducing(0., x -> x.x(), (x, y) -> x + y)));
	}

	public static Map<Punto2D.Cuadrante, Double> ejemplo2_iterativo(List<Punto2D> ls) {
		Integer e = 0;
		Map<Punto2D.Cuadrante, Double> b = new HashMap<Punto2D.Cuadrante, Double>();
		while (e < ls.size()) {
			Punto2D p = ls.get(e);
			Punto2D.Cuadrante key = p.getCuadrante();
			if(b.containsKey(key)) b.put(key, b.get(key)+p.x());
			else b.put(key,p.x());
			e++;
		}
		return b;

	}

	public static Map<Punto2D.Cuadrante, Double> ejemplo2_recursivo(List<Punto2D> ls) {
		Map<Punto2D.Cuadrante, Double> res = new HashMap<Punto2D.Cuadrante, Double>();
		return ejemplo2_recursivo(ls, res, 0);

	}

	public static Map<Punto2D.Cuadrante, Double> ejemplo2_recursivo(List<Punto2D> ls, Map<Punto2D.Cuadrante, Double> b,
			Integer e) {
		if (e < ls.size()) {
			Punto2D p = ls.get(e);
			Punto2D.Cuadrante key = p.getCuadrante();
			if(b.containsKey(key)) b.put(key, b.get(key)+p.x());
			else b.put(key,p.x());
			b = ejemplo2_recursivo(ls, b, e + 1);
		}
		return b;
	}

}
