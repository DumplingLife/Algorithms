package graphs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class MyDGShortestPath {
	public static void main(String[] args) {
		DGShortestPath graph = new DGShortestPath(6);
		graph.addEdge(0, 1);
		graph.addEdge(1, 2);
		graph.addEdge(0, 2);
		graph.addEdge(2, 3);
		graph.addEdge(3, 4);
		graph.addEdge(3, 5);
		graph.findShortestPaths(0);
		System.out.println(graph.getShortestPath(5));
	}
	static class DGShortestPath {
		Node[] nodes;
		class Node {
			HashSet<Node> out = new HashSet<>();
			int shortestPath = Integer.MAX_VALUE;
		}
		public DGShortestPath(int size) {
			nodes = new Node[size];
			for(int i = 0;i<size;i++) {
				nodes[i] = new Node();
			}
		}
		public void addEdge(int a, int b) {
			nodes[a].out.add(nodes[b]);
		}
		public void findShortestPaths(int rootIndex) {
			Queue<Node> queue = new LinkedList<>();
			HashSet<Node> settled = new HashSet<>();
			
			nodes[rootIndex].shortestPath = 0;
			queue.add(nodes[rootIndex]);
			while(!queue.isEmpty()) {
				Node node = queue.poll();
				settled.add(node);
				for(Node neighbor : node.out) {
					if(settled.contains(neighbor)) continue;
					neighbor.shortestPath = node.shortestPath + 1;
					queue.add(neighbor);
					settled.add(neighbor);
				}
			}
		}
		public int getShortestPath(int nodeIndex) {
			return nodes[nodeIndex].shortestPath;
		}
	}
}
