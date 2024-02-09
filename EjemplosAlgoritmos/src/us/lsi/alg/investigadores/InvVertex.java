package us.lsi.alg.investigadores;

import java.util.List;

import us.lsi.alg.investigadores.DatosInv.VertexType;
import us.lsi.common.IntPair;
import us.lsi.graphs.virtual.VirtualVertex;

public interface InvVertex extends VirtualVertex<InvVertex, InvEdge, Integer>{

	/**
	 * index: indice que recorre los pares (i,j) desde 0 hasta n*m
	 * i = this.index()%DatosInvestigadores.n;
	 * j = this.index()/DatosInvestigadores.n;
	 * k = DatosInvestigadores.getEspecialidadDeInvestigador(this.i());
	 * p = IntPair.of(this.j(),k);
	 * ldIr: diasInvestigadorRestantes: dias restantes de cada trabajador, dIr.get(i) los restantes del trabajador i
	 * tA: El trabajo estaAcabado[j] is true si el trabajo j está acabado
	 * ldEr: diasEspecialidadRestantes[j,k] dias que faltan dedicar al trabajo j de la especialidad k
	 *
	 */
	
	public static InvVertex first() {
		return InvVertexI.of(0, DatosInv.ldid, DatosInv.ldEn);
	}
	
	public static InvVertex of(Integer index, List<Integer> ldIr, List<List<Integer>> ldEr) {
	   return new InvVertexI(index, ldIr, ldEr);
	}
	
	Integer index();
	
	List<Integer> ldIr();
	
	List<List<Integer>> ldEr();
	
	Integer i();

	IntPair pjk();

	IntPair pij();

	Integer esp(Integer i);

	/**
	 * @return Lista de dias disponibles restantes de cada una de las especialidades
	 */
	List<Integer> dEr();

	/**
	 * @param j El trabajo j
	 * @return Si al trabajo j se le han enpezado a asignar investigadores
	 */
	Boolean esTrabajoIniciado(Integer j);

	/**
	 * @param j El trabajo j
	 * @return Si el trabajo j esta completo de investigadores
	 */
	Boolean esTrabajoAcabado(Integer j);

	/**
	 * @param j El trabajo j
	 * @return Si el trabajo j se puede terminar dados las horas de investigadores restantes por especialidades
	 */
	Boolean esTrabajoTerminable(Integer j);

	DatosInv.VertexType tipo(Integer j);

	List<VertexType> tipos();

	Integer mayorAccionPosible();

	Integer fo();

}