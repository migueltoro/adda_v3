package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphWalk;

import us.lsi.alg.mochila.MochilaEdge;
import us.lsi.alg.mochila.MochilaVertex;
import us.lsi.alg.mochila.MochilaVertexI;
import us.lsi.alg.mochila.SolucionMochila;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.mochila.datos.DatosMochila;

public class Heuristica {

	public static Integer valorVoraz(MochilaVertex v1) {
		MochilaVertex v = v1;
		Integer r = 0;
		while (!v.goal()) {
			Integer a = v.greedyAction();
			r = r + a * DatosMochila.getValor(v.index());
			v = v.neighbor(a);
		}
		return r;
	}
	
	public static GraphPath<MochilaVertex,MochilaEdge> caminoVoraz(MochilaVertex v1,EGraph<MochilaVertex,MochilaEdge> g) {
		MochilaVertex v = v1;
		Integer r = 0;
		List<MochilaVertex> vertices = new ArrayList<>(List.of(v));
		while (!v.goal()) {
			Integer a = v.greedyAction();
			r = r + a * DatosMochila.getValor(v.index());
			v = v.neighbor(a);
			vertices.add(v);
		}
		return new GraphWalk<>(g,vertices,r);
	}
	
	public static SolucionMochila solucionVoraz(MochilaVertex v1) {
		List<Integer> acciones = new ArrayList<>();
		MochilaVertex v = v1;
		while (v.capacidadRestante() > 0 && v.index() < DatosMochila.n) {
			Integer a = v.greedyAction();
			acciones.add(a);
			v = v.neighbor(a);
		}
		return SolucionMochila.of(acciones);
	}
	
	public static Double heuristica(MochilaVertex v1, Predicate<MochilaVertex> p, MochilaVertex v2) {
		return heuristica1(v1);	
	}
	
	public static Double heuristica1(MochilaVertex v1) { 
		Double weight = 0.;
		Integer index = v1.index();
		Double cr = (double)v1.capacidadRestante();		
		while(!(index==MochilaVertexI.n || cr==0.)) {
			Double a = Math.min(cr/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
			cr = cr - a * DatosMochila.getPeso(index);
			weight += a * DatosMochila.getValor(index);
			index++;
		} 
		return weight;
	}
	
	public static Double heuristica2(MochilaVertex v1) {
		MochilaVertex v = v1;
		Integer r = 0;
		while (!v.goal()) {
			Integer a = v.greedyAction()+1;
			r = r + a * DatosMochila.getValor(v.index());
			Integer cr = v.capacidadRestante() - a * DatosMochila.getPeso(v.index());
			v = MochilaVertex.of(v.index() + 1, cr>=0?cr:0);
		}
		return r.doubleValue();
	}
	
	public static Double heuristica3(MochilaVertex v1) {
		MochilaVertex v = v1;
		Double r = 0.;
		Double a = (1.*v1.capacidadRestante())/DatosMochila.getPeso(v.index());
		r = r + a * DatosMochila.getValor(v.index());
		return r;
	}
	
	public static Double cota(MochilaVertex v1, Integer a) {
		MochilaVertex v2 = v1.neighbor(a);
		return a*DatosMochila.getValor(v1.index()).doubleValue()+heuristica1(v2);
	}
	
	public static Double cota2(MochilaVertex v1, Integer a) {
		MochilaVertex v2 = v1.neighbor(a);
		return a*DatosMochila.getValor(v1.index()).doubleValue()+heuristica2(v2);
	}
	
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosMochila.iniDatos("ficheros/mochila/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;
		MochilaVertex v1 = MochilaVertex.of(0, DatosMochila.capacidadInicial);
		Integer r = Heuristica.valorVoraz(v1);
		Double s1 = Heuristica.heuristica1(v1);
		Double s2 = Heuristica.heuristica2(v1);
		Double s3 = Heuristica.heuristica3(v1);
		SolucionMochila s = Heuristica.solucionVoraz(v1);
		System.out.printf("G=%d,H=%.2f,H2=%.2f,H3=%.2f\n",r,s1,s2,s3);
		System.out.printf("%s",s);
	}

}

