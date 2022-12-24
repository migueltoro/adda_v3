package us.lsi.expression;

public class Test {

	public static void main(String[] args) {
		for(int i= 0;i<1000;i+=50){
			System.out.print(1.*(i*i*i+2*i*i+3)+",");
//			System.out.print(i*1.+",");
		}
	}

}
