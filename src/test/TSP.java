package test;

public class TSP {

	
	public static int[][] W; //weight 그래프 정보
	public static int[][] dp; // 최소비용 누적값 [a][b] : a는 현재 위치, b는 visited 정보 비트마스크
							//예를들어 dp[1][7] 은 현재 위치 1번정점에서 111(=7) 즉 1,2,3번 도시 다 방문했을때의 최소비용 저장
	public static int N;  //정점 갯수
	public static final int INF = 1000000000; //리턴값 default
	/**
	 * 
	 * @param current 현재 정점 위치
	 * @param visited 현재까지 경로
	 * @return
	 */
	
	//최소비용 리턴 (우리 프로젝트에서는 시간이 해당)
	public static int getShortestPath(int current, int visited) {
		
		// 모든 정점을 다 들른 경우 
		//여기에 예외 생김.. 완전그래프 아닐경우에 값이 존재하지 않을 수 있는 경우를 위해 W[current][1] !=0 조건 추가했는데 테스트 한 케이스만 해봄
		if (visited == (1 << N) - 1 && W[current][1] !=0)
			return W[current][1];

		// 이미 들렀던 경로이므로 바로 return
		if (dp[current][visited] >= 0)
			return dp[current][visited];

		int ret = INF; //ret는 리턴할 값 임시 저장 변수

		// 집합에서 다음에 올 원소를 고르자! (부분집합)
		for (int i = 1; i <= N; i++) {
			int next = i;

			if ((visited & (1 << (next - 1))) != 0) // 이미 방문한 곳인지 확인
				continue;
			
			if(W[current][next] == 0 || W[current][next]==-1) // 0은 경로가 없으므로 pass
				continue;
			
			int temp = W[current][next] + getShortestPath(next, visited + (1 << (next - 1)));
			ret = Math.min(ret, temp);
		}

		return dp[current][visited] = ret;

	}
	
	
	
	public static void main(String[] args){
		
		
			int n= 5;
			//int testNum = 1<<4;
			
			
			
			N=4;//N =5;
			W = new int[N+1][N+1];
			
			dp = new int[N+1][(1<<N)+1];
			
			W[1][1]=0;
			W[1][2]=1;
			W[1][3]=2;
			W[1][4]=2;
			
			W[2][1]=3;
			W[2][2]=0;
			W[2][3]=2;
			W[2][4]=1;
			
			W[3][1]=1;
			W[3][2]=2;
			W[3][3]=0;
			W[3][4]=0;
			
			W[4][1]=2;
			W[4][2]=1;
			W[4][3]=0;
			W[4][4]=0;
			
			/*정점 5개 가지고 그림 그린거 테스트
			W[1][1]=0;
			W[1][2]=1;
			W[1][3]=2;
			W[1][4]=0;
			W[1][5]=2;
			
			W[2][1]=1;
			W[2][2]=0;
			W[2][3]=1;
			W[2][4]=0;
			W[2][5]=1;

			W[3][1]=2;
			W[3][2]=1;
			W[3][3]=0;
			W[3][4]=4;
			W[3][5]=2;

			W[4][1]=0;
			W[4][2]=0;
			W[4][3]=4;
			W[4][4]=0;
			W[4][5]=1;
			
			W[5][1]=2;
			W[5][2]=1;
			W[5][3]=2;
			W[5][4]=1;
			W[5][5]=0;
			*/
			
			// 모든 원소를 -1로 초기화
			for (int i = 1; i <= N; i++) {
				for( int j= 1; j<=(1<<N); j++){
					dp[i][j]=-1;
				}
			}
			
		 
		System.out.print(getShortestPath(1, 1));
			
	}
}


