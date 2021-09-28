/*
 * small algorithms (usually methods)
 * 
 * max range sum
 * round x down to nearest y
 */

package algorithms;

public class Miscal {
	
	//find max range sum
	public static int maxRangeSum(int[] arr) {
		int max = 0;
		int maxEndingHere = 0;
		for(int ele : arr) {
			maxEndingHere += ele;
			if(maxEndingHere > max) max = maxEndingHere;
			if(maxEndingHere < 0) maxEndingHere = 0;
		}
		return max;
	}
	//round x down to nearest y
	public static int roundDown(int x, int y) {
		return (x/y) * y;
	}
	
}
