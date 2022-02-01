package string.tests;

import string.MyPrefixHasher.PrefixHasher;
import string_diff.MyPrefixHasherDiff.PrefixHasherDiff;

public class PrefixHasherDiffComparison {
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<100000; i++) {
			sb.append((char) (Math.random()*26+97));
		}
		String s = sb.toString();
		PrefixHasher p = new PrefixHasher(s);
		PrefixHasherDiff pDiff = new PrefixHasherDiff(s);
		for(int i=0; i<100000; i++) {
			int a = (int) (Math.random() * 100000);
			int b = (int) (Math.random() * 100000);
			if(b<a) continue;
			if(p.hash(a, b) != pDiff.hash(a, b)) System.out.println(i);
		}
	}
}
