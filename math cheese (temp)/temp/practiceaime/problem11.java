package temp.practiceaime;

public class problem11 {
	public static void main(String[] args) {
		int ways = 0;
		for(int a=-10; a<=10; a++) {
			for(int b=-10; b<=10; b++) {
				for(int c=-10; c<=10; c++) {
					int f2 = 4+2*a+b;
					int f4 = 16+4*a+b;
					for(int d=-10000; d<=10000; d++) {
						if(f2*f2+c*f2+d == 0 && f4*f4+c*f4+d == 0) {
							System.out.println(a + " " + b + " " + c);
							ways++;
						}
					}
				}
			}
		}
		System.out.println(ways);
	}
}
