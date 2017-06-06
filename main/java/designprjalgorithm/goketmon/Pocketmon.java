package designprjalgorithm.goketmon;

/**
 * Created by 이석영 on 2017-06-01 0001.
 */
public class Pocketmon {
    final private String mName;//포켓몬 이름
    final private int mId; //아이디
    private int mLocation; //위치
    final private int mCombatPower; //cp
    final private int mHitPoint;//hp
    private boolean catchFlag; //잡혔는지 여부를 저장하는 플래그


    //constructor
    public Pocketmon(String name, int id)
    {
        this.mName = name;
        this.mId = id;
        this.mCombatPower = 0;
        this.mHitPoint = 0;
        this.catchFlag = false;
    }
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
    public int getCP(){return mCombatPower;}
    public int getHP(){return mHitPoint;}
    public boolean getCatchFlag() {return catchFlag;}

    //set functions
    public void setLocation(int location ){this.mLocation = location;}
    public void moveLocation(){ mLocation++;}
    public void changeFlag(){ this.catchFlag = false;}
}
