//TODO: bug: minX must be tiebroken by smaller y (or larger y), otherwise it might not be on the convex hull

package geometry;

import java.util.Arrays;
import java.util.Stack;

public class MyActualConvexHull {
	public static void main(String[] args) {
		/*
		Point[] points = new Point[10];
		points[0] = new Point(1,1);
		points[1] = new Point(2,3);
		points[2] = new Point(3,3);
		points[3] = new Point(4,2);
		points[4] = new Point(5,3);
		points[5] = new Point(6,5);
		points[6] = new Point(7,5);
		points[7] = new Point(8,3);
		points[8] = new Point(8,7);
		points[9] = new Point(9,6);
		
		Arrays.sort(points, (a, b) -> {
			if(a.x == b.x) return b.y - a.y;
			else return b.x - a.x;
		});
		PartialHull topHull = new PartialHull(points);
		Arrays.sort(points, (a, b) -> {
			if(a.x == b.x) return a.y - b.y;
			else return a.x - b.x;
		});
		PartialHull bottomHull = new PartialHull(points);
		
		
		System.out.println(topHull.isPointIncluded(new Point(7,5)));
		System.out.println(bottomHull.isPointIncluded(new Point(7,5)));
		System.out.println(Arrays.toString(topHull.points));
		
		for(int y=11; y>=0; y--) {
			System.out.print(y);
			for(int x=0; x<=11; x++) {
				if(topHull.isPointIncluded(new Point(x,y)) && bottomHull.isPointIncluded(new Point(x,y))) System.out.print("X");
				else System.out.print(" ");
			}
			System.out.println();
		}
		System.out.print(" ");
		for(int x=0; x<=11; x++) {
			System.out.print((x + "").substring(0,1));
		}
		*/
		
		Point[] points = new Point[4];
		points[0] = new Point(0,0);
		points[1] = new Point(0,2);
		points[2] = new Point(0,4);
		points[3] = new Point(0,6);
		
		Arrays.sort(points, (a, b) -> {
			if(a.x == b.x) return b.y - a.y;
			else return b.x - a.x;
		});
		PartialHull topHull = new PartialHull(points);
		Arrays.sort(points, (a, b) -> {
			if(a.x == b.x) return a.y - b.y;
			else return a.x - b.x;
		});
		PartialHull bottomHull = new PartialHull(points);
		
		System.out.println(topHull.isPointIncluded(new Point(0,5)));
		System.out.println(bottomHull.isPointIncluded(new Point(0,5)));
		System.out.println(Arrays.toString(topHull.points));
		System.out.println(Arrays.toString(bottomHull.points));
	}

	static class PartialHull {
		Point[] points;		
		public PartialHull(Point[] inputPoints) {
			build(inputPoints);
		}
		
		public void build(Point[] inputPoints) {
			Stack<Point> stack = new Stack<>();
			stack.push(inputPoints[0]);
			stack.push(inputPoints[1]);
			for(int i=2; i<inputPoints.length; i++) {
				while(true) {
					if(stack.size() == 1) break;
					Vector prev = new Vector(stack.get(stack.size()-2), stack.get(stack.size()-1));
					Vector curr = new Vector(stack.get(stack.size()-1), inputPoints[i]);
					if(crossp(prev,curr) <= 0) {
						stack.pop();
					}
					else break;
				}
				stack.push(inputPoints[i]);
			}
			
			points = stack.toArray(new Point[0]);
		}
		
		//if there is a vertical line at x, this will be bad
		public double getYAtX(int x) {
			int ind = binarySearch(x);
			if(ind == -1 || ind == points.length-1) {
				System.out.println("error");
				return Double.MIN_VALUE;
			}
			Line l = new Line(points[ind], points[ind+1]);
			return l.getYAtX(x);
		}
		
		public boolean isPointIncluded(Point p) {
			int ind = binarySearch(p.x);		
			
			//point.x is already outside
			if(ind == -1 || ind == points.length-1) return false;
			
			//edge case: points[ind].x = points[ind+1].x
			if(points[ind].x == points[ind+1].x) {
				int min = Math.min(points[ind].y, points[ind+1].y);
				int max = Math.max(points[ind].y, points[ind+1].y);
				if(min <= p.y && p.y <= max) return true;
				else return false;
			}
			
			Vector convexHullVector = new Vector(points[ind], points[ind+1]);
			Vector pVector = new Vector(points[ind], p);
			if(crossp(convexHullVector, pVector) >= 0) return true;
			else return false;
		}
		
		private int binarySearch(int x) {
			int ind;
			if(points[0].x <= points[points.length-1].x) ind = binarySearchAsc(x);
			else ind = binarySearchDesc(x);
			//if the point is on the edge but it equals one of the edge x values, ind--
			if(ind == points.length-1 && points[points.length-1].x == x) ind--;
			return ind;
		}
		private int binarySearchAsc(int x) {
			int l = -1;
			int r = points.length-1;
			while(l < r) {
				int m = (int) Math.ceil((l+r)/2.0);
				if(points[m].x <= x) {
					l = m;
				}
				else r = m-1;
			}
			
			return l;
		}
		public int binarySearchDesc(int x) {
			int l = -1;
			int r = points.length-1;
			while(l < r) {
				int m = (int) Math.ceil((l+r)/2.0);
				if(points[m].x >= x) {
					l = m;
				}
				else r = m-1;
			}
			
			return l;
		}
	}
	

	public static long crossp(Vector v1, Vector v2) {
		return 1L*v1.x*v2.y - 1L*v1.y*v2.x;
	}
	
		
	static class Point {
		int x, y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}
	static class Vector {
		int x, y;
		public Vector(Point a, Point b) {
			x = b.x - a.x;
			y = b.y - a.y;
		}
		
		@Override
		public String toString() {
			return "<" + x + "," + y + ">";
		}
	}
	
	static class Line {
		double m, b;
		public Line(Point p1, Point p2) {
			m = (1.0*p2.y-p1.y)/(p2.x-p1.x);
			b = p1.y - m*p1.x;
		}
		public double getYAtX(int x) {
			return m*x + b;
		}
		@Override
		public String toString() {
			return "y=" + m + "x + " + b;
		}
	}
}
