package provaJung;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import Automata.Automata;
import Graph.Edge;
import Graph.Vertex;
import Tesi.Algoritmo;
import TreeTrie.TreeTrie;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ConstantDirectionalEdgeValueTransformer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class DisegnoTree {

	Automata grafo;
	DelegateTree<Vertex<Integer>, Edge<String>> g;
	
	public DisegnoTree(){

		
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
		grafo.insertEdge(cinque, tre, "b");
		grafo.insertEdge(cinque, quattro, "a");
		grafo.insertEdge(sei, uno, "b");
		grafo.insertEdge(sei, quattro, "a");
		
		
		
		ArrayList<String> alfabeto= new ArrayList<String>();
		alfabeto.add(0, "a");
		alfabeto.add(1, "b");
		alfabeto.add(2, "c");
		alfabeto.add(3, "d");
		
		
		
		Algoritmo prova=null;
		try {
			prova = new Algoritmo(grafo, alfabeto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//prova.MinimizingLocalAutomata();
		
		Iterable<Vertex<Integer>> vertici=prova.getTreeTrie().vertices();
		Iterable<Edge<String>> archi=prova.getTreeTrie().edges();
		
		g = new DelegateTree<Vertex<Integer>, Edge<String>>();
		
		
		g.setRoot(prova.root);
		Iterable<Edge<String>> archis=prova.getTreeTrie().OutgoingEdges(prova.root);
		for(Edge<String> s: archis){
			//System.out.println(s.element());
			g.addChild(s, prova.root, prova.getTreeTrie().nextNode(prova.root, s));
			disegnafigli(prova.getTreeTrie(),prova.getTreeTrie().nextNode(prova.root, s));
		}
		
		
	}
	
	public void disegnafigli(TreeTrie t,Vertex<Integer> x){
		//System.out.println("nel metodo disegna figli");
		if(x.element()!=0)
			return;
		Iterable<Edge<String>> archis=t.OutgoingEdges(x);
		for(Edge<String> s: archis){
			System.out.println(s.element());
			g.addChild(s, x, t.nextNode(x, s));
			disegnafigli(t,t.nextNode(x, s));
		}
		
	}
	
	public static void main(String[] args) {
	     DisegnoTree sgv = new DisegnoTree(); // This builds the graph
		 System.out.println(sgv.g.getEdgeCount());
		 System.out.println(sgv.g.getVertexCount());
	   	 // Layout<V, E>, BasicVisualizationServer<V,E>
	   	 Layout<Vertex<Integer>,Edge<String>> layout = new TreeLayout(sgv.g);
	   	// layout.setSize(new Dimension(300,300));
	  
	   	 BasicVisualizationServer<Vertex<Integer>, Edge<String>> vv = new BasicVisualizationServer<Vertex<Integer>,Edge<String>>(layout);
	   	 
	   	 vv.setPreferredSize(new Dimension(350,350));
	   	 // Setup up a new vertex to paint transformer...
	   	 Transformer<Vertex<Integer>,Paint> vertexPaint = new Transformer<Vertex<Integer>,Paint>() {
	   	 public Paint transform(Vertex<Integer> i) {
	   		if(i.element()!=0)
	   			 return Color.RED;
	   		 return Color.GREEN;
	   	 }
	   	 }; 
	   	 // Set up a new stroke Transformer for the edges
	   	 //float dash[] = {10.0f};
	   	 //final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
	   	 //BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
	   	
	   	 vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
	   	 //vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
	   	 //vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
	   	 //vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
	   	 
	   	vv.getRenderContext().setEdgeLabelTransformer(new Transformer<Edge<String>, String>() {
	        public String transform(Edge<String> e) {
	        	//System.out.println("Valore:"+e.element());
	            return e.element();
	        }
	    });
	   	
	   	ConstantDirectionalEdgeValueTransformer<Vertex<Integer>,Edge<String>> edgelabel=
	    		(ConstantDirectionalEdgeValueTransformer<Vertex<Integer>,Edge<String>>)vv.getRenderContext().getEdgeLabelClosenessTransformer();
	    edgelabel.setDirectedValue(0.5);
	    edgelabel.setUndirectedValue(0.5);
	    vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<Vertex<Integer>,Edge<String>>());
	   	
		vv.getRenderContext().setVertexLabelTransformer(new Transformer<Vertex<Integer>, String>() {
	        public String transform(Vertex<Integer> e) {
	        	//System.out.println("Valore:"+e.element());
	            return e.element().toString();
	        }
	    });
	   	 
	   	 vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR); 
	   	 
	   	 JFrame frame = new JFrame("Grafo");
	   	 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   	 frame.getContentPane().add(vv);
	   	 frame.pack();
	   	 frame.setVisible(true);
	}

}
