package us.lsi.enero_2024.e4;

import us.lsi.common.Multiset;

public record Datos(String nombre, Character sexo) {

	public static Datos of(String nombre, Character sexo) {
		return new Datos(nombre, sexo);
	}
	
	public static Datos parse(String text) {
		String[] fam = text.split("-");
		return Datos.of(fam[0], fam[1].charAt(0));
	}
	
	public String toString() {
		return String.format("(%s,%s)",this.nombre(),this.sexo());
	}
	
	public static Double porcentaje(Multiset<Datos> m, Datos dt) {
		Integer nSexos = m.elementSet().stream()
				.filter(d->d.sexo().equals(dt.sexo()))
				.mapToInt(d->m.count(d))
				.sum()-1;
		Integer ndatos = m.count(dt)-1;
		if (nSexos == 0) return 0.0;
		return ((double)ndatos)/nSexos;
	}
	
	public static void main(String[] args) {
		System.out.println(Datos.parse("Felipe-H"));
	}
}
