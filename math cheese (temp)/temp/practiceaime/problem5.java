package temp.practiceaime;

import java.util.ArrayList;
import java.util.Arrays;

public class problem5 {
	static int len = 6;
	static int ans = 0;
	public static void main(String[] args) {
		permute(new int[] {1,2,3,4,5,6}, 0);
		System.out.println(ans);
	}
	public static void callback(int[] arr) {
		for(int i=0; i<6; i++) {
			ArrayList<Integer> list = new ArrayList<>();
			for(int j=0; j<6; j++) {
				if(j==i) continue;
				list.add(arr[j]);
			}
			if(list.get(0)<list.get(1) && list.get(1)<list.get(2)
				&& list.get(2)<list.get(3) && list.get(3)<list.get(4)) {
				ans++;
				return;
			}
		}
		
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
