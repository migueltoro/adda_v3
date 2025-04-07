package us.lsi.alg.festival;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record FestivalEdge(FestivalVertex source,FestivalVertex target,Integer action, Double weight)
      implements SimpleEdgeAction<FestivalVertex, Integer>{
	
	public static FestivalEdge of(FestivalVertex source, FestivalVertex target, Integer action) {	
		return new FestivalEdge(source, target, action, 1.*source.c() * action);
	}
}

