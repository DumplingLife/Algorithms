//division unfinished
//nothing is really thouroughly tested, only tested 2-digit stuff

package math;

import test.MyTimer;

public class MyLongLong {
	public static void main(String[] args) {
		LL a = new LL((1L<<32) + 100);
		LL b = new LL(200);
		subtractAndMutate(a,b);
		System.out.println(a);
		
		//multiplication correctness test
		/*
		long x = (int) (Integer.MAX_VALUE * Math.random());
		long y = (int) (Integer.MAX_VALUE * Math.random());
		LL xLL = new LL(x);
		LL yLL = new LL(y);
		LL zLL = multiply(xLL,yLL);
		long zLLLong = (castUnsigned(zLL.digits[1]) << 32) + castUnsigned(zLL.digits[0]);
		if(zLLLong != x*y) System.out.println("error");
		*/
		
		//comparison correctness test
		/*
		LL x = new LL((1<<31) - 1);
		LL y = new LL(182937409019L);
		System.out.println(compare(x,y));
		*/
	}
	
	
	public static void addAndMutate(LL a, LL b) {
		int carry = 0;
		for(int i=0; i<len; i++) {
			long temp = 0L + castUnsigned(a.digits[i]) + castUnsigned(b.digits[i]) + carry;
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
	
	public static void subtractAndMutate(LL a, LL b) {
		int borrow = 0;
		for(int i=0; i<len; i++) {
			if(Integer.compareUnsigned(a.digits[i], b.digits[i]) == -1) borrow(a, i+1);
			a.digits[i] -= b.digits[i];
		}
	}
	public static void borrow(LL a, int digit) {
		if(digit >= a.digits.length) System.out.println("error: negative");
		if(a.digits[digit] != 0) a.digits[digit]--;
		else {
			a.digits[digit]--;
			borrow(a, digit+1);
		}
	}
	
	public static LL multiply(LL a, LL b) {
		LL c = new LL(0);
		for(int i=0; i<len; i++) {
			for(int j=0; j<len; j++) {
				int cDigit = i+j;
				long temp = 1L * castUnsigned(a.digits[i]) * castUnsigned(b.digits[j]);
				int val = (int) temp;
				int carry = (int) (temp >>> 32);
				addIntAndMutate(c, val, cDigit);
				addIntAndMutate(c, carry, cDigit+1);
			}
		}
		return c;
	}
	
	public static void divideAndMutate(LL R, LL D) {
		//TODO: normalization
		long d = 0;
		for(int digit : D.digits) {
			if(digit != 0) {
				d = castUnsigned(digit);
				break;
			}
		}
		for(int i=len; i>=1; i--) {
			long r = (i==len ? 0 : (R.digits[i] << 32)) + R.digits[i-1];
			int q = (int) (r/d);
			subtractAndMutate(R, multiply(new LL(q), D));
			
		}
	}
	
	public static int compare(LL a, LL b) {
		for(int i=len-1; i>=0; i--) {
			if(a.digits[i] + (1<<31) > b.digits[i] + (1<<31)) return 1;
			if(a.digits[i] + (1<<31) < b.digits[i] + (1<<31)) return -1;
		}
		return 0;
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
