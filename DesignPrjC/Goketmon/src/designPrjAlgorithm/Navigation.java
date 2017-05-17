package designPrjAlgorithm;

import designPrjAlgorithm.Graph;
import designPrjAlgorithm.TSP;
import designPrjAlgorithm.Floyd;
import java.util.List;

public class Navigation {
	private Graph mGraph; //��ǲ���� ���� �׷��� ��ü.
	private TSP mTsp; //tsp ��ü
	private Floyd mFloyd;//�÷��̵� ��ü
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
	
	
	
	//ù���� ��쿡 ����
	public void algorithmMode1(int monsterId, int inputTime)
	{
		
	}

}
