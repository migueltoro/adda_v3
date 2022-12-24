package us.lsi.alg.productos;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


import us.lsi.common.Files2;
import us.lsi.common.String2;

public class DatosProductos {
	public static Integer NUM_PRODUCTOS, NUM_FUNCIONES;
	private static Set<String> funciones;
	private static List<Producto> productos;
	private static Map<String, Integer> map_fObjetivo;
	
	public static void iniDatos(String fichero) {
		List<String> ls = Files2.linesFromFile(fichero);
		
		DatosProductos.funciones = Arrays.stream(ls.remove(0).split(":")[1].split(","))
		.map(s->s.trim()).collect(Collectors.toSet());
		DatosProductos.funciones = Set.copyOf(funciones);
		
		DatosProductos.productos = ls.stream().map(s->Producto.parse(s)).filter(p->!p.excluded())
				.sorted(Comparator.naturalOrder()).collect(Collectors.toList());
		
		DatosProductos.productos = List.copyOf(productos);
		
		
		DatosProductos.NUM_PRODUCTOS = productos.size();
		DatosProductos.NUM_FUNCIONES = funciones.size();	
		
		DatosProductos.map_fObjetivo = new HashMap<>();
		Integer id = 0;
		for(String f: funciones) {
			map_fObjetivo.put(f, id++);
		}	
	}
	
	public static List<Producto> getProductos() {
		return DatosProductos.productos;
	}

	public static Producto getProducto(int index) {
		return DatosProductos.productos.get(index);
	}	
	
	public static Set<String> getFunciones() {
		return funciones;
	}
	
	public static Set<String> getFuncionesProducto(int index) {
		return DatosProductos.productos.get(index).funciones();
	}

	public static Map<String, Integer> getFObj() {
		return DatosProductos.map_fObjetivo;
	}		
	
	private static String mem;
	public static void toConsole() {
		String2.toConsole("Funcionalidades a cubrir: "+funciones);
		mem = "Nº de productos: "+NUM_PRODUCTOS;
		productos.forEach(a->mem += ("\n"+a.toString()));
		String2.toConsole(mem);
	}	
	
	// Test de la lectura del fichero
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		iniDatos("ficheros/productos2.txt");
		toConsole();
		String2.toConsole(DatosProductos.getProducto(0).toString());
		String2.toConsole(DatosProductos.NUM_PRODUCTOS.toString());
	}	
}

