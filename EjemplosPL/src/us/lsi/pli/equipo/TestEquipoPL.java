package us.lsi.pli.equipo;

import java.io.IOException;
import java.util.Locale;
import java.util.stream.Collectors;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class TestEquipoPL {
	
	public static Integer get(String s, Integer i) {
		String[] partes = s.split("_");
		return Integer.parseInt(partes[i]);
	}
	
	public static void equipo_model(String fichero) throws IOException {
		DatosEquipo.iniDatos(fichero);
		AuxGrammar.generate(DatosEquipo.class,"models/equipo.lsi","ficheros/equipo.lp");
		GurobiSolution solution = GurobiLp.gurobi("ficheros/equipo.lp");
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(solution.values.keySet()
				.stream()
				.filter(k ->k.startsWith("x") && solution.values.get(k)==1)
				.sorted()
				.map(k->String.format("%s,%s,%.2f",k,solution.values.get(k),DatosEquipo.getR(get(k,1),get(k,2))))
				.collect(Collectors.joining("\n")));
		
	}

	public static void main(String[] args) throws IOException {	
		Locale.setDefault(new Locale("en", "US"));
		equipo_model("ficheros/DatosEquipo1.txt");
		equipo_model("ficheros/DatosEquipo2.txt");
		equipo_model("ficheros/DatosEquipo3.txt");
	}

}
