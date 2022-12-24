package us.lsi.alg.reinas.manual;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.common.IntegerSet;

public record ReinasProblem(Integer index, List<Integer> fo, IntegerSet dpo, IntegerSet dso) {
	
	public static Integer n = 8;
	
	public static ReinasProblem of(Integer index, List<Integer> fo, IntegerSet dpo, IntegerSet dso) {
		return new ReinasProblem(index, fo, dpo, dso);
	}

	public static ReinasProblem first() {
		return new ReinasProblem(0,List2.empty(),IntegerSet.empty(-n,10),IntegerSet.empty());
	}

	public static Predicate<ReinasProblem> goal() {
		return v->v.index == ReinasProblem.n;
	}
	
	public List<Integer> acciones() {
		List<Integer> r = IntStream.range(0,ReinasProblem.n).boxed()
				.filter(e->!this.fo.contains(e) && !this.dpo.contains(e-this.index) && !this.dso.contains(e+this.index))
				.collect(Collectors.toList());
		return r;
	}
	
	public ReinasProblem vecino(Integer a) {
		Integer index = this.index+1;
		List<Integer> fo = new ArrayList<>(this.fo); fo.add(a);
		IntegerSet dpo = this.dpo.addNew(a-this.index);
		IntegerSet dso = this.dso.addNew(a+this.index);
		return ReinasProblem.of(index, fo, dpo, dso);
	}
	
	public Integer size() {
		return ReinasProblem.n-this.index();
	}

}
