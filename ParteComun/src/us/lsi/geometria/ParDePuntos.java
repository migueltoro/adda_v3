package us.lsi.geometria;


public class ParDePuntos implements Comparable<ParDePuntos> {

	public static ParDePuntos create(Punto2D p1, Punto2D p2) {
		return new ParDePuntos(p1, p2);
	}

	private Punto2D p1;
	private Punto2D p2;
	
	private ParDePuntos(Punto2D p1, Punto2D p2) {
		super();
		this.p1 = p1.compareTo(p2)>=0?p1:p2;
		this.p2 = p1.compareTo(p2)>=0?p2:p1;;
	}

	public Punto2D getP1() {
		return p1;
	}

	public Punto2D getP2() {
		return p2;
	}
	
	public Double getDistancia(){
		return p1.getDistanciaA(p2);
	}

	@Override
	public int compareTo(ParDePuntos a) {
		// TODO Auto-generated method stub
		int r = this.getDistancia().compareTo(a.getDistancia());
		if(r==0){
			r = this.getP1().compareTo(a.getP1());
		}
		if(r==0){
			r = this.getP2().compareTo(a.getP2());
		}
		return r;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p1 == null) ? 0 : p1.hashCode());
		result = prime * result + ((p2 == null) ? 0 : p2.hashCode());
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
		ParDePuntos other = (ParDePuntos) obj;
		if (p1 == null) {
			if (other.p1 != null)
				return false;
		} else if (!p1.equals(other.p1))
			return false;
		if (p2 == null) {
			if (other.p2 != null)
				return false;
		} else if (!p2.equals(other.p2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String st1 = p1.compareTo(p2)<=0? p1.toString() : p2.toString();
		String st2 = p1.compareTo(p2)>0? p1.toString() : p2.toString();		
		return "[" + st1 + "," + st2 + ","+getDistancia()+"]";
	}
	
	
}
