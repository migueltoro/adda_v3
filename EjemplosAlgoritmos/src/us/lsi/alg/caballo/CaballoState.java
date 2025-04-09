package us.lsi.alg.caballo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CaballoState {
	
		private List<CaballoVertex> vertices;
		private Set<CaballoVertex> sVertices;
		private List<CaballoAction> acciones;

		private CaballoState(List<CaballoVertex> vertices) {
			super();
			this.vertices = vertices;
			this.acciones = new ArrayList<>();
			this.sVertices = new HashSet<>(vertices);
		}

		public static CaballoState of(CaballoVertex vertex) {
			List<CaballoVertex> vt = new ArrayList<>();
			vt.add(vertex);
			return new CaballoState(vt);
		}

		void forward(CaballoAction a) {
			this.acciones.add(a);
			CaballoVertex vcn = this.vertice().neighbor(a);
			this.vertices.add(vcn);
			this.sVertices.add(vcn);
		}

		void back(CaballoAction a) {
			this.acciones.remove(this.acciones.size() - 1);
			CaballoVertex v = this.vertices.remove(this.vertices.size() - 1);
			this.sVertices.remove(v);
		}

		public CaballoVertex vertice() {
			return this.vertices.get(vertices.size() - 1);
		}
		
		public Boolean contains(CaballoVertex v) {
			return this.sVertices.contains(v);
		}

		public List<CaballoVertex> vertices() {
			return this.vertices;
		}
		
		public Integer nv() {
			return this.sVertices.size();
		}
  
		public static void main(String[] args) {
			CaballoState st = CaballoState.of(CaballoVertex.initial(2,2));
			System.out.println(st.vertices());
			st.forward(CaballoAction.DOWN_RIGHT);
			System.out.println(st.vertices());
			System.out.println(st.vertice().actions());
			
		}
}
