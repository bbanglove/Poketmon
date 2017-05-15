package designPrjAlgorithm;

import designPrjAlgorithm.User;
import designPrjAlgorithm.Pocketmon;
import designPrjAlgorithm.Vertex;
import designPrjAlgorithm.Edge;
import designPrjAlgorithm.Navigation;
import designPrjAlgorithm.Graph;
import java.util.List;
import java.util.ArrayList;
import designPrjAlgorithm.TSP;
import designPrjAlgorithm.Floyd;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Manager {
	private int inputTime;//입력받은 시간
	private int inputMonster; //입력받은 몬스터
	private User currentUser; //현재 사용자 객체
	//private int numOfMonster;//monsterList의 index 지정을 위한 변수. 0으로 시작한다.
	
	//읽은 데이터를 저장할 애들
	private ArrayList<Pocketmon> monsterList = new ArrayList<Pocketmon>();//몬스터 배열
	private ArrayList<Vertex> vertexList = new ArrayList<Vertex>();//위치 정보 배열
	private ArrayList<ArrayList<Edge>> edgeList = new ArrayList<ArrayList<Edge>>(); //노드 간 가중치 배열
	
	private Navigation navigator;
	//문제 1,2,3에 대한 해답 경로를 저장한 리스트
	private ArrayList<ArrayList<Edge>> shortestPath = new ArrayList<ArrayList<Edge>>();//플로이드를 이용해 계산한 노드 간 최단 경로가 저장된 배열.
	private ArrayList<Vertex> pocketStopList = new ArrayList<Vertex>();
	private  ArrayList<Vertex> solutionList1;
	//private  ArrayList<Edge> solutionPath1 = new ArrayList<Edge>();
	private  ArrayList<Vertex> solutionList2;
	//private  ArrayList<Edge> solutionPath2= new ArrayList<Edge>();
	private  ArrayList<Vertex> solutionList3;
	//private  ArrayList<Edge> solutionPath3= new ArrayList<Edge>();
	
	//각 알고리즘의 수행 시간
	private double timeOption1;
	private double timeOption2;
	private double timeOption3;
	
	//최단경로 탐색을 위한 탐색 클레스 객체추가
	//private TSP mTSP;
	private Floyd mFloyd;
	//Constructor
	public Manager()
	{
		this.inputTime = 0;
		this.inputMonster = 0;
		//this.numOfMonster = 0;
		this.currentUser = null;
		this.readDataFile1();
		this.readDataFile2();
		this.readDataFile3();
		this.mFloyd = new Floyd(100, edgeList);
		this.initShortestPath();
		this.edgeList.clear();
		this.readDataFile3();
		//solutionList1 = null;
		//solutionList2 = null;
		//solutionList3 = null;
	}
	
	public Manager(int time, int monster, User user)
	{
		this.inputTime = time;
		this.inputMonster = monster;
		//this.numOfMonster = 0;
		this.currentUser = user;
		this.monsterList = null;
		this.vertexList = null;
		this.edgeList = null;
		
	}
	
	//get Function
	public int getTime(){return this.inputTime;}
	public int getMonster(){return this.inputMonster;}
	public User getUser(){return this.currentUser;}
	public ArrayList<Pocketmon> getMonsterList(){return this.monsterList;}
	public ArrayList<Vertex> getVertexList(){return this.vertexList;}
	public ArrayList<ArrayList<Edge>> getEdgeList(){return this.edgeList;}
	public ArrayList<ArrayList<Edge>> getShortestPath() {return this.shortestPath;}
	public List<Vertex> getSolutionList1(){return this.solutionList1;}
	public List<Vertex> getSolutionList2(){return this.solutionList2;}
	public List<Vertex> getSolutionList3(){return this.solutionList3;}
	
	//set Function
	public void setTime(int time){this.inputTime = time; }
	public void setMonster(int monster){this.inputMonster = monster;}
	public void setUser(User user){this.currentUser = user;}
	public void setMonsterList(ArrayList<Pocketmon> mList){this.monsterList = mList;}
	public void setVertexList(ArrayList<Vertex> vList){this.vertexList = vList;}
	public void setEdgeList(ArrayList<ArrayList<Edge>> eList){this.edgeList = eList;}
	
	//모든 노드를 방문하지않은 것으로 세팅한다.
	public void initVisited()
	{
		for(int i = 0; i<this.vertexList.size(); i++)
			vertexList.get(i).setVisitedFlag(0);
	}
	//플로이드 함수를 호출해 최단 경로를 계산, 미리 저장한다.
	public void initShortestPath()
	{
		mFloyd.getShortestPath();
		for(int i = 0; i<100; i++){
			ArrayList<Edge> eRow = new ArrayList<Edge>();
			for(int j = 0; j<100; j++){
				eRow.add(this.mFloyd.getGraph().get(i).get(j));
			}
			this.shortestPath.add(eRow);
		}
		
	}
	//display Function
	
	//FILE i/o
	//식별자 파일 입력
	public void readDataFile1()
	{
		try{
		//1.위치 식별자
		System.out.println(Manager.class.getResource("").getPath());//파일을 읽어오기 위한 경로 설정
		BufferedReader reader = new BufferedReader(new FileReader(Manager.class.getResource("").getPath() + "위치식별자.txt"));
		//한 문자씩 읽어들인다
		String temp="";
		while((temp = reader.readLine()) != null)//한 줄씩 읽어들인다.
		{
			String tId,tName; //임시 저장 객체
			int idx = temp.indexOf(":"); //:의 위치를 찾아내 idx에 저장한다
			tId = temp.substring(0, idx);//idx를 기준으로 앞부분을 id에 저장
			tName = temp.substring(idx + 1); //idx를 기준으로 뒷부분을 name에 저장
			Pocketmon tMonster = new Pocketmon(tName, Integer.parseInt(tId)); //받은 내용들을 담은 포켓몬 객체 생성
			this.monsterList.add(tMonster);//몬스터배열에 담는다
			//this.numOfMonster++; //배열 index를 하나 증가시킨다.
		}//while 문 끝
		
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//몬스터리스트를 id 기준으로 검색
	public Pocketmon findPocketmonById(int id)
	{
		for(int i = 0; i<this.monsterList.size(); i++)
		{
			if(this.monsterList.get(i).getId() == id)
			{
				return this.monsterList.get(i);
			}
		}
		return null;//못 찾았을 경우 null을 반환
	}
	
	//몬스터리스트를 name 기준으로 검색
	public Pocketmon findPocketmonById(String name)
	{
		for(int i = 0; i<this.monsterList.size(); i++)
		{
			if(this.monsterList.get(i).getName() == name)
			{
				return this.monsterList.get(i);
			}
		}
		return null;//못 찾았을 경우 null을 반환
	}
	
	//위치정보 파일 입력
	public void readDataFile2()
	{
		try{
		//2.위치정보
			System.out.println(Manager.class.getResource("").getPath());//파일을 읽어오기 위한 경로 설정
			BufferedReader reader = new BufferedReader(new FileReader(Manager.class.getResource("").getPath() + "위치정보.txt"));
			String temp ="";
			while((temp = reader.readLine()) != null)
			{
				String num, monsterId; //장소 번호와 해당 위치에 존재하는 몬스터의 위치를 저장할 임시변수
				int idx = temp.indexOf("\t");//뛰어쓰기 위치를 찾아 해당 인덱스를 저장한다.
				num = temp.substring(0, idx);//뛰어쓰기를 기준으로 왼쪽 문자열을 저장
				monsterId = temp.substring(idx+1);//뛰어쓰기를 기준으로 오른쪽 문자열을 저장
				if(Integer.parseInt(monsterId) == 11)//만일 index = 11이어서 해당 장소가 포켓스탑이라고 판단된다면
				{
					Vertex tVertex = new Vertex(Integer.parseInt(num), true);//포켓스탑 vertex로 초기화해  
					this.vertexList.add(tVertex);//리스트에 추가한다. 인덱스는 0부터 시작하고 장소번호는 1부터 시작하므로 1을 빼준다.
					this.pocketStopList.add(tVertex);//포켓스탑 리스트에 해당 vertex를 추가한다.
				}
				else //단순히 몬스터가 존재하는 노드인 경우
				{
					Vertex tVertex = new Vertex(Integer.parseInt(num), Integer.parseInt(monsterId)); //몬스터가 존재하는 vertex 객체를 생성해
					this.vertexList.add(tVertex); //리스트에 추가한다.
				}
			}
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//위치별 소모시간
	public void readDataFile3()
	{
		try{
			System.out.println(Manager.class.getResource("").getPath());//파일을 읽어오기 위한 경로 설정
			BufferedReader reader = new BufferedReader(new FileReader(Manager.class.getResource("").getPath() + "위치별소모시간.txt"));
			int i = 0;//위치 정보 배열 접근을 위한 index 선언
			String temp ="";
			int idx =0;
			while((temp = reader.readLine()) != null) //한 행씩 읽어들인다,
			{
				ArrayList<Edge> eRow = new ArrayList<Edge>();
				for(int j = 0; j<100; j++){//100개의 열에 대해
					String pre, post;
					idx = temp.indexOf("\t"); //첫번째 뛰어쓰기 인덱스를 찾아내 저장한다
					pre = temp.substring(0, idx); //뛰어쓰기 전 열을 받아온다
					post = temp.substring(idx + 1); //뛰어쓰기 이후의 열
					//Edge생성자 : Edge(엣지번호, 가중치,이전vertex, 이후vertex)
					Edge tEdge = new Edge((i*100 + j),Integer.parseInt(pre), this.vertexList.get(i), this.vertexList.get(j));
					eRow.add(tEdge);//edge 리스트에 추가한다.
					if(j == 98) //만일 더 이상 찾아낼 \t가 없을 것으로 예상된다면
					{
						//남은 부분응로 edge를 구성해 추가한다.
						Edge lastEdge = new Edge((i*100 + j + 1), Integer.parseInt(post), this.vertexList.get(i), this.vertexList.get(j)); 
						eRow.add(lastEdge);
						break;
					}
					temp = post;
				}
				this.edgeList.add(eRow);
				i++; //다음 행으로 이동한다.
			}
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//입력받은 몬스터로 새로운 그래프를 형성한다
	public Graph selectGraph()
	{
		//vertex 0에서 99까지를 돌아다니며 해당 몬스터 경우를 찾는다. - 10개가 나올 것. 
		//찾아낸 몬스터에 대해 새로운 edgelist를 만들어야 한다.
		//어떻게 만들 것인가: 일단 vertex를 다 뽑아내 구성한다. 
		//그리고 각 vertex 넘버에 대해 각각을 이어주는 최단경로를 플루이드를 이용해 계산해 낸다.
		if(this.inputMonster == 0)//만일 입력된 몬스터 정보가 없는 경우
		{
			return null; //false를 리턴하고 종료한다.
		}
		
		ArrayList<Vertex> selectedVertex = new ArrayList<Vertex>();//navigator로 넘긴 그래프 객체를 이룰 
		selectedVertex.add(this.vertexList.get(0));
		int numSelected = 1;
		for(int i = 0; i<100; i++) //100개의 노드에 대해 검사를 수행한다.
		{
			if(vertexList.get(i).getMonsterId() == this.inputMonster) //만일 vertexList의 해당 index노드에 inputMonster와 같은 몬스터가 있다면
			{
				selectedVertex.add(vertexList.get(i)); //해당 vertex를 새로운 그래프를 만들기 위한 vertex 배열에 순서대로 집어넣는다.
				numSelected++; //index를 증가시킨다.
			}
		} //vertex 선별 및 추출이 완료되었다.
		
		//각 vertex에 해당하는 엣지를 추출해야 한다.
		ArrayList<ArrayList<Edge>> selectedEdge = new ArrayList<ArrayList<Edge>>();
		for(int i = 0; i<selectedVertex.size(); i++)
		{
			ArrayList<Edge> eRow = new ArrayList<Edge>();
			for(int j= 0; j<selectedVertex.size(); j++){
				int src = selectedVertex.get(i).getVertexNum();
				int dst = selectedVertex.get(j).getVertexNum();
				eRow.add(this.shortestPath.get(src-1).get(dst-1));
			}
			selectedEdge.add(eRow);
		}
		//추출 끝.
		Graph selectedGraph = new Graph(selectedVertex, selectedEdge, numSelected);
		
		return selectedGraph;//추출 결과를 반환한다.
	}
	
	//넘긴 그래프에 대해 구간 내에 들어 있는 포켓스탑의 인덱스를 세서 반환한다. 
	public ArrayList<Integer> calcNumberOfPocketStop(ArrayList<Edge> eList)
	{
		ArrayList<Integer> indexOfPocketStop = new ArrayList<Integer>();
		for(int i = 0; i<eList.size(); i++){
			if(eList.get(i).getPocketStopNum() != 0){
				indexOfPocketStop.add(i);
				//System.out.println(i);
			}
		}

		return indexOfPocketStop;
	}
	
	
	public void printPStopinSPath()
	{
		int count = 0; 
		for(int i =0; i<100; i++)
		{
			for(int j = 0; j<100; j++){
				if(this.shortestPath.get(i).get(j).getPocketStopNum() != 0){
					int src = this.shortestPath.get(i).get(j).getPreVertex().getVertexNum();
					int dst = this.shortestPath.get(i).get(j).getPostVertex().getVertexNum();
					int num = this.shortestPath.get(i).get(j).getPocketStopNum();
					System.out.println("from " + src + " to " + dst + " have " + num);
					count ++;
				}
			}
		}
		System.out.println(count);
	}
	//입력 받은 vertex들로부터 가장 가까운 포켓스탑을 찾아 반환해주는 함수
	//각각의 vertex 사이의 구간에 대해, 그 구간 사이에 포켓스탑이 없을 경우
	//존재하는 포켓스탑중 해당 구간에서 가장 가까운 포켓스탑을 찾아준다.
	public ArrayList<Vertex> findNearestPocketStop(ArrayList<Vertex> vList)
	{
		ArrayList<Vertex> nearestPstop = new ArrayList<Vertex>();//각 구간 별로 가장 가까운 포켓스탑이 담길 것.
		for(int i = 0; i<vList.size(); i++)
		{
			int minpath = 10000; //최소값 비교를 위한 임의의 값 설정
			int nearest = 0; //가장 가까운 포켓스탑의 인덱스
			for(int j = 0; j<this.pocketStopList.size(); j++){ //전체 포켓스탑에 대해
				if(i == vList.size()-1){//맨 마지막꺼
					int src = vList.get(i).getVertexNum(); //마지막 vertex일 경우에는 출발점을 도착점으로 잡는다.
					int dst = vList.get(0).getVertexNum();
					int pStop = this.pocketStopList.get(j).getVertexNum();
					//가는 길 + 오는 길 = 해당 포켓스탑을 경유할 떄의 거리
					int path = this.shortestPath.get(src -1).get(pStop-1).getWeight() 
							+ this.shortestPath.get(pStop-1).get(dst-1).getWeight(); 
					if(minpath > path){//지금까지의 최단경로보다도 더 짧다면
						minpath = path;
						nearest = pStop;
					}
				}
				else{
					int src = vList.get(i).getVertexNum();
					int dst = vList.get(i+1).getVertexNum();
					int pStop = this.pocketStopList.get(j).getVertexNum();
					//가는 길 + 오는 길
					int path = this.shortestPath.get(src-1).get(pStop-1).getWeight() 
							+ this.shortestPath.get(pStop-1).get(dst-1).getWeight(); 
					if(minpath > path ){//지금까지의 최단경로보다도 더 짧다면
						minpath = path;
						nearest = pStop;
					}
				}
			}
			nearestPstop.add(this.vertexList.get(nearest-1));
		}
		return nearestPstop;
	}
	
	//첫 번째 경우에 대헤
	public void calcAlgorithm1()
	{
		Graph sGraph = selectGraph(); //출발점 + 9개의 노드로 구성된 선별 그래프
		TSP mTsp = new TSP(sGraph.getSize(), sGraph.getEdgeList());//tsp를 호출해 초기화한다.
		List<Integer> path  = mTsp.getVertexNum(mTsp.calculateShortestPath(0));//최단 비용 순회 경로를 받아온다.
		path.add(0,0);//반환된 경로에는 출발점이 제외되어 있으므로 첫 번째 자리에 이를 추가해준다. 
		
		//순서가 정해진 TSP 경로의 vertex와 엣지 리스트
		ArrayList<Vertex> orderVertex = new ArrayList<Vertex>();
		for(int i = 0; i<path.size(); i++){
			orderVertex.add(sGraph.getVertexList().get(path.get(i)));//path가 저장하고 있는 인덱스로부터 vertex 객체를 불러와 저장한다.
		}
		
		ArrayList<Edge> orderEdge = new ArrayList<Edge>();
		for(int i = 0; i<orderVertex.size() -1; i++){
			int src = orderVertex.get(i).getVertexNum();
			int dst = orderVertex.get(i+1).getVertexNum();
			orderEdge.add(this.shortestPath.get(src -1).get(dst-1));
		}
		
		int  numOfPStop = this.calcNumberOfPocketStop(orderEdge).size();
		//this.findNearestPocketStop(orderVertex);
		this.calcSolutionList1(numOfPStop, orderEdge, orderVertex);
	}
	//최단 소요 시간을계산하는 함수
	public int minTimeVisitPocketStop(int vnum, ArrayList<Edge> eList, ArrayList<Vertex> vList)
	{
		int min= 1000000; //최소값을 담을 변수 초기화
		int start = 0;
		
		//구간별 포켓스탑의 갯수를 저장하자.
		int section1 = 0; int section2 = 0; int section3 = 0; 
		ArrayList<Integer> indexPStop = new ArrayList<Integer>();
		for(int i = 0; i<indexPStop.size(); i++)
		{
			if(indexPStop.get(i) <4) //만약 첫번째 구간에 포켓스탑이 존재한다면
				section1++;
			else if(indexPStop.get(i) > 3 &&indexPStop.get(i) < 7 )//두번째구간에 존재
				section2++;
			else if(indexPStop.get(i) > 6)//세번째 구간에 존재
				section3++;
		}		
		
		for(int i = 1; i<vList.size() - vnum + 1;  i++){ //들릴 노드의 갯수만큼 방문하자. 
			this.initVisited();//모든 노드를 방문하지 않은 것으로 초기화한다.
			//this.solutionList1.clear();
			int ptime1 = 0; int pStop = 0; int visit1 =0; int visit2 = 0; //포켓스탑으로 우회했는지 여부를 알려주는 플래그
			if(vnum>3 && section1 == 0)//4보다 더 많이 지나는데 첫번째 구간 내에 포켓스탑이 없다면
			{
				visit2 = 1; //포켓스탑 우회 경로를 한 번 통과하였음
				pStop = this.findNearestPocketStop(vList).get(3).getVertexNum() -1; //해당 포켓스탑의 인덱스
				int src = vList.get(3).getVertexNum()-1; //출발점의 인덱스
				int dst= vList.get(4).getVertexNum()-1; //도착점의 인덱스
				//해당 포켓스탑을경유하는 비용을 계산
				ptime1 = this.shortestPath.get(src).get(pStop).getWeight() + this.shortestPath.get(pStop).get(dst).getWeight(); 
				this.vertexList.get(pStop).setVisitedFlag(1); //해당 포켓스탑의 방문 플래그를 1로 바꿔준다.
			}
			
			int ptime2 = 0; int pStop2 = 0;
			if(vnum>6 && section1 != 2){ //두 개를 들러야 할 경우, 두 번쨰 구간에서의 맨 첫 번째 에지에서 들린다. 
				visit2 = 1;//두 번째 우회경로를 통과하였슴
				int src2 = vList.get(6).getVertexNum()-1;//출발점의 인덱스
				int dst2 = vList.get(7).getVertexNum()-1;//도착점의 인덱스
				pStop2 = this.findNearestPocketStop(vList).get(6).getVertexNum()-1;//해당 포켓스탑의 인덱스
				//해당 포켓스탑을경유하는 비용을 계산
				ptime2 = this.shortestPath.get(src2).get(pStop2).getWeight() + this.shortestPath.get(pStop2).get(dst2).getWeight();
				this.vertexList.get(pStop2).setVisitedFlag(1);
			}
			
			int time1 = this.shortestPath.get(0).get(vList.get(i).getVertexNum()-1).getWeight();//가는 길
			int time2 = this.shortestPath.get(vList.get(i+vnum-1).getVertexNum()-1).get(0).getWeight();//오는 길
			this.vertexList.get(vList.get(i+vnum-1).getVertexNum()-1).setVisitedFlag(1);
			int time3 = 0;
			for(int j = i; j< i + vnum -1; j++){
				this.vertexList.get(vList.get(j).getVertexNum()-1).setVisitedFlag(1);
				boolean skip1 = (j == 3 && visit1 ==1); //첫번쨰 우회경로를 들른 상황
				boolean skip2 = (j == 6 && visit2 ==1); //두 번째 우회결로를 들른 상황
				if((!skip1 || !skip2)){ //만약 둘 중 어느 한 조건에라도 해당이 되지 않는다면
					time3 += eList.get(j).getWeight();
				}
				
			}
			int time  = time1 + time2 + time3 + ptime1 + ptime2;
			if(min > time){
				start =i;
				min = time;
			}
		}
		
		return min;
	}
	
	//최단 경로 리턴 비용을 계산한다.
	public int minTime(int vnum, ArrayList<Edge> eList, ArrayList<Vertex> vList)
	{
		int min= 1000000;
		int start = 0;
		for(int i = 1; i<vList.size() - vnum + 1;  i++){

			int time1 = this.shortestPath.get(0).get(vList.get(i).getVertexNum()-1).getWeight();//가는 길
			int time2 = this.shortestPath.get(vList.get(i+vnum-1).getVertexNum()-1).get(0).getWeight();//오는 길
			int time3 = 0;
			for(int j = i; j<vnum; j++){
				time3 += eList.get(j).getWeight();
			}
			int time  = time1 + time2 + time3;
			if(min > time){
				start =i;
				min = time;
			}
		}
		
		return min;
	}
	
	public int minTimeStartIndex(int vnum, ArrayList<Edge> eList, ArrayList<Vertex> vList)
	{
		int min= 1000000;
		int start = 0;
		for(int i = 1; i<vList.size() - vnum + 1;  i++){
			int time1 = this.shortestPath.get(0).get(vList.get(i).getVertexNum()-1).getWeight();//가는 길
			int time2 = this.shortestPath.get(vList.get(i+vnum-1).getVertexNum()-1).get(0).getWeight();//오는 길
			int time3 = 0;
			for(int j = i; j<vnum; j++){
				time3 += eList.get(j).getWeight();
			}
			int time  = time1 + time2 + time3;
			if(min > time){
				start =i;
				min = time;
			}
		}
		
		return start;
	}
	
	//각 노드에서 출발점으로 돌아가는 시간을 계산해 어레이로 반환한다.
	public ArrayList<Integer> calcBackTime(ArrayList<Vertex> vList)
	{
		ArrayList<Integer> backTime = new ArrayList<Integer>();
		for(int i = 1; i<vList.size(); i++){
			int time = this.shortestPath.get(0).get(vList.get(i).getVertexNum() -1).getWeight();
			backTime.add(time);
		}
		return backTime;
	}
	
	//input타입에 따라 경로를 결정해낸다. 
	public void calcSolutionList1(int numpstop, ArrayList<Edge> eList, ArrayList<Vertex> vList)
	{
		/*
		for(int i = 1; i<10;i++)
		{
			System.out.println(minTime(i,eList, vList));
		}*/
		
		for(int i = 1; i<9; i++)
		{
			int minTime1 = this.minTimeVisitPocketStop(i, eList, vList);
			//int minTime2 = this.minTimeVisitPocketStop(i+1, eList, vList, pnum);
			if(this.inputTime <minTime1){
				System.out.println("몬스터를" + (i-1) + "마리 잡았습니다.");
				return;
			}
		}
		
	}
	
	
	////알고리즘 3/////
	

	public static void main(String[] args){
		long start = System.currentTimeMillis();
		Manager tempM = new Manager();
		
		tempM.setMonster(3);
		tempM.setTime(300);
		//long start = System.currentTimeMillis();
		tempM.calcAlgorithm1();
		long end = System.currentTimeMillis();

		System.out.println( "실행 시간 : " + ( end - start )/1000.0);
	}

}