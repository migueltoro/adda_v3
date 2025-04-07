package us.lsi.alg.festival;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DatosFestival2 {
	
	public static record Coste(Integer i, Integer j, Integer c) {
		public static Coste of(Integer i, Integer j, Integer c) {
            return new Coste(i, j, c);
        }
	}
	
	public static List<Coste> costes = null;
	public static Map<Integer,Integer> costesMin = null;
	public static Integer n;
	public static Integer m;
	public static List<Integer> aforoMaximoAreas;
	public static List<Integer> cuotasMinimas;
	
	public static void iniDatos() {
		n = DatosFestival.getNumTiposEntrada();
		m = DatosFestival.getNumAreas();
		
		costes = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				costes.add(Coste.of(i, j, DatosFestival.getCosteAsignacion(i, j)));
			}
		}
		Collections.sort(costes, Comparator.comparing(Coste::c));
		aforoMaximoAreas = DatosFestival.getAforoMaximoAreas();
		cuotasMinimas = DatosFestival.getCuotasMinimas();
		costesMin = costes.stream().collect(
				Collectors.groupingBy(x -> x.i(), 
						Collectors.collectingAndThen(
								Collectors.mapping(x -> x.c(), 
										Collectors.minBy(Comparator.naturalOrder())), x -> x.get())));
	}

	public static void main(String[] args) {
		DatosFestival.iniDatos("ficheros/DatosEntrada3.txt");
		DatosFestival2.iniDatos();
		System.out.println(n + " " + m);
		System.out.println(costes);
		System.out.println(costesMin);
	}

}
