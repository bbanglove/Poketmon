package designPrjAlgorithm;

import java.util.List;

public class ShortestPathResult {
	private List<Vertex> shortestPath;
	private int shortestPathWeight;
	
	public ShortestPathResult(List<Vertex> shortestPath, int shortestPahtWeight) {
		this.shortestPathWeight=shortestPahtWeight;
		this.shortestPath=shortestPath;
	}

	public List<Vertex> getShortestPath() {
		return shortestPath;
	}

	public int getShortestPathWeight() {
		return shortestPathWeight;
	}
}
