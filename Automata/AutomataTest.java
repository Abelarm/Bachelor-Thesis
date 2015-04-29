package Automata;

import Graph.Edge;
import Graph.Vertex;


public class AutomataTest {

	public static void main(String[] args) {
		
		Automata grafo= new Automata();
		
		Vertex<Integer> uno=grafo.insertVertex(1);
		Vertex<Integer> due=grafo.insertVertex(2);
		Vertex<Integer> tre=grafo.insertVertex(3);
		Vertex<Integer> quattro=grafo.insertVertex(4);
		
		grafo.insertEdge(uno, tre, "d");
		grafo.insertEdge(uno, quattro, "a");
		grafo.insertEdge(due, tre, "b");
		grafo.insertEdge(due, quattro, "a");
		grafo.insertEdge(quattro, tre, "c");
		
	
		
		Iterable<Edge<String>> lista= grafo.edges();
		for(Edge<String> x: lista){
			System.out.println(x.element());
		}
		
		//System.out.println(grafo.haveIngoingEdge(tre, "b"));
		System.out.println(grafo.haveOutgoingEdge(uno, "s"));
	}
	
}
