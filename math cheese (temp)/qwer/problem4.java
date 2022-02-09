package qwer;

public class problem4 {
	public static void main(String[] args) {
		int ways = 0;
		for(int r=1; r<=100; r++) {
			for(int s=1; s<=100; s++) {
				if((90+30*r)%360 == (120*s)%360) ways++;
			}
		}
		System.out.println(ways);
	}
}
