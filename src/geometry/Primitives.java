package geometry;

public class Primitives {
	public static void main(String[] args) {
		Line l1 = new Line(new Point(0,9), new Point(4,17));
		Line l2 = new Line(new Point(0,7), new Point(-4,-5));
		
		System.out.println(intersect(l1, l2));
	}
	
	public static Point add(Point p1, Vector p2) {
		return new Point(p1.x + p2.x, p1.y + p2.y);
	}
	public static Vector subtract(Point p1, Point p2) {
		return new Vector(p1.x - p2.x, p1.y - p2.y);
	}
	public static Vector multiply(double a, Vector v) {
		return new Vector(a * v.x, a * v.y);
	}
		
	public static double dotp(Vector v1, Vector v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}
	public static double crossp(Vector v1, Vector v2) {
		return v1.x * v2.y - v1.y * v2.x;
	}
	public static Point intersect(Line l1, Line l2) {
		if(crossp(l1.d, l2.d) == 0) return null;
		return add(l1.a, multiply(crossp(subtract(l2.a, l1.a), l2.d)/(crossp(l1.d, l2.d)), l1.d));
	}
	public static Point intersectAsRays(Line l1, Line l2) {
		if(crossp(l1.d, l2.d) == 0) return null;
		double t = crossp(subtract(l2.a, l1.a), l2.d)/(crossp(l1.d, l2.d));
		if(t < 0) return null;
		return add(l1.a, multiply(t, l1.d));
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
	
	static class Line {
		Point a;
		Vector d;
		public Line(Point p1, Point p2) {
			a = p1;
			d = new Vector(p2.x - p1.x, p2.y - p1.y);
		}
	}
	
	//t is on range [0,1]
	static class LineSegment {
		Point a;
		Vector d;
		public LineSegment(Point p1, Point p2) {
			a = p1;
			d = new Vector(p2.x - p1.x, p2.y - p1.y);
		}
	}
}
