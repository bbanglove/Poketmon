package designPrjAlgorithm;

import designPrjAlgorithm.Pocketmon;
import java.util.ArrayList;

public class User {
	final private String mName; //사용자 이름
	private int numOfMonsterBall;//가지고 있는 몬스터 볼의 수
	private ArrayList<Pocketmon> backOfMonster;//잡은 몬스터리스트
	
	//constructor
	public User(String name)
	{
		this.mName = name;
		numOfMonsterBall = 3;//초기 몬스터볼 갯수는 3으로 초기화
		ArrayList<Pocketmon> backOfMonster = new ArrayList<Pocketmon>(); //새 리스트를 할당한다. 
	}
	
	//getFunctions
	public String getName(){ return mName;}
	public int getNumOfBall(){return numOfMonsterBall;}
	
	//set Functions
	//몬스터볼 추가 및 사용
	public void addMonsterBall()
	{
		numOfMonsterBall += 3;//포켓스탑을 들르면 한 번에 3개를 얻는다.
	}
	public void useMonsterBall()
	{
		numOfMonsterBall -= 1; //몬스터를 잡아 몬스터볼의 갯수가 하나 줄었다
	}
	
	//몬스터를 잡음
	public void addMonster(Pocketmon newMonster)
	{
		backOfMonster.add(newMonster);
	}
	
	//몬스터를 잡았을 경우의 operation 수행.
	public void catchMonster(Pocketmon newMonster)
	{
		this.addMonster(newMonster);//잡은 몬스터를 추가하고
		this.useMonsterBall();//몬스터볼의 갯수를 줄인다.
	}
	
	//잡은 포켓몬이 있는지 여부를 체크한다.
	public boolean checkMonster(int monsterId)
	{
		for(int i = 0; i<backOfMonster.size(); i++){
			if(backOfMonster.get(i).getId() == monsterId){
				return true;
			}
		}
		return false;
	}

}
