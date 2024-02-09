package us.lsi.ag.mochila;



import java.util.List;
import java.util.Locale;

import us.lsi.ag.RangeIntegerData;
import us.lsi.common.String2;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.alg.mochila.SolucionMochila;
import us.lsi.sa.AlgoritmoSA;
import us.lsi.sa.StateSa;
import us.lsi.sa.StateSaChromosome;

public class TestMochilaSARange {
	

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		AlgoritmoSA.alfa = 0.9999;
		AlgoritmoSA.numeroDeIteracionesPorIntento = 100000;
		AlgoritmoSA.numeroDeIntentos = 30;
		AlgoritmoSA.numeroDeIteracionesALaMismaTemperatura = 1;
		AlgoritmoSA.temperaturaInicial = 627651.60;
		
		DatosMochila.capacidadInicial = 78;
		
		RangeIntegerData<SolucionMochila> p = new DatosMochilaAGRange("ficheros/objetosmochila.txt");
		p.iniValues(p);
		StateSaChromosome<List<Integer>, SolucionMochila> c = StateSaChromosome.of(p);
		AlgoritmoSA<List<Integer>, SolucionMochila> a = AlgoritmoSA.of(c);
		String2.toConsole("%.2f",a.averageIncrement(20));
		a.ejecuta();		
		StateSa<List<Integer>, SolucionMochila> s = a.mejorSolucionEncontrada;
		List<Integer> d = s.data().decode(s.chromosome());
		System.out.println(p.solucion(d));
	}

}
