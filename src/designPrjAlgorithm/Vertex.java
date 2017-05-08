package designPrjAlgorithm;
import java.util.ArrayList;

public class Vertex {
	private int vertexNum;
	private int mMonsterId;
	private int mUserId;
	private int visited;
	private boolean pocketStop;
	private ArrayList<Vertex> adjacentVertex = new ArrayList<Vertex>();
	
	//constructor
	public Vertex(int vnum, boolean pStop){
		this.vertexNum = vnum;
		this.mMonsterId = 0;
		this.mUserId = 0;
		this.visited = 0;
		this.pocketStop = pStop;
	}
	
	//get Function
	public int getVertexNum(){return this.vertexNum;}
	public int getMonsterId() {return this.mMonsterId;}
	public int getUserId(){return  this.mUserId;}
	public int getVistedFlag() {return this.visited;}
	public boolean getPocketStop() {return this.pocketStop;}
	
	//set Function
	public void setVertexNum(int vnum){ this.vertexNum = vnum;}
	public void setMonsterId(int monsterId){this.mMonsterId = monsterId;}
	public void setUserId(int userId){this.mUserId = userId;}
	public void setVisitedFlag(int vFlag){this.visited = vFlag;}
	public void setPocketStop(boolean pStop){ this.pocketStop = pStop;}


}
