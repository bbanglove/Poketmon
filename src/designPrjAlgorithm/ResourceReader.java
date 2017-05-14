package designPrjAlgorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResourceReader {

	private static ResourceReader instance;
	private File vectorMatrix;
	private File locationIdentifier;
	private File locationInfo;
	
	private List<Vertex> 		vertexList; //size 100 fixed
	private Edge[][] 			edgeList;
	private List<Pocketmon> 	monsterList;
	
	private static final int totalVertexNum = 100; 
	private static final String projectDir = System.getProperty("user.dir");
	private static final String resourceDir = projectDir+File.separator+"resource"+File.separator;
	
	private ResourceReader() throws Exception{
		vertexList		= new ArrayList<>(totalVertexNum);
		edgeList 		= new Edge[totalVertexNum][totalVertexNum];	
		monsterList 	= new ArrayList<>();
		
		vectorMatrix = new File(resourceDir+"vectorMatrix.txt");
		locationInfo = new File(resourceDir+"locationInfo.txt");
		locationIdentifier = new File(resourceDir+"locationIdentifier.txt");
		
		if(!vectorMatrix.exists() || !locationInfo.exists() || !locationIdentifier.exists()){
			StringBuilder builder =new StringBuilder();
			builder.append("vectorMatrix file : "+vectorMatrix.exists());
			builder.append("locationInfo file : "+locationInfo.exists());
			builder.append("locationIdentifier file : "+locationIdentifier.exists());
			throw new FileNotFoundException(builder.toString());
		}
		readlocationIdentifier();
		readLocationInfo();
		readEdgeMatrix();
	}
	
	public List<Pocketmon> getPocketmonList() {
		return monsterList;
	}

	public List<Vertex> getVertexList() {
		return vertexList;
	}

	public Edge[][] getEdgeList() {
		return edgeList;
	}

	private void readEdgeMatrix(){
		try{
			BufferedReader reader = new BufferedReader(new FileReader(vectorMatrix));
			int i = 0;
			String temp ="";
			int idx =0;
			while((temp = reader.readLine()) != null) 
			{
				for(int j = 0; j<100; j++){
					String pre, post;
					idx = temp.indexOf("\t"); 
					pre = temp.substring(0, idx); 
					post = temp.substring(idx + 1); 

					Edge tEdge = new Edge((i*100 + j),Integer.parseInt(pre), vertexList.get(i), vertexList.get(j));
					this.edgeList[i][j] = tEdge;
					if(j == 98)
					{
						Edge lastEdge = new Edge((i*100 + j + 1), Integer.parseInt(post), vertexList.get(i), vertexList.get(j+1)); 
						this.edgeList[i][j+1] = lastEdge;
						break;
					}
					temp = post;
				}
				i++; 
			}
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	private void readLocationInfo(){
		try{
				BufferedReader reader = new BufferedReader(new FileReader(locationInfo));
				String temp ="";
				while((temp = reader.readLine()) != null)
				{
					String num, monsterId; 
					int idx = temp.indexOf("\t");
					num = temp.substring(0, idx);
					monsterId = temp.substring(idx+1);
					if(Integer.parseInt(monsterId) != 11)
					{
						Vertex tVertex = new Field(Integer.parseInt(num), Integer.parseInt(monsterId)); //���Ͱ� �����ϴ� vertex ��ü�� ������
						vertexList.add(Integer.parseInt(num)-1, tVertex);
					}else{
						Vertex tVertex = new PocketStop(Integer.parseInt(num));  
						vertexList.add(Integer.parseInt(num)-1, tVertex); 
					}
				}
			} catch(IOException e){
				e.printStackTrace();
			}
	}

	private void readlocationIdentifier() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(locationIdentifier));
			String temp = "";
			while ((temp = reader.readLine()) != null) {
				String tId, tName;
				int idx = temp.indexOf(":");
				tId = temp.substring(0, idx);
				tName = temp.substring(idx + 1);
				Pocketmon tMonster = new Pocketmon(tName, Integer.parseInt(tId));
				monsterList.add(tMonster);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static ResourceReader getInstance(){
		if(instance==null){
			try{
				instance = new ResourceReader();
			}catch(Exception e){
				System.out.println("resource file not found!\n"+e.getMessage() +"\n"+e.toString());
			}
		}
		return instance;
	}
	
	
}
