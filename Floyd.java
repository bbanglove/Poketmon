package designPrjAlgorithm;

import java.util.Arrays;
import designPrjAlgorithm.Edge;


//��ü �׷����� ��������Ʈ�� �ƴ� weight�� �޴� ������÷��̵� �˰���
public class Floyd {

    // graph represented by an adjacency matrix
    private Edge[][] graph = new Edge[100][100];//�˴� ��ΰ� ���� 
    public static final int INF = 1000000000; //���ϰ� default  - ����Ǿ� ���� ������� ����ġ�� ���Ѵ뿡 ������ �����Ѵ�.
    private int size;
    private Edge[][] dimen = new Edge[100][100];//���� ����ġ ���
    
    public Edge[][] getGraph()
    {
    	return graph;
    }
    //constrouctor
    public Floyd(int n, Edge[][] weight) {
        this.size = n;
        for(int i = 0; i<n; i++)
        {
        	for(int j = 0; j<n; j++){
        		
        		graph[i][j] = weight[i][j];
        		dimen[i][j] = weight[i][j];
        		if(weight[i][j].getWeight() == -1){
        			graph[i][j].setWeight(INF);
        		}
        	}
        }
    }
    
    public void getShortestPath(){
    	for(int k = 0; k<this.size; k++){
    		for(int i = 0; i<this.size; i++){
    			for(int j = 0; j<this.size; j++){
    				if(graph[i][j].getWeight() > graph[i][k].getWeight() + graph[k][j].getWeight()){
    					graph[i][j].setWeight(graph[i][k].getWeight() + graph[k][j].getWeight());
    					//dimen[i][j] = k;
    				}
    			}
    		}
    	}
    }
    
	public static void main(String[] args)
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
    

}
