package us.lsi.alg.sudoku;

import us.lsi.common.Preconditions;

/**
 * @author Miguel Toro
 *
 *<p> La casilla de un Sudoku
 */
public class Casilla {
	
	public static Casilla parse(String s) {
		String[] sp = s.split(",");
		Integer x = Integer.parseInt(sp[0].trim());
		Integer y = Integer.parseInt(sp[1].trim());
		Integer value =	Integer.parseInt(sp[2].trim());
		return Casilla.of(x,y,value);
	}
	public static Casilla of(Integer x, Integer y, Integer value) {
		Casilla c = new Casilla(x, y, value); 
		return c;
	}
	public static Casilla of(Integer x, Integer y) {
		return Casilla.of(x,y,null);
	}
	public static Casilla of(Integer p) {
		Preconditions.checkArgument(0<=p && p < DatosSudoku.numeroDeCasillas);	
		Integer x = p%DatosSudoku.numeroDeFilas;
		Integer y = p/DatosSudoku.numeroDeFilas;
		return Casilla.of(x,y,null);
	}
	final private Integer x;
	final private Integer y;
	final private Integer sc;
	final private Integer p;
	private Boolean withInitialValue;
	private Integer value;
	
	private Casilla(Integer x, Integer y, Integer value) {
		super();
		Preconditions.checkArgument(0 <= x && x < DatosSudoku.numeroDeFilas);
		Preconditions.checkArgument(0 <= y && y < DatosSudoku.numeroDeFilas);
		this.x = x;
		this.y = y;
		Integer tm = DatosSudoku.tamSubCuadro;
		this.sc = x/tm+tm*(y/tm);
	    this.p = y*DatosSudoku.numeroDeFilas+x;	
		this.value = value;	
		this.withInitialValue = false;
	}
		
	private Casilla(Integer x, Integer y, Integer value, Boolean initialFree, Integer initialValue) {
		super();
		this.x = x;
		this.y = y;
		this.value = value;
		Integer tm = DatosSudoku.tamSubCuadro;
		this.sc = x/tm+tm*(y/tm);
	    this.p = y*DatosSudoku.numeroDeFilas+x;
	    this.withInitialValue = false;
	}
	/**
	 * @return La coordenada x de la casilla en 0..DatosSudoku.numeroDeFilas-1
	 */
	public Integer getX() {
		return x;
	}
	/**
	 * @return La coordenada y de la casilla en 0..CuadroSudoku.numeroDeFilas-1
	 */
	public Integer getY() {
		return y;
	}
	/**
	 * @return El subcuadro de la casilla en 0..CuadroSudoku.numeroDeFilas-1
	 */
	public Integer getSubCuadro() {
		return sc;
	}
	/**
	 * @return La posición de la casilla si se las
	 * numera por filas en 0..CuadroSudoku.numeroDeFilas-1
	 */
	public Integer getP() {
		return p;
	}
	/**
	 * @return Si la casilla está libre
	 */
	public boolean isFree() {
		return value==null?true:false;
	}
	
	/**
	 * @return Si tiene un valor inicial
	 */
	public Boolean isWithInitialValue() {
		return withInitialValue;
	}
	/**
	 * @return El valor contenido en la casilla
	 */
	public Integer getValue() {
		Preconditions.checkArgument(!this.isFree(),String.format("La casilla %s está libre",this));
		return value;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}
	
	public void setInitialValue(Boolean withInitialValue) {
		this.withInitialValue = withInitialValue;
	}
	
	public Casilla copy() {
		return new Casilla(x,y,value);
	}
	
	public static String blank = "_";
	
	public String getStringValue() {
		String r;
		if(this.isFree()) {
			r = String.format("%2s", blank);
		} else if(this.withInitialValue){
			r = String.format("X%1d", this.getValue());
		} else {
			r = String.format("%2d", this.getValue());
		}
		return r;	
	}
	
	public String getString() {
		return String.format("(%d,%d)",x,y);
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + "," + sc + "," + p
				+ "," + isFree() + ")";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Casilla))
			return false;
		Casilla other = (Casilla) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}
	
}
