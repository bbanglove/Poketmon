package designprjalgorithm.goketmon;

/**
 * Created by 이석영 on 2017-06-01 0001.
 */

import java.util.ArrayList;
import java.util.List;
import designprjalgorithm.goketmon.Vertex;
import designprjalgorithm.goketmon.Edge;

public class TSP {
    private ArrayList<ArrayList<Integer>> W= new ArrayList<ArrayList<Integer>>(); // weight �׷��� ����
    private int[][] dp; // �ּҺ�� ������ [a][b] : a�� ���� ��ġ, b�� visited ���� ��Ʈ����ũ
    // ������� dp[1][7] �� ���� ��ġ 1���������� 111(=7) �� 1,2,3��
    // ���� �� �湮�������� �ּҺ�� ����
    private  List<Integer> path;
    private  int N; // ���� ����
    private final int INF = 1000000000; // ���ϰ� default
    private long distance; // ������� ����
    private int last;

    public TSP(int n, ArrayList<ArrayList<Edge>> weight) {
        N = n; // N �ʱ�ȭ
        // W[][]�ʱ�ȭ
        // dp[][]�ʱ�ȭ
        dp = new int[N][(1 << N)];
        for (int i = 0; i < N; i++) {
            ArrayList<Integer> eRow = new ArrayList<Integer>();
            for (int j = 0; j < N; j++) {
                if(weight.get(i).get(j).getWeight() == -1){
                    eRow.add(0);
                }
                else{
                    eRow.add(weight.get(i).get(j).getWeight()); // �ӽ� ��̸���Ʈ�� ����ġ �߰�
                }
            }
            W.add(eRow);
        }
        // ��� ���Ҹ� -1�� �ʱ�ȭ
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < (1 << N); j++) {
                dp[i][j] = -1;
            }
        }
    }

    public List<Integer> getVertexNum(int distance) // ���� ��� ����
    {
        List<Integer> tem = new ArrayList<Integer>();
        int piv = 0;
        int masking = 1; // piv�� ���� ���� ���, masking�� �湮���õ�
        for (int i = 0; i < N; i++) {
            for (int k = 0; k < N; k++) {
                if ((masking & (1 << k)) != 0) // �湮�� ������� Ȯ��
                    continue;

                if (W.get(piv).get(k) == 0) // 0�� ��ΰ� �����Ƿ� pass
                    continue;

                if (distance - W.get(piv).get(k) == dp[k][masking + (1 << k)]) {
                    // �������
                    tem.add(k);
                    distance = dp[k][masking + (1 << k)];
                    piv = k;
                    masking += (1 << k);
                    break;
                }
            }
        }
        return tem;
    }

    private int getShortestPath(int current, int visited) {
        if (visited == (1 << N) - 1 && W.get(current).get(0) != 0) {
            dp[current][0]=W.get(current).get(0);
            return W.get(current).get(0);
        }

        // �̹� �鷶�� ����̹Ƿ� �ٷ� return
        if (dp[current][visited] >= 0)
            return dp[current][visited];

        int ret = INF; // ret�� ������ �� �ӽ� ���� ����

        // ���տ��� ������ �� ���Ҹ� ����! (�κ�����)
        for (int i = 0; i < N; i++) {
            int next = i;

            if ((visited & (1 << next)) != 0) // �̹� �湮�� ������ Ȯ��
                continue;

            if (W.get(current).get(next) == 0 || W.get(current).get(next) == -1) // 0�� ��ΰ� �����Ƿ�
                // pass
                continue;

            int temp = W.get(current).get(next) + getShortestPath(next, visited + (1 << next));
            ret = Math.min(ret, temp);
        }

        return dp[current][visited] = ret;

    }

    public int calculateShortestPath(int home) {
        int shortPath = getShortestPath(home, (1 << home));
        for (int i = 0; i < N; i++) {
            dp[i][(1 << N) - 1] = dp[i][home];
        }
        return shortPath;
    }
}
