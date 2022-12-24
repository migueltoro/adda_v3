package us.lsi.geometria;

import us.lsi.common.Preconditions;

public class Vector2D {

	public static Vector2D of(Double x, Double y) {
		return new Vector2D(x, y);
	}

	public static Vector2D of(Punto2D p) {
		return new Vector2D(p.x(), p.y());
	}
	
	public static Vector2D of(Vector2D p) {
		return new Vector2D(p.getX(), p.getY());
	}
	
	public static Vector2D ofDegrees(Double modulo, Double angulo){
		return ofRadians(modulo, Math.toRadians(angulo));
	}
	
	public static Vector2D ofRadians(Double modulo, Double angulo){
		Preconditions.checkArgument(modulo >=0);
		return of(modulo*Math.cos(angulo),modulo*Math.sin(angulo));		
	}
	
	private Double x;
	private Double y;
	private Double modulo;
	private Double angulo;
	
	private Vector2D(Double x, Double y) {
		super();
		this.x = x;
		this.y = y;
		this.modulo = Math.hypot(x, y);
		this.angulo = Math.atan2(y, x);
	}

	public Double getX() {
		return x;
	}

	public Double getY() {
		return y;
	}

	public void setX(Double x) {
		this.x = x;
		this.modulo = Math.hypot(x, y);
		this.angulo = Math.atan2(y, x);
	}

	public void setY(Double y) {
		this.y = y;
		this.modulo = Math.hypot(x, y);
		this.angulo = Math.atan2(y, x);
	}
	
	public Double getModulo() {
		return modulo;
	}

	public Double getAngulo() {
		return angulo;
	}

	public void setAngulo(Double angulo) {
		this.angulo = angulo;
		this.x = modulo*Math.cos(angulo);
		this.y = modulo*Math.sin(angulo);
	}
	
	public void setModulo(Double modulo) {
		this.modulo = modulo;
		this.x = modulo*Math.cos(angulo);
		this.y = modulo*Math.sin(angulo);
	}

	public Double getAnguloEnGrados() {
		return Math.toDegrees(angulo);
	}

	public Double getAngulo(Vector2D v) {
		return Math.asin(this.multiplicaVectorial(v)/(this.modulo*v.getModulo()));
	}
	
	public Double getAnguloEnGrados(Vector2D v) {
		return Math.toDegrees(getAngulo(v));
	}
	
	public Vector2D proyectaSobre(Vector2D v){
		Vector2D u = v.getUnitario();
		return u.multiplica(this.multiplicaEscalar(v));
	}	
	
	public Vector2D getOrtogonal() {
		return Vector2D.of(-y, x);
	}
	
	public Vector2D getUnitario() {
		return ofRadians(1.,this.angulo);
	}
	
	public Vector2D getOpuesto() {
		return Vector2D.of(-x, -y);
	}
	
	public Vector2D rota(Double angulo) {
		return ofRadians(this.modulo,this.angulo+angulo);
	}
	
	public Vector2D suma(Vector2D v) {
		return of(this.x+v.getX(),this.y+v.getY());
	}
		
	public Vector2D multiplica(Double factor) {
		return of(this.x*factor,this.y*factor);
	}
	
	public Double multiplicaVectorial(Vector2D v) {
		return this.getX()*v.getY()-this.getY()*v.getX();
	}
	
	public Double multiplicaEscalar(Vector2D v) {
		return this.getX()*v.getX()+this.getY()*v.getY();
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
		if (getClass() != obj.getClass())
			return false;
		Vector2D other = (Vector2D) obj;
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

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
}
