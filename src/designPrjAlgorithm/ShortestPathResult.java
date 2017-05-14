package designPrjAlgorithm;

import java.util.List;

public class ShortestPathResult {
	private List<Vertex> shortestPath;
	private int shortestPahtWeight;
	
	public ShortestPathResult(List<Vertex> shortestPath, int shortestPahtWeight) {
		this.shortestPahtWeight=shortestPahtWeight;
		this.shortestPath=shortestPath;
	}

	public List<Vertex> getShortestPath() {
		return shortestPath;
	}

	public int getShortestPahtWeight() {
		return shortestPahtWeight;
	}
}
