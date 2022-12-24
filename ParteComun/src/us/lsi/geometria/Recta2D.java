package us.lsi.geometria;

import us.lsi.math.Math2;

public class Recta2D {


	public static Recta2D createEnGrados(Punto2D p, Double angulo) {
		return new Recta2D(p, Math.toRadians(angulo));
	}
	
	public static Recta2D create(Punto2D p, Double angulo) {
		return new Recta2D(p, angulo);
	}
	
	public static Recta2D create(Punto2D p, Vector2D d) {
		return new Recta2D(p, d);
	}

	public static Recta2D create(Punto2D p1, Punto2D p2) {
		return new Recta2D(p1, p2);
	}
	
	public static Recta2D create() {
		return new Recta2D();
	}

	public static Recta2D create(Recta2D r) {
		return new Recta2D();
	}

	private Double a;
	private Double b;
	private Double c;
	private Vector2D vector;
	private Punto2D punto;
	private Double angulo;
	private Double distanciaAlOrigenConSigno;
		
	private Recta2D() {
		this(Punto2D.getOrigen(),Vector2D.ofRadians(1.,0.));
	}
	
	private Recta2D(Punto2D p, Double angulo) {
		this(p,Vector2D.ofRadians(1., angulo));
	}

	protected Recta2D(Punto2D p, Vector2D vector) {	
		this.punto = Punto2D.of(p);
		this.vector = Vector2D.of(vector);
		this.angulo = Math.atan2(vector.getY(), vector.getX());
		this.angulo = this.angulo <0 ? this.angulo+Math.PI : this.angulo;
		this.a = this.vector.getY();
		this.b = -this.vector.getX();
		this.c = -(a*punto.x()+b*punto.y());
		this.distanciaAlOrigenConSigno = getDistancia(Punto2D.getOrigen());
	}

	private Recta2D(Punto2D p1, Punto2D p2) {
		this(p1,p2.minus(p1));
	}
	
	protected Recta2D(Recta2D r) {
		this(r.getPunto(),r.getVector());
	}
	
	public Vector2D getVector() {
		return vector;
	}

	public Punto2D getPunto() {
		return punto;
	}
	
	public Double getAngulo() {
		return angulo;
	}

	public Double getA() {
		return a;
	}

	public Double getB() {
		return b;
	}

	public Double getC() {
		return c;
	}

	public Double getAnguloEnGrados() {
		return Math.toDegrees(angulo);
	}
	
	public Double getAngulo(Recta2D r) {
		return this.vector.getAngulo(r.getVector());
	}

	public Double getAnguloEnGrados(Recta2D r) {
		return Math.toDegrees(getAngulo(r));
	}
	
	public Boolean contienePunto(Punto2D p) {
		return a*p.x()+b*p.y()+c == 0.;
	}

	public Double getDistanciaAlOrigenConSigno() {
		return this.distanciaAlOrigenConSigno;
	}
	
	public Double getDistancia(Punto2D p) {
		Double r = a*p.x()+b*p.y()+c;
		r = r/Math.hypot(a, b);
		return r;
	}
	
	public Recta2D getParalela(Punto2D p) {
		return Recta2D.create(p, vector);
	}
	
	public Recta2D getPerpendicular(Punto2D p) {
		return Recta2D.create(p, vector.getOrtogonal());
	}
	
	public Punto2D cortaA(Recta2D r) {
		Punto2D p = null;
		Double d = this.a*r.b-r.a*this.b;
		if(d!=0.){
			p = Punto2D.of(this.b*r.c-r.b*this.c, r.a*this.c-this.a*r.c);
		}
		return p;
	}
	
	
	public Recta2D rota(Punto2D p, Double angulo) {
		// TODO Auto-generated method stub
		Punto2D p1 = this.punto;
		Punto2D p2 = this.punto.add(vector);
		return Recta2D.create(p1.rota(p, angulo), p2.rota(p, angulo));
	}

	
	public Recta2D traslada(Vector2D v) {
		// TODO Auto-generated method stub
		return Recta2D.create(this.getPunto().traslada(v), this.angulo);
	}
	
	
	@Override
	public String toString() {
		return Math2.simplify(a) + " X " + Math2.simplify(b)+ " Y " + Math2.simplify(c) + " = 0";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((this.angulo == null) ? 0
						: this.angulo.hashCode());
		result = prime
				* result
				+ ((this.distanciaAlOrigenConSigno == null) ? 0
						: this.distanciaAlOrigenConSigno.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Recta2D))
			return false;
		Recta2D s2 = (Recta2D) obj;
		if (this.angulo == null) {
			if (s2.angulo != null)
				return false;
		} else if (!this.angulo
				.equals(s2.angulo))
			return false;
		if (this.getDistanciaAlOrigenConSigno() == null) {
			if (s2.getDistanciaAlOrigenConSigno() != null)
				return false;
		} else if (!this.getDistanciaAlOrigenConSigno()
				.equals(s2.getDistanciaAlOrigenConSigno()))
			return false;
		return true;
	}
	
	
}
