package designPrjAlgorithm;

public class Pocketmon {
	final private String mName;//���ϸ��� �̸�
	final private int mId; //id
	private int mLocation; //��ġ
	final private int mCombatPower; //cp
	final private int mHitPoint;//hp
	private boolean catchFlag; //if catched true
	
	//constructor
	public Pocketmon(String name, int id, int cp, int hp){
		this.mName = name;
		this.mId = id;
		this.mCombatPower = cp;
		this.mHitPoint = hp;
		this.catchFlag = false;
	}
	
	//get functions
	public String getName(){ return mName;}
	public int getId() {return mId;}
	public int getLocation() {return mLocation;}
	
	//search
	public int getIdFromLocation(int m_location){return mId;}
	
	public int getCP(){return mCombatPower;}
	public int getHP(){return mHitPoint;}
	public boolean getCatchFlag() {return catchFlag;}
	
	//set functions
	public void setLocation(int location ){this.mLocation = location;}
	public void moveLocation(){ mLocation++;}
	public void changeFlag(){ this.catchFlag = false;}

}
	