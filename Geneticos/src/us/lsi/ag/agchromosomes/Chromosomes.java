package us.lsi.ag.agchromosomes;


import java.util.List;

import us.lsi.ag.BinaryData;
import us.lsi.ag.BlocksData;
import us.lsi.ag.ExpressionData;
import us.lsi.ag.InSetData;
import us.lsi.ag.PermutationData;
import us.lsi.ag.RangeDoubleData;
import us.lsi.ag.RangeIntegerData;
import us.lsi.tiposrecursivos.ast.Exp;

public class Chromosomes {
	
	public enum ChromosomeType {Binary,RangeInteger,RangeDouble,InSet,Permutation,PermutationSubList,PermutationPrefix,Blocks,Expression}
	
	public static <S> AChromosome<List<Integer>,List<Integer>,S> ofBinary(BinaryData<S> cd) {
		BinaryValues<S> rv = BinaryValues.of(cd);
		ABinaryChromosome.iniValues(rv);
		return ABinaryChromosome.getInitialChromosome();	
	}
	
	public static <S> AChromosome<List<Integer>,List<Double>,S> ofRangeInteger(RangeIntegerData<S> cd) {
		RangeIntegerValues<S> rv = RangeIntegerValues.of(cd);
		ARandomKey.iniValues(rv);
		return ARandomKey.getInitialChromosome();	
	}
	
	public static <S> AChromosome<List<Integer>,List<Double>,S> ofInset(InSetData<S> cd) {
		InSetValues<S> rv = InSetValues.of(cd);
		ARandomKey.iniValues(rv);
		return ARandomKey.getInitialChromosome();	
	}
	
	public static <S> AChromosome<List<Double>,List<Double>,S> ofRangeDouble(RangeDoubleData<S> cd) {
		RangeDoubleValues<S> rv = RangeDoubleValues.of(cd);
		ARandomKey.iniValues(rv);
		return ARandomKey.getInitialChromosome();	
	}
	
	public static <S> AChromosome<List<Integer>, List<Double>, S> ofPermutation(PermutationData<S> cd) {
		PermutationValues<S> rv = PermutationValues.of(cd);
		ARandomKey.iniValues(rv);
		return ARandomKey.getInitialChromosome();
	}
	
	public static <S> AChromosome<List<Integer>, List<Double>, S> ofPermutationSubList(PermutationData<S> cd) {
		PermutationSubListValues<S> rv = PermutationSubListValues.of(cd);
		ARandomKey.iniValues(rv);
		return ARandomKey.getInitialChromosome();
	}
	
	public static <S> AChromosome<List<Integer>, List<Double>, S> ofPermutationPrefix(PermutationData<S> cd) {
		PermutationPrefixValues<S> rv = PermutationPrefixValues.of(cd);
		ARandomKey.iniValues(rv);
		return ARandomKey.getInitialChromosome();
	}
	
	public static <S> AChromosome<List<Integer>, List<Double>, S> ofBlocks(BlocksData<S> cd) {
		BlocksValues<S> rv = BlocksValues.of(cd);
		ARandomKey.iniValues(rv);
		return ARandomKey.getInitialChromosome();
	}
	
	public static AChromosome<Exp, List<Double>, Exp> ofExp(ExpressionData cd) {
		AuxExpression.iniValues(cd);
		ExpressionValues rv = ExpressionValues.of(cd);
		ARandomKey.iniValues(rv);
		return ARandomKey.getInitialChromosome();
	}
	
	

}
