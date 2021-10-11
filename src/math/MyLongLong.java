package math;

public class MyLongLong {
	public static void main(String[] args) {
		LL x = new LL(2147483649L);
		LL y = new LL(2147483649L);
		System.out.println(x + " + " + y + " =");
		addAndMutate(x,y);
		System.out.println(x);
		
		System.out.println(Long.toBinaryString(2147483648L + 2147483648L));
		System.out.println((int) 2147483649L);
	}
	
	
	//
	public static void addAndMutate(LL a, LL b) {
		int carry = 0;
		for(int i=0; i<len; i++) {
			//TODO: when cast int to long, it gets messed up if int was negative
			//(I am casting a.digits[i] and b.digits[i] to longs implicitly here
			long temp = 0L + a.digits[i] + b.digits[i] + carry;
			System.out.println(Integer.toBinaryString(a.digits[i]) + " " + Integer.toBinaryString(b.digits[i]) + " " + Long.toBinaryString(temp));
			a.digits[i] = (int) temp;
			carry = (int) (temp >>> 32);
			System.out.println(carry);
		}
	}
	static final int len = 4;
	static class LL {
		int[] digits = new int[len];
		public LL(long x) {
			System.out.println(Long.toBinaryString(x));
			digits[0] = (int) x;
			digits[1] = (int) (x >>> 32);
		}
		@Override
		public String toString() {
			String s = "[";
			for(int i=len-1; i>0; i--) {
				s += digits[i] + ", ";
			}
			s += digits[0];
			s += "]";
			return s;
		}
	}
}
