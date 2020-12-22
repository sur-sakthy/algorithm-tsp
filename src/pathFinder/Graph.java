// Graph.java 
// S.Somaskandan, DCU, 2016

package pathFinder;

import java.util.ArrayList;

public class Graph {
	private final int v;
	private int E;
	private boolean[][] adj;

	//	Constructor
	public Graph(int v){
		this.v=v;		// Number of vertices
		this.E=0;		// Initializing the number of edges
		adj=new boolean[v][v];	// Initializing the array of adjacent vertices
	}

	//	Returns the number of vertices
	public int v(){ return v; }

	//	Returns the number of edges
	public int E(){ return E; }

	//	Adding an edge using two vertices
	public void addEdge(int v, int w){
		adj[v][w]=true;		//	True if edge is present
		E++;				// 	Edge is incremented by 1
	}

	//	Return a list of adjacent vertices to the current vertex
	public ArrayList<Integer> adj(int v){
		ArrayList<Integer> adjvertices=new ArrayList<Integer>();
		if(v>=this.v){ return null; }
		for(int i=0;i<this.v;i++){
			if(adj[v][i] || adj[i][v]){
				adjvertices.add(i);
			}
		}

		return adjvertices;
	}
}
