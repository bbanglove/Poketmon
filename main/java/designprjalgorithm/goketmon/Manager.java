package designprjalgorithm.goketmon; /**
 * Created by 이석영 on 2017-06-01 0001.
 */

import android.app.Application;

import designprjalgorithm.goketmon.User;
import designprjalgorithm.goketmon.Pocketmon;
import designprjalgorithm.goketmon.Vertex;
import designprjalgorithm.goketmon.Edge;
import designprjalgorithm.goketmon.Graph;
import designprjalgorithm.goketmon.TSP;
import designprjalgorithm.goketmon.Floyd;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

public class Manager {

    private Context context;
    private static Manager managerInstance; //static 객체변수 인스턴스 선언

    //static 객체변수 getter 선언
    public static Manager getManagerInstance(AssetManager am){
        if(managerInstance== null){
            managerInstance = new Manager(am);
        }
        return managerInstance;
    }

    private int inputTime;//입력받은 시간
    private int inputMonster; //입력받은 몬스터
    private User currentUser; //현재 사용자 객체
    //private int numOfMonster;//monsterList의 index 지정을 위한 변수. 0으로 시작한다.

    //읽은 데이터를 저장할 애들
    private ArrayList<Pocketmon> monsterList = new ArrayList<Pocketmon>();//몬스터 배열
    private ArrayList<Vertex> vertexList = new ArrayList<Vertex>();//위치 정보 배열
    private ArrayList<ArrayList<Edge>> edgeList = new ArrayList<ArrayList<Edge>>(); //노드 간 가중치 배열
    private ArrayList<ArrayList<Integer>> savedPath = new ArrayList<ArrayList<Integer>>(); //##### 플로이드 이용해 다음 노드 저장된 배열

    private ArrayList<ArrayList<Edge>> shortestPath = new ArrayList<ArrayList<Edge>>();//플로이드를 이용해 계산한 노드 간 최단 경로가 저장된 배열.
    private ArrayList<Vertex> pocketStopList = new ArrayList<Vertex>();
    private  ArrayList<Vertex> solutionList1 = new ArrayList<Vertex>();
    //private  ArrayList<Edge> solutionPath1 = new ArrayList<Edge>();
    private  ArrayList<Vertex> solutionList2= new ArrayList<Vertex>();
    //private  ArrayList<Edge> solutionPath2= new ArrayList<Edge>();
    private  ArrayList<Vertex> solutionList3= new ArrayList<Vertex>();
    //private  ArrayList<Edge> solutionPath3= new ArrayList<Edge>();

    //알고리즘1에서 잡은 몬스터 갯수
    private int catchNum;
    //각 알고리즘의 수행 시간
    private int timeOption1;
    private int timeOption2;
    private int timeOption3;

    //최단경로 탐색을 위한 탐색 클레스 객체추가
    //private TSP mTSP;
    private Floyd mFloyd;

    private int[][] visited = new int[100][100];
    //Constructor
    public Manager(AssetManager am)
    {
        this.inputTime = 0;
        this.inputMonster = 0;
        this.catchNum = 0;
        //this.numOfMonster = 0;
        this.currentUser = null;
        this.readDataFile1(am);
        this.readDataFile2(am);
        this.readDataFile3(am);
        this.mFloyd = new Floyd(100, edgeList);
        this.initShortestPath(am);
        this.edgeList.clear();
        this.readDataFile3(am);
        this.readVisited(am);
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
    public int getCatchNum(){return this.catchNum;}
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
    public int getTimeOp1(){return this.timeOption1;}
    public int getTimeOp2(){return this.timeOption2;}
    public int getTimeOp3(){return this.timeOption3;}

    //set Function
    public void setCatchNum(int num){this.catchNum = num;}
    public void setTime(int time){this.inputTime = time; }
    public void setMonster(int monster){this.inputMonster = monster;}
    public void setUser(User user){this.currentUser = user;}
    public void setMonsterList(ArrayList<Pocketmon> mList){this.monsterList = mList;}
    public void setVertexList(ArrayList<Vertex> vList){this.vertexList = vList;}
    public void setEdgeList(ArrayList<ArrayList<Edge>> eList){this.edgeList = eList;}
    public void setTimeOp1(int t){this.timeOption1 = t;}
    public void setTimeOp2(int t){this.timeOption2 = t;}
    public void setTimeOp3(int t){this.timeOption3 = t;}

    public void addCatchNum(){this.catchNum++;}
    public boolean setMonsterByString(String monster)
    {
       if(findPocketmonByName(monster) != null) {
           int id = findPocketmonByName(monster).getId();
           this.inputMonster = id;
           return true;
       }
        return false;
    }
    //모든 노드를 방문하지않은 것으로 세팅한다.
    public void initVisited()
    {
        for(int i = 0; i<this.vertexList.size(); i++)
            vertexList.get(i).setVisitedFlag(0);
    }
    //플로이드 함수를 호출해 최단 경로를 계산, 미리 저장한다.
    public void initShortestPath(AssetManager am)
    {
        /*
        mFloyd.getShortestPath(vertexList);
        for(int i = 0; i<100; i++){
            ArrayList<Edge> eRow = new ArrayList<Edge>();
            ArrayList<Integer> iRow = new ArrayList<Integer>();
            for(int j = 0; j<100; j++){
                eRow.add(this.mFloyd.getGraph().get(i).get(j));
                iRow.add(this.mFloyd.getPGraph().get(i).get(j));
            }
            this.shortestPath.add(eRow);
            this.savedPath.add(iRow);
        }*/
        try{
            InputStream is = am.open("path.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
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
                this.shortestPath.add(eRow);
                i++; //다음 행으로 이동한다.
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }


    }
    //display Function

    //FILE i/o
    //식별자 파일 입력
    public void readDataFile1(AssetManager am)
    {
        try{
            //1.위치 식별자
            InputStream is = am.open("identifier.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
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

    //몬스터 이름을 알아낸다
    public String getMonsterName(int id)
    {
        for(int i = 0; i<this.monsterList.size(); i++){
            if(this.monsterList.get(i).getId() == id)
                return this.monsterList.get(i).getName();
        }
        return "None";
    }
    //몬스터리스트를 name 기준으로 검색
    public Pocketmon findPocketmonByName(String name)
    {
        for(int i = 0; i<this.monsterList.size(); i++)
        {
            if(this.monsterList.get(i).getName().equals(name))
            {
                return this.monsterList.get(i);
            }
        }
        return null;//못 찾았을 경우 null을 반환
    }

    //위치정보 파일 입력
    public void readDataFile2(AssetManager am)
    {
        try{
            //2.위치정보
            InputStream is = am.open("location.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
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
    public void readDataFile3(AssetManager am)
    {
        try{
            InputStream is = am.open("time.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
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

    public void readVisited(AssetManager am)
    {
        try{
            InputStream is = am.open("visited.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
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
                    this.visited[i][j] = Integer.parseInt(pre);//저장
                    if(j == 98) //만일 더 이상 찾아낼 \t가 없을 것으로 예상된다면
                    {
                        //남은 부분응로 edge를 구성해 추가한다.
                        this.visited[i][j] = Integer.parseInt(post);
                        break;
                    }
                    temp = post;
                }
                i++; //다음 행으로 이동한다.
            }

        }catch(FileNotFoundException e){
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
            //solutionList1.add(sGraph.getVertexList().get(path.get(i)));//solutionList에 해당 index를 저장하고
        }

        ArrayList<Edge> orderEdge = new ArrayList<Edge>();
        for(int i = 0; i<orderVertex.size() -1; i++){
            int src = orderVertex.get(i).getVertexNum();
            int dst = orderVertex.get(i+1).getVertexNum();
            orderEdge.add(this.shortestPath.get(src -1).get(dst-1));
        }

        int  numOfPStop = this.calcNumberOfPocketStop(orderEdge).size();
        this.calcSolutionList1(numOfPStop, orderEdge, orderVertex);
        this.calcCatchMonster();
    }

    public void calcCatchMonster()
    {
        for(int i = 0; i<solutionList1.size(); i++)
        {
            if(solutionList1.get(i).getMonsterId() == this.inputMonster){
                addCatchNum();
            }
        }
    }
    public int getSList1Index(int vNum)
    {
        for(int i = 0; i<solutionList1.size(); i++){ //헤당 vertex가 존재하는지 검사
            if(solutionList1.get(i).getVertexNum() == vNum)//존재한다면
                return i;//해당 인덱스를 반환한다.
        }
        return -1; //존재하지 않을 경우 -1을 반환.
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
            int ptime1 = 0; int pStop = 0; int visit1 =0; int visit2 = 0; //포켓스탑으로 우회했는지 여부를 알려주는 플래그
            if(vnum>3 && section1 == 0 )//4보다 더 많이 지나는데 첫번째 구간 내에 포켓스탑이 없다면
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
            if(vnum>6 && section1 != 2 ){ //두 개를 들러야 할 경우, 두 번쨰 구간에서의 맨 첫 번째 에지에서 들린다.
                visit2 = 1;//두 번째 우회경로를 통과하였슴
                //this.currentUser.addMonsterBall();
                int src2 = vList.get(6).getVertexNum()-1;//출발점의 인덱스
                int dst2 = vList.get(7).getVertexNum()-1;//도착점의 인덱스
                pStop2 = this.findNearestPocketStop(vList).get(6).getVertexNum()-1;//해당 포켓스탑의 인덱스
                //this.solutionList1.add(this.getSList1Index(src2)+1, this.vertexList.get(pStop2)); //해당 출발점 뒤에 추가.
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
        this.solutionList1.add(vertexList.get(0));
        for(int i = 1; i<9; i++)
        {
            //this.solutionList1.add(vList.get(i-1));
            int visited = this.visited[vList.get(i-1).getVertexNum()-1][vList.get(i).getVertexNum()-1];//경유점
            if(visited != 0)
                solutionList1.add(i, this.vertexList.get(visited));
            this.solutionList1.add(vList.get(i));
            int minTime1 = this.minTimeVisitPocketStop(i, eList, vList);
            //int minTime2 = this.minTimeVisitPocketStop(i+1, eList, vList, pnum);
            if(this.inputTime <minTime1){
                this.solutionList1.remove(vList.get(i));
                this.solutionList1.remove(this.vertexList.get(visited));
                System.out.println("몬스터를" + (i-1) + "마리 잡았습니다.");
                setTimeOp1(minTime1);
                for(int j = 0; j<solutionList1.size(); j++){
                    System.out.println("Vertex: " + solutionList1.get(j).getVertexNum());
                }
                return;
            }
        }

    }

    ///두번째 알고리즘 //#####
    public void calcAlgorithm2(AssetManager am){

       this.readDataFile2(am);
        Graph pGraph = new Graph();
        pGraph = selectPoketStopGraph(); //포켓스탑을 가져와 만든 그래프

        TSP pTsp = new TSP(pGraph.getSize(),pGraph.getEdgeList());//tsp를 호출해 초기화한다.
        List<Integer> path = pTsp.getVertexNum(pTsp.calculateShortestPath(0));//최단 비용 순회 경로를 받아온다.
        int totaltime = pTsp.calculateShortestPath(0);
        path.add(0,0);//반환된 경로에는 출발점이 제외되어 있으므로 첫 번째 자리에 이를 추가해준다.
        path.add(path.size(),0);
        //순서가 정해진 TSP 경로의 vertex와 엣지 리스트
        ArrayList<Vertex> orderVertex = new ArrayList<Vertex>();
        for(int i = 0; i<path.size(); i++){
            orderVertex.add(pGraph.getVertexList().get(path.get(i)));//path가 저장하고 있는 인덱스로부터 vertex 객체를 불러와 저장한다.
        }
        //9개 포켓스탑 도는데 498분 약 8시간 걸림
        int result=0;
        ArrayList<Edge> orderEdge = new ArrayList<Edge>();
        for(int i = 0; i<orderVertex.size() -1; i++){
            int src = orderVertex.get(i).getVertexNum();
            int dst = orderVertex.get(i+1).getVertexNum();
            orderEdge.add(this.shortestPath.get(src -1).get(dst-1));
        }
        int shortTime = 0; //단위시간
        int poketball = 3; //전체 포켓볼 갯수
        int monsterNum = 0; //노드-노드간 경유해서 잡을 수 있는 몬스터 수
        int getMon = 0; //총 잡은 몬스터 갯수
        int visitedVertex=0; //두개 정점간 경유한 노드 개수
        int restTime = 0;
        int vNum=0;
        int circleNum = 0; //몇바퀴 돌았는지 저장

        ArrayList<Vertex> vNumVertex = new ArrayList<Vertex>(); //포켓정점 갯수에 따른 최단경로 저장

        ArrayList<Vertex> totalVertex = new ArrayList<Vertex>(); //합칠것~
        ArrayList<ArrayList<Edge>> vNumEdge = new ArrayList<ArrayList<Edge>>();

        int timeByVnum []= {134,206,206,232,282,326,377,459,498}; //포켓스탑 정점 9개 기준 0~8 인덱스


        int getMoreMonNum []= new int [9];

        //시간이랑 비교하기!
        for(int i=0; i<8; i++){

            if(inputTime>=timeByVnum[i] && inputTime<timeByVnum[i+1]){
                vNum=i+1;
                restTime= inputTime - timeByVnum[i];
            }
            else if(inputTime==498){
                vNum=9;
                restTime= 0;
            }
            else if (inputTime>498){
                circleNum=inputTime/498;
                if(inputTime%498 >=timeByVnum[i] && inputTime%498<timeByVnum[i+1]){
                    vNum=i+1;
                    restTime= inputTime%498 - timeByVnum[i];
                }
                else if(inputTime%498==0){
                    circleNum--;
                    vNum=9;
                    restTime =0;
                }
                else if(inputTime%498<134){
                    vNum=0;
                    restTime = inputTime%498;
                }
                else;
            }//여러번 돌때
            else if (inputTime<134){
                vNum=0;
                restTime=inputTime;
            } //
            else;


        }

        //입력시간과 경유 노드 갯수 출력

        monsterNum=0;
        visitedVertex=0;
        getMon=0;
        poketball=3;

        vNumVertex = findShortNumOfVertex(vNum,orderVertex);
        //정점의 개수에 따라 달라지는 순회 정점의 조합을 모두 구해서 리스트에 저장한다

        for(int k = 0; k<vNumVertex.size(); k++)
        {
            ArrayList<Edge> eRow = new ArrayList<Edge>();
            for(int j= 0; j<vNumVertex.size(); j++){
                int src = vNumVertex.get(k).getVertexNum();
                int dst = vNumVertex.get(j).getVertexNum();
                eRow.add(this.shortestPath.get(src-1).get(dst-1));
            }
            vNumEdge.add(eRow);
        }

        if(vNum!=0){

            //k는 현재 방문한 vertex 갯수 //더 잡을 수 있는 몬스터 수와 잡은 몬스터 계산해줌
            for(int i=0; i<vNumVertex.size()-1; i++){


                int src = vNumVertex.get(i).getVertexNum();
                int des = vNumVertex.get(i+1).getVertexNum();

                monsterNum = shortestPath.get(src-1).get(des-1).getMonsterNum();
                //System.out.println(src+"to"+des+" : "+monsterNum+"p "+poketball+"get"+getMon);
                //만약에 노드-노드간에 포켓볼 수를 넘어버리면?
                if(monsterNum>poketball){
                    getMon+=poketball;
                    poketball = 0;
                    //그리고 들렸던 노드인지도 예외처리 해주기
                }
                else{
                    poketball = poketball-monsterNum;
                    getMon+=monsterNum;
                }
                if(i!=vNumVertex.size()-2){poketball=poketball+3;}
                else;
            }
        }
        //1차 경로 저장

        totalVertex.addAll(mergeVertex(vNumVertex));//경로추출
        int monCount =totalVertex.size()-vNumVertex.size()-1; //몬스터 잡은갯수

        System.out.println(" ");
        getMoreMonNum[vNum-1]=poketball;
        solutionList2=null;
        solutionList2=totalVertex;

    }

    public ArrayList<Vertex> pathMiddleVertex(int start, int end ){ //start에서 end까지 사이경로를 "출력"만해주는함수
        //vertexNum-1 을 입력파라미터로 받아야함
        //recursive해서 변수를 저장하거나 리턴하기 힘들어 그냥 출력만함

        ArrayList<Vertex> temp = new ArrayList<Vertex>();

        if(visited[start][end] !=0){
            temp.addAll(pathMiddleVertex(start,visited[start][end]));

            temp.add(vertexList.get(visited[start][end]));

            temp.addAll(pathMiddleVertex(visited[start][end],end));

        }
        return temp;
    }

    //처음 정점을 뽑아낸 경로에 그 사이 플로이드알고리즘을 통해 알아낸 노드 추가하여 하나의 어레이리스트로 합쳐주는 함수
    public ArrayList<Vertex> mergeVertex(ArrayList<Vertex>poketStop){ // : poketStop에 꼭 시작점 끝점 들가이써야함

        ArrayList <Vertex> pointNode = new ArrayList<Vertex>();
        //pointNode : 안에 시작점 끝점(출발점) 둘다 들가있는 arraylist
        pointNode.addAll(poketStop);

        ArrayList<Vertex> tempList = new ArrayList<Vertex>();

        for(int i=0; i<pointNode.size()-1; i++){
            tempList.add(pointNode.get(i));
            tempList.addAll(pathMiddleVertex(pointNode.get(i).getVertexNum()-1, pointNode.get(i+1).getVertexNum()-1));

        }

        tempList.add(pointNode.get(pointNode.size()-1));

        return tempList;
    }

    // vnum은 정점 갯수, 정점의 갯수가 정해졌을 때의 TSP경로 중 가장 빠른 정점리스트 출력해줌
    ArrayList<Vertex> findShortNumOfVertex (int vnum, ArrayList<Vertex> pStop){
        ArrayList<Vertex> vlist = new ArrayList<Vertex>();
        if(vnum==1){
            vlist.add(pStop.get(0));
            vlist.add(pStop.get(1));
            vlist.add(pStop.get(0));
        }
        else if(vnum==2){
            vlist.add(pStop.get(0));
            vlist.add(pStop.get(8));
            vlist.add(pStop.get(9));
            vlist.add(pStop.get(0));
        }
        else if(vnum==3){
            vlist.add(pStop.get(0));
            vlist.add(pStop.get(7));
            vlist.add(pStop.get(8));
            vlist.add(pStop.get(9));
            vlist.add(pStop.get(0));
        }
        else if(vnum==4){
            vlist.add(pStop.get(0));
            vlist.add(pStop.get(6));
            vlist.add(pStop.get(7));
            vlist.add(pStop.get(8));
            vlist.add(pStop.get(9));
            vlist.add(pStop.get(0));
        }
        else if(vnum==5){
            vlist.add(pStop.get(0));
            vlist.add(pStop.get(5));
            vlist.add(pStop.get(6));
            vlist.add(pStop.get(7));
            vlist.add(pStop.get(8));
            vlist.add(pStop.get(9));
            vlist.add(pStop.get(0));
        }
        else if(vnum==6){
            vlist.add(pStop.get(0));
            vlist.add(pStop.get(4));
            vlist.add(pStop.get(5));
            vlist.add(pStop.get(6));
            vlist.add(pStop.get(7));
            vlist.add(pStop.get(8));
            vlist.add(pStop.get(9));
            vlist.add(pStop.get(0));
        }
        else if(vnum==7){
            vlist.add(pStop.get(0));
            vlist.add(pStop.get(3));
            vlist.add(pStop.get(4));
            vlist.add(pStop.get(5));
            vlist.add(pStop.get(6));
            vlist.add(pStop.get(7));
            vlist.add(pStop.get(8));
            vlist.add(pStop.get(9));
            vlist.add(pStop.get(0));
        }
        else if(vnum==8){
            vlist.add(pStop.get(0));
            vlist.add(pStop.get(2));
            vlist.add(pStop.get(3));
            vlist.add(pStop.get(4));
            vlist.add(pStop.get(5));
            vlist.add(pStop.get(6));
            vlist.add(pStop.get(7));
            vlist.add(pStop.get(8));
            vlist.add(pStop.get(9));
            vlist.add(pStop.get(0));
        }
        else if(vnum==9){
            vlist=pStop;
        }
        else;
        return vlist;
    }
    void calcSolutionList2(){}

    //포켓스탑으로 이루어진 그래프 생성  (11,30,40,47,48,57,64,78,83)
    public Graph selectPoketStopGraph(){//#####
        ArrayList<Vertex> selectedVertex = new ArrayList<Vertex>();//navigator로 넘긴 그래프 객체를 이룰
        selectedVertex.add(this.vertexList.get(0));
        int numSelected = 1;
        for(int i = 0; i<100; i++) //100개의 노드에 대해 검사를 수행한다.
        {
            if(vertexList.get(i).getPocketStop() == true) //만일 vertexList의 해당 index노드에 inputMonster와 같은 몬스터가 있다면
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


    ////알고리즘 3///////
    //어떤 한 점으로부터 가장 가까운 포켓스탑의 인덱스 반환하기
    public int findNearestPocketStopNtoN(int sNum)
    {
        int min = 10000; //최솟값 저장을 위한 임시변수
        int nearest = 0;
        for(int i = 0; i<this.pocketStopList.size(); i++)
        {
            if(pocketStopList.get(i).getVertexNum()-1 != sNum){
                int dst = this.pocketStopList.get(i).getVertexNum()-1;
                int time = this.shortestPath.get(sNum).get(dst).getWeight();
                if(min > time)
                {
                    min = time;
                    nearest = dst;
                }
            }
        }
        return nearest;
    }

    //편의를 위해 만든 arraylist와int를 받아 arraylist안에 그 값이 있는지 index를 반환해주는 함수
    public boolean arrayListHasNum(ArrayList<Integer> arr, int num)
    {
        for(int i = 0; i<arr.size(); i++)
        {
            if(arr.get(i) == num)
                return true;
        }
        return false;
    }

    //역시 편의를 위해 만든, 인덱스를 받아 해당 인덱스에서 출발점으로 돌아오는 최단거리를 알려주는 함수
    public int shortestTimeToHome(int index)
    {
        return this.shortestPath.get(index).get(0).getWeight();
    }

    //algorithm3 의 값을 반환
    public void calcAlgorithm3()
    {
        ArrayList<Integer> toVisit = new ArrayList<Integer>();
        for(int i = 1; i<11; i++){
            toVisit.add(i);
        }
        int time = 0;
        //가장 가까운 포켓스탑 세 개 들리기
        int startindex = 0;
        for(int i = 0; i<3; i++){//가장 가까운 포켓스탑 세 개를 들리자
            int pstopindex = this.findNearestPocketStopNtoN(startindex);//가장 가까운 포켓스탑 인덱스
            time += this.shortestPath.get(startindex).get(pstopindex).getWeight();//가중치를 누적시간에 추가
            Vertex pstop = this.vertexList.get(pstopindex);
            int visitedindex =visited[startindex][pstopindex];
            if(visitedindex != -1)
            {//경유점을 솔루션리스트에 추가
                this.solutionList3.add(this.vertexList.get(visitedindex)); //경유 지점을 solutionlist에 추가하고
                this.vertexList.get(visitedindex).setVisitedFlag(1);//방문 플래그를 1로 설정한다.
                for(int j = 0; j<toVisit.size(); j++){//잡아야 하는 인덱스 배열에서 지난 경유지에서 잡은 것 제외
                    if(toVisit.get(j) == this.vertexList.get(visitedindex).getMonsterId()){
                        toVisit.remove(j);//잡힌 몬스터 제거
                        System.out.println("get MonsterId " + this.findPocketmonById(this.vertexList.get(visitedindex).getMonsterId()).getId()
                                + " in Vertex " + (visitedindex + 1));
                    }
                }
            }
            this.solutionList3.add(this.vertexList.get(pstopindex));//포켓스탑 인덱스에 추가
            this.vertexList.get(pstopindex).setVisitedFlag(1); //방문 플래그를 1로 설정한다.
            startindex = pstopindex;
        } //몬스터볼 만당 -잡힌 갯수

        //이제부터 BFS를 시작한다.
        while(toVisit.size() != 0)//더 이사 못 잡은 몬스터가 없을 때까지.
        {
            int best = 100000;//최적 순회 비용
            int bestindex = 0;//최적의 vertex의 인덱스
            for(int i = 1; i<100; i++){//전체 vertex에 대해
                if(this.edgeList.get(startindex).get(i).getWeight() != -1 && i != startindex  && this.vertexList.get(i).getVistedFlag() == 0){//현 startindex와 연결되어 있는 점에 대해
                    if(this.arrayListHasNum(toVisit, this.vertexList.get(i).getMonsterId())){//아직 잡지않은몬스터가 있을때
                        int mintime = this.shortestPath.get(startindex).get(i).getWeight() //현재 startindex에서 해당 경로로 가는 최단 경로
                                + this.shortestPath.get(i).get(0).getWeight();// + 이동할 인덱스에서 집으로 돌아가는  최단경로
                        if(mintime <best){//만약 지금까지 나온 비용보다 작은, 최소 비용으로 판단된다면
                            best = mintime;
                            bestindex = i;
                        }
                    }//아직 잡지 않은 몬스터 if문 끝
                }//연결된 깊이 1 엣지 탐색 끝.
            }//엣지 탐색 끝.
            if(bestindex != 0 && best != 100000){//만일 깊이 1인 인접노드 내에 처음 잡는 몬스터가 존재한다면)
                for(int j = 0; j<toVisit.size(); j++){//잡아야하는 인덱스 배열에서 이 노드에서 잡는 것을 제외한다.
                    if(toVisit.get(j) == this.vertexList.get(bestindex).getMonsterId()){//이 노드에 존재하는 잡을몬스터와 일치하는 것을 찾아
                        toVisit.remove(j);//잡힌 몬스터를 잡아야 하는 몬스터 배열애서 삭제한다.
                        System.out.println("get MonsterId " + this.findPocketmonById(this.vertexList.get(bestindex).getMonsterId()).getId()
                                + " in Vertex " + (bestindex + 1));//들른 곳을 출력
                    }
                }
                this.solutionList3.add(this.vertexList.get(bestindex));//이 노드를 솔루션 리스트에 추가한다.
                this.vertexList.get(bestindex).setVisitedFlag(1);
                time += this.edgeList.get(startindex).get(bestindex).getWeight(); //이 노드까지 오는 이동경로를 전체 비용에 더한다.
                startindex = bestindex;//새로운 시작점으로 설정
            }
            else{//만일 깊이 1인 인접노드 내에 처음 잡는 몬스터가 존재하지 않는다면
                int min = 100000;
                int minindex = 0;
                for(int i = 1; i<100; i++){//전체 vertex에 대해
                    if(this.edgeList.get(startindex).get(i).getWeight() != -1 && i != startindex && this.vertexList.get(i).getVistedFlag() == 0){//인접한 깊이 1의 노드들에 대해
                        int mintime =  this.shortestPath.get(startindex).get(i).getWeight() //현재 startindex에서 해당 경로로 가는 최단 경로
                                + this.shortestPath.get(i).get(0).getWeight();// + 이동할 인덱스에서 집으로 돌아가는  최단경로
                        if(min > mintime){//최소비용 인접 노드를 찾는다.
                            min = mintime;
                            minindex = i;
                        }
                    }
                }//포문 끝
                time += this.shortestPath.get(startindex).get(minindex).getWeight();//해당 인접노드로 가는 비용을 누적시킨다.
                this.solutionList3.add(this.vertexList.get(minindex));//해당 인접노드를 솔루션 리스트에 추가한다.
                this.vertexList.get(minindex).setVisitedFlag(1);//방문 플래그를 1로 설정한다.
                startindex = minindex; //새로운 출발점을 이 인접노드로 하고 다시 탐색을 수행한다.
                System.out.println("go through Vertex " + (startindex+1));
            }//else 문 끝
        }//while 문 끝

        //이제 모든 몬스터를 잡았다.
        this.setTimeOp3(time);
        System.out.println("time : " + time);
        setTimeOp3(time);
    }

    ///////////////////////2차 알고리즘////////////////////////////////////////////////////////////////
    public void calcAlgorithm1_2()
    {

    }
}
