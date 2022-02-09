package qwer;

public class problem7 {
	static int len = 9;
	public static void main(String[] args) {
		permute(new int[] {1,2,3,4,5,6,7,8,9}, 0);
		System.out.println(ans);
	}
	static double ans = 100;
	public static void callback(int[] a) {
		double x = ((1.0*a[0]*a[1]*a[2]) - (1.0*a[3]*a[4]*a[5]))/(a[6]*a[7]*a[8]);
		if(x>0) ans = Math.min(ans, x);
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
