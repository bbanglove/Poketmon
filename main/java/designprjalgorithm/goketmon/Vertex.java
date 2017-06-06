package designprjalgorithm.goketmon;

/**
 * Created by 이석영 on 2017-06-01 0001.
 */
public class Vertex {
    private int vertexNum; //노드 번호
    private int mMonsterId; //노드에 존재하는몬스터의 아이디
    private int mUserId; //현재 노드에 있는 유저의 아이디
    private int visited; //방문했는지여부를 저장하는 플래그
    private boolean pocketStop; //포켓스탑이 존재하는지 여부

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
