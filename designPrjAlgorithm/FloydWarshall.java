package designPrjAlgorithm;

import java.util.Arrays;
import designPrjAlgorithm.Edge;


//전체 그래프나 엣지리스트가 아닌 weight만 받는 경우의플로이드 알고리즘
public class FloydWarshall {

    // graph represented by an adjacency matrix
    private int[][] graph;//죄단 경로가 담기는 
    public static final int INF = 1000000000; //리턴값 default  - 연결되어 있지 않은경우 가중치를 무한대에 가깝게 설정한다.
    private boolean negativeCycle;
    private int size;
    private int[][] dimen;//원본 가중치 행렬
    
    public int[][] getGraph()
    {
    	return graph;
    }
    //constrouctor
    public FloydWarshall(int n, int[][] weight) {
        this.size = n;
    	this.graph = new int[n][n];
    	this.dimen = new int[n][n];
        for(int i = 0; i<n; i++)
        {
        	for(int j = 0; j<n; j++){
        		graph[i][j] = weight[i][j];
        		dimen[i][j] = weight[i][j];
        		if(weight[i][j] == -1){
        			graph[i][j] = INF;
        		}
        	}
        }
    }
    
    public void getShortestPath(){
    	for(int k = 0; k<this.size; k++){
    		for(int i = 0; i<this.size; i++){
    			for(int j = 0; j<this.size; j++){
    				if(graph[i][j] > graph[i][k] + graph[k][j]){
    					graph[i][j] = graph[i][k] + graph[k][j];
    					dimen[i][j] = k;
    				}
    			}
    		}
    	}
    }
    /*
	public static void main(String[] args)
	{
		int [][] temp = {{0, 3, -1, -1}, {-1, 0,12, 6}, {-1, 5, 0, 7}, {2, 9, 4, 0},};
		FloydWarshall f = new FloydWarshall(3, temp);
		f.getShortestPath();
		for(int i = 0; i<3; i++){
			for(int j = 0; j<3; j++){
				System.out.println(f.getGraph()[i][j]);
			}
		}
		
	}
    */

}
