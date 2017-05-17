package designPrjAlgorithm;

import designPrjAlgorithm.Vertex;
import designPrjAlgorithm.Edge;
import java.util.ArrayList;

public class Graph {
	private ArrayList<Vertex> vertexList = new ArrayList<Vertex>(); //100짜리 1차원 행렬로 바꿈
	private ArrayList<ArrayList<Edge>> edgeList = new ArrayList<ArrayList<Edge>>();
	private int sizeOfArray;
	
	//constructor
	public Graph()
	{
		this.sizeOfArray = 100;
		
	}
	public Graph(ArrayList<Vertex> vList, ArrayList<ArrayList<Edge>> eList, int size)
	{
		this.vertexList = vList;
		this.edgeList = eList;
		this.sizeOfArray = size;
	}
	
	public ArrayList<Vertex> getVertexList(){
		return this.vertexList;
	}
	
	public ArrayList<ArrayList<Edge>> getEdgeList(){
		return this.edgeList;
	}
	
	public int getSize(){
		return this.sizeOfArray;
	}

}

/*
 * 메모
 * floyd 짤 때  결정할 것 : 가중치, vertex를 어떤 식으로 더해나갈 것인지, 파일입출력으로 받은 데이터는 어떤 형태인지 결정
 * 자료형은 기본형대로 edge, vertex형태가 적정한지 아니면 변형할 것인지
 * 
 */