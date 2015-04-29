package Tesi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import provaJung.DisegnoTree;
import Graph.Edge;
import Graph.Vertex;
import TreeTrie.TreeTrie;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ConstantDirectionalEdgeValueTransformer;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class DisegnaTree {

	DelegateTree<Vertex<Integer>, Edge<String>> g;
	
	public DisegnaTree(TreeTrie t,Vertex<Integer> root){
		Iterable<Vertex<Integer>> vertici=t.vertices();
		Iterable<Edge<String>> archi=t.edges();
		
		g = new DelegateTree<Vertex<Integer>, Edge<String>>();
		
		
		g.setRoot(root);
		disegnafigli(t,g.getRoot());
		
		
	}
	

	public void disegnafigli(TreeTrie t,Vertex<Integer> x){
		if(x.element()!=0)
			return;
		Iterable<Edge<String>> archis=t.OutgoingEdges(x);
		for(Edge<String> s: archis){
			System.out.println(s.element());
			g.addChild(s, x, t.nextNode(x, s));
			disegnafigli(t,t.nextNode(x, s));
		}

	}
	
	public void Print(){
		DisegnoTree sgv = new DisegnoTree(); // This builds the graph
	   	 // Layout<V, E>, BasicVisualizationServer<V,E>
	   	 Layout<Vertex<Integer>,Edge<String>> layout = new TreeLayout(g);
	   	//layout.setSize(new Dimension(300,300));
	  
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
	   	
	   	 vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
	   	 
	   	 
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
