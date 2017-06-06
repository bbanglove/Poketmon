package designprjalgorithm.goketmon;

/**
 * Created by 이석영 on 2017-06-01 0001.
 */

import java.util.ArrayList;
import designprjalgorithm.goketmon.Edge;

public class Floyd {
    private ArrayList<ArrayList<Edge>> graph = new ArrayList<ArrayList<Edge>>();
    private ArrayList<ArrayList<Integer>> pGraph = new ArrayList<ArrayList<Integer>>();
    public static final int INF = 1000000000; //���ϰ� default  - ����Ǿ� ���� ������� ����ġ�� ���Ѵ뿡 ������ �����Ѵ�.
    private int size;// ���� ����ִ� ������ ������ ������ size ����
    private ArrayList<Edge> eSelected = new ArrayList<Edge>();
    private ArrayList<Integer> selectedEdgeNum = new ArrayList<Integer>();
    private int[][] visited = new int[100][100];


    public ArrayList<ArrayList<Edge>> getGraph() {return graph;}

    public ArrayList<ArrayList<Integer>> getPGraph(){return pGraph;}

    public int[][] getVisited() {return visited;}

    public int getvisitedVNum(int i,int j) {return visited[i][j];}

    public ArrayList<Edge> getSelectedEdge() {return this.eSelected;}

    //constrouctor
    public Floyd(int n, ArrayList<ArrayList<Edge>> weight)
    { //���� �迭�� �޾� �ʱ�ȭ�� �����Ѵ�.
        this.size = n;//�Է� ����� ũ��� ����� �ʱ�ȭ
        for(int i = 0; i<100; i++)
            for(int j = 0; j<100; j++)
                visited[i][j] = 0;
        for(int i = 0; i<n; i++)//��ü ũ�⿡ ����
        {
            ArrayList<Edge> eRow = new ArrayList<Edge>();
            ArrayList<Integer> vRow = new ArrayList<Integer>();
            for(int j = 0; j<n; j++){
                eRow.add(weight.get(i).get(j));
                vRow.add(0);
                if(eRow.get(j).getWeight() == -1){//���� ����Ǿ� ���� �ʴٸ�,����ġ�� ���Ѵ�� �����Ѵ�.
                    eRow.get(j).setWeight(INF);
                    vRow.add(0);
                }
            }
            graph.add(eRow);
            pGraph.add(vRow);
        }
    }

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
                        graph.get(i).get(j).setWeight(graph.get(i).get(k).getWeight() + graph.get(k).get(j).getWeight()); //�ش� �ּ� ����ġ�� �����Ѵ�.
                        graph.get(i).get(j).setPostVertex(graph.get(k).get(j).getPostVertex());
                        pGraph.get(i).set(j, k);
                        this.visited[i][j] = vList.get(k).getVertexNum()-1;
                        if(graph.get(i).get(k).getPostVertex().getPocketStop()) //���� �߰� ��ο� �ִ� vertex�� ���Ͻ�ž�̶��
                            graph.get(i).get(j).addPocketStop(); //�ش� ������ ���Ͻ�ž ������ ������Ų��.
                    }
                    else{
                        graph.get(i).get(j).addMonsterNum();
                    }
                }
            }
        }
    }
}
