//TODO: it shouldn't be longest side, but whichever side has wider x interval

package geometry;

public class MyMultiPolygonPlane {
	public static void main(String[] args) {
		Plane plane = new Plane(new Point[] {
			new Point(1,9),
			new Point(2,3),
			new Point(3,4),
			new Point(3,5),
			new Point(6,3),
		});
		System.out.println(plane.countNumPointsInTriangle(0,1,4));
		int breakpoint = 1;
		breakpoint++;
	}
	
	static class Plane {
		Point[] points;
		int[] numUnderPoint;
		int[][] numUnderSeg;
		int[][] segXIntervals;
		
		public Plane(Point[] points) {
			this.points = points;
			numUnderPoint = new int[points.length];
			for(int i=0; i<points.length; i++) {
				numUnderPoint[i] = countNumUnderPoint(points[i]);
			}
			
			numUnderSeg = new int[points.length][points.length];
			segXIntervals = new int[points.length][points.length];
			for(int i=0; i<points.length; i++) {
				for(int j=0; j<points.length; j++) {
					if(i == j) continue;
					LineSegment seg = new LineSegment(points[i], points[j]);
					numUnderSeg[i][j] = countNumUnderSeg(seg);
					segXIntervals[i][j] = seg.getXInterval();
				}
			}
		}
		
		//only works if no points lie on sides of the triangle
		public int countNumPointsInTriangle(int ind1, int ind2, int ind3) {
			//swap so that the segment with points ind1, ind2 is longest
			double maxXInterval = Math.max(Math.max(segXIntervals[ind1][ind2], segXIntervals[ind2][ind3]), segXIntervals[ind3][ind1]); 
			if(maxXInterval == segXIntervals[ind2][ind3]) {
				//swap 1 and 3
				int temp = ind1;
				ind1 = ind3;
				ind3 = temp;
			}
			else if(maxXInterval == segXIntervals[ind1][ind3]) {
				//swap 2 and 3
				int temp = ind2;
				ind2 = ind3;
				ind3 = temp;
			}
			
			
			//numUnderOther2: remember to subtract stuff under the 3rd point
			int numUnderOther2 = numUnderSeg[ind1][ind3] + numUnderSeg[ind2][ind3] - numUnderPoint[ind3];
			if(numUnderSeg[ind1][ind2] > numUnderOther2) {
				//-1 to exclude 3rd point
				return numUnderSeg[ind1][ind2] - numUnderOther2 - 1;
			}
			else {
				return numUnderOther2 - numUnderSeg[ind1][ind2];
			}
		}
		
		//counts # points under (exclusive, so on does not count)
		public int countNumUnderSeg(LineSegment l) {
			int numPoints = 0;
			for(Point p : points) {
				//vertical line
				if(l.x1 == l.x2) {
					if(l.x1 == p.x && compareDouble(p.y,l.getYAtX(p.x)) < 0) numPoints++;
				}
				else {
					//if p.x is on l's interval && p.y < l.getYAtX(p.x), with epsilon
					if(l.x1 <= p.x && p.x <= l.x2 && compareDouble(p.y,l.getYAtX(p.x)) < 0) numPoints++;
				}
			}
			return numPoints;
		}
		public int countNumUnderPoint(Point point) {
			int numPoints = 0;
			for(Point p : points) {
				if(p.x == point.x && p.y < point.y) numPoints++;
			}
			return numPoints;
		}
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
		public double getYAtX(int x) {
			return m*x + b;
		}
		public int getXInterval() {
			return x2 - x1;
		}
		@Override
		public String toString() {
			return "y=" + m + "x + " + b + ", [" + x1 + "," + x2 + "]";
		}
	}

	static final double epsilon = 0.00001;
	public static double compareDouble(double a, double b) {
		if(-epsilon <= a-b && a-b <= epsilon) return 0;
		else return a-b;
	}
}
