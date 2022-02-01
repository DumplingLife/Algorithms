/*
 * good, 2x faster
 * tested in Algorithms/test/DijkstraComparison.java
 * 		For the test, make sure you set infinity to the same value for both versions
 */

package graphs_diff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

public class MyDijkstraDiff {
	public static void main(String[] args) {
		GraphDiff graph = new GraphDiff(5);
		graph.addEdge(0, 1, 1);
		graph.addEdge(0, 2, 1);
		graph.addEdge(2, 3, 1);
		graph.addEdge(3, 4, 1);
		graph.addEdge(0, 4, 5);
		graph.dijkstra();
		System.out.println(Arrays.toString(graph.dists));
	}
	
	public static class GraphDiff {
		Node[] nodes;
		public int[] dists;
		class Node {
			ArrayList<Node> neighbors = new ArrayList<>();
			ArrayList<Integer> edgeLens = new ArrayList<>();
			int index;
			public Node(int index) {
				this.index = index;
			}
		}
		class Pair {
			Node node;
			int dist;
			public Pair(Node node, int dist) {
				this.node = node;
				this.dist = dist;
			}
		}
		public GraphDiff(int N) {
			nodes = new Node[N];
			dists = new int[N];
			for(int i=0; i<N; i++) {
				nodes[i] = new Node(i);
				dists[i] = 1000000000;
			}
		}
		public void addEdge(int a, int b, int edgeLen) {
			nodes[a].neighbors.add(nodes[b]);
			nodes[a].edgeLens.add(edgeLen);
			nodes[b].neighbors.add(nodes[a]);
			nodes[b].edgeLens.add(edgeLen);
		}
		public void dijkstra() {
			PriorityQueue<Pair> pq = new PriorityQueue<>((a,b) -> a.dist - b.dist);
			HashSet<Node> settled = new HashSet<>();
			dists[0] = 0;
			pq.add(new Pair(nodes[0], 0));
			while(!pq.isEmpty()) {
				Pair pair = pq.poll();
				Node node = pair.node;
				int dist = pair.dist;
				if(settled.contains(node)) continue;
				settled.add(node);
				
				for(int i=0; i<node.neighbors.size(); i++) {
					int newDist = dist + node.edgeLens.get(i);
					Node neighbor = node.neighbors.get(i);
					if(newDist < dists[neighbor.index]) {
						dists[neighbor.index] = newDist;
						pq.add(new Pair(neighbor, newDist));
					}
				}
			}
		}
	}
}
