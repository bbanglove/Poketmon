package designPrjAlgorithm;

import designPrjAlgorithm.User;
import designPrjAlgorithm.Pocketmon;
import designPrjAlgorithm.Vertex;
import designPrjAlgorithm.Edge;
import java.util.List;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Manager {
	private int inputTime;//입력받은 시간
	private int inputMonster; //입력받은 몬스터
	private User currentUser; //현재 사용자 객체
	private int numOfMonster;//monsterList의 index 지정을 위한 변수. 0으로 시작한다.
	
	//읽은 데이터를 저장할 애들
	private Pocketmon[] monsterList = new Pocketmon[10];//몬스터 배열
	private Vertex[] vertexList = new Vertex[100];//위치 정보 배열
	private Edge[][] edgeList = new Edge[100][100]; //노드 간 가중치 배열
	
	//문제 1,2,3에 대한 해답 경로를 저장한 리스트
	private List<Vertex> solutionList1;
	private List<Vertex> solutionList2;
	private List<Vertex> solutionList3;
	
	//Constructor
	public Manager()
	{
		this.inputTime = 0;
		this.inputMonster = 0;
		this.numOfMonster = 0;
		this.currentUser = null;
		this.readDataFile1();
		this.readDataFile2();
		this.readDataFile3();
		this.edgeList = null;
		this.solutionList1 = null;
		this.solutionList2 = null;
		this.solutionList3 = null;
	}
	
	public Manager(int time, int monster, User user)
	{
		this.inputTime = time;
		this.inputMonster = monster;
		this.numOfMonster = 0;
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
	public Pocketmon[] getMonsterList(){return this.monsterList;}
	public Vertex[] getVertexList(){return this.vertexList;}
	public Edge[][] getEdgeList(){return this.edgeList;}
	public List<Vertex> getSolutionList1(){return this.solutionList1;}
	public List<Vertex> getSolutionList2(){return this.solutionList2;}
	public List<Vertex> getSolutionList3(){return this.solutionList3;}
	
	//set Function
	public void setTime(int time){this.inputTime = time; }
	public void setMonster(int monster){this.inputMonster = monster;}
	public void setUser(User user){this.currentUser = user;}
	public void setMonsterList(Pocketmon[] mList){this.monsterList = mList;}
	public void setVertexList(Vertex[] vList){this.vertexList = vList;}
	public void setEdgeList(Edge[][] eList){this.edgeList = eList;}
	
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
			this.monsterList[this.numOfMonster] = tMonster;//몬스터배열에 담는다
			this.numOfMonster++; //배열 index를 하나 증가시킨다.
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
		for(int i = 0; i<this.numOfMonster; i++)
		{
			if(this.monsterList[i].getId() == id)
			{
				return this.monsterList[i];
			}
		}
		return null;//못 찾았을 경우 null을 반환
	}
	
	//몬스터리스트를 name 기준으로 검색
	public Pocketmon findPocketmonById(String name)
	{
		for(int i = 0; i<this.numOfMonster; i++)
		{
			if(this.monsterList[i].getName() == name)
			{
				return this.monsterList[i];
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
					this.vertexList[Integer.parseInt(num)-1] = tVertex;//리스트에 추가한다. 인덱스는 0부터 시작하고 장소번호는 1부터 시작하므로 1을 빼준다.
				}
				else //단순히 몬스터가 존재하는 포켓스탑인 경우
				{
					Vertex tVertex = new Vertex(Integer.parseInt(num), Integer.parseInt(monsterId)); //몬스터가 존재하는 vertex 객체를 생성해
					this.vertexList[Integer.parseInt(num)-1] = tVertex; //리스트에 추가한다.
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
				for(int j = 0; j<100; j++){//100개의 열에 대해
					String pre, post;
					idx = temp.indexOf("\t"); //첫번째 뛰어쓰기 인덱스를 찾아내 저장한다
					pre = temp.substring(0, idx); //뛰어쓰기 전 열을 받아온다
					post = temp.substring(idx + 1); //뛰어쓰기 이후의 열
					//Edge생성자 : Edge(엣지번호, 가중치,이전vertex, 이후vertex)
					Edge tEdge = new Edge((i*100 + j),Integer.parseInt(pre), this.vertexList[i], this.vertexList[j]);
					this.edgeList[i][j] = tEdge;//edge 리스트에 추가한다.
					if(j == 98) //만일 더 이상 찾아낼 \t가 없을 것으로 예상된다면
					{
						//남은 부분응로 edge를 구성해 추가한다.
						Edge lastEdge = new Edge((i*100 + j + 1), Integer.parseInt(post), this.vertexList[i], this.vertexList[j+1]); 
						this.edgeList[i][j+1] = lastEdge;
						break;
					}
					temp = post;
				}
				i++; //다음 행으로 이동한다.
			}
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		Manager tempM = new Manager();
		
	}
	

}
