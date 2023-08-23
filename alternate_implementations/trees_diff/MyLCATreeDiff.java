/*
 * good
 * Tested with Algorithms/tests/LCAComparison.java
 * 		random tree with size 10,000 and 10,000 queries, compared against original
 */

package trees_diff;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;

public class MyLCATreeDiff {
	public static void main(String[] args) {
		ArrayList<Integer>[] adjLists = new ArrayList[8];
		for(int i=0; i<adjLists.length; i++) adjLists[i] = new ArrayList<>();
		adjLists[0].add(1);
		adjLists[1].add(0);
		adjLists[0].add(2);
		adjLists[2].add(0);
		adjLists[2].add(3);
		adjLists[3].add(2);
		adjLists[3].add(4);
		adjLists[4].add(3);
		adjLists[4].add(5);
		adjLists[5].add(4);
		adjLists[6].add(5);
		adjLists[5].add(6);
		adjLists[1].add(7);
		adjLists[7].add(1);
		LCATreeDiff tree = new LCATreeDiff(adjLists);
		System.out.println(tree.findLCA(4, 7));
	}
	
	public static class LCATreeDiff {
		Node[] nodes;
		final int K = 20;
		class Node {
			Node parent;
			Node[] parents = new Node[K];
			HashSet<Node> children = new HashSet<>();
			int index;
			int depth;
			public Node(int index) {
				this.index = index;
			}
			@Override
			public String toString() {
				return index + "";
			}
		}
		public LCATreeDiff(ArrayList<Integer>[] adjLists) {
			nodes = new Node[adjLists.length];
			for(int i=0; i<adjLists.length; i++) nodes[i] = new Node(i);
			Queue<Integer> queue = new ArrayDeque<>();
			nodes[0].depth = 0;
			queue.add(0);
			while(!queue.isEmpty()) {
				int node = queue.poll();
				for(int neighbor : adjLists[node]) {
					if(nodes[neighbor] == nodes[node].parent) continue;
					nodes[neighbor].parent = nodes[node];
					nodes[neighbor].depth = nodes[node].depth + 1;
					nodes[node].children.add(nodes[neighbor]);
					queue.add(neighbor);
				}
			}
			
			for(int i=0; i<nodes.length; i++) {
				nodes[i].parents[0] = nodes[i].parent;
			}
			for(int k=1; k<K; k++) {
				for(int i=0; i<nodes.length; i++) {
					if(nodes[i].parents[k-1] == null) nodes[i].parents[k] = null;
					else nodes[i].parents[k] = nodes[i].parents[k-1].parents[k-1]; 
				}
			}
		}
		
		
		public int findLCA(int aInd, int bInd) {
			Node a = nodes[aInd];
			Node b = nodes[bInd];
			//make a the deeper one
			if(a.depth < b.depth) {
				Node temp = a;
				a = b;
				b = temp;
			}
			
			while(a.depth > b.depth) {
				int k = 0;
				for(; k<K; k++) {
					if(a.parents[k] == null || a.parents[k].depth < b.depth) {
						k--;
						break;
					}
				}
				a = a.parents[k];
			}
			return findLCASameDepth(a, b).index;
		}
		
		public Node findLCASameDepth(Node a, Node b) {
			if(a == b) return a;
			
			int k = 0;
			for(; k<K; k++) {
				if(a.parents[k] == b.parents[k]) {
					k--;
					break;
				}
			}
			if(k == -1) return a.parent;
			else return findLCASameDepth(a.parents[k], b.parents[k]);
		}
	}
}
