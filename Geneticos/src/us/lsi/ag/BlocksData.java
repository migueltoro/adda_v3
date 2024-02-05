package us.lsi.ag;

import java.util.ArrayList;
import java.util.List;

public interface BlocksData<S> extends ChromosomeDoubleData<List<Integer>,S> {
	
	/**
	 * @return Limites de los bloques en los que se descompone el cromosoma. 
	 * Un bloque est� definido por dos valore consecutivos
	 */
	List<Integer> blocksLimits();
	
	/**
	 * @return Valores iniciales del cromosoma. 
	 * Los valores del cromosoma serán permutaciones de los valores dentro de cada bloque
	 */
	List<Integer> initialValues();


	default List<Integer> decode(List<Double> r) {
		List<Integer> s = new ArrayList<>();
		List<Integer> p = this.blocksLimits();
		Integer pn = p.size();
		for(int i=0; i<pn-1;i++) {
			List<Double> rp = r.subList(p.get(i),p.get(i+1));
			List<Integer> values = this.initialValues().subList(p.get(i),p.get(i+1));
			List<Integer> v = AuxiliaryAg.convert(rp,values);			
			s.addAll(v);			
		}
		return s;
	}	

}
