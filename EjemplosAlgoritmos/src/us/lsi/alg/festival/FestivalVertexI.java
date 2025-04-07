package us.lsi.alg.festival;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import us.lsi.common.List2;

public record FestivalVertexI(Integer k, List<Integer> af, List<Integer> q) 
	implements FestivalVertex {
	
	public static FestivalVertexI of(Integer k, List<Integer> af, List<Integer> q) {
		return new FestivalVertexI(k, af, q);
	}
	
	public List<Integer> af(){
		return new ArrayList<>(this.af);
	}
	
	public List<Integer> q(){
		return new ArrayList<>(this.q);
	}
	
	@Override
	public Integer greedyAction() {
		return Math.min(af.get(this.j()), q.get(this.i()));
	}

	@Override
	public List<Integer> actions() {
		List<Integer> r = List2.rangeList(0, greedyAction()+1);
		Collections.reverse(r);
		return r;
	}

	@Override
	public FestivalVertex neighbor(Integer a) {
		List<Integer> af = List2.set(this.af, this.j(), this.af.get(this.j())-a);
		List<Integer> q = List2.set(this.q, this.i(), this.q.get(this.i())-a);
		return of(k+1, af, q);
	}
	
	@Override
	public FestivalVertex neighbor2(Integer a) {
		List<Integer> af = List2.set(this.af, this.j(), this.af.get(this.j()));
		List<Integer> q = List2.set(this.q, this.i(), this.q.get(this.i())-a);
		return of(k+1, af, q);
	}

	@Override
	public FestivalEdge edge(Integer a) {
		return FestivalEdge.of(this, this.neighbor(a), a);
	}

	@Override
	public Integer i() {
		return DatosFestival2.costes.get(k).i();
	}

	@Override
	public Integer j() {
		return DatosFestival2.costes.get(k).j();
	}

	@Override
	public Integer c() {
		return DatosFestival2.costes.get(k).c();
	}

	@Override
	public Integer n() {
		return DatosFestival2.n;
	}

	@Override
	public Integer m() {
		return DatosFestival2.m;
	}

	@Override
	public Boolean goal() {
		return DatosFestival2.costes.size() == k;
	}

	@Override
	public Boolean goalHasSolution() {
		return this.q().stream().allMatch(x->x==0);
	}
}
