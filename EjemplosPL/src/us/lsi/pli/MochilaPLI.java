package us.lsi.pli;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.ObjetoMochila;
import us.lsi.solve.AuxGrammar;

public class MochilaPLI {
	public static Integer CI;
	public static List<ObjetoMochila>  objetos;
	public static int n;
	public static Integer getN() {
		return n;
	}
	public static Integer getCI() {
		return CI;
	}
	public static Integer getValor(Integer i) {
		return objetos.get(i).getValor();
	}
	public static Integer getPeso(Integer i) {
		return objetos.get(i).getPeso();
	}
	public static Integer getNMU(Integer i) {
		return objetos.get(i).getNumMaxDeUnidades();
	}
	
	public static void mochila_model() throws IOException {
		DatosMochila.iniDatos("data/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		CI = DatosMochila.capacidadInicial;
		objetos = DatosMochila.getObjetos();
		n = objetos.size();
		AuxGrammar.generate(MochilaPLI.class,"models/mochila.lsi","ficheros/mochila.lp");
		GurobiSolution solution = GurobiLp.gurobi("ficheros/mochila.lp");
		Locale.setDefault(new Locale("en", "US"));
		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void main(String[] args) throws IOException {	
		mochila_model();
	}

}
