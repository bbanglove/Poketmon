package designPrjAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import designPrjAlgorithm.Edge;
import designPrjAlgorithm.Vertex;

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

	/**
	 * 
	 * @param current
	 *            ���� ���� ��ġ
	 * @param visited
	 *            ������� ���
	 * @return
	 */

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
/*
	// vertex 4��¥�� �׽�Ʈ constructor
	public TSP() {

		N = 4;
		W = new int[N][N];
		dp = new int[N][(1 << N)];
		last = -1;

		W[0][0] = 0;
		W[0][1] = 1;
		W[0][2] = 2;
		W[0][3] = 2;//

		W[1][0] = 3;//
		W[1][1] = 0;
		W[1][2] = 2;
		W[1][3] = 1;

		W[2][0] = 1;
		W[2][1] = 2;
		W[2][2] = 0;
		W[2][3] = 0;

		W[3][0] = 2;
		W[3][1] = 1;//
		W[3][2] = 0;
		W[3][3] = 0;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < (1 << N); j++) {
				dp[i][j] = -1;
			}
		}
		

	}
*/
	// �ּҺ�� ���� (�츮 ������Ʈ������ �ð��� �ش�)
	private int getShortestPath(int current, int visited) {

		// ��� ������ �� �鸥 ���
		// ���⿡ ���� ����.. �����׷��� �ƴҰ�쿡 ���� �������� ���� �� �ִ� ��츦 ���� W[current][1] !=0 ����
		// �߰��ߴµ� �׽�Ʈ �� ���̽��� �غ�
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
		/**
		 *  dp[x][home]�� ��Ȯ�� ����� ���ؼ� �Ʒ��� for ���� �����ؾ��Ѵ�.
		 *  getShortestPath �Լ� ������ ��� ��带 �湮�ϸ�, dp[x][home]�� ����Ѵ�.
		 *  ������ ���� �ִ� ��� ����Ʈ�� ã�� ���� �ҷ����� getVertexNum �Լ������� dp[x][1111]�� ����Ͽ� ������ ���ƿ��� ��θ� ����Ѵ�.
		 *  �̶� �Ʒ��� �۾��� ������ ������ dp[x][1111]�� -1�� �Ǿ ������ ��ΰ� ����� ���� �ʴ´�.
		 *  ���� dp[x][1111]�� dp[x][home] ���� �Ҵ�������Ѵ�.
		 */
		for (int i = 0; i < N; i++) {
			dp[i][(1 << N) - 1] = dp[i][home];
		}
		return shortPath;
	}
	/*
	public static void main(String[] args) {

		TSP t = new TSP();
		int shortPath = t.calculateShortestPath(0);
		
		List<Integer> showVertex = new ArrayList<Integer>();
		System.out.println(shortPath);
		showVertex = t.getVertexNum(shortPath);

		System.out.println("size : " + showVertex.size());
		for(int result : showVertex){
			System.out.print(result +"\t");
		}

	}*/

}
