package us.lsi.ag.agchromosomes;

import java.util.ArrayList;
import java.util.List;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.BlocksData;

public class BlocksValues<S> implements ChromosomeValues<List<Integer>, List<Double>, S> {

	public static <S> BlocksValues<S> of(BlocksData<S> data) {
        return new BlocksValues<S>(data);
    }

    private BlocksData<S> data;

    private BlocksValues(BlocksData<S> data) {
        this.data = data;
    }
    
    @Override
    public BlocksData<S> data() {
        return data;
    }

    @Override
    public List<Integer> decodeValues(List<Double> r) {
    	List<Integer> s = new ArrayList<>();
		List<Integer> p = data.blocksLimits();
		Integer pn = p.size();
		for(int i=0; i<pn-1;i++) {
			List<Double> rp = r.subList(p.get(i),p.get(i+1));
			List<Integer> values = data.initialValues().subList(p.get(i),p.get(i+1));
			List<Integer> v = AuxiliaryAg.convert(rp,values);			
			s.addAll(v);			
		}
		return s;
    }

    @Override
    public Integer dimension() {
        return data.size();
    }
}
