package combo;

import java.util.Arrays;

public class Lists {
	static int ans = 0;
	public static void main(String[] args) {
		recur(new int[] {0,1}, 0, new int[10]);
		System.out.println(ans);
	}
	
	public static void callback(int[] a) {
		int sum = a[0]*18 + a[1]*465 + a[2]*278 + a[3]*291 + a[4]*202 + a[5]*96 + a[6]*375 + a[7]*941 + a[8]*870 + a[9]*540;
		if(sum == 1904)	System.out.println(Arrays.toString(a));
	}
	//loop through all the numbers, then recursion
	public static void recur(int[] possibleNums, int currentIndex, int[] rollingArr) {
		if(currentIndex == rollingArr.length) callback(rollingArr);
		else {
			for(int num : possibleNums) {
				rollingArr[currentIndex] = num;
				recur(possibleNums, currentIndex+1, rollingArr);
			}
		}
	}
}
