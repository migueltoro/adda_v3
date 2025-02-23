package us.lsi.ag.mochila;



import java.util.List;
import java.util.Locale;


import us.lsi.ag.agchromosomes.AChromosome;
import us.lsi.ag.agchromosomes.Chromosomes;
import us.lsi.common.String2;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.sa.AlgoritmoSA;
import us.lsi.sa.StateSa;
import us.lsi.sa.StateSaChromosome;

public class TestMochilaSARange {
	

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		AlgoritmoSA.alfa = 0.99;
		AlgoritmoSA.numeroDeIteracionesPorIntento = 1000;
		AlgoritmoSA.numeroDeIntentos = 30;
		AlgoritmoSA.numeroDeIteracionesALaMismaTemperatura = 1;
		AlgoritmoSA.temperaturaInicial = 627651.60;
		
		DatosMochila.capacidadInicial = 78;
		
		DatosMochilaAGRange p = new DatosMochilaAGRange("ficheros/objetosmochila.txt");
		AChromosome<List<Integer>, List<Double>, SolucionMochila> cv = Chromosomes.ofRangeInteger(p);
		StateSaChromosome<List<Integer>,List<Double>, SolucionMochila> c = StateSaChromosome.of(cv);
		AlgoritmoSA<List<Integer>, List<Double>,SolucionMochila> a = AlgoritmoSA.of(c);
		String2.toConsole("%.2f",a.averageIncrement(20));
		a.ejecuta();		
		StateSa<List<Integer>, List<Double>, SolucionMochila> s = a.mejorSolucionEncontrada;
		List<Integer> d = s.achromosome().decode();
		System.out.println(p.solution(d));
	}

}
