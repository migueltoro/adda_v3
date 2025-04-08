package us.lsi.alg.caballo;

import java.util.ArrayList;
import java.util.List;
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
    
    public static Integer n = 4;

    @Override
    public List<CaballoAction> actions() {
        List<CaballoAction> moves = new ArrayList<>();
        for (CaballoAction move : CaballoAction.values()) {
            CaballoVertex nextPos = move.move(this);
            if (isValidPosition(nextPos)) {
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
    public Boolean goal() {
        return false; // No specific goal, we want to visit all positions
    }

    @Override
    public Boolean goalHasSolution() {
        return true; // Not used in this context
    }

    private boolean isValidPosition(CaballoVertex pos) {
        return pos.row >= 0 && pos.row < 8 && pos.col >= 0 && pos.col < 8;
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
