package us.lsi.geometria;

import us.lsi.common.Preconditions;

public record Punto2D(Double x,Double y) implements Comparable<Punto2D>, ObjetoGeometrico2D {	

	private static Punto2D cero = Punto2D.of(0.,0.);
	
	public static Punto2D getOrigen(){
		return cero;
	}
	
	public static Punto2D of(Double x, Double y) {
		return new Punto2D(x, y);
	}

	public static Punto2D of(Punto2D p) {
		return new Punto2D(p.x(), p.y());
	}
	
	
	public static Punto2D of(Vector2D v){
		return Punto2D.of(v.getX(),v.getY());
	}
	
	public enum Cuadrante{PRIMER_CUADRANTE, SEGUNDO_CUADRANTE, TERCER_CUADRANTE, CUARTO_CUADRANTE};
	
	
	
    
    public Cuadrante getCuadrante(){
		Cuadrante c;
		if(this.x() >=0 && this.y() >=0){
			c = Cuadrante.PRIMER_CUADRANTE;
		} else if(this.x() <=0 && this.y() >=0){
			c = Cuadrante.SEGUNDO_CUADRANTE;
		} else if(this.x() <=0 && this.y() <=0){
			c = Cuadrante.TERCER_CUADRANTE;
		} else {
			c = Cuadrante.CUARTO_CUADRANTE;
		}
		return c;
	}
    public Punto2D add(Vector2D v){
    	return Punto2D.of(this.x+v.getX(),this.y+v.getY());
    }
    
    public Vector2D minus(Punto2D p){
    	return Vector2D.of(this.x-p.x(),this.y-p.y());
    }
    
    public Double getDistanciaA(Punto2D p) {
		return minus(p).getModulo();
	}
    
	public Double getDistanciaAlOrigen() {
		return Vector2D.of(this).getModulo();
	} 
	
	public Punto2D traslada(Vector2D v){
		return add(v);
	}
    
	public Punto2D rota(Punto2D p, Double angulo){
		Vector2D v = minus(p).rota(angulo);
		return p.add(v);
	}
	
	
	public boolean dominaA(Punto2D p){
		return !this.equals(p) && this.x() >= p.x() && this.y() >= p.y();
	}
	
	public String toString() {
    	String s="("+x()+","+ y()+")";
    	return s;
    }

	
	@Override
	public int compareTo(Punto2D p) {
		Preconditions.checkNotNull(p,"El punto no puede ser null");
		int r = x().compareTo(p.x());
		if(r==0){
			r = y().compareTo(p.y());
		}
		return r;
	}

		
	
}
