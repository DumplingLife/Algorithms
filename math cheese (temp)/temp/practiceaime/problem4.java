package temp.practiceaime;

public class problem4 {
	public static void main(String[] args) {
		int sum = 0;
		for(int x=1; x<=1000000; x++) {
			long N = 10000L*x+2020;
			if(N%x == 0) {
				System.out.println(x);
				sum += sumDigits(x)+4;
			}
		}
		System.out.println(sum);
	}
	public static int sumDigits(int x) {
		if(x==0) return 0;
		return x%10 + sumDigits(x/10);
	}
}
