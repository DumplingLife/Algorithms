package qwer;

public class problem2 {
	public static void main(String[] args) {
		for(int a=0; a<=8; a++) {
			for(int b=0; b<=8; b++) {
				for(int c=0; c<=8; c++) {
					if(100*a+10*b+c == 81*b+9*c+a) System.out.println(a + " " + b + " " + c);
				}
			}
		}
	}
}
