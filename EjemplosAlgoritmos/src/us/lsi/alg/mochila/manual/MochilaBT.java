package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MochilaBT {
	
	public static class StateMochila {
			private MochilaProblem vertice;
			private Integer valorAcumulado; 
			private List<Integer> acciones; 
			private List<MochilaProblem> vertices; 
					
		private StateMochila(MochilaProblem vertice, Integer valorAcumulado, List<Integer> acciones,
					List<MochilaProblem> vertices) {
			super();
			this.vertice = vertice;
			this.valorAcumulado = valorAcumulado;
			this.acciones = acciones;
			this.vertices = vertices;
		}

		public static StateMochila of(MochilaProblem vertex) {
			List<MochilaProblem> vt = new ArrayList<>();
			vt.add(vertex);
			return new StateMochila(vertex,0,new ArrayList<>(),vt);
		}

		void forward(Integer a) {
			this.acciones.add(a);
			MochilaProblem vcn = this.vertice().vecino(a);		
			this.vertices.add(vcn);
			this.valorAcumulado = this.valorAcumulado() + a * DatosMochila.valor(this.vertice().index());
			this.vertice = vcn;
		}

		void back(Integer a) {
			this.acciones.remove(this.acciones.size()-1);
			this.vertices.remove(this.vertices.size()-1);
			this.vertice = this.vertices.get(this.vertices.size()-1);
			this.valorAcumulado = this.valorAcumulado() - a * DatosMochila.valor(this.vertice.index());			
		}
		
		SolucionMochila solucion() {
			return SolucionMochila.of(MochilaBT.start,this.acciones);
		}

		public MochilaProblem vertice() {
			return vertice;
		}

		public Integer valorAcumulado() {
			return valorAcumulado;
		}
		
	}
	
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
		Locale.setDefault(new Locale("en", "US"));
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
