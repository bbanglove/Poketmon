package designPrjAlgorithm;

import designPrjAlgorithm.Vertex;

public class Edge {
	private int edgeNum;
	private int weight;
	private Vertex source;
	private Vertex destination;
	
	//constructor
	public Edge(int n, int w)
	{
		this.edgeNum = n;
		this.weight = w;
		this.source = null;
		this.destination = null;
	}
	public Edge(int eNum, int w, Vertex pre, Vertex post)
	{
		this.edgeNum = eNum;
		this.weight = w;
		this.source = pre;
		this.destination = post;
	}
	public void copyEdge(Edge temp)
	{
		this.edgeNum = temp.getEdgeNum();
		this.weight = temp.getWeight();
		this.source = temp.getPreVertex();
		this.destination = temp.getPostVertex();
	}
	//get Function
	public int getEdgeNum(){return edgeNum;}
	public int getWeight() {return weight;}
	public Vertex getPreVertex(){return source;}
	public Vertex getPostVertex(){return destination;}
	
	//set Function
	public void setEdgeNum(int eNum){ this.edgeNum = eNum;}
	public void setWeight(int w){this.weight = w;}
	public void setPreVertex(Vertex pre){this.source = pre;}
	public void setPostVertex(Vertex post){this.destination = post;}
	
	//operations
	public void disConnect()
	{
		this.source = null;
		this.destination = null;
	}
	
	public void reConnect(Vertex pre, Vertex post)
	{
		this.source = pre;
		this.destination = post;
	}
	

}
