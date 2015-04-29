package Tesi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import Automata.Automata;
import Graph.Edge;
import Graph.Vertex;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.AbstractEdgeShapeTransformer;
import edu.uci.ics.jung.visualization.decorators.ConstantDirectionalEdgeValueTransformer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class DisegnaAutomata {

	Graph<Vertex<Integer>, Edge<String>> g;
	
	public DisegnaAutomata(Automata a){
		Iterable<Vertex<Integer>> vertici=a.vertices();
		Iterable<Edge<String>> archi=a.edges();
		
		g = new DirectedSparseGraph<Vertex<Integer>, Edge<String>>();
		for(Vertex<Integer> x: vertici){
			g.addVertex(x);
		}
		for(Edge<String> s: archi){
			g.addEdge(s, a.endVertices(s)[0],a.endVertices(s)[1]);
		
		}
	}
	
	public void Print(){
		
	   	 Layout<Vertex<Integer>,Edge<String>> layout = new FRLayout(g);
	   	 layout.setSize(new Dimension(450,450));
	  
	   	 BasicVisualizationServer<Vertex<Integer>, Edge<String>> vv = new BasicVisualizationServer<Vertex<Integer>,Edge<String>>(layout);
	   	 
	   	 //vv.setPreferredSize(new Dimension(500,500));
	   	 // Setup up a new vertex to paint transformer...
	   	 Transformer<Vertex<Integer>,Paint> vertexPaint = new Transformer<Vertex<Integer>,Paint>() {
	   	 public Paint transform(Vertex<Integer> i) {
	   		 if(i.element()!=0)
	   			 return Color.RED;
	   		 return Color.GREEN;
	   	 }
	   	 }; 
	   	
	   	
	   	 vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
	   	 
	   	 
	   	AbstractEdgeShapeTransformer<Vertex<Integer>,Edge<String>> aesf = 
	            (AbstractEdgeShapeTransformer<Vertex<Integer>,Edge<String>>)vv.getRenderContext().getEdgeShapeTransformer();
	        aesf.setControlOffsetIncrement(30);
	        
	    ConstantDirectionalEdgeValueTransformer<Vertex<Integer>,Edge<String>> edgelabel=
	    		(ConstantDirectionalEdgeValueTransformer<Vertex<Integer>,Edge<String>>)vv.getRenderContext().getEdgeLabelClosenessTransformer();
	    edgelabel.setDirectedValue(0.5);
	    edgelabel.setUndirectedValue(0.5);
	   	 
	   	vv.getRenderContext().setEdgeLabelTransformer(new Transformer<Edge<String>, String>() {
	        public String transform(Edge<String> e) {
	        	//System.out.println("Valore:"+e.element());
	            return e.element();
	        }
	    });
	   	
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
	   	 
	   	 frame.setResizable(false);
	   	 frame.setEnabled(false);
	   	
	   	 
	   	 frame.pack();
	   	 frame.setLocationRelativeTo(frame.getRootPane());
	   	 frame.setVisible(true);
	   	 
	   	try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   	
	   	frame.setVisible(false); //you can't see me!
	   	frame.dispose(); //Destroy the JFrame object
	}

}
