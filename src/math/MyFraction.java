package math;

public class MyFraction {
	public static void main(String[] args) {
		Fraction a = new Fraction(10,12);
		Fraction b = new Fraction(5,6);
	}
	
	public static Fraction add(Fraction a, Fraction b) {
		return new Fraction(a.n*b.d + a.d*b.n, a.d*b.d);
	}
	public static Fraction multiply(Fraction a, Fraction b) {
		return new Fraction(a.n * b.n, a.d * b.d); 
	}
	
	static final double epsilon = 1e-6;
	public static boolean equalFractions(Fraction a, Fraction b) {
		//using doubles with epsilon here is ok
		double aEstimate = 1.0*a.n/a.d;
		double bEstimate = 1.0*b.n/b.d;
		return Math.abs(aEstimate-bEstimate) < epsilon;
	}
	
	static class Fraction {
		long n;
		long d;
		public Fraction(long n, long d) {
			this.n = n;
			this.d = d;
		}
	}
}
