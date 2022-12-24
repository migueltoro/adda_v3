package us.lsi.geometria;


public interface ObjetoGeometrico2D {
	ObjetoGeometrico2D rota(Punto2D p, Double angulo);
	ObjetoGeometrico2D traslada(Vector2D v);
}
