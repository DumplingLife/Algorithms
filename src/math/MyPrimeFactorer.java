/*
 * 2 parts
 * Both need to call populateLargestPrimeFactor
 * 
 * Basic: find prime factors as HashSet. Share factors = are sets disjoint
 * Advanced: class that represents prime factorization
 * 		fields:
 * 		  -	array with count of each prime factor (HashMap has faster initialize, slower get)
 * 		  -	HashSet of prime factors to make share factors faster
 * 		methods:
 * 		  -	multiply/divide
 * 		  -	share factors = are sets disjoint
 */

package math;

import java.util.Collections;
import java.util.HashSet;

public class MyPrimeFactorer {
	public static void main(String[] args) {
		populateLargestPrimeFactor();
		//start with 1 as default, not 0
		PrimeFactorization x = new PrimeFactorization(1);
		x.multiply(2);
		x.multiply(15);
		System.out.println(x.shareFactor(8));
		x.divide(2);
		System.out.println(x.shareFactor(8));
	}
	
	//BASIC
	static final int maxNum = 100000;
	static int[] largestPrimeFactor;

	public static void populateLargestPrimeFactor() {
		largestPrimeFactor = new int[maxNum+1];
		
		for(int i = 2; i<=maxNum; i++) {
			if(largestPrimeFactor[i] == 0) {
				//i is prime
				largestPrimeFactor[i] = i;
				for(int j = i; j<=maxNum; j+=i) {
					largestPrimeFactor[j] = i;
				}
			}
		}
	}
	
	public static HashSet<Integer> findPrimeFactors(int x) {
		HashSet<Integer> ret = new HashSet<>();
		while(x != 1) {
			int temp = largestPrimeFactor[x];
			ret.add(temp);
			x /= temp;
		}
		return ret;
	}
	
	static class PrimeFactorization {
		int[] primeFactorCount = new int[maxNum];
		public PrimeFactorization(int x) {
			while(x != 1) {
				int temp = largestPrimeFactor[x];
				primeFactorCount[temp]++;
				x /= temp;
			}
		}
		
		//multiply/divide 2 numbers
		//big number represented as prime factorization array
		public void multiply(int other) {
			while(other != 1) {
				int temp = largestPrimeFactor[other];
				primeFactorCount[temp]++;
				other /= temp;
			}
		}
		public void divide(int other) {
			while(other != 1) {
				int temp = largestPrimeFactor[other];
				primeFactorCount[temp]--;
				other /= temp;
			}
		}
		public boolean shareFactor(int other) {
			while(other != 1) {
				int temp = largestPrimeFactor[other];
				if(primeFactorCount[temp] > 0) return true;
				other /= temp;
			}
			return false;
		}
		
		//very slow, debug only
		public String toString() {
			int ret = 1;
			for(int i = 0;i<primeFactorCount.length;i++) {
				for(int j = 0;j<primeFactorCount[i];j++) {
					ret *= i;
				}
			}
			return ret + "";
		}
	}
}
