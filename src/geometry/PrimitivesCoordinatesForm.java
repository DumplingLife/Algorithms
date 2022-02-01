package geometry;

public class PrimitivesCoordinatesForm {
	public static void main(String[] args) {
		
	}
	
	public static Point midpoint(Point a, Point b) {
		return new Point((a.x+b.x)/2, (a.y+b.y)/2);
	}
	public static double dist(Point a, Point b) {
		return Math.sqrt((b.x-a.x)*(b.x-a.x) + (b.y-a.y)*(b.y-a.y));
	}
	
	static class Point {
		long x, y;
		public Point(long x, long y) {
			this.x = x;
			this.y = y;
		}
		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}
	
	static class Line {
		double m, b;
		public Line(double m, double b) {
			this.m = m;
			this.b = b;
		}
		public Line(Point p1, Point p2) {
			if(p1.x != p2.x) {
				m = (1.0*p2.y-p1.y)/(p2.x-p1.x);
				b = p1.y - m*p1.x;
			}
		}
		public double getYAtX(int x) {
			return m*x + b;
		}
		@Override
		public String toString() {
			return "y=" + m + "x + " + b;
		}
	}
	
	static class LineSegment {
		double m, b;
		long x1, x2;
		public LineSegment(Point p1, Point p2) {
			x1 = Math.min(p1.x,  p2.x);
			x2 = Math.max(p1.x,  p2.x);
			if(x1 != x2) {
				m = (1.0*p2.y-p1.y)/(p2.x-p1.x);
				b = p1.y - m*p1.x;
			}
		}
		@Override
		public String toString() {
			return "y=" + m + "x + " + b + ", [" + x1 + "," + x2 + "]";
		}
	}
}
