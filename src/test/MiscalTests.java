//some small tests
package test;

public class MiscalTests
{
	public static void main(String[] args) {
		int reps = 5000000;
		int[][] arr = new int[reps][3];
		for(int i=0; i<reps; i++) {
			arr[i][0] = 10;
			arr[i][1] = 20;
			arr[i][2] = 30;
		}
		MyTimer timer = new MyTimer();
		for(int i=0; i<reps; i++) {
			int prod = 1;
			prod *= arr[i][0];
			prod *= arr[i][1];
			prod *= arr[i][2];
		}
		timer.lap();
	}
}
