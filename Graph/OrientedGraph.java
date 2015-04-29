package Graph;


import Graph.Edge;
import Graph.Vertex;
import NodeList.InvalidPositionException;

public interface OrientedGraph<V, E> {
	
	/**Restituisce il numero dei vertici**/
	public int numVertices();
	
	/**Restituisce il numero degli archi**/
	public int numEdges();
	
	/**Restituisce una collezione iterabile di vertici**/
	public Iterable<Vertex<V>> vertices();
	
	/**Restituisce una collezione iterabile di archi**/
	public Iterable<Edge<E>> edges();
	
	/**Restituisce una collezione iterabile degli archi uscenti in quel nodo**/
	public Iterable<Edge<E>> OutgoingEdges(Vertex<V> v) throws InvalidPositionException;
	
	/**Restituisce una collezione iterabile degli archi entranti in quel nodo**/
	public Iterable<Edge<E>> IngoingEdges(Vertex<V> v) throws InvalidPositionException;
	
	/**Restituisce un array di vertici estremi dell'arco passato in input
	 * Per convenzione in posizione 0 c'è il vertice da cui è partito l'arco
	 * in posizione 1 c'è il vertice in cui entra l'arco**/
	public Vertex<V>[] endVertices(Edge<E> e)  throws InvalidPositionException;
	
	/**Restituisce il vertice in cui entra l'arco e che parte da v**/
	public Vertex<V> nextNode(Vertex<V> v, Edge<E> e) throws InvalidPositionException;
	
	/**Restituisce vero se estite un'arco tra i due nodi passati in input, false altrimenti**/
	public boolean areAdjacent(Vertex<V> u, Vertex<V> v) throws InvalidPositionException;
	
	/**Rimpiazza l'elemento contenuto nel vertice passato in input, con un'altro elemento**/
	public V replace(Vertex<V> p, V o) throws InvalidPositionException;
	
	/**Rimpiazza l'elemento contenuto nell'arco passato in input, con un'altro elemento**/
	public E replace(Edge<E> p, E o) throws InvalidPositionException;
	
	/**Inserisce un vertice con valore o, e lo restituisce**/
	public Vertex<V> insertVertex(V o);
	
	/**Inserisce un arco nel grafo, uscente dal vertice u ed entrante in v con valore o, e lo restituisce**/
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E o) throws InvalidPositionException;
	
	/**Rimuove il vertice passato in input, e ne restituisce il valore**/
	public V removeVertex(Vertex<V> v) throws InvalidPositionException;
	
	/**Rimuove l'arco passato in input, e ne restituisce il valore**/
	public E removeEdge(Edge<E> e) throws InvalidPositionException;
}
