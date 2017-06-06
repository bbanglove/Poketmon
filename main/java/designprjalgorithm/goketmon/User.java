package designprjalgorithm.goketmon;

/**
 * Created by 이석영 on 2017-06-01 0001.
 */

import designprjalgorithm.goketmon.Pocketmon;
import java.util.ArrayList;

public class User {
    final private String mName; //����� �̸�
    private int numOfMonsterBall;//������ �ִ� ���� ���� ��
    private ArrayList<Pocketmon> backOfMonster;//���� ���͸���Ʈ

    //constructor
    public User(String name) {
        this.mName = name;
        numOfMonsterBall = 3;//�ʱ� ���ͺ� ������ 3���� �ʱ�ȭ
        ArrayList<Pocketmon> backOfMonster = new ArrayList<Pocketmon>(); //�� ����Ʈ�� �Ҵ��Ѵ�.
    }

    //getFunctions
    public String getName() {
        return mName;
    }

    public int getNumOfBall() {
        return numOfMonsterBall;
    }

    //set Functions
    //���ͺ� �߰� �� ���
    public void addMonsterBall() {
        numOfMonsterBall += 3;//���Ͻ�ž�� �鸣�� �� ���� 3���� ��´�.
    }

    public void useMonsterBall() {
        numOfMonsterBall -= 1; //���͸� ��� ���ͺ��� ������ �ϳ� �پ���
    }

    //���͸� ����
    public void addMonster(Pocketmon newMonster) {
        backOfMonster.add(newMonster);
    }

    //���͸� ����� ����� operation ����.
    public void catchMonster(Pocketmon newMonster) {
        this.addMonster(newMonster);//���� ���͸� �߰��ϰ�
        this.useMonsterBall();//���ͺ��� ������ ���δ�.
    }

    //���� ���ϸ��� �ִ��� ���θ� üũ�Ѵ�.
    public boolean checkMonster(int monsterId) {
        for (int i = 0; i < backOfMonster.size(); i++) {
            if (backOfMonster.get(i).getId() == monsterId) {
                return true;
            }
        }
        return false;
    }
}