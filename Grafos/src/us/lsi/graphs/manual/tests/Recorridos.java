package us.lsi.graphs.manual.tests;

import us.lsi.graphs.manual.EGrafo.GraphType;
import us.lsi.graphs.manual.EGrafo.TraverseType;
import us.lsi.graphs.manual.recorridos.RecorridoEnAnchura;
import us.lsi.graphs.manual.recorridos.RecorridoEnProfundidadPostorden;
import us.lsi.graphs.manual.recorridos.RecorridoEnProfundidadPreorden;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;

public class Recorridos {

	public static void main(String[] args) {
		Mapa m = Mapa.parse("ficheros/ciudades.txt", 
				"ficheros/carreteras.txt", 
				GraphType.UNDIRECTED,
				TraverseType.FORWARD);
		
		RecorridoEnAnchura<Ciudad,Carretera> ra = RecorridoEnAnchura.of(m);
		ra.traverseAll();
		System.out.println(ra.groups().size());
		System.out.println(ra.groups().keySet());
		System.out.println(ra.path());
		System.out.println(ra.pathExist(m.ciudad("Almeria"), m.ciudad("Cordoba")));
		RecorridoEnProfundidadPreorden<Ciudad,Carretera> rpp = RecorridoEnProfundidadPreorden.of(m);
		rpp.traverseAll();
		System.out.println(rpp.groups().size());
		System.out.println(rpp.groups().keySet());
		System.out.println(rpp.path());
		System.out.println(rpp.pathExist(m.ciudad("Almeria"), m.ciudad("Cordoba")));
		RecorridoEnProfundidadPostorden<Ciudad,Carretera> rpo = RecorridoEnProfundidadPostorden.of(m);
		rpo.traverseAll();
		System.out.println(rpo.groups().size());
		System.out.println(rpo.groups().keySet());
		System.out.println(rpo.path());
		System.out.println(rpo.pathExist(m.ciudad("Almeria"), m.ciudad("Cordoba")));
	}

}
