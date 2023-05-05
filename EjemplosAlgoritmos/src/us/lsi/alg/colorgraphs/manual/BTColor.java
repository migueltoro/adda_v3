package us.lsi.alg.colorgraphs.manual;


import java.util.List;
import java.util.Locale;

import us.lsi.alg.colorgraphs.ColorVertex;
import us.lsi.alg.colorgraphs.DatosColor;
import us.lsi.alg.colorgraphs.SolucionColor;


public class BTColor {
	
	private StateColor estado;
	private Integer minValue;
	private SolucionColor solucion;
	
	public static BTColor of() {
		return new BTColor();
	}
	
	public SolucionColor solucion() {
		return solucion;
	}

	private BTColor() {
		this.estado = StateColor.of(ColorVertex.first());
		this.minValue = DatosColor.m;
		this.solucion = null;
	}
	
	public void btm() {
		if(this.estado.vertice().index() == DatosColor.n) {
			Integer value = estado.valorAcumulado();
			if(value < this.minValue) {
				this.minValue = value;
				this.solucion = this.estado.solucion();
			}
		} else {
			List<Integer> alternativas = this.estado.vertice().actions();
			for(Integer a:alternativas) {	
				Integer cota = this.estado.vertice().neighbor(a).nc();
				if(cota >= this.minValue) continue;
				this.estado.forward(a);
				btm();  
				this.estado.back(a);
			}
		}
	}
	
	

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		DatosColor.data(4,"ficheros/andalucia.txt");
		
		BTColor bt = BTColor.of();
		
		bt.btm();
		
		System.out.println("BT = "+ bt.solucion());

	}

}
