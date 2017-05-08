package test;

import java.util.Random;

public class TSP {

	
	public static int[][] W;
	public static int[][] dp;
	public static int N;
	public static final int INF = 1000000000;
	/**
	 * 
	 * @param start 시작점
	 * @param visited 현재까지 경로
	 * @return
	 */
	/*
	public static int getShortestPath(int current, int visited) {
		
		// 모든 정점을 다 들른 경우
		if (visited == (1 << N) - 1)
			return W[current][1];

		// 이미 들렀던 경로이므로 바로 return
		if (dp[current][visited] >= 0)
			return dp[current][visited];

		int ret = INF;

		// 집합에서 다음에 올 원소를 고르자!
		for (int i = 1; i <= N; i++) {
			int next = i;

			if ((visited & (1 << (next - 1))) != 0) // 중요!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				continue;
			
			if(W[current][next] == 0) // 0은 경로가 없으므로 pass
				continue;
			
			int temp = W[current][next] + getShortestPath(next, visited + (1 << (next - 1)));
			ret = Math.min(ret, temp);
		}

		return dp[current][visited] = ret;

	}
	
	*/
	
	public static void main(String[] args){
		
		
			int n= 1<<3;
			int testNum = 1<<4;
			
			int[][] test = new int [10][n];
			Random random = new Random();
			
			for (int i=0; i<10; i++){
				
				for(int j=0; j<n; j++){
					
					test[i][j]= random.nextInt(10);
					
				}
			}
			
			for (int i=0; i<10; i++){
				
				for(int j=0; j<n; j++){
					
					System.out.print(test[i][j]+" ");
					
				}
				
				System.out.print('\n');
			}
			
			System.out.print((testNum<<1)-1);
	}
}


