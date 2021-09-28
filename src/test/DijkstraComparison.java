package test;

import java.io.File;
import java.io.IOException;

import fastGraphs.MyFastDijkstra.Graph;

public class DijkstraComparison {
	public static void main(String[] args) throws IOException {
		File file = new File("graph2M.in"); 
		MyReader reader = new MyReader(file);

		MyTimer timer = new MyTimer();
		int N = reader.nextInt();
		int M = reader.nextInt();
		
		int[] arr1 = new int[M], arr2  = new int[M], arr3 = new int[M];
		for(int i=0; i<N-1; i++) {
			arr1[i] = reader.nextInt()-1;
			arr2[i] = reader.nextInt()-1;
			arr3[i] = reader.nextInt();
		}
		timer.lap("read");
		
		Graph graph = new Graph(N,M,arr1, arr2,arr3);
		timer.lap("create graph");
		
		graph.dijkstra();
		timer.lap("dijkstra");
		
		int breakpoint = 0;
	}
}
