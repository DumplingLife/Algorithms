package qwer;

import java.util.HashSet;

public class problem13 {
	public static void main(String[] args) {
		HashSet<Integer> set = new HashSet<>();
		for(int x=1; x<=9999; x++) {
			int y = x / gcd(x,9999);
			set.add(y);
		}
		System.out.println(set.size());
		for(int x=1; x<=9999; x++) {
			if(!set.contains(x)) System.out.println(x);
		}
		System.out.println("----------------------");
		System.out.println("----------------------");
		System.out.println("----------------------");
		System.out.println("----------------------");
		for(int x=1; x<=9999; x++) {
			if(set.contains(x)) System.out.println(x);
		}
	}

	public static int gcd(int a, int b) {
		if(b==0) return a;
		return gcd(b, a%b);
	}
}
