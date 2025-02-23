package us.lsi.ag.agchromosomes;

import java.util.List;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.PermutationData;

public class PermutationValues<S> implements ChromosomeValues<List<Integer>, List<Double>, S> {
	
	public static <S> PermutationValues<S> of(PermutationData<S> data) {
        return new PermutationValues<S>(data);
    }

    private PermutationData<S> data;

    PermutationValues(PermutationData<S> data) {
        this.data = data;
    }
    
    @Override
    public PermutationData<S> data() {
        return data;
    }

    @Override
    public List<Integer> decodeValues(List<Double> ls) {
    	return AuxiliaryAg.convert(ls,data.normalSequence());
    }

    @Override
    public Integer dimension() {
        return data.normalSequence().size();
    }

}
