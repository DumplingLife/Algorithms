package test;

import java.io.File;
import java.io.IOException;

import fastGraphs.MyFastDFSTree.Graph;
import fastGraphs.MyFastDFSTree.DFSTree;

public class DFSTreeComparison {
	public static void main(String[] args) throws IOException {
		//DFSTree only works on connected graphs. graph2M.in is connected, but not graph1M.in
		File file = new File("graph2M.in"); 
		MyReader reader = new MyReader(file);
		
		int N = reader.nextInt();
		int M = reader.nextInt();
		MyTimer timer = new MyTimer();
		
		
		int[] arr1 = new int[M], arr2  = new int[M];
		for(int i=0; i<M; i++) {
			arr1[i] = reader.nextInt()-1;
			arr2[i] = reader.nextInt()-1;
			reader.nextInt();
		}
		timer.lap("read");
		
		Graph graph = new Graph(N,M,arr1, arr2);
		timer.lap("create graph");
		
		/*
		TempGraph graph = new TempGraph(N);
		for(int i=0; i<N-1; i++) {
			graph.addEdge(reader.nextInt()-1, reader.nextInt()-1);
		}
		*/
		
		//timer.startInterval();
		DFSTree tree = new DFSTree(graph);
		timer.lap("create tree");
		
		int breakpoint = 0;
	}
}
