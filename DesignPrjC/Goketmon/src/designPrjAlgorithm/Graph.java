package designPrjAlgorithm;

import designPrjAlgorithm.Vertex;
import designPrjAlgorithm.Edge;
import java.util.ArrayList;

public class Graph {
	private ArrayList<Vertex> vertexList = new ArrayList<Vertex>(); //100¥�� 1���� ��ķ� �ٲ�
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
 * �޸�
 * floyd © ��  ������ �� : ����ġ, vertex�� � ������ ���س��� ������, ������������� ���� �����ʹ� � �������� ����
 * �ڷ����� �⺻����� edge, vertex���°� �������� �ƴϸ� ������ ������
 * 
 */