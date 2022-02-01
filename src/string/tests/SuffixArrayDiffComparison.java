package string.tests;

import test.MyTimer;
import string.suffixarrays.MySuffixArray.SuffixArray;
import string_diff.MySuffixArrayDiff.SuffixArrayDiff;

public class SuffixArrayDiffComparison {
	static MyTimer timer2;
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<100000; i++) {
			sb.append((char) (Math.random()*26+97));
		}
		String s = sb.toString();
		
		MyTimer timer = new MyTimer();
		
		SuffixArray suffixArr = new SuffixArray(s);
		timer.lap();
		
		SuffixArrayDiff suffixArrDiff = new SuffixArrayDiff(s);
		timer.lap();
		for(int i=0; i<s.length(); i++) {
			if(suffixArr.suffixArr[i] != suffixArrDiff.suffixArr[i]) System.out.println(i);
		}
	}
}
