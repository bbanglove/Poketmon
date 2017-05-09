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
 * �޸�
 * floyd © ��  ������ �� : ����ġ, vertex�� � ������ ���س��� ������, ������������� ���� �����ʹ� � �������� ����
 * �ڷ����� �⺻����� edge, vertex���°� �������� �ƴϸ� ������ ������
 * 
 */