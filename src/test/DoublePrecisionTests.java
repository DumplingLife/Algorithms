package test;

public class DoublePrecisionTests {
	public static void main(String[] args) {
		long a = 1_000_000_000_000_000_000L;
		long b = 1_000_000_000_000_000_050L;
		double x = a;
		double y = b;
		System.out.println(x == y);
	}
}
