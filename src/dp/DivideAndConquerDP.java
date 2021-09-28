/*
 * newer version of argmin DP
 */

package dp;

public class DivideAndConquerDP {
	static int N;
	static int G;
	static int[] arr;
	static int[][] f;
	public static void main(String[] args) {
		N = 6;
		G = 2;
		arr = new int[] {1, 1, 0, 1, 1, 0};
		
		costPreprocess();
		
		f = new int[G+1][N];
		//g = 1
		for(int i = 0;i<N;i++) {
			f[1][i] = cost(0, i);
		}
		for(int g = 2; g<=G; g++) {
			recur(g, 0, N-1, 0, N-1);
		}

		System.out.println(f[G][N-1]);
	}
	
	public static void recur(int g, int queryL, int queryR, int boundL, int boundR) {
		if(queryL > queryR) return;
		
		int queryM = (queryL + queryR)/2;
		//recurrence
		int min = Integer.MAX_VALUE;
		int boundM = -1;
		//<= queryM so that you can have a 0 size group
		for(int j = 0;j<=queryM;j++) {
			int temp = f[g-1][j] + cost(j+1,queryM);
			if(temp < min) {
				min = temp;
				boundM = j;
			}
		}
		f[g][queryM] = min;
		recur(g, queryL, queryM-1, boundL, boundM);
		recur(g, queryM+1, queryR, boundM, boundR);
	}
	
	//customize
	/*
	 * use any states you want. If O(N^2) is fine, you can just use a NxN cost array
	 * and remove the cost method
	 */
	static int[] prefixSums;
	public static void costPreprocess() {
		prefixSums = new int[N];
		prefixSums[0] = arr[0];
		for(int i = 1;i<arr.length;i++) {
			prefixSums[i] = prefixSums[i-1] + arr[i];
		}
	}
	public static int cost(int a, int b) {
		//need to handle edge cases: a>b
		//a might also be out of bounds, but this handles it
		if(a>b) return 0;
		
		int num1 = prefixSums[b] - (a == 0 ? 0 : prefixSums[a-1]);
		int num0 = b-a+1 - num1;
		return num0 * num1;
	}
}
