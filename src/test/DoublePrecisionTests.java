package test;

public class DoublePrecisionTests {
	public static void main(String[] args) {
		long a = 1_000_000_000_000_000_000L;
		long b = 1_000_000_000_000_000_050L;
		double x = 1.0*a;
		double y = 1.0*b;
		System.out.println(x == y);
	}
}
