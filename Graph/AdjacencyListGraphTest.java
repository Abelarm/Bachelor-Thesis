package Graph;

import java.util.ArrayList;

public class AdjacencyListGraphTest{
	
	public static void main(String[] args) {
		AdjacencyListGraph<Integer,String> grafo= new AdjacencyListGraph<Integer,String>();
		
		Vertex<Integer> uno=grafo.insertVertex(1);
		Vertex<Integer> due=grafo.insertVertex(2);
		Vertex<Integer> tre=grafo.insertVertex(3);
		Vertex<Integer> quattro=grafo.insertVertex(4);
		
		grafo.insertEdge(uno, tre, "d");
		grafo.insertEdge(uno, quattro, "a");
		grafo.insertEdge(due, tre, "b");
		grafo.insertEdge(due, quattro, "a");
		grafo.insertEdge(quattro, tre, "c");
		
		ArrayList<String> alfabeto= new ArrayList<String>();
		alfabeto.add(0, "a");
		alfabeto.add(1, "b");
		alfabeto.add(2, "c");
		alfabeto.add(3, "d");
		
		
		
	
	}
	
}
