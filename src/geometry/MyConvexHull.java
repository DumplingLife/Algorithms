/*
 * Given some lines, for any x value, calculate the min/max y value on some line
 * 		min and max are 2 different classes. Turns out there is very little difference, only
 * 		1 character is different (comparing yIntercepts of parallel lines)
 * 
 * possible customization (optimization): if you know the getMin x values always increase, then
 * you can keep track of the last x value and change the binary search into iteration, which gives
 * O(N) + O(Q) for Q queries
 * 
 * TODO: allow parallel lines
 */

package geometry;

import java.util.ArrayList;

public class MyConvexHull
{
	public static void main(String[] args) {
		ConvexHullMin ch = new ConvexHullMin();
		ch.addLineSmallerSlope(4, 0);
		ch.addLineSmallerSlope(3, 1);
		ch.addLineSmallerSlope(2, 5);
		//intersections: x = 1, x = 4
		System.out.println(ch.get(2)); //7
		//make 3,1 and 2,5 obsolete
		ch.addLineSmallerSlope(-1, 0);
		System.out.println(ch.get(2)); //-2
		
		ConvexHullMax chMax = new ConvexHullMax();
		chMax.addLineLargerSlope(1, 0);
		chMax.addLineLargerSlope(2, 5);
		chMax.addLineLargerSlope(3, 1);
		System.out.println(chMax.get(3));
	}
}

abstract class ConvexHullBasic
{
	ArrayList<Integer> slopes = new ArrayList<>();
	ArrayList<Integer> yIntercepts = new ArrayList<>();
	ArrayList<Double> xIntersections = new ArrayList<>();
	
	
	public int get(int x) {
		int slope;
		int yIntercept;
		//find the line (set slope and yIntercept)
		int l = 0;
		int r = xIntersections.size()-1;
		//edge cases: 1 line, left of first intersection, right of last intersection
		if(xIntersections.size() == 0) {
			slope = slopes.get(0);
			yIntercept = yIntercepts.get(0);
		}
		else if(x <= xIntersections.get(l)) {
			slope = slopes.get(0);
			yIntercept = yIntercepts.get(0);
		}
		else if(x >= xIntersections.get(r)) {
			slope = slopes.get(r+1);
			yIntercept = yIntercepts.get(r+1);
		}
		//binary search
		else {
			while(r - l > 1) {
				int m = (l+r)/2;
				if(xIntersections.get(m) < x) {
					l = m;
				}
				else r = m;
			}
			slope = slopes.get(l+1);
			yIntercept = yIntercepts.get(l+1);
		}
		
		return slope*x + yIntercept;
	}
	
	//HELPER
	public void removeObselete(int m, int b) {
		int i = slopes.size()-1;
		while(i >= 1 && calculateIntersect(m, b, i) < xIntersections.get(i-1)) {
			removeLast();
			i--;
		}
	}
	//add line to the end without removing obsolete, checking for parallel
	public void appendLine(int m, int b) {
		if(slopes.size() > 0) xIntersections.add(calculateIntersect(m,b,slopes.size()-1));
		slopes.add(m);
		yIntercepts.add(b);
	}
	public void removeLast() {
		xIntersections.remove(xIntersections.size()-1);
		slopes.remove(slopes.size()-1);
		yIntercepts.remove(yIntercepts.size()-1);
	}
	//intersection between y = mx + b and line i
	public double calculateIntersect(int m, int b, int i) {
		double mi = slopes.get(i);
		double bi = yIntercepts.get(i);
		
		return (b - bi)/(mi - m);
	}
}
class ConvexHullMin extends ConvexHullBasic
{
	public void addLineSmallerSlope(int m, int b) {
		if(slopes.size() > 0 && m == slopes.get(slopes.size()-1)) {
			if(b < yIntercepts.get(slopes.size()-1)) {
				yIntercepts.set(slopes.size()-1, b);
			}
			return;
		}
		
		removeObselete(m, b);
		appendLine(m, b);
	}
}
class ConvexHullMax extends ConvexHullBasic
{
	public void addLineLargerSlope(int m, int b) {
		if(slopes.size() > 0 && m == slopes.get(slopes.size()-1)) {
			if(b > yIntercepts.get(slopes.size()-1)) {
				yIntercepts.set(slopes.size()-1, b);
			}
			return;
		}
		
		removeObselete(m, b);
		appendLine(m, b);
	}
}