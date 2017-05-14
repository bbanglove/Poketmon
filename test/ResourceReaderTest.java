import org.junit.Test;
import static junit.framework.Assert.*;
import designPrjAlgorithm.Edge;
import designPrjAlgorithm.PocketStop;
import designPrjAlgorithm.Pocketmon;
import designPrjAlgorithm.ResourceReader;
import designPrjAlgorithm.Vertex;

public class ResourceReaderTest {

	@Test
	public void test() {
		System.out.println(System.getProperty("user.dir"));
		int a;
		int b=2;
		
		System.out.println( (a=b));
	}

	@Test
	public void testResourceRead(){
		for(Pocketmon pocketmon : ResourceReader.getInstance().getPocketmonList()){
			System.out.println(pocketmon.getName() +" : "+pocketmon.getId());
		}
		int pocketStopCount=0;
		for(Vertex v : ResourceReader.getInstance().getVertexList()){
			if(v instanceof PocketStop){
				pocketStopCount++;
				System.out.println(v.getVertexNum());
			}else{
				System.out.println(v.getVertexNum() + " : "+v.getMonsterId());
			}
		}
		for(Edge[] e1 : ResourceReader.getInstance().getEdgeList()){
			for(Edge e : e1){
				System.out.print(e.getEdgeNum() + "("+e.getWeight()+") : "+e.getPreVertex().getVertexNum() +"-"+e.getPostVertex().getVertexNum()+"\t");
			
			}
			System.out.println();
		}
	}
	
	@Test
	public void testVertexListIndexAndNum(){
		 for(int i=0; i<ResourceReader.getInstance().getVertexList().size();i++){
			 assertEquals("index["+i+"] : ", true, i==ResourceReader.getInstance().getVertexList().get(i).getVertexNum()-1);
		 }
	}
	@Test
	public void testMatrixIsSymetry(){
		Edge[][] edgeMatrix=ResourceReader.getInstance().getEdgeList();
		for(int i=0; i<edgeMatrix.length;i++){
			for(int j=0; j<edgeMatrix[i].length;j++){
				assertEquals("Is Symmetry ("+i+","+j+")", true, edgeMatrix[i][j].getWeight()==edgeMatrix[j][i].getWeight());
			}
		}
	}
}
