package TreeTrie;

import position.Position;
import AlphabetArray.AlphabetMap;
import Graph.Edge;
import Graph.MyPosition;
import Graph.OrientedGraph;
import Graph.Vertex;
import NodeList.InvalidPositionException;
import NodeList.NodePositionList;
import NodeList.PositionList;

public class TreeTrie implements OrientedGraph<Integer,String> {
	private PositionList<Vertex<Integer>> VList;// contenitore per i vertici
	private PositionList<Edge<String>> EList; // contenitore per gli archi
	
	
	//CLASSE MYVERTEX INTERNA
	/**Il vertice estende MyPosition(position con attaccata una Mappa) e implementa vertex
	 * questo oggetto conterrà:
	 * un elemento di tipo Integer
	 * un riferimento della sua position all'interno della lista dei Integerertici
	 * un riferimento ad un contenitore incEdges contente gli archi incidenti su quel vertice**/
	
	@SuppressWarnings("hiding")
	public class MyVertex<Integer> extends MyPosition<Integer> implements Vertex<Integer> {
		protected AlphabetMap OutgoingEdges;
		protected AlphabetMap IngoingEdges;
		protected Position<Vertex<Integer>> loc;

		public MyVertex(Integer o){
			element = o;
			OutgoingEdges = new AlphabetMap();
			IngoingEdges = new AlphabetMap();
		}
		
		/**Restituisce il grado del vertice(il numero di archi entranti meno quelli uscenti nel vertice)**/
		public int degree(){
			return IngoingEdges.size()-OutgoingEdges.size();			
		}
		
		/**Restituisce la collezzione iterabile degli archi incidenti sul nodo su cui è richiamato**/
		public Iterable<Edge<String>> OutgoingEdges() {
			return OutgoingEdges;
		}
		
		/**Restituisce la collezzione iterabile degli archi incidenti sul nodo su cui è richiamato**/
		public Iterable<Edge<String>> IngoingEdges() {
			return IngoingEdges;
		}
		
		/**Inserisce un nuovo arco entrante al nodo su cui è richiamato**/
		public Edge<String> insertIngoingEdges(Edge<String> e) {
			IngoingEdges.put(e);				//aggiunge questo arco alla fine della nostra lista
			return e;							//restituisce l'ultimo che corrisponde proprio alla position di edge Position<Edge<String>>
		}
		
		/**Inserisce un nuovo arco uscente al nodo su cui è richiamato**/
		public Edge<String> insertOutgoingEdges(Edge<String> e) {
			OutgoingEdges.put(e);				//aggiunge questo arco alla fine della nostra lista
			return e;							//restituisce l'ultimo che corrisponde proprio alla position di edge Position<Edge<String>>
		}
		
		/**Rimuove un arco dalle lista di incidenze **/
		public void removeIncidence(Edge<String> p) { 
				Edge<String> flag=OutgoingEdges.remove(p);						//rimuove dagli archi uscenti
				if(flag==null){													//se ciò che viene restiutio è null (allora non è stato rimosso)
					
					IngoingEdges.remove(p);											//lo cancella da quelli entranti
					System.out.println("rimosso dagli archi entranti di"+element);
					
				}
				else{
					System.out.println("rimosso dagli archi uscenti di"+element);
					System.out.println(flag.element());
				}
																		
				
		}
		
		/** Restituisce la position di questo vertice nel contenitore dei vertici del grafo. */
		public Position<Vertex<Integer>> location() { 
			return loc;  
		}
		
		/** Imposta la position di questo vertice nel contenitore dei vertici del grafo. */
		public void setLocation(Position<Vertex<Integer>> p) { 
			loc = p; 
		}
	}

	
	
	//CLASSE MYEDGE INTERNA
	
	/**L'arco estende MyPosition(position con attaccata una Mappa) e implementa edge
	 * questo oggetto conterrà:
	 * un elemento di tipo String
	 * un riferimento della sua position all'interno della lista degli archi
	 * due riferimenti a vertex (0 è quella da cui esce, 1 e quello in cui entra)
	 * due riferimenti all'arco all'interno della lista di adiacente degli archi su cui è incidente**/
	public class MyEdge<String> extends MyPosition<String> implements Edge<String> {
		protected MyVertex<Integer>[] endVertices; 				//array che contiente i vertici estremi dell'arco
		protected Edge<String>[] Inc; 								//array che contiene le position dell'arco che si trovano all'interno delle liste di incidenza(dei verici etremi)
		protected Position<Edge<String>> loc; 					//position dell'arco nella lista degli archi(puntatore alla position di se stesso)
		
		/**Costruttuore **/
		MyEdge (Vertex<Integer> v, Vertex<Integer> w, String o) {
			element = o;											//all'elemento della position assegnamo quello passato in input
			endVertices = (MyVertex<Integer>[]) new MyVertex[2];			//istanza l'array degli archi su cui è incidente
			endVertices[0] = (MyVertex<Integer>)v;						//come primo elemento dell'arco mette il primo vertice passato in input, quello da cui esce
			endVertices[1] = (MyVertex<Integer>)w;						//come secondo elemento dell'arco mette il secondo vertice passato in input
			Inc = (Edge<String>[]) new Edge[2];			//istanzia l'array delle position dell'arco all'interno delle due liste di incidenza 
		}
		
		/**Restiuisce l'array dei nodi estremi dell'arco su cui è invocato**/
		public MyVertex<Integer>[] endIntegerertices() {
			return endVertices;
		}
		
		/**Restituisce l'array che contiene le position dell'arco che si trovano all'interno delle lista di incidenza(dei verici etremi)**/
		public Edge<String>[] incidences() {
			return Inc;
		}
		
		/**Setta le position dell'arco che si trovano all'interno delle lista di incidenza(dei verici etremi) come elementi dell'array **/
		public void setIncidences(Edge<String> dacuiesce, Edge<String> dacuientra) {
			Inc[0] = dacuiesce;
			Inc[1] = dacuientra;
		}
		
		/**Restituisce la position dell'arco all'iterno della lista degli archi(Position che punta a se stessa)**/
		public Position<Edge<String>> location() { 
			return loc; 
			}
		
		/**Setta la position dell'arco all'interno della lista**/
		public void setLocation(Position<Edge<String>> p) { 
			loc = p; 
		}
	
		@SuppressWarnings("unchecked")
		/**Ho dovuto riscrivere il metodo setElement(ovveride) perchè quando si modifica il valore dell'elemento bisogna prima rimuoverlo e poi
		reinserirlo cosi che possa andare nella giusta posizione all'interno della mappa, affinche non abbiamo problemi di consistenza o errori*/
		public void setElement(String newElem){								
			endVertices[0].removeIncidence((Edge<java.lang.String>) this);
			endVertices[1].removeIncidence((Edge<java.lang.String>) this);
			element=newElem;
			endVertices[0].insertOutgoingEdges((Edge<java.lang.String>) this);
			endVertices[1].insertIngoingEdges((Edge<java.lang.String>) this);
		}

			
	}

	

	
	
	
	
	
	/** Costruttore di default che crea un grafo vuoto */
	public TreeTrie() {
	VList = new NodePositionList<Vertex<Integer>>();
	EList = new NodePositionList<Edge<String>>();
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
	public Iterable<Vertex<Integer>> vertices() {
		return VList;
	}

	/**Restituisce una collezione iterabile degli archi**/
	public Iterable<Edge<String>> edges() {
		return EList;
	}

	/**Restituisce la lista degli archi uscenti dal nodo passato in input **/
	public Iterable<Edge<String>> OutgoingEdges(Vertex<Integer> v) throws InvalidPositionException {
		MyVertex<Integer> temp=this.checkVertex(v);
		return temp.OutgoingEdges(); 
	}

	/**Restituisce la lista degli archi entranti dal nodo passato in input **/
	public Iterable<Edge<String>> IngoingEdges(Vertex<Integer> v) throws InvalidPositionException {
		MyVertex<Integer> temp=this.checkVertex(v);
		return temp.IngoingEdges();
	}

	private MyVertex<Integer> checkVertex(Vertex<Integer> v) throws InvalidPositionException {
		try{
			if(v.equals(null))
				throw new InvalidPositionException();
		
			MyVertex<Integer>temp =(MyVertex<Integer>)v;
			return temp;
		}
		catch(Exception e){
			throw new InvalidPositionException();
		}
	}
	
	private MyEdge<String> checkEdge(Edge<String> e) {
		try{
			if(e.equals(null))
					throw new InvalidPositionException();
			
			MyEdge<String>temp =(MyEdge<String>)e;
			return temp;
		}
		catch(Exception x){
			throw new InvalidPositionException();
		}
	}

	/**Restituisce l'array di vertici esterni all'arco passato in input**/
	public Vertex[] endVertices(Edge<String> e) throws InvalidPositionException {
		MyEdge<String> temp=checkEdge(e);			//Controlla la validità dell'arco
		return temp.endIntegerertices();				//e su quell'arco invoca endIntegerertices
	}

	

	/**Restituisce il nodo a cui e collegato il nodo passato in input**/
	public Vertex<Integer> nextNode(Vertex<Integer> v, Edge<String> e) throws InvalidPositionException {
		MyEdge<String> edg=this.checkEdge(e);
		
		MyVertex<Integer>[] endIntegerertices=edg.endIntegerertices();
		
		//inserire controllo if se è uguale a endIntegerertices[0]
		return endIntegerertices[1];
	}
	
	
	public int size(Vertex<Integer> x){
		Iterable<Edge<String>> list= this.OutgoingEdges(x);
		AlphabetMap s= (AlphabetMap)list;
		if(s.size()==0){
				String val=x.element().toString();
				return val.length();
		}
			
		int sum = 0;
		for(Edge<String> y: list){
			sum+= this.size(this.nextNode(x, y));
		}
		return sum;
	}
	
	public int size(String a, String p){
		Iterable<Vertex<Integer>> vertici=this.vertices();
		int sum=0;
		for(Vertex<Integer> x: vertici){
			if(this.haveIngoingEdges(x, a+p))
				sum+=this.size(x);
		}
		return sum;
	}
	
	public PositionList<Vertex<Integer>> arc(String a, String p){
		NodePositionList<Vertex<Integer>> list = new NodePositionList<Vertex<Integer>>();
		Iterable<Vertex<Integer>> vertici=this.vertices();
		for(Vertex<Integer> x: vertici){
			if(this.haveOutgoingEdges(x, a+p))
				list.addLast(x);
		}
		return list;
	}
	
	public Vertex<Integer> parent(Vertex<Integer> x){
		MyVertex<Integer> ver=this.checkVertex(x);
		MyEdge<String>y=this.checkEdge(ver.IngoingEdges.iterator().next());
		return y.endVertices[0];
	}

	
	public boolean areAdjacent(Vertex<Integer> u, Vertex<Integer> v) throws InvalidPositionException {
		MyVertex<Integer> ver=this.checkVertex(v);
		MyVertex<Integer> ver1=this.checkVertex(u);
		Iterable<Edge<String>> tosearch=ver1.OutgoingEdges();				//cerco gli archi uscenti dal primo noodo
		for(Edge<String> x: tosearch){										//per ogni arco
			if(this.nextNode(ver1, x).equals(ver))							//se il next node invocato su un argo generico e sul primo nood è uguale
				return true;													//resituisco true
		}
		return false;
	}
	
	
	public boolean haveOutgoingEdges(Vertex<Integer> v,String s){
		MyVertex<Integer> ver=this.checkVertex(v);
		Edge<String> a=ver.OutgoingEdges.get(s);
		if(a!=null)
			return true;
		else
			return false;
	}
	
	
	public boolean haveIngoingEdges(Vertex<Integer> v,String s){
		MyVertex<Integer> ver=this.checkVertex(v);
		Edge<String> a=ver.IngoingEdges.get(s);
		if(a!=null)
			return true;
		else
			return false;
	}
	
	public Edge<String> getOutgoingEdges (Vertex<Integer> v,String s) throws Exception{
		if(haveOutgoingEdges(v, s)){
			MyVertex<Integer> ver=this.checkVertex(v);
			Edge<String> a=ver.OutgoingEdges.get(s);
			return a;
		}else
			throw new Exception();
			 
	}

	@Override
	public Integer replace(Vertex<Integer> p, Integer o) throws InvalidPositionException {
		MyVertex<Integer> ver=this.checkVertex(p);
		Integer toreturn=ver.element();
		ver.setElement(o);
		return toreturn;
	}

	/**Sostituisce l'elemento contenuto nell'arco passato in input con un nuovo elemento passato in input, restituendo quello vecchio**/
	public String replace(Edge<String> p, String o) throws InvalidPositionException {
		MyEdge<String> edg=this.checkEdge(p);										//controlla e casta a MyEdge l'arco passato in input
		String toreturn= edg.element();												//in toreturn salva il vecchio valore dell'arco
		edg.setElement(o);														//modifica l'elemento dell'arco con quello passato in input
		return toreturn;														//restituisce il vecchio elemento
		
	}

	/**inserisce nel grafo un nuovo vertice che ha come elemento l'elemento passato in input**/
	public Vertex<Integer> insertVertex(Integer o) {
		MyVertex<Integer> vv= new MyVertex<Integer>(o);										//crea il nuovo vertice con l'elemento passato in input
		VList.addLast(vv);														//aggiunge il vertice in coda alla lista dei vertici del grafo
		vv.setLocation(VList.last());											//conserva all'interno del vertice la propria position all'interno della lista dei nodi
		return vv;																//restituisce il vertice inserito nel grafo
	}

	/**inserisce nel grafo un nuovo arco che ha come estremi i vertici passati in input, e come elemento l'elemento passato in input**/
	public Edge<String> insertEdge(Vertex<Integer> dacuiesce, Vertex<Integer> dacuientra, String o) throws InvalidPositionException {
		MyEdge<String> ee=new MyEdge<String>(dacuiesce, dacuientra, o);									//crea il nuovo arco con i vertici e l'elemento passato in input
		EList.addLast(ee);														//aggiunge l'arco in coda alla lista degli archi del grafo
		ee.setLocation(EList.last());											//aggiorna la position interna all'arco in maniera che punti alla propria posizione nella lista degli archi
		MyVertex<Integer> out=this.checkVertex(dacuiesce);										//controlla e casta a MyVertex il primo vertice passato in input
		MyVertex<Integer> inc=this.checkVertex(dacuientra);										//controlla e casta a MyVertex il secondo vertice passato in input
		ee.setIncidences(out.insertOutgoingEdges(ee), inc.insertIngoingEdges(ee));		//Inserisce l'arco nella lista di incidenza di ogni estremo, e salva le position in queste liste all'interno dell'arco stesso
		return ee;																//restituisce l'arco inserito nel grafo
	}

	/**Rimuove il vertice passato in input dal grafo**/
	public Integer removeVertex(Vertex<Integer> v) throws InvalidPositionException {
		MyVertex<Integer> temp=this.checkVertex(v);				//controlla e casta a MyVertex il nodo passato in input
		Position<Vertex<Integer>> pos=temp.location();			//pos mantiene la position del vertice nella lista dei vertici
		VList.remove(pos);									//rimuove il vertice dalla lista dei vertici
		temp.setLocation(null);								//cancella la location all'interno del vertice
		Iterable<Edge<String>> list=temp.OutgoingEdges();		//lista iterabile degli archi uscenti da quel vertice da eliminare
		for(Edge<String> x: list){								//per ogni arco appartenente a list (e quindi uscente sul vertice da eliminare)
			System.out.println("Rimozione vertice:"+v.element()+"arco:"+x.element());
			this.removeEdge(x);								//rimuovi l'arco dal grafo
		}
		
		list=temp.IngoingEdges();							//lista iterabile degli archi entranti da quel vertice da eliminare
		for(Edge<String> x: list){								//per ogni arco appartenente a list (e quindi uscente sul vertice da eliminare)
			System.out.println("Rimozione vertice:"+v.element()+"arco:"+x.element());
			this.removeEdge(x);								//rimuovi l'arco dal grafo
		}
		return temp.element();								//alla fine restituisci l'elemento del vertice rimosso
	}

	/**Rimuove l'arco passato in input dal grafo**/
	public String removeEdge(Edge<String> e) throws InvalidPositionException {
		MyEdge<String> temp=this.checkEdge(e);					//controlla e casta a MyEdge l'arco passato in input
		Position<Edge<String>> pos= temp.location();				//pos mantiene la position dell'arco nella lista degli arcHi 
		EList.remove(pos);									//rimuove l'arco dalla lista degli archi
		temp.setLocation(null);								//cancella la location all'interno dell'arco
		MyVertex<Integer>[] array=temp.endIntegerertices();	//array contiene i due estremi dell'arco
		array[0].removeIncidence(temp.incidences()[0]);		//Leggiamo la position dell'arco nella lista di incidenza del primo nodo, e la eliminiamo da essa
		array[1].removeIncidence(temp.incidences()[1]);		//Leggiamo la position dell'arco nella lista di incidenza del secondo nodo, e la eliminiamo da essa
		temp.setIncidences(null, null);						//cancelliamo nell'arco le position dell'arco nelle due liste di incidenza
		//System.out.println("ho cancellato: "+temp.element());
		return temp.element();								//restituisce l'elemento dell'arco rimosso
	}
	
	
	
	public boolean Cammino(Vertex<Integer> v, int k){
		boolean toreturn=true;
		
		if(k==0)
			return true;
		
		MyVertex<Integer> temp=this.checkVertex(v);
		
		if(temp.get("Visitato").equals("Si"))
			return false;
		
		Iterable<Edge<String>> listaedge=this.OutgoingEdges(temp);
		for(Edge<String> e: listaedge){
			MyVertex<Integer> ver=this.checkVertex(this.nextNode(temp, e));
			ver.put("Visitato", "Si");
			toreturn =this.Cammino(ver, k-1);
		}
		return toreturn;
	}


	  /**prende in input un vertice v e restituisce una collezione iterabile dei vertici del grafo
	    * che sono adiacenti a v. */
	    public Iterable<Vertex<Integer>> Adiacenti(Vertex<Integer> v) throws InvalidPositionException {

	    MyVertex<Integer> vertex = this.checkVertex(v); // controllo se il vertice v è valido
	    NodePositionList<Vertex<Integer>> adjacent = new NodePositionList<Vertex<Integer>>(); // lista degli adjacenti a v
	    Vertex<Integer> opp = null;
	    for (Edge<String> e : vertex.OutgoingEdges()) { // scorro tutti gli archi uscenti a v

	    MyEdge<String> edge = this.checkEdge(e); // controllo che l'arco sia valido
	    opp = this.nextNode(vertex,edge); // prendo l'oposto
	    MyVertex<Integer> temp = this.checkVertex(opp); // controllo che sia valido
	    adjacent.addLast(temp); // lo aggiungo alla lista adiacenti
	    }
	    return adjacent;
	    }
	
}

