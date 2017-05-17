package designPrjAlgorithm;

import designPrjAlgorithm.Graph;
import designPrjAlgorithm.TSP;
import designPrjAlgorithm.Floyd;
import java.util.List;

public class Navigation {
	private Graph mGraph; //인풋으로 받을 그래프 객체.
	private TSP mTsp; //tsp 객체
	private Floyd mFloyd;//플로이드 객체
	private List<Vertex> solutionList;//
	
	//constructor
	public Navigation()
	{
		this.mGraph = null;
		this.mTsp = null;
		this.mFloyd = null;	
		this.solutionList =  null;
	}
	
	
	//get Function
	public Graph getGraph(){return this.mGraph;}
	public TSP getTsp(){return this.mTsp;}
	public Floyd getFloyd(){return this.mFloyd;}
	public List<Vertex> getSolutionList(){return this.solutionList;}
	
	
	
	//첫번쨰 경우에 대해
	public void algorithmMode1(int monsterId, int inputTime)
	{
		
	}

}
