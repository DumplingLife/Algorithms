package geometry;

public class MyActualConvexHull {
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
		public Vector(Point a, Point b) {
			x = a.x - b.x;
			y = a.y - b.y;
		}
		
		@Override
		public String toString() {
			return "<" + x + "," + y + ">";
		}
	}
	
	static class ConvexHull {
		Point[] points;		
		public ConvexHull(Point[] points) {
			this.points = points;
		}

		public void buildVectorVariation() {
			
		}
	}
}
