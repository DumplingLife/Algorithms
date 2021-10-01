package geometry;

public class PrimitivesCoordinatesForm {
	public static void main(String[] args) {
		
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
	
	static class Line {
		double m, b;
		public Line(double m, double b) {
			this.m = m;
			this.b = b;
		}
		@Override
		public String toString() {
			return "y=" + m + "x + " + b;
		}
	}
	
	static class LineSegment {
		double m, b;
		int x1, x2;
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
