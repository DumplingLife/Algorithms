package numberTheory;

import java.util.ArrayList;

public class GeneratePrimes {
	public static void main(String[] args) {
		ArrayList<Integer> primes = new ArrayList<>();
		for(int i=2; i<1000; i++) {
			if(isPrime(i)) primes.add(i);
		}
		System.out.println(primes);
	}
	public static boolean isPrime(int x) {
		if(x==1) return false;
		for(int i=2; i<x && i<=Math.sqrt(x); i++) {
			if(x % i == 0) return false;
		}
		return true;
	}
}
