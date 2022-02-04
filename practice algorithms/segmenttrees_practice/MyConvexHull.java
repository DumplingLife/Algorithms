package segmenttrees_practice;

import java.util.ArrayList;
import java.util.Arrays;

public class MyConvexHull {
	//12 min
	public static void main(String[] args) {
		PartialHull hull = new PartialHull(new Point[] {
				new Point(8,9),
				new Point(8,11),
				new Point(6,4),
				new Point(6,9),
				new Point(4,2),
				new Point(3,3),
		});
		System.out.println(Arrays.toString(hull.points));
	}
	
	public static class PartialHull {
		Point[] points;
		public PartialHull(Point[] inpPoints) {
			ArrayList<Point> pointsList = new ArrayList<>();
			for(int i=0; i<inpPoints.length; i++) {
				while(pointsList.size() >= 2
				   && crossp(subtract(pointsList.get(pointsList.size()-1), pointsList.get(pointsList.size()-2)),
						     subtract(inpPoints[i], pointsList.get(pointsList.size()-2))) <= 0) {
					pointsList.remove(pointsList.size()-1);
				}
				pointsList.add(inpPoints[i]);
			}
			points = pointsList.toArray(new Point[0]);
		}
	}
	
	public static Point subtract(Point p1, Point p2) {
		return new Point(p1.x - p2.x, p1.y - p2.y);
	}
	public static long crossp(Point p1, Point p2) {
		return 1L*p1.x*p2.y - 1L*p2.x*p1.y;
	}
	static class Point {
		int x, y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}
}
