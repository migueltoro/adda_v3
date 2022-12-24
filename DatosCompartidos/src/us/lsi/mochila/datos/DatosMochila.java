package us.lsi.mochila.datos;


import java.util.Comparator;
import java.util.List;



import java.util.stream.Collectors;

import us.lsi.common.Files2;


/**
 * <p> Esta clase implementa el tipo ProblemaMochila. Los objetos correspondientes son problemas generalizados de la mochila. </p>
 * <p> Las propiedades de estos problemas son: </p>
 * <ul>
 * <li> Capacidad
 * <li> Index 
 * <li> Objetos Disponibles (propiedad compartida)
 * </ul> 
 * 
 * 
 * 
 * @author Miguel Toro
 *
 */
public class DatosMochila {
	
	private static List<ObjetoMochila> objetosDisponibles;
	private static Comparator<ObjetoMochila> ordenObjetos;
	public static Integer capacidadInicial;
	public static Integer numeroDeObjetos;
	public static Integer n;

	/**
	 * El método lee el fichero de entrada y actualiza la lista ObjetosDisponibles que queda ordenada 
	 * según el orden natural de los objetos 
	 * 
	 * @param fichero Fichero que contiene las propiedades de los objetos disponibles. Un objeto por línea
	 */
	public static void iniDatos(String fichero) {
		ordenObjetos = Comparator.reverseOrder();
		objetosDisponibles = Files2.streamFromFile(fichero)
				.<ObjetoMochila> map((String s) -> ObjetoMochila.create(s))
				.sorted(ordenObjetos)
				.collect(Collectors.<ObjetoMochila> toList());
		numeroDeObjetos = objetosDisponibles.size();
		n = numeroDeObjetos;
	}
	
	public static List<ObjetoMochila> getObjetos() {
		return objetosDisponibles;
	}
	
	public static Comparator<ObjetoMochila> getOrdenObjetos() {
		return ordenObjetos;
	}	

	public static ObjetoMochila getObjeto(int index){
		return DatosMochila.getObjetos().get(index);
	}
	
	public static Integer getValor(int index){
		return DatosMochila.getObjetos().get(index).getValor();
	}
	
	public static Integer getPeso(int index){
		return DatosMochila.getObjetos().get(index).getPeso();
	}
	
	public static Integer getNumMaxDeUnidades(int index){
		return DatosMochila.getObjetos().get(index).getNumMaxDeUnidades();
	}
	
	public static Integer getNumUnidadesPosibles(int index, Integer capacidad){
		return Math.min(DatosMochila.getNumMaxDeUnidades(index),capacidad/DatosMochila.getPeso(index));
	}
	
	public static Double getCantidadPosible(int index, Double capacidad){
		return Math.min(DatosMochila.getNumMaxDeUnidades(index),capacidad/DatosMochila.getPeso(index));
	}
	
	public static Boolean restricciones(Integer c) {
		return c >=0;
	}

	public static Integer getCotaSuperior(Integer index, Integer cr) {
		Double r = 0.;
		int ind = index;
		int n = getObjetos().size();
		Double capacidadRestante = (double)cr;
		Double nu =0.;	
		while(ind < n && capacidadRestante > 0) {	
			nu = getCantidadPosible(ind,capacidadRestante);
			r = r+nu*getValor(ind);
			capacidadRestante = capacidadRestante-nu*getPeso(ind);			
			ind++;		
		} 
		return (int)Math.ceil(r);
	}
}
