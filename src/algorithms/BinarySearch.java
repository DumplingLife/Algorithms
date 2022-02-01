package algorithms;

public class BinarySearch 
{
	public static void main(String[] args) {
		int[] arr = {1, 2, 4, 4, 5, 6, 6, 6, 6, 6, 6, 9};		
		System.out.println(closestFromLeft(arr, -1));
	}

	//index of smallst ele >= x
	public static int closestFromRight(int[] arr, int x) {
		int l = 0;
		int r = arr.length;
		while(l < r) {
			int m = (l+r)/2;
			if(x <= arr[m]) {
				r = m;
			}
			else l = m+1;
		}
		return l;
	}
	//index of greatest element <= x
	public static int closestFromLeft(int[] arr, int x) {
		int l = -1;
		int r = arr.length-1;
		while(l < r) {
			int m = (int) Math.ceil((l+r)/2.0);
			if(arr[m] <= x) {
				l = m;
			}
			else r = m-1;
		}
		return l;
	}
	//boolean if element exists or not
	public static boolean exists(int[] arr, int x) {
		int l = 0;
		int r = arr.length;
		while(l < r) {
			int m = (l+r)/2;
			if(arr[m] >= x) {
				r = m;
			}
			else l = m+1;
		}
		if(x == arr[l]) return true;
		else return false;
	}
}
