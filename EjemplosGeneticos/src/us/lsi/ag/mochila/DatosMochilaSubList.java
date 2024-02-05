package us.lsi.ag.mochila;

import java.util.List;

import us.lsi.ag.PermutationData;
import us.lsi.ag.AuxiliaryAg;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

public class DatosMochilaSubList implements PermutationData<SolucionMochila> {

	public DatosMochilaSubList(String fichero) {
		DatosMochila.iniDatos(fichero);
	}
	
	private Double valor;
	private Double peso;
	private Double fitness = null;
	
	private void calcula(List<Integer> ls) {
		this.peso = 0.;
		this.valor = 0.;
		for (int i = 0; i < ls.size(); i++) {
			peso = peso + DatosMochila.getPeso(ls.get(i));
			valor = valor + DatosMochila.getValor(ls.get(i));
		}
	}

	@Override
	public Double fitnessFunction(List<Integer> ls) {
		calcula(ls);
		fitness = valor - 10*AuxiliaryAg.distanceToGeZero(DatosMochila.capacidadInicial - peso);
		return fitness;
	}

	@Override
	public SolucionMochila solucion(List<Integer> cr) {
		SolucionMochila s = SolucionMochila.empty();
		for (int i=0; i< cr.size();i++) {
			s.add(DatosMochila.getObjeto(cr.get(i)),1);
		}
		return s;
	}
	
	@Override
	public Integer maxMultiplicity(int index) {
		return DatosMochila.getObjetos().get(index).getNumMaxDeUnidades();
	}

	@Override
	public Integer itemsNumber() {
		return DatosMochila.getObjetos().size();
	}
}
