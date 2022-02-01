package generatefile;

public class GenerateArray {
	public static void main(String[] args) {
		int[] arr = new int[10000000]; //size
		for(int i = 0;i<arr.length;i++) {
			arr[i] = random(0, 10000000); //minVal, maxVal
		}
	}
	public static int random(int lower, int upper) {
		return (int) (Math.floor(Math.random() * (upper - lower + 1)) + lower);
	}
}
