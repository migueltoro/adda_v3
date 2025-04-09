package us.lsi.alg.caballo;

import java.util.List;


public class CaballoBT {
	
	public static CaballoBT of() {
		return new CaballoBT();
	}
	
	private CaballoVertex start;
	private CaballoState estado;
	public static CaballoSolution solution = null;
	public static boolean stop = false;
	
	private CaballoBT() {
		this.start = CaballoVertex.initial0();
		this.estado = CaballoState.of(start);
	}

	public void btm(CaballoVertex start) {
		this.start = start;
		this.estado = CaballoState.of(start);
		btm();
	}

	private void btm() {
		if(this.estado.nv().equals(CaballoVertex.n*CaballoVertex.n)) {
			CaballoBT.solution = CaballoSolution.of(this.estado.vertices());
			CaballoBT.stop = true;
		} else {
			List<CaballoAction> alternativas = this.estado.vertice().actions();
			for(CaballoAction a:alternativas) {	
				if(CaballoBT.stop) break;
				CaballoVertex nb = this.estado.vertice().neighbor(a);
				if(this.estado.vertices().contains(nb)) continue;
				this.estado.forward(a);
				btm();  
				this.estado.back(a);
			}
		}
	}
	
	
	public static void main(String[] args) {
		CaballoBT.of().btm(CaballoVertex.initial0());
		System.out.println(CaballoBT.solution);
		System.out.println(CaballoBT.solution.position(1, 1));
	}

}
