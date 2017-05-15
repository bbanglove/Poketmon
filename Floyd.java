package designPrjAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import designPrjAlgorithm.Edge;


//��ü �׷����� ��������Ʈ�� �ƴ� weight�� �޴� ������÷��̵� �˰���
//�÷��̵�� 
public class Floyd {

    // graph represented by an adjacency matrix
    //private Edge[][] graph = new Edge[100][100];//�˴� ��ΰ� ���� 
    private ArrayList<ArrayList<Edge>> graph = new ArrayList<ArrayList<Edge>>();
	public static final int INF = 1000000000; //���ϰ� default  - ����Ǿ� ���� ������� ����ġ�� ���Ѵ뿡 ������ �����Ѵ�.
    private int size;// ���� ����ִ� ������ ������ ������ size ����
   // private Edge[][] dimen = new Edge[100][100];//���� ����ġ ���
    private ArrayList<Edge> eSelected = new ArrayList<Edge>();
    private ArrayList<Integer> selectedEdgeNum = new ArrayList<Integer>();
    private int[][] visited = new int[100][100];
    
    
    public ArrayList<ArrayList<Edge>> getGraph()//�ִܰ�� �׷����� ��ȯ�Ѵ�.
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
    public Floyd(int n, ArrayList<ArrayList<Edge>> weight) { //���� �迭�� �޾� �ʱ�ȭ�� �����Ѵ�.
        this.size = n;//�Է� ����� ũ��� ����� �ʱ�ȭ
        for(int i = 0; i<100; i++)
        	for(int j = 0; j<100; j++)
        		visited[i][j] = 0;
        for(int i = 0; i<n; i++)//��ü ũ�⿡ ����
        {
        	ArrayList<Edge> eRow = new ArrayList<Edge>();
        	for(int j = 0; j<n; j++){
        		eRow.add(weight.get(i).get(j));
        		//System.arraycopy(weight, 0, graph, 0, weight.length);
        		//System.arraycopy(weight, 0, dimen, 0, weight.length);
        		//graph[i][j] = weight[i][j];//���� ����� �ʱ�ȭ�Ѵ�.
        		//dimen[i][j] = weight[i][j];
        		if(eRow.get(j).getWeight() == -1){//���� ����Ǿ� ���� �ʴٸ�,����ġ�� ���Ѵ�� �����Ѵ�.
        			eRow.get(j).setWeight(INF);
        			//dimen[i][j].setWeight(INF);
        		}
        	}
        	graph.add(eRow);
        }
    }
    //�ߺ��� ��θ� ������ �ϴ� ��ο� ���ؼ��� ������ �ʿ䰡 ����.
    //�ֳ��ϸ� ��ü������ Floyd�� �����ϴ� ���� �ƴ϶�, ������ ������ ��� - ��� �� ��� - �� �����ϴ� ���̱� ����.
    public void addEdgeNum(int eNum){//���� �ѹ��� üũ�� ���� �� ����ִٸ� selectedEdgeNum ����Ʈ�� ���Խ�Ų��.
    	for(int i = 0; i < this.selectedEdgeNum.size();i++){
    		if(this.selectedEdgeNum.get(i) == eNum)
    			return;
    	}
    	this.selectedEdgeNum.add(eNum);
    }
    
    public void getShortestPath(ArrayList<Vertex> vList){ //�ִܰ�θ� ���ϴ� �Լ�
    	for(int k = 0; k<this.size; k++){//��ü ������ ��ŭ �ݺ��Ѵ�.������
    		for(int i = 0; i<this.size; i++){//������
    			for(int j = 0; j<this.size; j++){//�߰���
    				if(graph.get(i).get(j).getWeight() > graph.get(i).get(k).getWeight() + graph.get(k).get(j).getWeight()){//���� ���İ��� ���� �� �����ٸ�
    					//eSelected.add(dimen[i][k]);
    					//eSelected.add(dimen[k][j]);
    					graph.get(i).get(j).setWeight(graph.get(i).get(k).getWeight() + graph.get(k).get(j).getWeight()); //�ش� �ּ� ����ġ�� �����Ѵ�.
    					graph.get(i).get(j).setPostVertex(graph.get(k).get(j).getPostVertex());
    					this.visited[i][j] = vList.get(k).getVertexNum()-1;
    				if(graph.get(i).get(k).getPostVertex().getPocketStop()) //���� �߰� ��ο� �ִ� vertex�� ���Ͻ�ž�̶��
    						graph.get(i).get(j).addPocketStop(); //�ش� ������ ���Ͻ�ž ������ ������Ų��.
    					//dimen[i][j] = k;
    					//this.addEdgeNum(dimen[i][k].getEdgeNum());
    					//this.addEdgeNum(dimen[k][j].getEdgeNum());
    			
    				}
    				else{
    					//eSelected.add(dimen[i][j]);
    					//this.addEdgeNum(dimen[i][j].getEdgeNum());
    				}
    				//this.visited.add(vList.get(j));
    				//vList.get(j).setVisitedFlag(1);//�湮���� �ٲ۴�.
    			}
    		}
    	}
    }
/*
	public static void main(String[] args)//�׽�Ʈ�� �����Լ�
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
