package designPrjAlgorithm;

import designPrjAlgorithm.Graph;
import java.util.List;

public class Floyd {
	private Graph mGraph; //입력 그래프
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
	public List<Vertex> calcShortest()//최단 경로 계산 및 반환 함수
	{	
		for(int k = 0; k<mGraph.getSize(); k++){ //전체 사이즈에 대해 
			for(int i =0; i<mGraph.getSize(); i++){
				for(int j = 0; j<mGraph.getSize(); j++){
					if(mGraph.getEdgeList()[i][j].getWeight() > mGraph.getEdgeList()[i][k].getWeight() + mGraph.getEdgeList()[k][j].getWeight()){ //거쳐 가는 경로와 그냥 가는 경로를 비교해서 바로 가는 것이 더 느리다면
						this.shortestPath.add(mGraph.getVertexList()[i][k]); //거쳐 가는 경로를 넣는다.
						this.shortestPath.add(mGraph.getVertexList()[k][j]);
					}
					else{//바로 가는 것이 더 빠르
						this.shortestPath.add(mGraph.getVertexList()[i][j]);
					}
				}
			}
		}
		
		return this.shortestPath;
	}
	
	

}
