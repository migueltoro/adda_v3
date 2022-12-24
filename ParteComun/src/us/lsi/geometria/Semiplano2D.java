package us.lsi.geometria;

import us.lsi.math.Math2;

public class Semiplano2D extends Recta2D  {
	
	
	private double alfa;
	private double a;
	private double b;
	private double c;
	
	public static Semiplano2D create(Punto2D p, Vector2D d, Punto2D s) {
		return new Semiplano2D(p,d,s);
	}

	public static Semiplano2D create(Recta2D r, Punto2D s) {
		return new Semiplano2D(r,s);
	}
	
	public static Semiplano2D create(Semiplano2D r) {
		return new Semiplano2D(r.getPunto(),r.getVector(),r.alfa,r.a,r.b,r.c);
	}
	
	private Semiplano2D(Punto2D p, Vector2D vector, double alfa, double a,
			double b, double c) {
		super(p, vector);
		this.alfa = alfa;
		this.a = a;
		this.b = b;
		this.c = c;
	}

	private Semiplano2D(Punto2D p, Vector2D d, Punto2D pc) {
		super(p, d);
		if(getA()*pc.x()+getB()*pc.y()+getC() <=0. ){
			a = getA();
			b = getB();
			c = getC();
		} else {
			a = -getA();
			b = -getB();
			c = -getC();
		}
		double y = this.a;
		double x = -this.b;
		this.alfa = Math.atan2(y, x);
	}
	
	private Semiplano2D(Recta2D r, Punto2D pc) {
		super(r);
		if(getA()*pc.x()+getB()*pc.y()+getC() <=0. ){
			a = getA();
			b = getB();
			c = getC();
		} else {
			a = -getA();
			b = -getB();
			c = -getC();
		}
		double y = this.a;
		double x = -this.b;
		this.alfa = Math.atan2(y, x);
	}
	
	
	public Semiplano2D getOpuesto(){
		return new Semiplano2D(getPunto(),getVector(),this.alfa+Math.PI,-this.a,-this.b,-this.c);
	}
	
	public Boolean contains(Punto2D p) {
		return a*p.x()+b*p.y()+c <= 0.;
	}
	
	public Boolean contains(Poligono2D a) {
		Boolean r = true;
		for(Punto2D p:a.getVertices()){
			r = r & contains(p);
			if(!r) break;
		}
		return r;
	}
	
	public Poligono2D intersecta(Poligono2D a) {
		Poligono2D r = Poligono2D.create();
		int nv = a.getNumeroDeVertices();
		Boolean[] cc = new Boolean[nv];
		Recta2D lado = null;
		for(int i = 0; i < nv ; i++){
			if(contains(a.getVertices().get(i))){
				cc[i]= true;
			}else{
				cc[i]= false;
			}
		}
		for(int i = 0; i < nv; i++){
			if(cc[(i+1)%nv]){
				r.addVertice(a.getVertices().get((i+1)%nv));
			}
			if(!cc[(i+1)%nv] && cc[i]){
				lado = Recta2D.create(a.getVertices().get((i+1)%nv), a.getVertices().get(i));
				r.addVertice(lado.cortaA(this));
			}
		}
		return r;
	}

	
	@Override
	public String toString() {
		return Math2.simplify(a) + " X " + Math2.simplify(b)+ " Y " + Math2.simplify(c) + " < 0";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(alfa);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Semiplano2D))
			return false;
		Semiplano2D other = (Semiplano2D) obj;
		if (Double.doubleToLongBits(alfa) != Double
				.doubleToLongBits(other.alfa))
			return false;
		return true;
	}
		
}
