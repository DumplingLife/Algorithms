package algorithms;

public class MyPrefixSums
{
	static int[] arr = {0, 1, 3, 2, 2};
	static int[] prefixSums;
	public static void main(String[] args) {
		prefixSums = new int[arr.length];
	}
	public static void calculatePrefixSums() {
		prefixSums[0] = arr[0];
		for(int i = 1;i<arr.length;i++) {
			prefixSums[i] = prefixSums[i-1] + arr[i];
		}
	}
	public static int sum(int i, int j) {
		return prefixSums[j] - (i == 0 ? 0 : prefixSums[i-1]);
	}
}
