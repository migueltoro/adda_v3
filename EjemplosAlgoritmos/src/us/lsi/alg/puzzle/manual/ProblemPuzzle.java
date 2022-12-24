package us.lsi.alg.puzzle.manual;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.alg.puzzle.VertexPuzzle;
import us.lsi.common.Arrays2;
import us.lsi.common.IntPair;
import us.lsi.common.Preconditions;
import us.lsi.streams.Stream2;

public record ProblemPuzzle(IntPair blackPosition,Integer[][] datos) {
	
	
	public static Integer numFilas = 3;
	public static Integer n = numFilas;
	
	public static ProblemPuzzle of(Integer[][] datos, IntPair blackPosition) {
		Integer[][] dt = Arrays2.copyArray(datos);
		ProblemPuzzle r = new ProblemPuzzle(blackPosition,dt);
		Preconditions.checkArgument(r.isValid(),"No es válido");
		return r;
	}
	
	public static ProblemPuzzle of(Integer... d) {	
		Integer dt[][] = Arrays2.toMultiArray(d, VertexPuzzle.n, VertexPuzzle.n);	
		IntPair bp = Arrays2.findPosition(dt, e->e==0);
		Preconditions.checkArgument(isValid(dt),"No es valido");
		return ProblemPuzzle.of(dt,bp);
	}
	
	private static Boolean isValid(Integer[][] datos) {
		Set<Integer> s = Arrays.stream(datos)
				.flatMap(f->Arrays.stream(f))
				.filter(e->ProblemPuzzle.validDato(e))
				.collect(Collectors.toSet());
		return s.size()== n*n;
	}
	
	public Integer getDato(IntPair p) {
		Preconditions.checkArgument(validPosition(p),"No se cumple la precondición");
		return datos[p.first()][p.second()];
	}
	
	public Boolean isValid() {
		return isValid(this.datos()) && getDato(blackPosition()) == 0;
	}
	
	private static boolean validDato(Integer d) {
		return 0<=d && d < ProblemPuzzle.numFilas*ProblemPuzzle.numFilas;
	}
	
	public boolean validPosition(IntPair p) {
		return p.first()>=0 && p.first()< ProblemPuzzle.numFilas && p.second()>=0 && 
				p.second()<ProblemPuzzle.numFilas;
	}
	
	public Integer getNumDiferentes(ProblemPuzzle e){
		Integer n = ProblemPuzzle.numFilas;
		Long s = IntStream.range(0,n).boxed()
				.flatMap(f->IntStream.range(0,n).boxed().map(c->IntPair.of(f, c)))
				.filter(p->this.getDato(p) != e.getDato(p))
				.count();
		return s.intValue();
	}
	
	public Map<Integer,IntPair> positions(){
		return Stream2.allPairs(0,VertexPuzzle.n,0,VertexPuzzle.n)
				.collect(Collectors.toMap(p->datos()[p.first()][p.second()],p->p));
	}
	
	public List<ActionPuzzle> actions() {
    	return ActionPuzzle.actions.stream()
				.filter(a->a.isApplicable(this))
				.collect(Collectors.toList());
	}
	
	public ProblemPuzzle neighbor(ActionPuzzle a) {
		Preconditions.checkArgument(a.isApplicable(this), String.format("La acción %s no es aplicable",a.toString()));
		IntPair op = this.blackPosition();
		IntPair np = op.add(a.direction());
//		System.out.printf("\n%s,%s,%s\n",op,a,np);
		Integer dd[][] = Arrays2.copyArray(this.datos());
		Integer value = dd[np.first()][np.second()];
		dd[op.first()][op.second()] = value;
		dd[np.first()][np.second()] = 0;
		ProblemPuzzle v  = ProblemPuzzle.of(dd,np);
		Preconditions.checkState(!this.equals(v),String.format("No deben ser iguales %s \n %s \n %s",a.toString(),this.toString(),v.toString()));
		return v;
	}

	@Override
	public String toString() {
		String s = IntStream.range(0,ProblemPuzzle.numFilas).boxed()
				.map(y->fila(y))
				.collect(Collectors.joining("\n", "", ""));
		return s;
	}

	private String fila(int y) {
		Integer n = ProblemPuzzle.numFilas;
		return IntStream.range(0,n).boxed()
				.map(j->datos[y][j].toString()).collect(Collectors.joining("|", "|", "|"));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((blackPosition == null) ? 0 : blackPosition.hashCode());
		result = prime * result + Arrays.deepHashCode(datos);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProblemPuzzle other = (ProblemPuzzle) obj;
		if (blackPosition == null) {
			if (other.blackPosition != null)
				return false;
		} else if (!blackPosition.equals(other.blackPosition))
			return false;
		if (!Arrays.deepEquals(datos, other.datos))
			return false;
		return true;
	}
}
