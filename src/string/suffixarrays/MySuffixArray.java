package string.suffixarrays;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import test.MyTimer;

public class MySuffixArray
{
	public static void main(String[] args) throws FileNotFoundException {
		MyTimer timer = new MyTimer();
		Scanner scan = new Scanner(new File("test.in"));
		SuffixArray sa = new SuffixArray(scan.nextLine());
		System.out.println(Arrays.toString(sa.suffixArr));
		timer.lap();
	}
	
	
	
	public static class SuffixArray {
		public int[] suffixArr;
		public SuffixArray(String s) {
			s += "$";
			int[] prevRankings = new int[s.length()];
			//l=1
			{
				int[] aArr = new int[s.length()];
				int[] bArr = new int[s.length()];
				for(int i = 0;i<s.length();i++) {
					aArr[i] = s.charAt(i);
				}
				prevRankings = getRankings(aArr, bArr);
			}
			for(int l = 2; l<s.length(); l *= 2) {
				int[] aArr = new int[s.length()];
				int[] bArr = new int[s.length()];
				for(int i = 0; i<s.length(); i++) {
					aArr[i] = prevRankings[i];
					bArr[i] = prevRankings[(i + l/2)%s.length()];
				}
				prevRankings = getRankings(aArr, bArr);
			}
			suffixArr = new int[s.length()-1];
			for(int i = 0;i<s.length()-1;i++) {
				suffixArr[prevRankings[i]-1] = i;
			}
		}
		
		private int[] getRankings(int[] aArr, int[] bArr) {
			int L = aArr.length;
			
			int[] originalIndArr = new int[L];
			for(int i = 0;i<L;i++) {
				originalIndArr[i] = i;
			}
			
			Triple triple1 = sort(bArr, aArr, originalIndArr);
			bArr = triple1.val1;
			aArr = triple1.val2;
			originalIndArr = triple1.val3;
			
			Triple triple2 = sort(aArr, bArr, originalIndArr);
			aArr = triple2.val1;
			bArr = triple2.val2;
			originalIndArr = triple2.val3;
			
			int[] rankings = new int[L];
			for(int i = 0;i<L;i++) {
				rankings[originalIndArr[i]] = i;
				if(i > 0 && aArr[i] == aArr[i-1] && bArr[i] == bArr[i-1]) {
					rankings[originalIndArr[i]] = rankings[originalIndArr[i-1]];
				}
			}
			
			
			return rankings;
		}
		
		private Triple sort(int[] sortBy, int[] arr1, int[] arr2) {
			int L = sortBy.length;
			int[] newSortBy = new int[L];
			int[] newArr1 = new int[L];
			int[] newArr2 = new int[L];
			int[] cumulativeCount = new int[Math.max(123, L)];
			for(int ele : sortBy) {
				cumulativeCount[ele]++;
			}
			for(int i = 1;i<cumulativeCount.length;i++) {
				cumulativeCount[i] += cumulativeCount[i-1];
			}
			for(int i = L-1; i>=0; i--) {
				int ele = sortBy[i];
				int cumulativeCountTemp = cumulativeCount[ele];
				newSortBy[cumulativeCountTemp - 1] = sortBy[i];
				newArr1[cumulativeCountTemp - 1] = arr1[i];
				newArr2[cumulativeCountTemp - 1] = arr2[i];
				cumulativeCount[ele]--;
			}
			
			return new Triple(newSortBy, newArr1, newArr2);
		}
	}
	
	static class Triple {
		int[] val1, val2, val3;
		public Triple(int[] a, int[] b, int[] c) {
			this.val1 = a;
			this.val2 = b;
			this.val3 = c;
		}
	}
}
