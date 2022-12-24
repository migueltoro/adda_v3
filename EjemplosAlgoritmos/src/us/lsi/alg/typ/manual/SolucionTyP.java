package us.lsi.alg.typ.manual;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.alg.typ.manual.DatosTyP.Tarea;
import us.lsi.common.IntPair;

	
public record SolucionTyP(Integer maxCarga,Integer npMin, Integer npMax, Map<Integer, List<Tarea>> carga) {
		
		public static SolucionTyP of(Integer maxCarga, Integer npMin, Integer npMax,Map<Integer, List<Tarea>> carga) {
			return new SolucionTyP(maxCarga,npMin,npMax,carga);
		}	
		
		public static SolucionTyP of(TyPProblem v1, List<Integer> acciones) {
			TyPProblem v = v1;
			for(Integer a: acciones) {
				v = v.vecino(a);
			}
			Map<Integer, List<Tarea>> carga = 
					IntStream.range(0, acciones.size())
					.boxed()
					.map(t -> IntPair.of(t,acciones.get(t)))
				    .collect(Collectors.groupingBy(p -> p.second(),
							Collectors.mapping(p -> DatosTyP.tareas.get(p.first()), Collectors.toList())));
			return SolucionTyP.of(v.maxCarga(), v.npMin(), v.npMax(),carga);
		}

	}