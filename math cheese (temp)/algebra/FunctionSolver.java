package algebra;

import java.util.function.Function;

public class FunctionSolver {
	public static void main(String[] args) {
		solveFunction(x -> x*x*x-3*x*x+3*x-1, -1000, 1000);
	}
	
	//WARNING: not safe to use if f is tangent to x axis
	//WARNING: not safe to use to count zeroes, because 1 zero might be counted twice
	//plugs in 100K evenly spread out values on [minX, maxX), then for each pair of values that
	//cross 0 (i.e. 1 is positive, 1 is negative), recursively solves
	public static void solveFunction(Function<Double, Double> f, double minX, double maxX) {
		//settings
		//100,000 steps per interval
		final double step = (maxX - minX)/100_000;
		//zero defined to be within 0.0001 of true 0
		final double zeroError = 0.0001;
		
		//start prevX as minX. check if it works. If not, do the loop
		double prevX = minX;
		double prevY = f.apply(minX);
		if(Math.abs(prevY) < zeroError) {
			System.out.println(prevX);
			return;
		}
		
		for(double x=minX+step; x<maxX; x+=step ) {
			double y = f.apply(x);
			
			if((prevY<=0 && y>=0) || (prevY>=0 && y<=0)) {
				solveFunction(f, prevX, x);
			}
			
			prevY = y;
			prevX = x;
		}
	}
}
