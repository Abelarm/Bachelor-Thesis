package Tesi;

import java.util.ArrayList;

import Automata.Automata;
import Graph.AdjacencyListOrientedGraph;
import Graph.Vertex;
import NodeList.PositionList;

public class AlgoritmoTest {
	
	public static void main(String[] args) {
		Automata grafo= new Automata();
		
		Vertex<Integer> uno=grafo.insertVertex(1);
		Vertex<Integer> due=grafo.insertVertex(2);
		Vertex<Integer> tre=grafo.insertVertex(3);
		Vertex<Integer> quattro=grafo.insertVertex(4);
		Vertex<Integer> cinque=grafo.insertVertex(5);
		Vertex<Integer> sei=grafo.insertVertex(6);
		
		grafo.insertEdge(uno, due, "b");
		grafo.insertEdge(uno, quattro, "a");
		grafo.insertEdge(due, due, "b");
		grafo.insertEdge(due, sei, "a");
		grafo.insertEdge(due, cinque, "c");
		grafo.insertEdge(tre, quattro, "b");
		grafo.insertEdge(tre, due, "a");
		grafo.insertEdge(quattro, due, "b");
		grafo.insertEdge(quattro, cinque, "c");
		grafo.insertEdge(quattro, sei, "a");
		grafo.insertEdge(cinque, tre, "d");
		grafo.insertEdge(cinque, quattro, "a");
		grafo.insertEdge(sei, uno, "d");
		grafo.insertEdge(sei, quattro, "a");
		
		
		
		ArrayList<String> alfabeto= new ArrayList<String>();
		alfabeto.add(0, "a");
		alfabeto.add(1, "b");
		alfabeto.add(2, "c");
		alfabeto.add(3, "d");
		
	
		DisegnaAutomata autom= new DisegnaAutomata(grafo);
		autom.Print();
		
		
		Algoritmo prova=null;
		try {
			prova = new Algoritmo(grafo, alfabeto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DisegnaTree t=new DisegnaTree(prova.getTreeTrie(),prova.root);
		t.Print();
		
		
		PositionList<String> SignatureList=prova.getSignatureList();
		System.out.println(SignatureList.toString());
	
		System.out.println("Num vertici: "+ prova.numVertex());
		
		System.out.println("Num archi: "+ prova.numEdge());
		
		System.out.println(prova.StampaArchi());
		System.out.println(prova.StampaVertici());
		
		System.out.println("Prova size: "+prova.size("c","5"));
		
		System.out.println("Prova arc: "+prova.arc("a","9").size());
		
		ArrayList<Vertex<Integer>> listafoglie=prova.getFoglie();
		for(Vertex<Integer> x:listafoglie){
			System.out.println("Foglia:"+x.element()+" Depth:" + prova.depth(x));
		}
		
		prova.MinimizingLocalAutomata();
		//prova.Merge("b", "4", "1");
	
		DisegnaTree s= new DisegnaTree(prova.getTreeTrie(),prova.root);
		s.Print();
		
		
		System.out.println(prova.StampaArchi());
		System.out.println(prova.StampaVertici());
		
		
		DisegnaAutomata finale= new DisegnaAutomata(prova.getMinimizedAutomata());
		finale.Print();
		
		//System.out.println("Prova size: "+prova.size("a","6"));
		
		System.out.println("FINITO");
		
	}
}
