package us.lsi.ag.agchromosomes;

import us.lsi.ag.ChromosomeData;

public interface ChromosomeValues<V,G,S> {
	
	V decodeValues(G g);
	Integer dimension();
	ChromosomeData<V,S> data();

}
