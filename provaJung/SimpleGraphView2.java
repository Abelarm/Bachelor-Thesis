package provaJung;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author Dr. Greg M. Bernstein
 */
public class SimpleGraphView2 {
    public Graph<Integer, String> g;
    /** Creates a new instance of SimpleGraphView */
    public SimpleGraphView2() {
        // Graph<V, E> where V is the type of the vertices and E is the type of the edges
        // Note showing the use of a SparseGraph rather than a SparseMultigraph
        g = new DirectedSparseGraph<Integer, String>();
        // Add some vertices. From above we defined these to be type Integer.
        g.addVertex((Integer)1);
        g.addVertex((Integer)2);
        g.addVertex((Integer)3);
        g.addVertex((Integer)4);
        g.addVertex((Integer)5);
        g.addVertex((Integer)6);
        
        // g.addVertex((Integer)1);  // note if you add the same object again nothing changes
        // Add some edges. From above we defined these to be of type String
        // Note that the default is for undirected edges.
        g.addEdge("a", 1, 2); // Note that Java 1.5 auto-boxes primitives
        g.addEdge("b", 1, 4); 
        g.addEdge("c", 2, 2);
        g.addEdge("d", 2, 6);
        g.addEdge("e", 2, 5);
        g.addEdge("f", 3, 4);
        g.addEdge("g", 3, 2);
        g.addEdge("h", 4, 2);
        g.addEdge("i", 4, 5);
        g.addEdge("l", 4, 6);
        g.addEdge("m", 5, 3);
        g.addEdge("n", 5, 4);
        g.addEdge("o", 6, 1);
        g.addEdge("p", 6, 4);
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	 SimpleGraphView2 sgv = new SimpleGraphView2(); // This builds the graph
    	 // Layout<V, E>, BasicVisualizationServer<V,E>
    	 Layout<Integer, String> layout = new CircleLayout(sgv.g);
    	 layout.setSize(new Dimension(300,300));
    	 BasicVisualizationServer<Integer,String> vv = 
    	 new BasicVisualizationServer<Integer,String>(layout);
    	 vv.setPreferredSize(new Dimension(350,350)); 
    	 // Setup up a new vertex to paint transformer...
    	 Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
    	 public Paint transform(Integer i) {
    		 return Color.GREEN;
    	 }
    	 }; 
    	 // Set up a new stroke Transformer for the edges
    	 float dash[] = {10.0f};
    	 final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
    	 BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
    	
    	 vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
    	 //vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
    	 vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
    	 vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
    	 vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR); 
    	 
    	 JFrame frame = new JFrame("Simple Graph View 2");
    	 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	 frame.getContentPane().add(vv);
    	 frame.pack();
    	 frame.setVisible(true);
    
    }
    
}