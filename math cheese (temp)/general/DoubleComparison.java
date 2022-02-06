package general;

public class DoubleComparison {
	public static void main(String[] args) {
		
	}
	
	static final int epsilonUlps = 10;
	public static boolean doubleEquals(double a, double b) {
		return Math.abs(a-b) < epsilonUlps * Math.ulp(a);
	}
}
