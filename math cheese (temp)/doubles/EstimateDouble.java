package doubles;

public class EstimateDouble {
	public static void main(String[] args) {
		estimateAs_msqrtn(2095.3281533919);
	}
	
	public static void estimateAsFraction(double x) {
		final double error = 0.001;
		for(int d=1; d<10000; d++) {
			if(Math.abs(Math.round(d*x) - d*x) < error) {
				System.out.println(d*x + "/" + d);
			}
		}
	}
	
	public static void estimateAs_msqrtn(double x) {
		double minError = Double.POSITIVE_INFINITY;
		String res = "not found";
		for(int n=1; n<=1000; n++) {
			for(int m=1; m<=1000; m++) {
				double estimate = 1.0*n*Math.sqrt(1.0*m);
				double estimateError = Math.abs(x-estimate);
				if(estimateError < minError) {
					minError = estimateError;
					res = n + "sqrt(" + m + "), error: " + estimateError;
				}
			}
		}
		System.out.println(res);
	}
}
