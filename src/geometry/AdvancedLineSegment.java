package geometry;

public class AdvancedLineSegment {
	
	
	static class Plane {
		Point[] points;
		
	}
	
	
	static class Point {
		double x, y;
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}
	
	static class Vector {
		double x, y;
		public Vector(double x, double y) {
			this.x = x;
			this.y = y;
		}
		//return value is between -pi and pi
		public double getAngle() {
			return Math.atan2(x,y);
		}
		
		@Override
		public String toString() {
			return "<" + x + "," + y + ">";
		}
	}
	static class LineSegment {
		Point a;
		Vector d;
		public LineSegment(Point p1, Point p2) {
			a = p1;
			d = new Vector(p2.x - p1.x, p2.y - p1.y);
		}
	}
}
