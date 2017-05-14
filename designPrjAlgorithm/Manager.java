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
	private List<Vertex> solutionList1;
	private List<Vertex> solutionList2;
	private List<Vertex> solutionList3;
	
	//각 알고리즘의 수행 시간
	private double time1;
	private double time2;
	private double time3;
	
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
		this.solutionList1 = null;
		this.solutionList2 = null;
		this.solutionList3 = null;
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
		this.solutionList1 = null;
		this.solutionList2 = null;
		this.solutionList3 = null;
		
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
	
	//첫 번째 경우에 대헤
	public void calcSolutionList1()
	{
		Graph sGraph = selectGraph(); //출발점 + 9개의 노드로 구성된 선별 그래프
		TSP mTsp = new TSP(sGraph.getSize(), sGraph.getEdgeList());//tsp를 호출해 초기화한다.
		List<Integer> path  = mTsp.getVertexNum(mTsp.calculateShortestPath(0));//최단 비용 순회 경로를 받아온다.
		//현재 짜는 중
		
	}

	public static void main(String[] args){
		Manager tempM = new Manager();
		
		tempM.setMonster(2);
		tempM.setTime(20);
		tempM.calcSolutionList1();
	}

}