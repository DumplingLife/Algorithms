package string.suffixarrays;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import test.MyTimer;

public class MySuffixArrayMultipleStrings
{
	public static void main(String[] args) throws FileNotFoundException {
		MyTimer timer = new MyTimer();
		Scanner scan = new Scanner(new File("suffixArrayMultipleStrings.in"));
		int K = scan.nextInt();
		scan.nextLine();
		String[] strings = new String[K];
		for(int i=0; i<K; i++) {
			strings[i] = scan.nextLine();
		}
		SuffixArrayMultipleStrings sa = new SuffixArrayMultipleStrings(strings);
		System.out.println(Arrays.toString(sa.suffixArr));
		timer.lap();
	}
	
	
	
	static class SuffixArrayMultipleStrings {
		public String s;
		public int N;
		public int[] suffixArr;
		public int[] superstringIds;
		public int[] rank;
		public int[] superstringEndArr;
		
		public SuffixArrayMultipleStrings(String[] strings) {
			this.s = String.join("", strings);
			N = s.length();			
			suffixArr = findSuffixArr(strings);
			
			//build rank by inversing suffixArr
			rank = new int[N];
			for(int i=0; i<N; i++) {
				rank[suffixArr[i]] = i;
			}
			
			//build superstringIds and superstringEndArr by creating and destroying stringLens
			superstringIds = new int[N];
			superstringEndArr = new int[strings.length];
			int[] stringLens = new int[strings.length];
			for(int i=0; i<strings.length; i++) {
				stringLens[i] = strings[i].length();
			}
			for(int i=0, j=0; i<N; i++) {
				superstringIds[i] = j;
				stringLens[j]--;
				if(stringLens[j] == 0) {
					superstringEndArr[j] = i;
					j++;
				}
			}
		}

		//BUILD
		//main build method
		public int[] findSuffixArr(String[] strings) {
			//create new temp string we will use in build only
			StringBuilder strBuilder = new StringBuilder();
			for(int i = 0;i<strings.length;i++) {
				strBuilder.append(strings[i]);
				//append "#" at the end instead of "$" to make sure there are no ties
				if(i < strings.length-1) strBuilder.append("$");
				else strBuilder.append("#");
			}
			String str = strBuilder.toString();
			
			int L = str.length();
			int[] prevRankings = new int[L];
			//base case: l = 1
			{
				int[] aArr = new int[L];
				int[] bArr = new int[L];
				for(int i = 0;i<L;i++) {
					aArr[i] = str.charAt(i);
					//bArr[i] = 0, don't need to write this
				}
				prevRankings = getRankings(aArr, bArr);
			}
			//main loop
			for(int l = 2; l<L; l *= 2) {
				int[] aArr = new int[L];
				int[] bArr = new int[L];
				for(int i = 0; i<L; i++) {
					aArr[i] = prevRankings[i];
					bArr[i] = prevRankings[(i + l/2)%L];
				}
				prevRankings = getRankings(aArr, bArr);
			}
			
			//convert rankings into what we want (invert)
			int[] ret = new int[N];
			//skip any suffix that starts with "$", there are strings.length of these
			int skip = strings.length;
			//j is a counter to compress the final numbers to pretend like the "$" and "#" never existed
			int j = 0;
			for(int i=0; i<L; i++) {
				//don't include any ranking that is smaller than skip, these must be "$.."
				if(str.charAt(i) == '$' || str.charAt(i) == '#') continue;
				//offset the rankings by skip because all the "$.." place in the beginning
				ret[prevRankings[i] - skip] = j;
				j++;
			}
			return ret;
		}
		
		//helper used in findSuffixArr
		private int[] getRankings(int[] aArr, int[] bArr) {
			int L = aArr.length;
			
			int[] originalIndArr = new int[L];
			for(int i = 0;i<L;i++) {
				originalIndArr[i] = i;
			}
			
			//radix sort
			//sort by bArr, then copy the results to the local variables
			Triple triple1 = sort(bArr, aArr, originalIndArr);
			bArr = triple1.val1;
			aArr = triple1.val2;
			originalIndArr = triple1.val3;
			
			//sort by aArr, then copy the results to the local variables
			Triple triple2 = sort(aArr, bArr, originalIndArr);
			aArr = triple2.val1;
			bArr = triple2.val2;
			originalIndArr = triple2.val3;
			
			//calculate rankings
			int[] rankings = new int[L];
			for(int i = 0;i<L;i++) {
				rankings[originalIndArr[i]] = i;
				//get ties
				if(i > 0 && aArr[i] == aArr[i-1] && bArr[i] == bArr[i-1]) {
					rankings[originalIndArr[i]] = rankings[originalIndArr[i-1]];
				}
			}
			
			
			return rankings;
		}
		
		//sort by sortBy, move elements around in all 3 arrays
		private Triple sort(int[] sortBy, int[] arr1, int[] arr2) {
			int L = sortBy.length;
			int[] newSortBy = new int[L];
			int[] newArr1 = new int[L];
			int[] newArr2 = new int[L];
			//if it's the first iteration, rankings will be ascii codes, not actual rankings, so do this
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
