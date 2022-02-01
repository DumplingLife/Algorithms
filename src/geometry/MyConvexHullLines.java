package geometry;

import java.util.ArrayList;

public class MyConvexHullLines {
	public static void main(String[] args) {
		ConvexHull convexHull = new ConvexHull();
		convexHull.addLine(1, 1);
		convexHull.addLine(0, 3);
		convexHull.addLine(-2, 10);
		System.out.println(convexHull.query(2));
	}
	
	public static double findIntersectionX(long m1, long b1, long m2, long b2) {
		if(m1 == m2) System.out.println("error");
		return 1.0*(b2-b1)/(m1-m2);
	}
	
	public static class ConvexHull {
		ArrayList<Long> ms = new ArrayList<>();
		ArrayList<Long> bs = new ArrayList<>();
		ArrayList<Double> xs = new ArrayList<>();
		
		public void addLine(long m, long b) {
			//if last added line is parallel
			if(!ms.isEmpty() && ms.get(ms.size()-1) == m) {
				if(bs.get(bs.size()-1) < b) return;
				else {
					ms.remove(ms.size()-1);
					bs.remove(bs.size()-1);
					xs.remove(xs.size()-1);
				}
			}
			//remove obselete lines
			while(!ms.isEmpty() && findIntersectionX(m,b, ms.get(ms.size()-1),bs.get(bs.size()-1)) < xs.get(xs.size()-1)) {
				ms.remove(ms.size()-1);
				bs.remove(bs.size()-1);
				xs.remove(xs.size()-1);
			}
			if(ms.isEmpty()) xs.add(Double.NEGATIVE_INFINITY);
			else xs.add(findIntersectionX(m,b, ms.get(ms.size()-1),bs.get(bs.size()-1)));
			ms.add(m);
			bs.add(b);
		}
		
		public long query(long x) {
			int l=-1, r=xs.size()-1;
			while(l<r) {
				int m = (int) Math.ceil((l+r)/2.0);
				if(xs.get(m) <= x) l=m;
				else r=m-1;
			}
			return ms.get(l)*x + bs.get(l);
		}
	}
}
