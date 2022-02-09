package temp.practiceaime;

import java.math.BigInteger;

public class problem12 {
	public static void main(String[] args) {
		BigInteger a = BigInteger.valueOf(149);
		long n = 378157500;
		long m = 3L*3*3*5*5*5*5*5*7*7*7*7*7*7*7;
		a = a.modPow(BigInteger.valueOf(n), BigInteger.valueOf(m));
		BigInteger b = BigInteger.valueOf(2);
		b = b.modPow(BigInteger.valueOf(n), BigInteger.valueOf(m));
		a = a.subtract(b);
		System.out.println(a.longValue());
	}
}
