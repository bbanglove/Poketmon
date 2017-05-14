package designPrjAlgorithm;

public class Vertex {
	private int vertexNum; //노드의 위치 번호
	private int mMonsterId; //노드에 위치한 몬스터의 아이디
	private int mUserId; //노드에 위치한 유저의 아이디
	private int visited; //방문되었는지 여부
	private boolean pocketStop; //포켓스탑인지 여부
	
	//constructor
	public Vertex(int vnum, boolean pStop){
		this.vertexNum = vnum;
		this.mMonsterId = 0;
		this.mUserId = 0;
		this.visited = 0;
		this.pocketStop = pStop;
	}
	public Vertex(int vnum, int monsterId){
		this.vertexNum = vnum;
		this.mMonsterId = monsterId;
		this.mUserId = 0;
		this.visited = 0;
		this.pocketStop = false;
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
