import org.junit.Before;
import org.junit.Test;

import designPrjAlgorithm.Field;
import designPrjAlgorithm.CustomizedAlgorithm;
import designPrjAlgorithm.ResourceReader;
import designPrjAlgorithm.ShortestPathResult;
import designPrjAlgorithm.Vertex;
import static junit.framework.Assert.*;

public class CustomizedAlgorithmTest {

	CustomizedAlgorithm floyd;
	@Before
	public void setUp(){
		floyd=new CustomizedAlgorithm(ResourceReader.getInstance().getVertexList(), ResourceReader.getInstance().getEdgeList());
	}
	
	@Test
	public void test() {
		ShortestPathResult result = floyd.getShortestPathResult(new Field(1), new Field(1));
		System.out.println("weight : "+result.getShortestPathWeight());
		for(Vertex v : result.getShortestPath()){
			System.out.print("to " + v.getVertexNum()+", ");
		}
		
		assertEquals("13 to 2", 25, floyd.getShortestPathResult(new Field(13), new Field(2)).getShortestPathWeight());
		assertEquals("38 to 2", 30, floyd.getShortestPathResult(new Field(38), new Field(2)).getShortestPathWeight());
		assertEquals("38 to 64", 45, floyd.getShortestPathResult(new Field(38), new Field(64)).getShortestPathWeight());
		assertEquals("1 to 1", 28, floyd.getShortestPathResult(new Field(1), new Field(1)).getShortestPathWeight());
	}

	/**
	 * Test result
	 * Vertex 13 to 2 의 최소 경로값 : 25
	 * Vertex 38 to 2 의 최소 경로값 : 30 (38->13->2)
	 * Vertex 38 to 64 의 최소 경로값 : 45 (38->13->2->64)
	 * Vertex 1 to 1 의 최소 경로값 : 28 (1->70->46->1)
	 * 
	 */
}
