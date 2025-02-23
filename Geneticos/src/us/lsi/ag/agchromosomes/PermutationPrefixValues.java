package us.lsi.ag.agchromosomes;

import java.util.List;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.PermutationData;

public class PermutationPrefixValues<S> implements ChromosomeValues<List<Integer>, List<Double>, S> {
	
	public static <S> PermutationPrefixValues<S> of(PermutationData<S> data) {
        return new PermutationPrefixValues<S>(data);
    }

    private PermutationData<S> data;

    private PermutationPrefixValues(PermutationData<S> data) {
        this.data = data;
    }
    
    @Override
    public PermutationData<S> data() {
        return data;
    }

	@Override
    public List<Integer> decodeValues(List<Double> ls) {
    	List<Integer> la = AuxiliaryAg.convert(ls.subList(0,ls.size()-1),data.normalSequence());  
    	Integer n= AuxiliaryAg.convert(ls.get(ls.size()-1),0,ls.size()-1);	
    	System.out.println("=== "+n);
    	return la.subList(0,n);
    }

    @Override
    public Integer dimension() {
        return data.normalSequence().size()+1;
    }

}
