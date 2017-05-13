package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import designPrjAlgorithm.Edge;
import designPrjAlgorithm.Vertex;

public class TSP {

	public static int[][] W; // weight 그래프 정보
	public static int[][] dp; // 최소비용 누적값 [a][b] : a는 현재 위치, b는 visited 정보 비트마스크
								// 예를들어 dp[1][7] 은 현재 위치 1번정점에서 111(=7) 즉 1,2,3번
								// 도시 다 방문했을때의 최소비용 저장
	public static List<Integer> path;
	public static int N; // 정점 갯수
	public static final int INF = 1000000000; // 리턴값 default
	long distance; // 최저비용 저장
	public static int last;

	/**
	 * 
	 * @param current
	 *            현재 정점 위치
	 * @param visited
	 *            현재까지 경로
	 * @return
	 */

	public TSP(int n, ArrayList<ArrayList<Edge>> weight) {
		N = n; // N 초기화

		ArrayList<Edge> eRow = new ArrayList<Edge>();
		// W[][]초기화
		// dp[][]초기화
		W = new int[N][N];
		dp = new int[N][(1 << N)];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				eRow.add(weight.get(i).get(j)); // 임시 어레이리스트에 엣지 추가
				W[i][j] = eRow.get(j).getWeight(); // W배열에 가중치 저장
				if (eRow.get(j).getWeight() == -1) {// 만일 연결되어 있지 않다면,가중치를 0으로
													// 바꿔서 가져온다.
					eRow.get(j).setWeight(0);
					W[i][j] = eRow.get(j).getWeight();
				}
			}
		}

		// 모든 원소를 -1로 초기화
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < (1 << N); j++) {
				dp[i][j] = -1;
			}
		}

	}

	public List<Integer> getVertexNum(int distance) // 경유 노드 저장
	{
		 List<Integer> tem = new ArrayList<Integer>();

		int piv = 0;
		int masking = 1; // piv는 비교할 현재 노드, masking은 방문도시들
		for (int i = 0; i < N; i++) {
			
			for (int k = 0; k < N; k++) {
				if ((masking & (1 << k)) != 0) // 방문한 노드인지 확인
					continue;

				if (W[piv][k] == 0) // 0은 경로가 없으므로 pass
					continue;

				if (distance - W[piv][k] == dp[k][masking + (1 << k)]) {
					// 경로저장
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

	// vertex 4개짜리 테스트 constructor
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

	// 최소비용 리턴 (우리 프로젝트에서는 시간이 해당)
	private int getShortestPath(int current, int visited) {

		// 모든 정점을 다 들른 경우
		// 여기에 예외 생김.. 완전그래프 아닐경우에 값이 존재하지 않을 수 있는 경우를 위해 W[current][1] !=0 조건
		// 추가했는데 테스트 한 케이스만 해봄
		if (visited == (1 << N) - 1 && W[current][0] != 0) {
			dp[current][0]=W[current][0];
			return W[current][0];
		}

		// 이미 들렀던 경로이므로 바로 return
		if (dp[current][visited] >= 0)
			return dp[current][visited];

		int ret = INF; // ret는 리턴할 값 임시 저장 변수

		// 집합에서 다음에 올 원소를 고르자! (부분집합)
		for (int i = 0; i < N; i++) {
			int next = i;

			if ((visited & (1 << next)) != 0) // 이미 방문한 곳인지 확인
				continue;

			if (W[current][next] == 0 || W[current][next] == -1) // 0은 경로가 없으므로
																	// pass
				continue;

			int temp = W[current][next] + getShortestPath(next, visited + (1 << next));
			ret = Math.min(ret, temp);
		}

		return dp[current][visited] = ret;

	}

	public int calculateShortestPath(int home) {
		int shortPath = getShortestPath(home, (1 << home));
		/**
		 *  dp[x][home]의 정확한 계산을 위해서 아래의 for 문을 구현해야한다.
		 *  getShortestPath 함수 내에서 모든 노드를 방문하면, dp[x][home]을 계산한다.
		 *  하지만 추후 최단 경로 리스트를 찾기 위해 불러야할 getVertexNum 함수에서는 dp[x][1111]을 사용하여 마지막 돌아오는 경로를 계산한다.
		 *  이때 아래의 작업을 해주지 않으면 dp[x][1111]은 -1이 되어서 마지막 경로가 계산이 되지 않는다.
		 *  따라서 dp[x][1111]에 dp[x][home] 값을 할당해줘야한다.
		 */
		for (int i = 0; i < N; i++) {
			dp[i][(1 << N) - 1] = dp[i][home];
		}
		return shortPath;
	}
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

	}

}
