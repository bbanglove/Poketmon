package designPrjAlgorithm;

import designPrjAlgorithm.Vertex;
import designPrjAlgorithm.Edge;
import java.util.ArrayList;

public class Graph {
	private Vertex[][] vertexList;
	private Edge[][] edgeList;
	private int sizeOfArray;
	
	//constructor
	public Graph()
	{
		vertexList = new Vertex[100][100];
		edgeList = new Edge[100][100];
		this.sizeOfArray = 100;
		
	}
	public Graph(Vertex[][] vList, Edge[][] eList, int size)
	{
		this.vertexList = vList;
		this.edgeList = eList;
		this.sizeOfArray = size;
	}
	
	public Vertex[][] getVertexList(){
		return this.vertexList;
	}
	
	public Edge[][] getEdgeList(){
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