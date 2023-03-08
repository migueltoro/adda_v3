package us.lsi.alg.mochila.manual;


import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MochilaBT {
	
	public static MochilaProblem start;
	public static StateMochila estado;
	public static Integer maxValue;
	public static Set<SolucionMochila> soluciones;
	
	public static void btm(Integer capacidadInicial) {
		MochilaBT.start = MochilaProblem.of(0,capacidadInicial);
		MochilaBT.estado = StateMochila.of(start);
		MochilaBT.maxValue = Integer.MIN_VALUE;
		MochilaBT.soluciones = new HashSet<>();
		btm();
	}
	
	public static void btm(Integer capacidadInicial, Integer maxValue, SolucionMochila s) {
		MochilaBT.start = MochilaProblem.of(0,capacidadInicial);
		MochilaBT.estado = StateMochila.of(start);
		MochilaBT.maxValue = maxValue;
		MochilaBT.soluciones = new HashSet<>();
		MochilaBT.soluciones.add(s);
		btm();
	}
	
	public static void btm() {
		if(MochilaBT.estado.vertice().index() == DatosMochila.n) {
			Integer value = estado.valorAcumulado();
			if(value > MochilaBT.maxValue) {
				MochilaBT.maxValue = value;
				MochilaBT.soluciones.add(MochilaBT.estado.solucion());
			}
		} else {
			List<Integer> alternativas = MochilaBT.estado.vertice().acciones();
			for(Integer a:alternativas) {	
				Double cota = MochilaBT.estado.valorAcumulado()+Heuristica.cota(MochilaBT.estado.vertice(),a);
				if(cota <= MochilaBT.maxValue) continue;
				MochilaBT.estado.forward(a);
				btm();  
				MochilaBT.estado.back(a);
			}
		}
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		DatosMochila.datos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;
		MochilaProblem v1 = MochilaProblem.of(0, DatosMochila.capacidadInicial);
		SolucionMochila s = Heuristica.solucionVoraz(v1);
		long startTime = System.nanoTime();
		MochilaBT.btm(78);	
		long endTime = System.nanoTime() - startTime;
		System.out.println("1 = "+endTime);
		System.out.println(MochilaBT.soluciones);
	    startTime = System.nanoTime();
		MochilaBT.btm(78,s.valor(),s);
		long endTime2 = System.nanoTime() - startTime;
		System.out.println("2 = "+endTime2);
		System.out.println("2 = "+1.*endTime2/endTime);
		System.out.println(MochilaBT.soluciones);
	}


}
