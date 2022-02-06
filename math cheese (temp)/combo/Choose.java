package combo;
/*
 * nums: numbers to choose from
 * highestIndexSoFar: -1
 * currentIndex: 0
 * rollingArr: new array with length
 */
public class Choose {
	static int ans = 0;
	public static void main(String[] args) {
		choose(new int[] {0,1,2,3,4,5}, -1, 0, new int[3]);
		System.out.println(ans);
	}
	
	//find # ways to sum to 8
	public static void callback(int[] arr) {
		int sum = 0;
		for(int num : arr) {
			sum += num;
		}
		if(sum == 8) ans++;
	}
	
	//get distinct sets by only choosing from higher indexes. This correctly gets each combo exactly once
	public static void choose(int[] nums, int highestIndexSoFar, int currentIndex, int[] rollingArr) {
		if(currentIndex == rollingArr.length) callback(rollingArr);
		else {
			for(int i = highestIndexSoFar+1; i<nums.length;i++) {
				rollingArr[currentIndex] = nums[i];
				choose(nums, i, currentIndex+1, rollingArr);
			}
		}
	}
}
