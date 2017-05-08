package designPrjAlgorithm;

import designPrjAlgorithm.Vertex;
import designPrjAlgorithm.Edge;
import java.util.ArrayList;

public class Graph {
	private ArrayList<Vertex> vertexList;
	private ArrayList<Edge> edgeList;
	
	//constructor
	public Graph(ArrayList<Vertex> vList, ArrayList<Edge> eList)
	{
		this.vertexList = vList;
		this.edgeList = eList;
	}
	
	public ArrayList<Vertex> getVertexList(){
		return this.vertexList;
	}
	
	public ArrayList<Edge> getEdgeList(){
		return this.edgeList;
	}

}
