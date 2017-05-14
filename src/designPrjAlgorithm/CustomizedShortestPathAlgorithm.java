package designPrjAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 두 Vertex사이의 최소비용 경로를 찾는 알고리즘이다.
 * end Vertex를 기준으로 너비우선 방식으로 트리를 만들면서 최소 경로를 찾는다. 
 * 기존의 너비우선탐색과 다른점은, 트리를 다 만들고 경로를 찾는 것이 아니라,
 * 트리를 만들면서 경로를 찾고 최소경로를 찾고, 더 이상 최소비용이 발견될 가능성이 없으면 트리 만드는 작업 자체를 중단하고 결과를 가져온다.
 * 따라서 알고리즘 자체의 이론적인  average cost 비용보다도 실제 비용이 더 작게 나오는 경우가 많다. 
 * 
 * 객체를초기화 한 후,  getShortestPathResult(start, end) 메소드만을 통해서만 결과를 가져간다.
 * 결과 값의 shortestPathWeight 의 값이 0이라면 두 정점간의 경로가 없는 경우이다.
 * @author Jung-eun Kim
 *
 */
public class CustomizedShortestPathAlgorithm {

	private Edge[][] edgeMatrix;
	private List<Vertex> vertexList;
	
	private int curShortestPath;
	private VertexTreeNode curShortestTreeLeaf;
	
	
	public CustomizedShortestPathAlgorithm(List<Vertex> vertexList, Edge[][] edgeMatrix){
		this.vertexList=vertexList;
		this.edgeMatrix=edgeMatrix;
		this.curShortestPath = Integer.MAX_VALUE;
	}
	/**
	 * 오직 이 메소드를 통해서만알고리즘 계산 결과를 가져간다.
	 * @param start Vertex
	 * @param end Vertex
	 * @return ShortestPathResult 객체. 경로자체가 없다면 shortestPaht List의 size가 0, shortestPathWeight가 0 으로 리턴된다.
	 */
	public ShortestPathResult getShortestPathResult(Vertex start, Vertex end) {
		List<Vertex> resultPath = new ArrayList<>();
		try {
			curShortestTreeLeaf = new VertexTreeNode(null, start);
			// end로부터 인접 노드 기준 트리를 만들어 start 를 만나는 최소비용 경로를 찾는다.
			// 원리는 플로이드 방식이다.
			findShortestPath(start, new VertexTreeNode(null, end));
		} catch (TreeOverFlowException e) {
			return new ShortestPathResult(new ArrayList<>(), 0);
		}
		VertexTreeNode node = curShortestTreeLeaf;
		resultPath.add(node.getVertex());
		while (node.getParent() != null) {
			node = node.getParent();
			resultPath.add(node.getVertex());
		}

		return new ShortestPathResult(resultPath, curShortestPath);
	}
	
	private void findShortestPath(Vertex start, VertexTreeNode curNode) throws TreeOverFlowException{
		
		Vertex curVertex=curNode.getVertex();
		//너비 우선 방식으로 트리를 만든다.
		//만들면서 찾으므로 
		for(int i=0; i<edgeMatrix.length;i++){
			int curWeight=edgeMatrix[i][curVertex.getVertexNum()-1].getWeight() ;
			if(curWeight!= -1 && curWeight !=0){
				//즉 경로가 있으면,
				//단 방향성 없는 edge이므로, Parent가 i와 같다면 무시
				if(curNode.getParent()!=null){
					if(i == curNode.getParent().getVertex().getVertexNum()-1)
						continue;
				}
				//단, vertexList에 들어간 순서와, 실제 vertexNum-1 이 일치한다는 전제아래. 테스트결과 일치
				System.out.println("("+i+","+(curVertex.getVertexNum()-1)+") : "+curWeight);
				curNode.addChild(vertexList.get(i));
				System.out.println("add succcess");
				
			}
		}
		System.out.println("end for : "+(curVertex.getVertexNum()-1));
		
		//위에 자식을 추가한 작업을 반영하여, shortest path를 찾는다.
		for(VertexTreeNode node : curNode.getChildren()){
			if(node.getTotalPath() > curShortestPath){
				// parent부터 해당 leaf까지 온 경로가 이미 앞서 계산된 shortestPath 보다 크면, 더 이상 계산할 필요가 없다.
				// Floyd 의 원리 + 최적
				continue;
			}
			if(start.getVertexNum()==node.getVertex().getVertexNum()){
				// starNode를 찾은 경우 
				if( node.getTotalPath() < curShortestPath){
					//이미 앞서 계산된 shortestPath 보다 작으면 업데이트 
					curShortestPath = node.getTotalPath();
					curShortestTreeLeaf = node;
				}
				//더이상 인접 노드를 찾을 필요없다.
				continue;
			}
			//위의 두가지 경우가 아닌 경우는 계속해서 자식들로 찾아 나간다.
			findShortestPath(start, node);
		}
	}
	
	private class TreeOverFlowException extends Exception{
		
	}
	private class VertexTreeNode{
		private VertexTreeNode parent; //null 이면 root
		private List<VertexTreeNode> children;
		private Vertex vertex;
		private int totalPath; //현재 자신으로부터 최종 parent가 도달할 때까지 path값을 누적한 값.
		
		public int getTotalPath(){
			return totalPath;
		}
		public Vertex getVertex() {
			return vertex;
		}
		
		public VertexTreeNode getParent() {
			return parent;
		}
		
		public List<VertexTreeNode> getChildren() {
			return children;
		}
		public void addChild(Vertex child) throws TreeOverFlowException {
			this.children.add(new VertexTreeNode(this, child));
		}
		
		public VertexTreeNode(VertexTreeNode parent, Vertex vertex) throws TreeOverFlowException {
			super();
			this.vertex = vertex;
			this.parent = parent;
			children=new ArrayList<>();
			if(checkOverFlow(this)>100){
				throw new TreeOverFlowException();
			}
			totalPath=calculateTotalPath(this);
		}
		 
		private int checkOverFlow(VertexTreeNode leaf){
			if(leaf.getParent()==null)
				return 1;
			return 1+checkOverFlow(leaf.getParent());
		}
		
		private int calculateTotalPath(VertexTreeNode leaf){
			int total=0;
			if(leaf.getParent() == null){
				return 0;
			}
			int leafVertexId=leaf.getVertex().getVertexNum();
			int parentVertexId=leaf.getParent().getVertex().getVertexNum();
			
			total += calculateTotalPath(leaf.getParent())+edgeMatrix[leafVertexId-1][parentVertexId-1].getWeight();
			
			return total;
		}
		
	}
}
