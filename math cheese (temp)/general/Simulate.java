package general;

public class Simulate {
	public static void main(String[] args) {
		int reps = 10000000;
		int x = 0;
		for(int rep=0; rep<reps; rep++) {
			if(rep % 1000000 == 0) System.out.println(rep);
			
			double a = Math.random();
			if(a<0.4) x++;
		}
		
		System.out.println(1.0 * x/reps);
	}
}
