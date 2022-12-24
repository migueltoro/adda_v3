package us.lsi.alg.monedas.manual;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

public record Moneda(Integer valor,Integer peso) {
	
	public static Moneda of(Integer valor, Integer peso) {
		return new Moneda(valor, peso);
	}
	
	public static Moneda parse(String linea) {
		String[] p = linea.split(",");
		return new Moneda(Integer.parseInt(p[0]),Integer.parseInt(p[1]));
	}
	
	public Double pesoUnitario() {
		return (1.*this.peso)/this.valor;
	}

	@Override
	public String toString() {
		return String.format("(%d,%d,%.2f)",this.valor,this.peso,this.pesoUnitario());
	}
	
	public static Double getPesoUnitario(Integer i) {
		return Moneda.monedas.get(i).pesoUnitario();
	}

	public static List<Moneda> monedas;
	
	public static Moneda get(Integer i) {
		return Moneda.monedas.get(i);
	}
	
	public static Integer valor(Integer i) {
		return Moneda.monedas.get(i).valor;
	}
	
	public static Integer peso(Integer i) {
		return Moneda.monedas.get(i).peso;
	}
	
	public static void datos(String fichero){	
		monedas = Files2.streamFromFile(fichero)
				.map(ln->Moneda.parse(ln))
				.sorted(Comparator.comparing(m->-m.pesoUnitario()))
				.collect(Collectors.toList());
	}
	
}
