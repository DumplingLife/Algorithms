package algebra;

public class Quadratic {
	public static void main(String[] args) {
		for(int b=-10000; b<10000; b++) {
			if(countNumSols(2,b,5) == 0) System.out.println(b);;
		}
		
	}
	
	
	public static int countNumSols(int a, int b, int c) {
		int discriminant = b*b - 4*a*c;
		if(discriminant < 0) return 0;
		else if(discriminant == 0) return 1;
		else return 2;
	}
	
	public static int countNumSols(double a, double b, double c) {
		double discriminant = b*b - 4*a*c;
		//if discriminant = 0, with error
		if(Math.abs(discriminant) < 0.001) return 1;
		else if(discriminant < 0) return 0;
		else return 2;
	}
}
