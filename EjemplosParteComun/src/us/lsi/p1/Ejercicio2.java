package us.lsi.p1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ejercicio2 {

	public static String ejercicio2(List<List<String>> listas) {
		String res = "";
		res += String.format("Entrada: %s\n", listas);
		res += String.format("%-30s%s\n", "1. Iterativa (while): ", ejercicio2_iterativo(listas));
		res += String.format("%-30s%s\n", "2. Recursiva: ", ejercicio2_recursiva_final(listas));
		res += String.format("%-30s%s\n", "3. Funcional: ", ejercicio2_funcional(listas));
		return res;
	}

	public static Map<Integer, List<String>> ejercicio2_funcional(List<List<String>> listas) {
		return listas.stream()
				.flatMap(lista -> lista.stream())
				.collect(Collectors.groupingBy(String::length));
	}

	public static Map<Integer, List<String>> ejercicio2_iterativo(List<List<String>> listas) {
		Map<Integer, List<String>> res = new HashMap<Integer, List<String>>();
		Integer i = 0;
		while (i < listas.size()) {
			List<String> lista = listas.get(i);
			Integer j = 0;
			while (j < lista.size()) {
				String cadena = lista.get(j);
				Integer key = cadena.length();
				List<String> group;
				if (res.containsKey(key)) {
					group = res.get(key);
				} else {
					group = new LinkedList<String>();
					res.put(key, group);
				}
				group.add(cadena);
				j++;
			}
			i++;
		}
		return res;
	}

	public static Map<Integer, List<String>> ejercicio2_recursiva_final(List<List<String>> listas) {
		Map<Integer, List<String>> res = new HashMap<Integer, List<String>>();
		return ejercicio2_recursiva_final(listas, res, 0, 0);
	}

	public static Map<Integer, List<String>> ejercicio2_recursiva_final(List<List<String>> listas,
			Map<Integer, List<String>> ac, Integer i, Integer j) {

		if (i < listas.size()) {
			List<String> lista = listas.get(i);
			String cadena = lista.get(j);
			Integer key = cadena.length();
			List<String> group;
			if (ac.containsKey(key)) {
				group = ac.get(key);
			} else {
				group = new LinkedList<String>();
				ac.put(key, group);
			}
			group.add(cadena);
			if (j < lista.size()) j++;
			if (j == lista.size()) {
				i++;
				j = 0;
			}
			ac = ejercicio2_recursiva_final(listas, ac, i, j);
		}
		return ac;
	}

}
