package us.lsi.ag.test;

import java.util.List;

import us.lsi.ag.BinaryData;
import us.lsi.ag.PermutationData;
import us.lsi.ag.RangeDoubleData;
import us.lsi.ag.RangeIntegerData;
import us.lsi.ag.agchromosomes.AChromosome;
import us.lsi.ag.agchromosomes.Chromosomes;
import us.lsi.ag.agchromosomes.Chromosomes.ChromosomeType;
import us.lsi.tiposrecursivos.ast.Exp;

public class TestChromosome {
	
    public static class PD implements PermutationData<List<Integer>> {
    	public Integer size() {
    		return 20;
    	}
    	@Override
    	public List<Integer> solution(List<Integer> ls){
    		return ls;
    	}
		@Override
		public Double fitnessFunction(List<Integer> value) {
			return value.get(0).doubleValue();
		}
		public PD() {
			
		}
		@Override
		public ChromosomeType type() {
			return ChromosomeType.Permutation;
		}
    }
    
    public static class RI implements RangeIntegerData<List<Integer>> {
		
    	public Integer size() {
			return 20;
		}

		@Override
		public List<Integer> solution(List<Integer> ls) {
			return ls;
		}

		@Override
		public Integer max(Integer i) {
			return 10;
		}

		@Override
		public Integer min(Integer i) {
			return 0;
		}

		@Override
		public Double fitnessFunction(List<Integer> value) {
			return value.get(0).doubleValue();
		}
		
		public RI() {

		}
		@Override
		public ChromosomeType type() {
			return ChromosomeType.RangeInteger;
		}
	}
    
    public static class RD implements RangeDoubleData<List<Double>> {
		
    	public Integer size() {
			return 20;
		}

		@Override
		public List<Double> solution(List<Double> ls) {
			return ls;
		}

		@Override
		public Double max(Integer i) {
			return 10.;
		}

		@Override
		public Double min(Integer i) {
			return 0.;
		}

		@Override
		public Double fitnessFunction(List<Double> value) {
			return value.get(0);
		}
		
		public RD() {

		}
		@Override
		public ChromosomeType type() {
			return ChromosomeType.RangeDouble;
		}
	}

    public static class BIN implements BinaryData<List<Integer>> {
		
    	public Integer size() {
			return 20;
		}

		@Override
		public List<Integer> solution(List<Integer> ls) {
			return ls;
		}

		@Override
		public Double fitnessFunction(List<Integer> value) {
			return value.get(0).doubleValue();
		}
		
		public BIN() {

		}

		@Override
		public ChromosomeType type() {
			return ChromosomeType.Binary;
		}
		
	}
    
    public static void test0() {
    	PermutationData<List<Integer>> data = new PD();
		AChromosome<List<Integer>, List<Double>, List<Integer>> cr0 = Chromosomes.ofPermutation(data);
		System.out.println(cr0.dimension());
		System.out.println(cr0.decode());
		for (int i = 0; i < 10; i++) {
			System.out.println("----------------------");
			AChromosome<List<Integer>, List<Double>, List<Integer>> cr1 = Chromosomes.ofPermutationSubList(data);
			System.out.println(cr1.dimension());
			System.out.println(cr1.decode());
			AChromosome<List<Integer>, List<Double>, List<Integer>> cr2 = Chromosomes.ofPermutationPrefix(data);
			System.out.println(cr2.dimension());
			System.out.println(cr2.decode());
		}
		
    }
    
    public static void test1() {
    	RangeIntegerData<List<Integer>> data0 = new RI();
		AChromosome<List<Integer>, List<Double>, List<Integer>> cr0 = Chromosomes.ofRangeInteger(data0);
		System.out.println(cr0.dimension());
		System.out.println(cr0.decode());
		BinaryData<List<Integer>> data1 = new BIN();
		AChromosome<List<Integer>, List<Integer>, List<Integer>> cr1 = Chromosomes.ofBinary(data1);
		System.out.println(cr1.dimension());
		System.out.println(cr1.decode());
		RangeDoubleData<List<Double>> data2 = new RD();
		AChromosome<List<Double>, List<Double>, List<Double>> cr2 = Chromosomes.ofRangeDouble(data2);
		System.out.println(cr2.dimension());
		System.out.println(cr2.decode());
    }
    
    public static void test2() {
    	DatosExpression d = new DatosExpression();
    	AChromosome<Exp, List<Double>, Exp> cr0 = Chromosomes.ofExp(d);
		System.out.println(cr0.dimension());
		System.out.println(cr0.decode());
    }	
	
	public static void main(String[] args) {
		test2();
	}
}
