package TreeTrie;

import java.util.ArrayList;
import java.util.Iterator;

import Automata.Automata;
import Graph.Edge;
import Graph.Vertex;

public class TreeTrieTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) {
		
		TreeTrie grafo= new TreeTrie();
		
		Vertex<Integer> uno=grafo.insertVertex(1);
		Vertex<Integer> due=grafo.insertVertex(2);
		Vertex<Integer> tre=grafo.insertVertex(3);
		Vertex<Integer> quattro=grafo.insertVertex(4);
		
		grafo.insertEdge(uno, tre, "d2");
		grafo.insertEdge(uno, quattro, "a3");
		grafo.insertEdge(due, tre, "b6");
		grafo.insertEdge(due, quattro, "a1");
		grafo.insertEdge(quattro, tre, "c4");
		
	
		
		Iterable<Edge<String>> lista= grafo.edges();
		for(Edge<String> x: lista){
			System.out.println(x.element());
		}
		
		System.out.println(grafo.haveIngoingEdges(tre, "b6"));
		System.out.println(grafo.haveOutgoingEdges(uno, "s1"));
		try {
			grafo.getOutgoingEdges(uno, "s1");
		} catch (Exception e) {
			System.out.println("L'elemento non è presente");
		}
		
		Iterable<Edge<String>> s=grafo.OutgoingEdges(uno);
		for(Edge<String> x: s){
			System.out.println(x.element());
		}
		
		System.out.println(grafo.size(uno));
	}

}
