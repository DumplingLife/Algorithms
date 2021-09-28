package math;

import test.MyTimer;

public class ModularArithmetic {
	public static void main(String[] args) {
		int M = 1000000007;
		BarrettReduction br = new BarrettReduction(1000000007);
		System.out.println(br.reduce(23));
		
		MyTimer timer = new MyTimer();
		
		int sum = 0;
		
		for(int i=0; i<1e9; i++) {
			//sum += br.reduce(i);
			sum += i % M;
			if(i%M != br.reduce(i)) {
				System.out.println(i);
			}
		}
		
		timer.lap();
		System.out.println(sum);
	}
	
	public static int MOD = 1000000007;
	
	/*
	 * add, subtract, multiply are overkill, but divide and exp are useful
	 */
	
	public static int add(long a, long b) {
		return mod(a+b);
	}
	public static int subtract(long a, long b) {
		return mod(a-b+MOD);
	}
	public static int multiply(long a, long b) {
		return mod(a*b);
	}
	public static int divide(long a, long b) {
		return multiply(a, exp(b, MOD-2));
	}
	//assume M is prime, uses exponentation by squaring
	public static long exp(long base, int exp) {
		long res = 1;
		while(exp > 0) {
			if(exp % 2 == 1) res = multiply(res, base);
			base = multiply(base,base);
			exp /= 2;
		}
		return res;
	}
	//remainder, not mod
	public static int mod(long a) {
		return (int) (a % MOD);
	}
	
	static class BarrettReduction {
		final int M;
		final long m;
		public BarrettReduction(int M) {
			m = (1l << 32)/M;
			this.M = M;
		}
		public long reduce(long a) {
			long q = a - ((a*m) >> 32) * M;
			if(M <= q) return q-M;
			return q;
		}
	}
}
