/*
 * (not verified, but crudely tested)
 * 
 * temporarily use adjacency list instead of TempGraph
 */

package trees_diff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class MyTreeDiff {
	public static void main(String[] args) {
		ArrayList<Integer>[] adjLists = new ArrayList[6];
		for(int i=0; i<6; i++) {
			adjLists[i] = new ArrayList<>();
		}
		adjLists[0].add(1);
		adjLists[1].add(0);
		adjLists[0].add(2);
		adjLists[2].add(0);
		adjLists[0].add(4);
		adjLists[4].add(0);
		adjLists[1].add(3);
		adjLists[3].add(1);
		adjLists[2].add(5);
		adjLists[5].add(2);
		TreeDiff tree = new TreeDiff(adjLists);
		
		System.out.println("breakpoint");
	}
	
	static class TreeDiff {
		static class Node {
			int id;
			Node parent = null;
			HashSet<Node> children = new HashSet<>();
			public Node(int id) {
				this.id = id;
			}
			@Override
			public String toString() {
				return id + "";
			}
		}
		
		Node[] nodes;
		public TreeDiff(ArrayList<Integer>[] adjLists) {
			nodes = new Node[adjLists.length];
			for(int i=0; i<adjLists.length; i++) {
				nodes[i] = new Node(i);
			}
			build(0, adjLists);
		}
		public void build(int node, ArrayList<Integer>[] adjLists) {
			for(int neighbor : adjLists[node]) {
				if(nodes[neighbor] == nodes[node].parent) continue;
				nodes[node].children.add(nodes[neighbor]);
				nodes[neighbor].parent = nodes[node];
				build(neighbor, adjLists);
			}
		}
	}
}
