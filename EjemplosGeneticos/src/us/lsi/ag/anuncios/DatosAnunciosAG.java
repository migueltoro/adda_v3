package us.lsi.ag.anuncios;


import java.util.List;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.PermutationData;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;
import us.lsi.anuncios.datos.Anuncio;
import us.lsi.anuncios.datos.DatosAnuncios;

public class DatosAnunciosAG extends DatosAnuncios implements PermutationData<SolucionAnuncios> {

	public DatosAnunciosAG(String file) {
		super();
		super.leeYOrdenaAnuncios(file);
	}	
	
	@Override
	public SolucionAnuncios solution(List<Integer> dc) {		
		return  SolucionAnuncios.of(dc);
	}
	

	@Override
	public Double fitnessFunction(List<Integer> list) {	
		SolucionAnuncios s =SolucionAnuncios.of(list);
		Double valor = s.valor;
		Double nIn = AuxiliaryAg.distanceToEqZero((double)s.numAnunciosIncompatibles);
		Double f = valor - 100000.*nIn;
		return f;
	}

	
	public List<Anuncio> getObjetos() {
		return DatosAnuncios.todosLosAnunciosDisponibles;
	}

	@Override
	public Integer size() {
		return DatosAnuncios.todosLosAnunciosDisponibles.size();
	}
	
	@Override
	public ChromosomeType type() {
		return ChromosomeType.Permutation;
	}
	
}
