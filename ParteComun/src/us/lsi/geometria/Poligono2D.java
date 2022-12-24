package us.lsi.geometria;


import java.util.Collections;
import java.util.List;

import us.lsi.common.List2;
import us.lsi.common.Preconditions;


public class Poligono2D  implements ObjetoGeometrico2D {

	public static Poligono2D create() {
		return new Poligono2D();
	}
	
	public static Poligono2D create(Punto2D p1, Punto2D p2, Punto2D p3) {
		return new Poligono2D(p1, p2, p3);
	}

	public static Poligono2D create(Punto2D... lp) {
		return new Poligono2D(lp);
	}

	public static Poligono2D createRectangulo(Punto2D p, Double base, Double altura) {
		Poligono2D r = Poligono2D.create();
		r.addVertice(p);
		r.addVertice(p.add(Vector2D.of(base, 0.)));
		r.addVertice(p.add(Vector2D.of(base, altura)));
		r.addVertice(p.add(Vector2D.of(0., altura)));
		return r;
	}
	
	public static Poligono2D createRectangulo(Double xMin, Double xMax, Double yMin, Double yMax) {
		Poligono2D r = Poligono2D.create();
		r.addVertice(Punto2D.of(xMin, yMin));
		r.addVertice(Punto2D.of(xMax, yMin));
		r.addVertice(Punto2D.of(xMax, yMax));
		r.addVertice(Punto2D.of(xMin, yMax));
		return r;
	}
	private List<Punto2D> vertices;
	
	private Poligono2D() {
		vertices = List2.empty();
	}
	
	private Poligono2D(Punto2D p1, Punto2D p2, Punto2D p3) {
		vertices = List2.empty();
		vertices.add(p1);
		vertices.add(p2);
		vertices.add(p3);
	}
	
	
	private Poligono2D(Punto2D... lp) {
		vertices = List2.empty();
		Preconditions.checkArgument(lp.length>=3);
		for(Punto2D p : lp){
			vertices.add(p);
		}
	}
	
	public void addVertice(Punto2D p){
		vertices.add(p);
	}
	
	public int getNumeroDeVertices(){
		return vertices.size();
	}

	public List<Punto2D> getVertices() {
		return Collections.unmodifiableList(vertices);
	}
	
	public Double getArea(){
		Double area = 0.;
		List<Vector2D> vectores = List2.empty();
		for(int i = 1;  i < vertices.size(); i++){
			vectores.add(vertices.get(i).minus(vertices.get(0)));
		}
		for(int i = 1;  i < vectores.size(); i++){			
			area = area + vectores.get(i-1).multiplicaVectorial(vectores.get(i));
		}
		return area/2;
	}

	
	public Poligono2D rota(Punto2D p, Double angulo) {
		List<Punto2D> nVertices = List2.empty();
		for(Punto2D v: this.vertices){
			nVertices.add(v.rota(p, angulo));
		}
		return Poligono2D.create(nVertices.toArray(new Punto2D[vertices.size()]));
	}

	
	public Poligono2D traslada(Vector2D v) {
		List<Punto2D> nVertices = List2.empty();
		for(Punto2D vt: this.vertices){
			nVertices.add(vt.traslada(v));
		}
		return Poligono2D.create(nVertices.toArray(new Punto2D[vertices.size()]));
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((vertices == null) ? 0 : vertices.hashCode());
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
		Poligono2D other = (Poligono2D) obj;
		if (vertices == null) {
			if (other.vertices != null)
				return false;
		} else if (!vertices.equals(other.vertices))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Poligono [vertices=" + vertices + "]";
	}

	
	
	
}
