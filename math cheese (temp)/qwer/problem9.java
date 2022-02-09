package qwer;

public class problem9 {
	static int len = 10;
	static int ans = 0;
	static int total = 0;
	public static void main(String[] args) {
		permute(new int[] {1,2,3,4,5,6,7,8,9,10}, 0);
		System.out.println(ans + "/" + total);
	}
	public static void callback(int[] a) {
		if(a[0]%2 != a[1]%2
				&& a[2]%2 != a[3]%2
				&& a[4]%2 != a[5]%2
				&& a[6]%2 != a[7]%2
				&& a[8]%2 != a[9]%2) {
			ans++;
		}
		total++;
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
