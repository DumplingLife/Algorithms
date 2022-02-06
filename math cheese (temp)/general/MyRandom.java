package general;

public class MyRandom {
	public static void main(String[] args) {
		for(int i=0; i<100; i++) System.out.println(random(1,6));
	}
	//inclusive
	public static int random(int min, int max) {
		return (int) (Math.random() * (max-min+1) + min);
	}
}
