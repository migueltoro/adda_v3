package us.lsi.ag.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.ag.BlocksData;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.sudoku.datos.Casilla;
import us.lsi.sudoku.datos.DatosSudoku;
import us.lsi.sudoku.datos.SolucionSudoku;

public class BlocksDatosSudokuSubCuadroAG implements BlocksData<SolucionSudoku> {
	
	public List<Integer> blocksLimits;
	public List<Integer> initialValues;
	public List<Casilla> casillasLibres;
	
	public BlocksDatosSudokuSubCuadroAG(){
		super();
//		DatosSudoku.tamSubCuadro = 3;
//		DatosSudoku.iniDatos("ficheros/sudoku.txt");
		this.initialValues = new ArrayList<Integer>();
		this.blocksLimits = new ArrayList<Integer>();
		Integer limits = 0;
		this.casillasLibres = new ArrayList<Casilla>();
		Collections.sort(DatosSudoku.casillasLibres,Comparator.comparing(c->c.getSubCuadro()));
		this.blocksLimits.add(0);
		Set<Integer> s0 = new HashSet<>();
		Integer lastSubCuadro = DatosSudoku.casillasLibres.get(0).getSubCuadro();
		for(Casilla c:DatosSudoku.casillasLibres) {		
			if (c.getSubCuadro() != lastSubCuadro) {				
				if (s0.size() > 0) {
					limits += s0.size();
					this.blocksLimits.add(limits);
					this.initialValues.addAll(s0);
				} 
				s0 = new HashSet<>();
				lastSubCuadro = c.getSubCuadro();
			}
			s0.addAll(DatosSudoku.getValoresLibresEnCasilla(c));
		}
		if (s0.size() > 0) {
			limits += s0.size();
			this.blocksLimits.add(limits);
			this.initialValues.addAll(s0);
		} 
		s0 = new HashSet<>();
		this.initialValues = Collections.unmodifiableList(this.initialValues);
		this.casillasLibres = DatosSudoku.casillasLibres;
	}

	@Override
	public Integer size() {
		return this.initialValues.size();
	}

	@Override
	public Double fitnessFunction(List<Integer> cr) {
		Integer n = cr.size();
		for(int i=0; i<n;i++) {
			Integer v = cr.get(i);
			Casilla c = casillasLibres.get(i);
			c.setValue(v);
		}
		SolucionSudoku s = SolucionSudoku.of();
		return -(double) s.getErrores();
	}

	@Override
	public SolucionSudoku solucion(List<Integer> cr) {
		Integer n = cr.size();
		for(int i=0; i<n;i++) {
			Integer v = cr.get(i);
			Casilla c = casillasLibres.get(i);
			c.setValue(v);
		}
		SolucionSudoku s = SolucionSudoku.of();
		return s;
	}

	@Override
	public List<Integer> blocksLimits() {
		return blocksLimits;
	}

	@Override
	public List<Integer> initialValues() {
		return initialValues;
	}


}
