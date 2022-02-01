package segmenttrees.tests;

import java.util.Arrays;
import segmenttrees.MySegmentTree.SegmentTree;
import segmenttrees.MyDynamicST.DynamicSegmentTree;

public class STAccuracyTest {
	public static void main(String[] args) {
		final int size = 1000000;
		final int numElements = 100000;
		final int queriesOrUpdates = 100000;
		int[] arr = new int[size];
		Arrays.fill(arr, 0);
		SegmentTree st = new SegmentTree(arr);
		DynamicSegmentTree sparseSt = new DynamicSegmentTree(size, numElements);
		
		int disagreeCount = 0;
		int queryCount = 0;
		for(int i = 0;i<queriesOrUpdates;i++) {
			if(Math.random() < 0.5) {
				int ind = (int) (Math.random() * size);
				int val = (int) (Math.random() * numElements);
				//System.out.println("update: " + ind + " " + val);
				st.update(ind, val);
				sparseSt.update(ind, val);
			}
			else {
				queryCount++;
				int l = (int) (Math.random() * size);
				int r = (int) (Math.random() * size);
				if(l > r) {
					int temp = l;
					l = r;
					r = temp;					
				}
				//System.out.println("query: " + l + " " + r);
				
				int stRes = st.query(l, r);
				int sparseStRes = sparseSt.query(l, r);
				if(stRes != sparseStRes) {
					System.out.println("disagree " + stRes + " " + sparseStRes);
					disagreeCount++;
				}
			}
		}
		System.out.println("total queries: " + queryCount);
		System.out.println("disagree: " + disagreeCount);
	}
}
