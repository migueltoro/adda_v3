package us.lsi.graphs.manual.tests;

import us.lsi.graphs.manual.EGrafo.GraphType;
import us.lsi.graphs.manual.EGrafo.TraverseType;
import us.lsi.graphs.manual.recorridos.RecorridoAStar;
import us.lsi.graphs.manual.recorridos.RecorridoEnAnchura;
import us.lsi.graphs.manual.recorridos.RecorridoEnProfundidadPostorden;
import us.lsi.graphs.manual.recorridos.RecorridoEnProfundidadPreorden;
import us.lsi.graphs.manual.recorridos.RecorridoOrdenado;
import us.lsi.graphs.manual.recorridos.RecorridoTopologico;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;

public class Recorridos {

	
	public static void main(String[] args) {
		Mapa m = Mapa.parse("ficheros/ciudades_2.txt", 
				"ficheros/carreteras_2.txt", 
				GraphType.UNDIRECTED,
				TraverseType.FORWARD);
		System.out.println(m);
		System.out.println(m.successors(m.ciudad("VNFLN")));
		Ciudad c1 = m.ciudad("VNFLN");
		Ciudad c2 = m.ciudad("fCYqE");
		RecorridoEnAnchura<Ciudad,Carretera> ra = RecorridoEnAnchura.of(m);
		ra.traverseAll();
		System.out.println(ra.groups().size());
		System.out.println(ra.groups().keySet());
		System.out.println("Anchura = "+ra.path());
		System.out.println(ra.pathExist(c1, c2));
		RecorridoEnProfundidadPreorden<Ciudad,Carretera> rpp = RecorridoEnProfundidadPreorden.of(m);
		rpp.traverseAll();
		System.out.println(rpp.groups().size());
		System.out.println(rpp.groups().keySet());
		System.out.println("Preorden = "+rpp.path());
		System.out.println(rpp.pathExist(c1, c2));
		RecorridoEnProfundidadPostorden<Ciudad,Carretera> rpo = RecorridoEnProfundidadPostorden.of(m);
		rpo.traverseAll();
		System.out.println(rpo.groups().size());
		System.out.println(rpo.groups().keySet());
		System.out.println("Postorden = "+rpo.path());
		System.out.println(rpo.pathExist(c1, c2));
		RecorridoTopologico<Ciudad,Carretera> rt = RecorridoTopologico.of(m);
		rt.traverseAll();
		System.out.println(rt.groups().size());
		System.out.println(rt.groups().keySet());
		System.out.println("Topologico = "+rpo.path());
		System.out.println(rpo.pathExist(c1, c2));
		RecorridoOrdenado<Ciudad,Carretera> ro = RecorridoOrdenado.of(m);
		ro.traverse(c1);
		System.out.println(ro.pathExist(c1, c2));
		System.out.println("Ordenado = "+ro.path());
		System.out.println(ro.pathFromOrigin(c2));
		RecorridoAStar<Ciudad,Carretera> roa = RecorridoAStar.of(m,(x,y,z)->0.,c2,(x)->x.equals(c2));
		roa.traverse(c1);
		System.out.println("AStar = "+roa.path());
		System.out.println(roa.pathFromOrigin(c2));
		System.out.println(roa.pathWeight(c2));
	}

}
