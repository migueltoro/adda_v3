package us.lsi.alg.caballo;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record CaballoEdge(CaballoVertex source, CaballoVertex target, CaballoAction action, Double weight) 
	implements SimpleEdgeAction<CaballoVertex, CaballoAction> {

    public static CaballoEdge of(CaballoVertex c1, CaballoVertex c2, CaballoAction action) {
		return new CaballoEdge(c1, c2, action, 1.);
	}
}
