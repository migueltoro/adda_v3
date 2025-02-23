package us.lsi.ag.agchromosomes;

import java.util.List;
import java.util.stream.IntStream;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.RangeDoubleData;

public class RangeDoubleValues<S> implements ChromosomeValues<List<Double>, List<Double>, S> {
	
	public static <S> RangeDoubleValues<S> of(RangeDoubleData<S> data) {
		return new RangeDoubleValues<S>(data);
	}

	private RangeDoubleData<S> data;

	RangeDoubleValues(RangeDoubleData<S> data) {
		this.data = data;
	}
	
	@Override
	public RangeDoubleData<S> data() {
		return data;
	}

	@Override
	public List<Double> decodeValues(List<Double> ls) {
		return IntStream.range(0, ls.size()).boxed()
				.map(i -> AuxiliaryAg.convert(ls.get(i), data.min(i), data.max(i))).toList();
	}

	@Override
	public Integer dimension() {
		return data.size();
	}
	
}

