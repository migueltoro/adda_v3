package us.lsi.alg.caballo;

import java.util.ArrayList;
import java.util.List;

import us.lsi.common.IntPair;
import us.lsi.graphs.virtual.VirtualVertex;

public record CaballoVertex(Integer row, Integer col) 
	implements VirtualVertex<CaballoVertex, CaballoEdge, CaballoAction> {

    public static CaballoVertex of(int row, int col) {
		return new CaballoVertex(row, col);
	}
    
    public static CaballoVertex initial(int row, int col) {
		return new CaballoVertex(row, col);
	}
    
    public static CaballoVertex initial0() {
		return new CaballoVertex(0, 0);
	}
    
    public static Integer n = 6; // tamaño del tablero de ajedrez, 7x7;

    @Override
    public List<CaballoAction> actions() {
        List<CaballoAction> moves = new ArrayList<>();
        for (CaballoAction move : CaballoAction.values()) {
            CaballoVertex nextPos = move.move(this);
            if (nextPos.isValid()) {
                moves.add(move);
            }
        }
        return moves;
    }

    @Override
    public CaballoVertex neighbor(CaballoAction move) {
        return move.move(this);
    }

    @Override
    public CaballoEdge edge(CaballoAction move) {
        CaballoVertex neighbor = neighbor(move);
        return CaballoEdge.of(this, neighbor, move);
    }

    @Override
    public Boolean isValid() {
        return this.row >= 0 && this.row < CaballoVertex.n && this.col >= 0 && this.col < CaballoVertex.n;
    }
    
	public IntPair position() {
		return IntPair.of(row, col);
	}

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
