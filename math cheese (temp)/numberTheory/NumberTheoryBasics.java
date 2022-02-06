package numberTheory;

import java.util.ArrayList;

public class NumberTheoryBasics {
	public static void main(String[] args) {
		System.out.println(findPrimeFactorization(150));
		System.out.println(findDivisors(150));
		
		System.out.println(gcd(30,40));
		System.out.println(lcm(30,40));
	}

	public static boolean isPrime(int x) {
		if(x==1) return false;
		for(int i=2; i<x && i<=Math.sqrt(x); i++) {
			if(x % i == 0) return false;
		}
		return true;
	}
	public static ArrayList<Integer> findPrimeFactorization(int x) {
		ArrayList<Integer> ret = new ArrayList<>();
		for(int i=2; i<=x; i++) {
			if(x%i == 0) {
				ret.add(i);
				x /= i;
				i--;
			}
		}
		return ret;
	}
	public static ArrayList<Integer> findDivisors(int x) {
		ArrayList<Integer> ret = new ArrayList<>();
		for(int i=1; i<=x; i++) {
			if(x%i == 0) ret.add(i);
		}
		return ret;
	}
	public static int gcd(int a, int b) {
		if(b==0) return a;
		return gcd(b, a%b);
	}
	public static int lcm(int a, int b) {
		return (a*b)/gcd(a,b);
	}
}
