package combo;
/*
 * Permute is different becuase the way it is done is different
 * 
 * arr: numbers to permute
 * currentIndex: 0
 * len: how far to go
 * 
 * currently: 5 distinct numbers from 0..9, how many have 0 consecutive increase
 */

public class Permute {
	//customize
	static int len = 3;
	public static void main(String[] args) {
		permute(new int[] {1,2,3,4,5,6}, 0);
	}
	public static void callback(int[] arr) {
		//arr is not the final arr, only use the first len elements
		for(int i = 0;i<len;i++) {
			System.out.print(arr[i] + ", ");
		}
		System.out.println();
	}
	
	//permute in place by doing a weird trick using swap
	public static void permute(int[] arr, int currentIndex) {
		//swap is just a convenient way of tracking what numbers we haven't used
		if(currentIndex == len)	callback(arr);
		else {
			for(int i = currentIndex;i<arr.length;i++) {
				swap(arr, currentIndex, i);
				permute(arr, currentIndex+1);
				swap(arr, currentIndex, i);
			}
		}
	}
	public static void swap(int[] arr, int a, int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
}
