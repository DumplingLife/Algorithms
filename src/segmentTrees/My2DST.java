package segmentTrees;

public class My2DST
{
	public static void main(String[] args) {
		int[][] arr = {
				{1,2,3},
				{4,5,6},
				{7,8,9},
		};
		SegmentTree st = new SegmentTree(arr);
		st.update(2, 2, 100);
		System.out.println(st.query(2, 2, 1, 1));
		int breakpoint = 0;
	}
	
	//bottom left origin
	public static String formatXY(int[][] arr) {
		String ret = "";
		for(int y = arr[0].length-1;y>=0;y--) {
			String line = "";
			for(int x = 0;x<arr.length;x++) {
				line += (arr[x][y] + "  ").substring(0,2);
			}
			ret += line + "\n";
		}
		return ret;
	}
	
	static class SegmentTree {
		int[][] t;
		//these are size of grid, not t
		int sizeX;
		int sizeY;
		
		public SegmentTree(int[][] arr) {
			t = new int[arr.length*4][arr.length*4];
			sizeX = arr.length;
			sizeY = arr[0].length;
			build(arr);
		}
		public void build(int[][] arr) {
			buildX(1, 0, sizeX-1, arr);
		}
		private void buildX(int nodeIndX, int nodeLX, int nodeRX, int[][] arr) {
			if(nodeLX == nodeRX) build1Wide(nodeIndX, nodeLX, arr);
			else {
				int nodeM = (nodeLX + nodeRX)/2;
				buildX(2*nodeIndX, nodeLX, nodeM, arr);
				buildX(2*nodeIndX+1, nodeM+1, nodeRX, arr);
				for(int i = 0;i<t[0].length;i++) {
					t[nodeIndX][i] = merge(t[2*nodeIndX][i], t[2*nodeIndX+1][i]);
				}
			}
		}
		//build the 1-wide 1D ST with x at nodeX 
		private void build1Wide(int nodeIndX, int nodeX, int[][] arr) {
			build1Wide(nodeIndX, nodeX, 1, 0, sizeY-1, arr);
		}
		//build1Wide recursive helper
		private void build1Wide(int nodeIndX, int nodeX, int nodeIndY, int nodeLY, int nodeRY, int[][] arr) {
			if(nodeLY == nodeRY) t[nodeIndX][nodeIndY] = arr[nodeX][nodeLY];
			else {
				int nodeMY = (nodeLY + nodeRY)/2;
				build1Wide(nodeIndX, nodeX, 2*nodeIndY, nodeLY, nodeMY, arr);
				build1Wide(nodeIndX, nodeX, 2*nodeIndY+1, nodeMY+1, nodeRY, arr);
				t[nodeIndX][nodeIndY] = merge(t[nodeIndX][2*nodeIndY], t[nodeIndX][2*nodeIndY+1]);
			}
		}

		
		public int query(int queryLX, int queryRX, int queryLY, int queryRY) {
			return query(1, 0, sizeX-1, queryLX, queryRX, queryLY, queryRY);
		}
		private int query(int nodeIndX, int nodeLX, int nodeRX, int queryLX, int queryRX, int queryLY, int queryRY) {
			if(queryLX <= nodeLX && nodeRX <= queryRX) {
				return query1D(nodeIndX, queryLY, queryRY);
			}
			
			int nodeMX = (nodeLX + nodeRX)/2;
			if(nodeMX+1 > queryRX) return query(2*nodeIndX, nodeLX, nodeMX, queryLX, queryRX, queryLY, queryRY);
			if(nodeMX < queryLX) return query(2*nodeIndX+1, nodeMX+1, nodeRX, queryLX, queryRX, queryLY, queryRY);
			return merge(query(2*nodeIndX, nodeLX, nodeMX, queryLX, queryRX, queryLY, queryRY),
						 query(2*nodeIndX+1, nodeMX+1, nodeRX, queryLX, queryRX, queryLY, queryRY));
		}
		//query the 1D ST (at index nodeIndX)
		private int query1D(int nodeIndX, int queryLY, int queryRY) {
			return query1D(nodeIndX, 1, 0, sizeY-1, queryLY, queryRY);
		}
		//query1D recursive helper
		private int query1D(int nodeIndX, int nodeIndY, int nodeLY, int nodeRY, int queryLY, int queryRY) {
			if(queryLY <= nodeLY && nodeRY <= queryRY) return t[nodeIndX][nodeIndY];
			
			int nodeMY = (nodeLY + nodeRY)/2;
			if(nodeMY+1 > queryRY) return query1D(nodeIndX, 2*nodeIndY, nodeLY, nodeMY, queryLY, queryRY);
			if(nodeMY < queryLY) return query1D(nodeIndX, 2*nodeIndY+1, nodeMY+1, nodeRY, queryLY, queryRY);
			return merge(query1D(nodeIndX, 2*nodeIndY, nodeLY, nodeMY, queryLY, queryRY), 
					     query1D(nodeIndX, 2*nodeIndY+1, nodeMY+1, nodeRY, queryLY, queryRY));
		}
		
		
		public void update(int updateX, int updateY, int val) {
			update(1, 0, sizeX-1, updateX, updateY, val);
		}
		private void update(int nodeIndX, int nodeLX, int nodeRX, int updateX, int updateY, int val) {
			if(nodeLX == nodeRX) {
				update1Wide(nodeIndX, 1, 0, sizeY-1, updateY, val);
			}
			else {
				int nodeMX = (nodeLX + nodeRX)/2;
				
				if(updateX <= nodeMX) update(2*nodeIndX, nodeLX, nodeMX, updateX, updateY, val);
				else update(2*nodeIndX+1, nodeMX+1, nodeRX, updateX, updateY, val);
				
				update1D(nodeIndX, 1, 0, sizeY-1, updateY, val);
			}
		}
		private void update1D(int nodeIndX, int nodeIndY, int nodeLY, int nodeRY, int updateY, int val) {
			if(nodeLY != nodeRY) {
				int nodeMY = (nodeLY + nodeRY)/2;
				
				if(updateY <= nodeMY) update1D(nodeIndX, 2*nodeIndY, nodeLY, nodeMY, updateY, val);
				else update1D(nodeIndX, 2*nodeIndY+1, nodeMY+1, nodeRY, updateY, val);
			}
			
			t[nodeIndX][nodeIndY] = merge(t[2*nodeIndX][nodeIndY], t[2*nodeIndX+1][nodeIndY]);
		}
		private void update1Wide(int nodeIndX, int nodeIndY, int nodeLY, int nodeRY, int updateY, int val) {
			if(nodeLY == nodeRY) t[nodeIndX][nodeIndY] = val;
			else {
				int nodeMY = (nodeLY + nodeRY)/2;
				
				if(updateY <= nodeMY) update1Wide(nodeIndX, 2*nodeIndY, nodeLY, nodeMY, updateY, val);
				else update1Wide(nodeIndX, 2*nodeIndY+1, nodeMY+1, nodeRY, updateY, val);
				
				t[nodeIndX][nodeIndY] = merge(t[nodeIndX][2*nodeIndY], t[nodeIndX][2*nodeIndY+1]);
			}
		}
		
		public int merge(int a, int b) {
			return a+b;
		}
	}
}
