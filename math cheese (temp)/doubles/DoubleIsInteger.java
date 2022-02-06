package doubles;

public class DoubleIsInteger {
	public static void main(String[] args) {
		
	}
	
	public static boolean isInteger(double x) {
		return x-Math.floor(x) < 0.001 || Math.ceil(x)-x < 0.001;
	}
}
