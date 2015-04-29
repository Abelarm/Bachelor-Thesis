package Graph;

import java.util.Iterator;

import position.Position;
import NodeList.InvalidPositionException;
import NodeList.NodePositionList;
import NodeList.PositionList;

public class AdjacencyListGraph<V, E> implements Graph<V, E> {
	protected PositionList<Vertex<V>> VList;// contenitore per i vertici
	protected PositionList<Edge<E>> EList; // contenitore per gli archi
	
	
	//CLASSE MYVERTEX INTERNA
	/**Il vertice estende MyPosition(position con attaccata una Mappa) e implementa vertex
	 * questo oggetto conterrà:
	 * un elemento di tipo V
	 * un riferimento della sua position all'interno della lista dei Vertici
	 * un riferimento ad un contenitore incEdges contente gli archi incidenti su quel vertice**/
	public class MyVertex<V> extends MyPosition<V> implements Vertex<V> {
		protected PositionList<Edge<E>> incEdges; 
		protected Position<Vertex<V>> loc;

		public MyVertex(V o){
			element = o;
			incEdges = new NodePositionList<Edge<E>>();
		}
		
		/**Restituisce il grado del vertice(il numero di archi incidenti nel vertice)**/
		public int degree(){
			return incEdges.size();			//dimensione della lista degli archi incidenti nel vertice su cui è richiamato 
		}
		
		/**Restituisce la collezzione iterabile degli archi incidenti sul nodo su cui è richiamato**/
		public Iterable<Edge<E>> incidentEdges() {
			return incEdges;
		}
		
		/**Inserisce un nuovo arco incidente al nodo su cui è richiamato**/
		public Position<Edge<E>> insertIncidence(Edge<E> e) {
			incEdges.addLast(e);				//aggiunge questo arco alla fine della nostra lista
			return incEdges.last();				//restituisce l'ultimo che corrisponde proprio alla position di edge Position<Edge<E>>
		}
		
		/****/
		public void removeIncidence(Position<Edge<E>> p) { 
			incEdges.remove(p);
		}
		
		/** Restituisce la position di questo vertice nel contenitore dei vertici del grafo. */
		public Position<Vertex<V>> location() { 
			return loc;  
		}
		
		/** Imposta la position di questo vertice nel contenitore dei vertici del grafo. */
		public void setLocation(Position<Vertex<V>> p) { 
			loc = p; 
		}
	}

	
	
	//CLASSE MYEDGE INTERNA
	
	/**L'arco estende MyPosition(position con attaccata una Mappa) e implementa edge
	 * questo oggetto conterrà:
	 * un elemento di tipo E
	 * un riferimento della sua position all'interno della lista degli archi
	 * due riferimenti a vertex (Vertici su cui è incidente)
	 * due riferimenti all'arco all'interno della lista di adiacente degli archi su cui è incidente**/
	public class MyEdge<E> extends MyPosition<E> implements Edge<E> {
		protected MyVertex<V>[] endVertices; 			//array che contiente i vertici estremi dell'arco
		protected Position<Edge<E>>[] Inc; 					//array che contiene le position dell'arco che si trovano all'interno delle liste di incidenza(dei verici etremi)
		protected Position<Edge<E>> loc; 					//position dell'arco nella lista degli archi(puntatore alla position di se stesso)
		
		/**Costruttuore **/
		MyEdge (Vertex<V> v, Vertex<V> w, E o) {
			element = o;											//all'elemento della position assegnamo quello passato in input
			endVertices = (MyVertex<V>[]) new MyVertex[2];		//istanza l'array degli archi su cui è incidente
			endVertices[0] = (MyVertex<V>)v;					//come primo elemento dell'arco mette il primo vertice passato in input
			endVertices[1] = (MyVertex<V>)w;					//come secondo elemento dell'arco mette il secondo vertice passato in input
			Inc = (Position<Edge<E>>[]) new Position[2];			//istanzia l'array delle position dell'arco all'interno delle due liste di incidenza 
		}
		
		/**Restiuisce l'array dei nodi estremi dell'arco su cui è invocato**/
		public MyVertex<V>[] endVertices() {
			return endVertices;
		}
		
		/**Restituisce l'array che contiene le position dell'arco che si trovano all'interno delle lista di incidenza(dei verici etremi)**/
		public Position<Edge<E>>[] incidences() {
			return Inc;
		}
		
		/**Setta le position dell'arco che si trovano all'interno delle lista di incidenza(dei verici etremi) come elementi dell'array **/
		public void setIncidences(Position<Edge<E>> pv, Position<Edge<E>> pw) {
			Inc[0] = pv;
			Inc[1] = pw;
		}
		
		/**Restituisce la position dell'arco all'iterno della lista degli archi(Position che punta a se stessa)**/
		public Position<Edge<E>> location() { 
			return loc; 
			}
		
		/**Setta la position dell'arco all'interno della lista**/
		public void setLocation(Position<Edge<E>> p) { 
			loc = p; 
			}
			
	}

	

	
	
	
	
	
	/** Costruttore di default che crea un grafo vuoto */
	public AdjacencyListGraph() {
	VList = new NodePositionList<Vertex<V>>();
	EList = new NodePositionList<Edge<E>>();
	}
	
	/**Restituisce il numero dei vertici**/
	public int numVertices() {
		return VList.size();
	}
	
	/**Restituisce il numero degli archi**/
	public int numEdges() {
		return EList.size();
	}

	/**Restituisce una collezione iterabile dei vertici**/
	public Iterable<Vertex<V>> vertices() {
		return VList;
	}

	/**Restituisce una collezione iterabile degli archi**/
	public Iterable<Edge<E>> edges() {
		return EList;
	}

	/**Restituisce la collezione iterabile degli archi incidenti del vertice passato in input**/
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidPositionException {
		MyVertex<V> temp=this.checkVertex(v);
		return temp.incidentEdges();
	}

	private MyVertex<V> checkVertex(Vertex<V> v) throws InvalidPositionException {
	try{
		if(v.equals(null))
				throw new InvalidPositionException();
		
		MyVertex<V>temp =(MyVertex<V>)v;
		return temp;
	}
	catch(Exception e){
		throw new InvalidPositionException();
	}
	}

	/**Restituisce l'array di vertici esterni all'arco passato in input**/
	public Vertex[] endVertices(Edge<E> e) throws InvalidPositionException {
		MyEdge<E> temp=checkEdge(e);			//Controlla la validità dell'arco
		return temp.endVertices();				//e su quell'arco invoca endVertices
	}

	private MyEdge<E> checkEdge(Edge<E> e) {
		try{
			if(e.equals(null))
					throw new InvalidPositionException();
			
			MyEdge<E>temp =(MyEdge<E>)e;
			return temp;
		}
		catch(Exception x){
			throw new InvalidPositionException();
		}
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidPositionException {
		MyVertex<V> ver=this.checkVertex(v);
		MyEdge<E> edg=this.checkEdge(e);
		
		MyVertex<V>[] endVertices=edg.endVertices();
		
		if(endVertices[0].equals(ver))
			return endVertices[1];
		else
			return endVertices[0];
	}

	
	public boolean areAdjacent(Vertex<V> u, Vertex<V> v) throws InvalidPositionException {
		MyVertex<V> ver=this.checkVertex(v);
		MyVertex<V> ver1=this.checkVertex(u);
		Iterable<Edge<E>> tosearch=ver1.incidentEdges();
		for(Edge<E> x: tosearch){
			if(this.opposite(ver1, x).equals(ver))
				return true;
		}
		return false;
	}

	@Override
	public V replace(Vertex<V> p, V o) throws InvalidPositionException {
		MyVertex<V> ver=this.checkVertex(p);
		V toreturn=ver.element();
		ver.setElement(o);
		return toreturn;
	}

	/**Sostituisce l'elemento contenuto nell'arco passato in input con un nuovo elemento passato in input, restituendo quello vecchio**/
	public E replace(Edge<E> p, E o) throws InvalidPositionException {
		MyEdge<E> edg=this.checkEdge(p);										//controlla e casta a MyEdge l'arco passato in input
		E toreturn= edg.element();												//in toreturn salva il vecchio valore dell'arco
		edg.setElement(o);														//modifica l'elemento dell'arco con quello passato in input
		return toreturn;														//restituisce il vecchio elemento
		
	}

	/**inserisce nel grafo un nuovo vertice che ha come elemento l'elemento passato in input**/
	public Vertex<V> insertVertex(V o) {
		MyVertex<V> vv= new MyVertex<V>(o);										//crea il nuovo vertice con l'elemento passato in input
		VList.addLast(vv);														//aggiunge il vertice in coda alla lista dei vertici del grafo
		vv.setLocation(VList.last());											//conserva all'interno del vertice la propria position all'interno della lista dei nodi
		return vv;																//restituisce il vertice inserito nel grafo
	}

	/**inserisce nel grafo un nuovo arco che ha come estremi i vertici passati in input, e come elemento l'elemento passato in input**/
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E o) throws InvalidPositionException {
		MyEdge<E> ee=new MyEdge<E>(u, v, o);									//crea il nuovo arco con i vertici e l'elemento passato in input
		EList.addLast(ee);														//aggiunge l'arco in coda alla lista degli archi del grafo
		ee.setLocation(EList.last());											//aggiorna la position interna all'arco in maniera che punti alla propria posizione nella lista degli archi
		MyVertex<V> uc=this.checkVertex(u);										//controlla e casta a MyVertex il primo vertice passato in input
		MyVertex<V> vc=this.checkVertex(v);										//controlla e casta a MyVertex il secondo vertice passato in input
		ee.setIncidences(uc.insertIncidence(ee), vc.insertIncidence(ee));		//Inserisce l'arco nella lista di incidenza di ogni estremo, e salva le position in queste liste all'interno dell'arco stesso
		return ee;																//restituisce l'arco inserito nel grafo
	}

	/**Rimuove il vertice passato in input dal grafo**/
	public V removeVertex(Vertex<V> v) throws InvalidPositionException {
		MyVertex<V> temp=this.checkVertex(v);				//controlla e casta a MyVertex l'arco passato in input
		Position<Vertex<V>> pos=temp.location();			//pos mantiene la position del vertice nella lista dei vertici
		VList.remove(pos);									//rimuove il vertice dalla lista dei vertici
		temp.setLocation(null);								//cancella la location all'interno del vertice
		Iterable<Edge<E>> list=temp.incidentEdges();		//lista iterabile degli archi incidenti sul vertice da eliminare
		for(Edge<E> x: list)								//per ogni arco appartenente a list (e quindi incidente sul vertice da eliminare)
			this.removeEdge(x);								//rimuovi l'arco dal grafo
		return temp.element();								//alla fine restituisci l'elemento del vertice rimosso
	}

	/**Rimuove l'arco passato in input dal grafo**/
	public E removeEdge(Edge<E> e) throws InvalidPositionException {
		MyEdge<E> temp=this.checkEdge(e);					//controlla e casta a MyEdge l'arco passato in input
		Position<Edge<E>> pos= temp.location();				//pos mantiene la position dell'arco nella lista degli arcHi 
		EList.remove(pos);									//rimuove l'arco dalla lista degli archi
		temp.setLocation(null);								//cancella la location all'interno dell'arco
		MyVertex<V>[] array=temp.endVertices();				//array contiene i due estremi dell'arco
		array[0].removeIncidence(temp.incidences()[0]);		//Leggiamo la position dell'arco nella lista di incidenza del primo nodo, e la eliminiamo da essa
		array[1].removeIncidence(temp.incidences()[1]);		//Leggiamo la position dell'arco nella lista di incidenza del secondo nodo, e la eliminiamo da essa
		temp.setIncidences(null, null);						//cancelliamo nell'arco le position dell'arco nelle due liste di incidenza
		return temp.element();								//restituisce l'elemento dell'arco rimosso
	}
	
	
	
	public boolean Cammino(Vertex<V> v, int k){
		boolean toreturn=true;
		
		if(k==0)
			return true;
		
		MyVertex<V> temp=this.checkVertex(v);
		
		if(temp.get("Visitato").equals("Si"))
			return false;
		
		Iterable<Edge<E>> listaedge=this.incidentEdges(temp);
		for(Edge<E> e: listaedge){
			MyVertex<V> ver=this.checkVertex(this.opposite(temp, e));
			ver.put("Visitato", "Si");
			toreturn =this.Cammino(ver, k-1);
		}
		return toreturn;
	}


	  /**prende in input un vertice v e restituisce una collezione iterabile dei vertici del grafo
	    * che sono adiacenti a v. */
	    public Iterable<Vertex<V>> Adiacenti(Vertex<V> v) throws InvalidPositionException {

	    MyVertex<V> vertex = this.checkVertex(v); // controllo se il vertice v è valido
	    NodePositionList<Vertex<V>> adjacent = new NodePositionList<Vertex<V>>(); // lista degli adjacenti a v
	    Vertex<V> opp = null;
	    for (Edge<E> e : vertex.incidentEdges()) { // scorro tutti gli archi incidenti a v

	    MyEdge<E> edge = this.checkEdge(e); // controllo che l'arco sia valido
	    opp = this.opposite(vertex,edge); // prendo l'oposto
	    MyVertex<V> temp = this.checkVertex(opp); // controllo che sia valido
	    adjacent.addLast(temp); // lo aggiungo alla lista adiacenti
	    }
	    return adjacent;
	    }

	    
	    
	    public static<V,E>  E[] BFS_output(Graph<V, E> g, Vertex<V> s) {
	    	int numVertici = g.numVertices();													//in un intero inserisco il numero dei vertici
	    	E[] Strati = (E[]) new Object[numVertici+2]; 										//Creo un Array di E  dove andrò a inserire i layer per s
	    	int i=0; 																			//indica il layer
	    	NodePositionList<Vertex<V>> l = new NodePositionList<Vertex<V>>();					//lista d'appoggio dove inserirò i vertici che visito volta per volta
	    	l.addLast(s);																		//aggiungo alla lista il nodo passato in input
	    	s.put("esplorato", "si");															//setto come esplorato si
	    	Strati[0]= (E) l;																	// nella posizione 0 dell'array di strati inserisco l castato a E
	    	
	    	while (!((NodePositionList<Vertex>)Strati[i]).isEmpty()) {							//finche lo strato non è vuoto
	    	   l = new NodePositionList<Vertex<V>>();												//ad l assegnamo un nuova NodePositionList
	    	   NodePositionList<Vertex<V>> corrente =(NodePositionList<Vertex<V>>)Strati[i];		//in un NodePositionList di vertici inseriamo strati[i] castato a NodePositionList di vertici
	    	   Iterator<Vertex<V>> Itappoggio = corrente.iterator();								//creiamo un iterato della lsita sopra creata
	    	   
	    	   /**per ogni nodo u in L_i**/
	    	   while (Itappoggio.hasNext()) {														//finche l'iteratore ha un successivo
	    	        Vertex<V> u = Itappoggio.next(); 													//per ogni nodo u in L_i
	    	        Iterable<Vertex<V>> listaadiacenti= ((AdjacencyListGraph<V, E>) g).Adiacenti(u);    //casto il grafo passato in input a (AdjacencyListGraph<V, E>) per potergli invocare Adianceti che restituise
	    	        																					//i vertici adiacenti al vertice su cui è invocato
	    	        
	    	        for(Vertex<V> v: listaadiacenti){													//Per ognuno dei vertici dentro la lista									
	    	                    if (!(v.get("esplorato")=="si")) {											//se il campo esplorato non è settato a si
	    	                                  v.put("esplorato", "si");												//lo setto a si
	    	                                  	l.addLast(v); 														//e lo aggiungo alla lista dei vertici
	    	                                  	}
	    	        }
	    	   } 																					 //ho esaminato tutti i vertici dello strato i.
	    	
	    	Strati[i+1]= (E) l;																		//allo strato succesivo inserisco l (lista dei vertici che ho riempito attraverso la visita)
	    	i=i+1;																					//incremento il layer
	    	}
	    	return Strati;																			//ritorno l'array così creato
	    	}														

	    
	   public static<V,E> NodePositionList<Vertex<V>> DFS(Graph<V, E> g, Vertex<V> s){
		   NodePositionList<Vertex<V>> list= new NodePositionList<Vertex<V>>();					//creo una lista di vertici( che restituirò)
		  
		   s.put("Esplorato", "si");															//setto il vertice passato in input come esplorato
		   list.addLast(s);																		//aggiungo alla lista l'elemento passato in input
		   
		   Iterable<Vertex<V>> listaadiacenti= ((AdjacencyListGraph<V, E>) g).Adiacenti(s);    //casto il grafo passato in input a (AdjacencyListGraph<V, E>) per potergli invocare Adianceti che restituisce 
		   																						//i vertici adiacenti al vertice su cui è invocato
		  
		   for(Vertex<V> x: listaadiacenti){													//per ogni vertice appartenten a listaadianceti								
			   if(x.get("Esplorato")!="si")															//se il return di get con chiave esplorato è diverso da si (cioè non è stato ancora esplorato) 
				  list= DFS(g,x);																	//alla lista assegno ciò che ritornerà la chiamata ricorsiva dfs sul vertice corrente
		   }
		   return list;																			//restituisco la lista creata
	   }
}
