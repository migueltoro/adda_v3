package us.lsi.pli.reinas;

import java.io.IOException;
import java.util.Locale;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class ReinasPLI {
	
	public static int n = 5;
	
	public static void reinas_gen() throws IOException {
		AuxGrammar.generate(ReinasPLI.class,"models/reinas_1.lsi","ficheros/reinas_1.lp");
	}
	
	public static void reinas_model_2() throws IOException {
		AuxGrammar.generate(ReinasPLI.class,"models/reinas_3.lsi","ficheros/reinas_3.lp");
		GurobiSolution solution = GurobiLp.gurobi("ficheros/reinas_2.lp");
		Locale.setDefault(Locale.of("en", "US"));
		System.out.println(solution.toString((s,d)->!s.contains("$") && d >0));
	}
	
	public static void main(String[] args) throws IOException {
		reinas_model_2();
	}

}
