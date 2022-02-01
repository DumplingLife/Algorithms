package graphs.tests;

import graphs.dijkstras.MyDijkstra.Graph;
import graphs_diff.MyDijkstraDiff.GraphDiff;

import test.MyTimer;

public class DijkstraDiffComparison {
	public static void main(String[] args) {
		MyTimer timer = new MyTimer();
		int N = 100000;
		int M = 200000;
		
		int[] arr1 = new int[M], arr2  = new int[M], arr3 = new int[M];
		for(int i=0; i<M; i++) {
			arr1[i] = (int) (Math.random() * N);
			arr2[i] = (int) (Math.random() * N);
			arr3[i] = (int) (Math.random() * 100000);
		}
		timer.lap("variables");
		
		Graph graph = new Graph(N);
		GraphDiff graphDiff = new GraphDiff(N);
		for(int i=0; i<M; i++) {
			graph.addEdge(arr1[i], arr2[i], arr3[i]);
			graphDiff.addEdge(arr1[i], arr2[i], arr3[i]);
		}
		timer.lap("create");
		
		graph.dijkstra();
		timer.lap("dijkstra");
		
		graphDiff.dijkstra();
		timer.lap("graphDiff dijkstra");
		
		for(int i=0; i<N; i++) {
			if(graph.nodes[i].val != graphDiff.dists[i]) {
				System.out.println(i + " " + graph.nodes[i].val + " " + graphDiff.dists[i]);
			}
		}
	}
}
