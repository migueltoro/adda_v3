package us.lsi.ag.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.ag.BlocksData;
import us.lsi.ag.agchromosomes.ChromosomeFactory;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.sudoku.datos.Casilla;
import us.lsi.sudoku.datos.DatosSudoku;
import us.lsi.sudoku.datos.SolucionSudoku;

public class BlocksDatosSudokuFilasAG implements BlocksData<SolucionSudoku>{
	
	public List<Integer> blocksLimits;
	public List<Integer> initialValues;
	public List<Casilla> casillasLibres;
	
	public BlocksDatosSudokuFilasAG(){
		super();
//		DatosSudoku.tamSubCuadro = 3;
//		DatosSudoku.iniDatos("ficheros/sudoku.txt");
		this.initialValues = new ArrayList<Integer>();
		this.blocksLimits = new ArrayList<Integer>();
		Integer limits = 0;
		this.casillasLibres = new ArrayList<Casilla>();
		this.blocksLimits.add(0);
		for(int j=8; j>=0;j--) {	
			Set<Integer> s0 = new HashSet<>();
			for(int i=0; i<9;i++) {
				Casilla c = DatosSudoku.get(i, j);			
				if(c.isFree()) {
					s0.addAll(DatosSudoku.getValoresLibresEnCasilla(c));
					this.casillasLibres.add(c);
				}
			}
			this.initialValues.addAll(s0);
			if(s0.size()>0 && j != 0) {
				limits += s0.size();
				this.blocksLimits.add(limits);
			}
		}
		this.initialValues = Collections.unmodifiableList(this.initialValues);
		this.blocksLimits.add(this.initialValues.size());
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
