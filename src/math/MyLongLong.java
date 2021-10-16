package math;

import test.MyTimer;

public class MyLongLong {
	public static void main(String[] args) {
		MyTimer timer = new MyTimer();
		LL sum = new LL(0);
		for(int i=0; i<10000000; i++) {
			addAndMutate(sum, new LL(i));
		}
		System.out.println(sum);
		timer.lap();
		
		long longSum = 0;
		for(int i=0; i<10000000; i++) {
			longSum += i;
		}
		System.out.println(longSum);
		timer.lap();
		
		long sum2 = 0;
		for(int i=Integer.MIN_VALUE; i<Integer.MAX_VALUE-100000; i+=10) {
			sum2 += castUnsigned(i);
		}
		timer.lap();
		System.out.println(sum2);
		
		LL x = new LL((2*1L<<32) + 2000000000);
		LL y = new LL((2*1L<<32) + 2000000000);
		System.out.println(x + " * " + y + " =");
		LL z = multiply(x,y);
		System.out.println(z);	
	}
	
	
	public static void addAndMutate(LL a, LL b) {
		int carry = 0;
		for(int i=0; i<len; i++) {
			long temp = 0L + (a.digits[i] + (a.digits[i] < 0 ? 1L<<32 : 0)) + (b.digits[i] + (b.digits[i] < 0 ? 1L<<32 : 0)) + carry;
			a.digits[i] = (int) temp;
			carry = (int) (temp >>> 32);
		}
	}
	public static void addIntAndMutate(LL a, int b, int digit) {
		if(b == 0) return;
		if(digit >= a.digits.length) System.out.println("error: overflow");
		
		//fast way to determine carry: if either is negative and sum is positive
		if((a.digits[digit] < 0 || b < 0) && a.digits[digit]+b >= 0) {
			addIntAndMutate(a, 1, digit+1);
		}
		a.digits[digit] += b;
	}
	
	public static LL multiply(LL a, LL b) {
		LL c = new LL(0);
		for(int i=0; i<len; i++) {
			for(int j=0; j<len; j++) {
				int cDigit = i+j;
				long temp = 1L * (a.digits[i] + (a.digits[i] < 0 ? 1L<<32 : 0)) * (b.digits[j] + (b.digits[j] < 0 ? 1L<<32 : 0));
				int val = (int) temp;
				int carry = (int) (temp >>> 32);
				addIntAndMutate(c, val, cDigit);
				addIntAndMutate(c, carry, cDigit+1);
			}
		}
		return c;
	}
	
	public static void divide(LL a, LL b) {
		
	}
	
	public static long castUnsigned(int x) {
		return (((long) x) << 32) >>> 32;
	}
	
	static final int len = 4;
	static class LL {
		int[] digits = new int[len];
		public LL(long x) {
			digits[0] = (int) x;
			digits[1] = (int) (x >>> 32);
		}
		@Override
		public String toString() {
			String s = "[";
			for(int i=len-1; i>0; i--) {
				s += castUnsigned(digits[i]) + ", ";
			}
			s += castUnsigned(digits[0]);
			s += "]";
			return s;
		}
	}
}
