package designPrjAlgorithm;

import designPrjAlgorithm.Graph;
import java.util.List;

public class Floyd {
	private Graph mGraph; //�Է� �׷���
	private List<Vertex> shortestPath;
	
	//Constructor
	public Floyd(){
		this.mGraph = null;
		this.shortestPath = null;
	}
	public Floyd(Graph graph){
		mGraph = graph;
		shortestPath = null;
	}
	
	//get Function
	public Graph getGraph(){
		return this.mGraph;
	}
	
	public List<Vertex> getShortestPath(){
		return this.shortestPath;
	}
	
	//set Function
	public void setGraph(Graph graph){
		this.mGraph = graph;
	}
	public void setShortestPath(List<Vertex> list){
		this.shortestPath = list;
	}
	public List<Vertex> calcShortest()//�ִ� ��� ��� �� ��ȯ �Լ�
	{	
		for(int k = 0; k<mGraph.getSize(); k++){ //��ü ����� ���� 
			for(int i =0; i<mGraph.getSize(); i++){
				for(int j = 0; j<mGraph.getSize(); j++){
					if(mGraph.getEdgeList()[i][j].getWeight() > mGraph.getEdgeList()[i][k].getWeight() + mGraph.getEdgeList()[k][j].getWeight()){ //���� ���� ��ο� �׳� ���� ��θ� ���ؼ� �ٷ� ���� ���� �� �����ٸ�
						this.shortestPath.add(mGraph.getVertexList()[i][k]); //���� ���� ��θ� �ִ´�.
						this.shortestPath.add(mGraph.getVertexList()[k][j]);
					}
					else{//�ٷ� ���� ���� �� ����
						this.shortestPath.add(mGraph.getVertexList()[i][j]);
					}
				}
			}
		}
		
		return this.shortestPath;
	}
	
	

}
