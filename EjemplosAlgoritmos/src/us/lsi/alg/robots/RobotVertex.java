package us.lsi.alg.robots;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.graphs.virtual.VirtualVertex;

public class RobotVertex implements VirtualVertex<RobotVertex,RobotEdge,Integer> {
	
	public static Integer[][] mt1 = {{4,0,0,0},{2,0,0,0},{3,14,0,0},{3,0,7,0}};
	public static Integer[][] mt2 = {{2,0,0,0},{3,0,0,0},{3,8,0,0},{3,0,12,0}};
	
	public static RobotVertex of() {
		List<Integer> ri = List.of(1,0,0,0);
		List<Integer> xi = List.of(0,0,0,0);
		return new RobotVertex(ri, xi, 0);
	}
	
	public static RobotVertex of(List<Integer> r, List<Integer> x, Integer t) {
		return new RobotVertex(r, x, t);
	}

	private List<Integer> r;
	private List<Integer> x;
	private Integer t;
	public static Integer n = 4;
	public static Integer N = 24;
	
	private RobotVertex(List<Integer> r, List<Integer> x, Integer t) {
		super();
		this.r = new ArrayList<>(r);
		this.x = new ArrayList<>(x);
		this.t = t;
	}

	public List<Integer> getR() {
		return r;
	}

	public List<Integer> getX() {
		return x;
	}

	public Integer getT() {
		return t;
	}

	public Integer mt(Integer i, Integer j) {
		return mt1[i][j];
	}
	
	public Integer greedyAction() {
		return this.actions().get(0);
	}
	
	public RobotEdge greedyEdge() {
		return this.edge(this.actions().get(0));
	}
	
	public Boolean goal() {
		return this.t == N;
	}

	@Override
	public List<Integer> actions() {
		if(this.t == N) return List.of();
		List<Integer> ls = IntStream.range(0, n).boxed()
			.filter(a->IntStream.range(0,n).allMatch(j->this.x.get(j)>=this.mt(a,j)))
			.sorted(Comparator.reverseOrder())
			.collect(Collectors.toList());
		ls.add(-1);
		return ls;
	}

	@Override
	public RobotVertex neighbor(Integer a) {
		RobotVertex v;
		List<Integer> r = new ArrayList<>(this.r);
		List<Integer> x = new ArrayList<>(this.x);
		if(a==-1) {	
			for (int j = 0; j < n; j++) {
				x.set(j,x.get(j) + r.get(j));
			}	
		} else {
			for (int j = 0; j < n; j++) {
				x.set(j,x.get(j) + r.get(j) - this.mt(a, j));
			}
			r.set(a,r.get(a) + 1);
			v = RobotVertex.of(r, x, this.t + 1);
		}
		v = RobotVertex.of(r, x, this.t + 1);
		return v;
	}

	@Override
	public RobotEdge edge(Integer a) {
		return RobotEdge.of(this,this.neighbor(a), a);
	}

	@Override
	public String toString() {
		return this.r + "," + this.x + "," + this.t;
	}

	@Override
	public int hashCode() {
		return Objects.hash(r, x);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RobotVertex other = (RobotVertex) obj;
		return Objects.equals(r, other.r) && Objects.equals(x, other.x);
	}

	
}
