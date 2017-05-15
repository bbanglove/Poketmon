package designPrjAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import designPrjAlgorithm.Edge;


//전체 그래프나 엣지리스트가 아닌 weight만 받는 경우의플로이드 알고리즘
//플로이드는 
public class Floyd {

    // graph represented by an adjacency matrix
    //private Edge[][] graph = new Edge[100][100];//죄단 경로가 담기는 
    private ArrayList<ArrayList<Edge>> graph = new ArrayList<ArrayList<Edge>>();
	public static final int INF = 1000000000; //리턴값 default  - 연결되어 있지 않은경우 가중치를 무한대에 가깝게 설정한다.
    private int size;// 실제 들어있는 원소의 갯수를 저장할 size 변수
   // private Edge[][] dimen = new Edge[100][100];//원본 가중치 행렬
    private ArrayList<Edge> eSelected = new ArrayList<Edge>();
    private ArrayList<Integer> selectedEdgeNum = new ArrayList<Integer>();
    private int[][] visited = new int[100][100];
    
    
    public ArrayList<ArrayList<Edge>> getGraph()//최단경로 그래프를 반환한다.
    {
    	return graph;
    }
    
    public int[][] getVisited()
    {
    	return visited;
    }
    
    public int getvisitedVNum(int i,int j)
    {
    	return visited[i][j];
    }
    
    public ArrayList<Edge> getSelectedEdge()
    {
    	return this.eSelected;
    }
    //constrouctor
    public Floyd(int n, ArrayList<ArrayList<Edge>> weight) { //엣지 배열을 받아 초기화를 진행한다.
        this.size = n;//입력 행렬의 크기로 사이즈를 초기화
        for(int i = 0; i<100; i++)
        	for(int j = 0; j<100; j++)
        		visited[i][j] = 0;
        for(int i = 0; i<n; i++)//전체 크기에 대해
        {
        	ArrayList<Edge> eRow = new ArrayList<Edge>();
        	for(int j = 0; j<n; j++){
        		eRow.add(weight.get(i).get(j));
        		//System.arraycopy(weight, 0, graph, 0, weight.length);
        		//System.arraycopy(weight, 0, dimen, 0, weight.length);
        		//graph[i][j] = weight[i][j];//원본 행렬을 초기화한다.
        		//dimen[i][j] = weight[i][j];
        		if(eRow.get(j).getWeight() == -1){//만일 연결되어 있지 않다면,가중치를 무한대로 설정한다.
        			eRow.get(j).setWeight(INF);
        			//dimen[i][j].setWeight(INF);
        		}
        	}
        	graph.add(eRow);
        }
    }
    //중복된 경로를 지나야 하는 경로에 대해서는 생각할 필요가 없다.
    //왜냐하면 전체적으로 Floyd를 적용하는 것이 아니라, 각각의 독립된 경로 - 노드 대 노드 - 로 진행하는 것이기 때문.
    public void addEdgeNum(int eNum){//엣지 넘버를 체크해 만일 안 들어있다면 selectedEdgeNum 리스트에 포함시킨다.
    	for(int i = 0; i < this.selectedEdgeNum.size();i++){
    		if(this.selectedEdgeNum.get(i) == eNum)
    			return;
    	}
    	this.selectedEdgeNum.add(eNum);
    }
    
    public void getShortestPath(ArrayList<Vertex> vList){ //최단경로를 구하는 함수
    	for(int k = 0; k<this.size; k++){//전체 사이즈 만큼 반복한다.사이점
    		for(int i = 0; i<this.size; i++){//시작점
    			for(int j = 0; j<this.size; j++){//중간점
    				if(graph.get(i).get(j).getWeight() > graph.get(i).get(k).getWeight() + graph.get(k).get(j).getWeight()){//만일 거쳐가는 것이 더 빠르다면
    					//eSelected.add(dimen[i][k]);
    					//eSelected.add(dimen[k][j]);
    					graph.get(i).get(j).setWeight(graph.get(i).get(k).getWeight() + graph.get(k).get(j).getWeight()); //해당 최소 가중치로 변경한다.
    					graph.get(i).get(j).setPostVertex(graph.get(k).get(j).getPostVertex());
    					this.visited[i][j] = vList.get(k).getVertexNum()-1;
    				if(graph.get(i).get(k).getPostVertex().getPocketStop()) //만일 중간 경로에 있는 vertex가 포켓스탑이라면
    						graph.get(i).get(j).addPocketStop(); //해당 엣지의 포켓스탑 갯수를 증가시킨다.
    					//dimen[i][j] = k;
    					//this.addEdgeNum(dimen[i][k].getEdgeNum());
    					//this.addEdgeNum(dimen[k][j].getEdgeNum());
    			
    				}
    				else{
    					//eSelected.add(dimen[i][j]);
    					//this.addEdgeNum(dimen[i][j].getEdgeNum());
    				}
    				//this.visited.add(vList.get(j));
    				//vList.get(j).setVisitedFlag(1);//방문으로 바꾼다.
    			}
    		}
    	}
    }
/*
	public static void main(String[] args)//테스트용 메인함수
	{
		int [][] temp = {{0, 3, -1, -1}, {-1, 0,12, 6}, {-1, 5, 0, 7}, {2, 9, 4, 0},};
		Edge[][] etemp = new Edge[3][3];
		
		for(int i = 0; i<3; i++)
		{
			for(int j = 0; j<3; j++)
			{
				Edge t =  new Edge(i*10 + j, temp[i][j]);
				etemp[i][j] = t;
			}
		}
		
		Floyd f = new Floyd(3, etemp);
		f.getShortestPath();
		for(int i = 0; i<3; i++){
			for(int j = 0; j<3; j++){
				System.out.println(f.getGraph()[i][j].getWeight());
			}
		}
		
		
	}
*/

}
