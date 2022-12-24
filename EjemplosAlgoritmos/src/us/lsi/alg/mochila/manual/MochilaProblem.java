package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.common.Preconditions;

public record MochilaProblem(Integer index, Integer capacidadRestante) {
	
	public static MochilaProblem of(Integer index, Integer capacidadRestante) {
		Preconditions.checkArgument(index>=0 && index<= DatosMochila.n && capacidadRestante>=0, 
				String.format("index = %d, capacidadRestante = %d", index,capacidadRestante));
		return new MochilaProblem(index, capacidadRestante);
	}
	
	public MochilaProblem vecino(Integer a) {
		MochilaProblem r;
		if (this.capacidadRestante == 0.) r = MochilaProblem.of(DatosMochila.n, 0);
		else r = MochilaProblem.of(this.index()+1,this.capacidadRestante() - a * DatosMochila.peso(this.index()));
		return r;
	}
	
	public Integer greedyAction() {
		return Math.min(this.capacidadRestante/DatosMochila.peso(this.index()),DatosMochila.numMaxDeUnidades(this.index()));
	}
	

	public List<Integer> acciones() {
		if(this.index() == DatosMochila.n) return new ArrayList<>();
		Integer nu = this.greedyAction();
		if(this.index() == DatosMochila.n-1) return List2.of(nu);
		List<Integer> alternativas = IntStream.rangeClosed(0,nu)
				.boxed()
				.collect(Collectors.toList());
		Collections.reverse(alternativas);
		return alternativas;
	}

}
