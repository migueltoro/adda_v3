package us.lsi.sudoku.datos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Files2;
import us.lsi.common.Preconditions;


public class DatosSudoku {
	
	/**
	 * Tamaño de un subcuadro
	 */
	public static Integer tamSubCuadro = 3;
	/**
	 * Número de filas
	 */
	public static Integer numeroDeFilas = tamSubCuadro*tamSubCuadro;
	/**
	 * Numero de casillas
	 */
	public static Integer numeroDeCasillas = numeroDeFilas*numeroDeFilas;

	private static Map<Casilla,Casilla> casillas = new HashMap<>();
	private static Map<Casilla,Set<Casilla>> casillasEnConflicto = new HashMap<>();
	private static List<Set<Casilla>> casillasEnFila;
	private static List<Set<Casilla>> casillasEnColumna;
	private static List<Set<Casilla>> casillasEnSubCuadro;
	public static List<Casilla> casillasLibres;
//	private static Map<Casilla,List<Integer>> valoresLibres = new HashMap<>();
	private static Comparator<Casilla> orden = 
		Comparator.comparing(c->getValoresLibresEnCasilla(c).size());
	private static Set<Integer> todosLosValores;
	
	private static Casilla addCasilla(String s){
		Casilla c = Casilla.create(s);
		if(!casillas.containsKey(c)) {
			casillas.put(c, c);
		}
		return casillas.get(c);
	}
	
	private static Casilla addCasilla(Integer p){
		Casilla c = Casilla.create(p);
		if(!casillas.containsKey(c)) {
			casillas.put(c, c);
		}
		return casillas.get(c);
	}
	
	public static Casilla get(Integer p) {
		Casilla c = Casilla.create(p);
		Casilla r = casillas.get(c);
		return r;
	}

	public static Casilla get(Integer x, Integer y) {
		Casilla c = Casilla.create(x, y, null);
		Casilla r = casillas.get(c);
		return r;
	}
	
	public static Set<Casilla> getCasillasEnFila(int y){
		return casillasEnFila.get(y);
	}
	
	public static Set<Casilla> getCasillasEnColumna(int x){
		return casillasEnColumna.get(x);
	}
	
	public static Set<Casilla> getCasillasEnSubCuadro(int sc){
		return casillasEnSubCuadro.get(sc);
	}
	
	/**
	 * @param nf Fichero de datos
	 * @post Inicializa las variables del tipo
	 */
	public static void iniDatos(String nf) {
		Files2.streamFromFile(nf)
				.forEach(s -> DatosSudoku.addCasilla(s));
		IntStream.range(0,numeroDeCasillas)				
				.forEach(p -> DatosSudoku.addCasilla(p));		
		iniFilas();
		iniColumnas();
		iniSubCuadros();
		casillasLibres = casillasLibres();
		check();
		setValoresUnicos();
		casillasLibres = casillasLibres();
		setValoresUnicos();
		casillasLibres = casillasLibres();
	}

	private static void check() {		
		getCasillas()
		.stream()
		.filter(c->!c.isFree())
		.forEach(c->
			Preconditions.checkArgument(
				!getValoresOcupadosEnCasilla(c).contains(c.getValue()),
				"La casilla "+c+" está mal"));
	}

	public static Set<Casilla> getCasillas(){
		return casillas.keySet();
	}
	
	public static Casilla getCasillaLibre(int index){
		return casillasLibres.get(index);
	}
	
	public static Set<Casilla> getCasillasEnConflicto(Casilla c){
		if(!casillasEnConflicto.containsKey(c)) {
			Set<Casilla> r = new HashSet<>();
			r.addAll(getCasillasEnFila(c.getY()));
			r.addAll(getCasillasEnColumna(c.getX()));
			r.addAll(getCasillasEnSubCuadro(c.getSubCuadro()));
			casillasEnConflicto.put(c, r);
		}
		return casillasEnConflicto.get(c);
	}
	
	public static void ordena(Integer index) {
		List<Casilla> sl = casillasLibres.subList(index, casillasLibres.size());
		sl.sort(orden);
	}
	
	public static Set<Integer> getTodosLosValores(){
		if(todosLosValores ==null)
			todosLosValores =
			IntStream.rangeClosed(1, numeroDeFilas)
					 .boxed()
					 .collect(Collectors.toSet());
		return todosLosValores;
	}
	
	public static List<Integer> getValoresLibresEnCasilla(Casilla d){
		Set<Integer> vo = DatosSudoku.getCasillasEnConflicto(d).stream()
				.filter(c->c.getValue()!=null)
				.map(c->c.getValue())
				.collect(Collectors.toSet());
		List<Integer> r = new ArrayList<>(DatosSudoku.getTodosLosValores());
		r.removeAll(vo);
		return r;
	}
	
	public static Set<Integer> getValoresOcupadosEnCasilla(Casilla c){
		return getCasillasEnConflicto(c)
				.stream()
				.filter(x->!x.isFree()&&!x.equals(c))
				.map(x->x.getValue())
				.collect(Collectors.toSet());
	}
	
	public static Set<Integer> getValoresOcupadosEnFila(Integer y){
		return getCasillasEnFila(y)
				.stream()
				.filter(c->c.getValue()!=null)
				.map(c->c.getValue())
				.collect(Collectors.toSet());
	}
	
	public static Set<Integer> getValoresOcupadosEnColumna(Integer x){
		return getCasillasEnColumna(x)
				.stream()
				.filter(c->c.getValue()!=null)
				.map(c->c.getValue())
				.collect(Collectors.toSet());
	}
	
	public static Set<Integer> getValoresOcupadosEnSubCuadro(Integer sc){
		return getCasillasEnSubCuadro(sc)
				.stream()
				.filter(c->c.getValue()!=null)
				.map(c->c.getValue())
				.collect(Collectors.toSet());
	}
	
	public static List<Casilla> casillasLibres(){ 
		return DatosSudoku.getCasillas()
					   .stream()
					   .filter(c->c.getValue()==null)
					   .sorted((c1,c2)->getValoresLibresEnCasilla(c1).size()-getValoresLibresEnCasilla(c2).size())
					   .collect(Collectors.toList());
	}
	/**
	 * @return Una lista de conjuntos vacios
	 */
	private static List<Set<Casilla>> getListOfEmptySet() {
		return IntStream.range(0, numeroDeFilas)
		.boxed()
		.map(x->new HashSet<Casilla>())
		.collect(Collectors.toList());
	}
	
	private static void iniFilas() {
		casillasEnFila = getListOfEmptySet();
		getCasillas().stream()
				.forEach(c->casillasEnFila.get(c.getY()).add(c));
	}	
	private static void iniSubCuadros() {
		casillasEnSubCuadro = getListOfEmptySet();
		getCasillas().stream()
			.forEach(c->casillasEnSubCuadro.get(c.getSubCuadro()).add(c));
	}
	
	private static void iniColumnas() {
		casillasEnColumna = getListOfEmptySet();
		getCasillas().stream()
				.forEach(c->casillasEnColumna.get(c.getX()).add(c));
	}
	
	public static List<Integer> valoresEscogidos(){
		return getCasillas()
				.stream()
				.filter(c->!c.initialFree)
				.map(c->c.getValue())
				.sorted()
				.collect(Collectors.toList());
	}
	
	public static void setValoresUnicos() {
		casillasLibres()
		.stream()
		.filter(c->getValoresLibresEnCasilla(c).size()==1)
		.forEach(c->c.setValue(getValoresLibresEnCasilla(c).get(0)));
	}
		
}
