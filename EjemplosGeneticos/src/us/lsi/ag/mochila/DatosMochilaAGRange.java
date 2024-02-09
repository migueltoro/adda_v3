package us.lsi.ag.mochila;

import java.util.List;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.RangeIntegerData;
import us.lsi.alg.mochila.SolucionMochila;
import us.lsi.mochila.datos.DatosMochila;



public class DatosMochilaAGRange implements RangeIntegerData<SolucionMochila> {

	
	public DatosMochilaAGRange(String fichero) {
		DatosMochila.iniDatos(fichero);
	}	
	
	private Double valor;
	private Double peso;
	private Double fitness = null;
	
	private void calcula(List<Integer> ls) {
		this.peso = 0.;
		this.valor = 0.;
		for (int i = 0; i < ls.size(); i++) {
			peso = peso + ls.get(i) * DatosMochila.getPeso(i);
			valor = valor + ls.get(i) * DatosMochila.getValor(i);
		}
	}
	
	@Override
	public Double fitnessFunction(List<Integer> dc) {
		calcula(dc);
		fitness = valor - 100*AuxiliaryAg.distanceToGeZero(DatosMochila.capacidadInicial - peso);
		return fitness;
	}

	@Override
	public Integer size() {
		return DatosMochila.getObjetos().size();
	}

	@Override
	public Integer max(Integer i) {
		return DatosMochila.getObjetos().get(i).getNumMaxDeUnidades()+1;
	}

	@Override
	public Integer min(Integer i) {
		return 0;
	}

	@Override
	public SolucionMochila solucion(List<Integer> acciones) {
		return SolucionMochila.of(acciones);
	}
	
}
