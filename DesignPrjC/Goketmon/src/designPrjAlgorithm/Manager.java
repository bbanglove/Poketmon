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
	private int inputTime;//�Է¹��� �ð�
	private int inputMonster; //�Է¹��� ����
	private User currentUser; //���� ����� ��ü
	private int numOfMonster;//monsterList�� index ������ ���� ����. 0���� �����Ѵ�.
	
	//���� �����͸� ������ �ֵ�
	private Pocketmon[] monsterList = new Pocketmon[10];//���� �迭
	private Vertex[] vertexList = new Vertex[100];//��ġ ���� �迭
	private Edge[][] edgeList = new Edge[100][100]; //��� �� ����ġ �迭
	
	//���� 1,2,3�� ���� �ش� ��θ� ������ ����Ʈ
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
	//�ĺ��� ���� �Է�
	public void readDataFile1()
	{
		try{
		//1.��ġ �ĺ���
		System.out.println(Manager.class.getResource("").getPath());//������ �о���� ���� ��� ����
		BufferedReader reader = new BufferedReader(new FileReader(Manager.class.getResource("").getPath() + "��ġ�ĺ���.txt"));
		//�� ���ھ� �о���δ�
		String temp="";
		while((temp = reader.readLine()) != null)//�� �پ� �о���δ�.
		{
			String tId,tName; //�ӽ� ���� ��ü
			int idx = temp.indexOf(":"); //:�� ��ġ�� ã�Ƴ� idx�� �����Ѵ�
			tId = temp.substring(0, idx);//idx�� �������� �պκ��� id�� ����
			tName = temp.substring(idx + 1); //idx�� �������� �޺κ��� name�� ����
			Pocketmon tMonster = new Pocketmon(tName, Integer.parseInt(tId)); //���� ������� ���� ���ϸ� ��ü ����
			this.monsterList[this.numOfMonster] = tMonster;//���͹迭�� ��´�
			this.numOfMonster++; //�迭 index�� �ϳ� ������Ų��.
		}//while �� ��
		
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//���͸���Ʈ�� id �������� �˻�
	public Pocketmon findPocketmonById(int id)
	{
		for(int i = 0; i<this.numOfMonster; i++)
		{
			if(this.monsterList[i].getId() == id)
			{
				return this.monsterList[i];
			}
		}
		return null;//�� ã���� ��� null�� ��ȯ
	}
	
	//���͸���Ʈ�� name �������� �˻�
	public Pocketmon findPocketmonById(String name)
	{
		for(int i = 0; i<this.numOfMonster; i++)
		{
			if(this.monsterList[i].getName() == name)
			{
				return this.monsterList[i];
			}
		}
		return null;//�� ã���� ��� null�� ��ȯ
	}
	
	//��ġ���� ���� �Է�
	public void readDataFile2()
	{
		try{
		//2.��ġ����
			System.out.println(Manager.class.getResource("").getPath());//������ �о���� ���� ��� ����
			BufferedReader reader = new BufferedReader(new FileReader(Manager.class.getResource("").getPath() + "��ġ����.txt"));
			String temp ="";
			while((temp = reader.readLine()) != null)
			{
				String num, monsterId; //��� ��ȣ�� �ش� ��ġ�� �����ϴ� ������ ��ġ�� ������ �ӽú���
				int idx = temp.indexOf("\t");//�پ�� ��ġ�� ã�� �ش� �ε����� �����Ѵ�.
				num = temp.substring(0, idx);//�پ�⸦ �������� ���� ���ڿ��� ����
				monsterId = temp.substring(idx+1);//�پ�⸦ �������� ������ ���ڿ��� ����
				if(Integer.parseInt(monsterId) == 11)//���� index = 11�̾ �ش� ��Ұ� ���Ͻ�ž�̶�� �Ǵܵȴٸ�
				{
					Vertex tVertex = new Vertex(Integer.parseInt(num), true);//���Ͻ�ž vertex�� �ʱ�ȭ��  
					this.vertexList[Integer.parseInt(num)-1] = tVertex;//����Ʈ�� �߰��Ѵ�. �ε����� 0���� �����ϰ� ��ҹ�ȣ�� 1���� �����ϹǷ� 1�� ���ش�.
				}
				else //�ܼ��� ���Ͱ� �����ϴ� ���Ͻ�ž�� ���
				{
					Vertex tVertex = new Vertex(Integer.parseInt(num), Integer.parseInt(monsterId)); //���Ͱ� �����ϴ� vertex ��ü�� ������
					this.vertexList[Integer.parseInt(num)-1] = tVertex; //����Ʈ�� �߰��Ѵ�.
				}
			}
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//��ġ�� �Ҹ�ð�
	public void readDataFile3()
	{
		try{
			System.out.println(Manager.class.getResource("").getPath());//������ �о���� ���� ��� ����
			BufferedReader reader = new BufferedReader(new FileReader(Manager.class.getResource("").getPath() + "��ġ���Ҹ�ð�.txt"));
			int i = 0;//��ġ ���� �迭 ������ ���� index ����
			String temp ="";
			int idx =0;
			while((temp = reader.readLine()) != null) //�� �྿ �о���δ�,
			{
				for(int j = 0; j<100; j++){//100���� ���� ����
					String pre, post;
					idx = temp.indexOf("\t"); //ù��° �پ�� �ε����� ã�Ƴ� �����Ѵ�
					pre = temp.substring(0, idx); //�پ�� �� ���� �޾ƿ´�
					post = temp.substring(idx + 1); //�پ�� ������ ��
					//Edge������ : Edge(������ȣ, ����ġ,����vertex, ����vertex)
					Edge tEdge = new Edge((i*100 + j),Integer.parseInt(pre), this.vertexList[i], this.vertexList[j]);
					this.edgeList[i][j] = tEdge;//edge ����Ʈ�� �߰��Ѵ�.
					if(j == 98) //���� �� �̻� ã�Ƴ� \t�� ���� ������ ����ȴٸ�
					{
						//���� �κ����� edge�� ������ �߰��Ѵ�.
						Edge lastEdge = new Edge((i*100 + j + 1), Integer.parseInt(post), this.vertexList[i], this.vertexList[j+1]); 
						this.edgeList[i][j+1] = lastEdge;
						break;
					}
					temp = post;
				}
				i++; //���� ������ �̵��Ѵ�.
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
