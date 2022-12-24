package us.lsi.alg.productos;

import java.util.Set;

import us.lsi.common.Set2;


public record Producto(String nombre,Double precio,Set<String> funciones, Boolean excluded) implements Comparable<Producto>{

	public static Producto parse(String s) {
		String[] partes = s.split(":");
		String[] tokens = partes[0].split("[()]");
		String nombre = tokens[0].trim();
		Double precio = Double.parseDouble(tokens[1].split(" ")[0].trim());
		Set<String> funciones = Set2.parseSet(partes[1].trim().split(","),tk->tk.trim());
		Boolean excluded = Set2.intersection(funciones,DatosProductos.getFunciones()).isEmpty();
		return new Producto(nombre,precio,funciones,excluded);				
	}
	
	@Override
	public String toString() {		
		return String.format("%s (%5s euros) => %s, %.2f", nombre, precio,funciones, precioPorFuncionalidad());
	}
	
	public Double precioPorFuncionalidad() {
		Integer n = Set2.intersection(this.funciones,DatosProductos.getFunciones()).size();
		return this.precio()/n;
	}

	@Override
	public int compareTo(Producto p) {
		return this.precioPorFuncionalidad().compareTo(p.precioPorFuncionalidad());
	}
}

