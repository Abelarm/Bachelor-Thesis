package Tesi;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import Automata.Automata;
import Automata.NullEdgeException;
import Graph.Edge;
import Graph.Vertex;
import Map.HashTableMap;
import Map.Map;
import NodeList.NodePositionList;
import NodeList.PositionList;
import TreeTrie.TreeTrie;
import defaultComparator.DefaultComparator;

public class Algoritmo {
	
	private PositionList<String> SignatureList;						//PositionList di stringhe contenente le signature
	private TreeTrie trie;											//albero trie gestito come un grafo orientato
	private Automata automata;
	private ArrayList<String> alfabeto;
	public Vertex<Integer> root;
	private ArrayList<Vertex<Integer>> listafoglie;
	private Map<Integer,Vertex<Integer>> mappanodi;					//mappa dei nodi che ha come key l'element e come valore le foglie però all'interno dell'automa iniziale
																	//serve per calcolare Im(ai)
	
	public Algoritmo(Automata g, ArrayList<String> A) throws Exception{
		automata=g;
		alfabeto=A;
		listafoglie= new ArrayList<Vertex<Integer>>();
		try {
			this.CreaSignature(g, A);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.creaTrie();
		//DisegnaTree t= new DisegnaTree(trie,root);
		//t.Print();
		
	}
	
	/**Metodo per creare l'insieme delle signature prendendo in input un grafo e un alfabeto (ordinato)  
	 * @throws Exception */
	public void CreaSignature(Automata g, ArrayList<String> A) throws Exception{
		mappanodi=new HashTableMap<Integer,Vertex<Integer>>();
		String signature="";														
		PositionList<String> SignatureList= new NodePositionList<String>();			
		Iterable<Vertex<Integer>> vertices= g.vertices();								//lista iterabile di vertici
		for (Vertex<Integer> x : vertices){												//per ogni vertice nella lista
				for(int i=0;i<A.size();i++){													//Scorro ogni lettera all'interno dell'afabeto
					
					if(g.haveOutgoingEdge(x, A.get(i))){
						Edge<String> y=g.getOutgoingEdge(x, A.get(i));
						signature+=A.get(i)+g.nextNode(x, y).element();
					}
					
				}
					signature+="$"+x.element();											//infine alla fine della signature inserisco il carattere $(marker) e il nodo da cui abbiamo calcolato la signature			
					SignatureList.addLast(signature);									//lo aggiungo alla lista
					signature="";														//risetto a vuoto la stringa contente la signature
					mappanodi.put(x.element(), x);										//qui inserisco come chiave l'element del nodo e come valore il nodo stesso
		}
		
		this.setSignatureList(SignatureList);											//setto alla signature list della classe la signature lista cacolata
		DefaultComparator<String> comp = new DefaultComparator<String>();	
		NodePositionList.mergeSort(SignatureList, comp);								//invoco il mergesort sulla lista appena creata
	}		

	public PositionList<String> getSignatureList() {
		return SignatureList;
	}

	public void setSignatureList(PositionList<String> signatureList) {
		SignatureList = signatureList;
	}
	
	
	
	public void creaTrie() throws Exception{
		trie= new TreeTrie();		//creo grafo orientato
		int i=0;
		
		ArrayList<MyEntry> lista=new ArrayList<MyEntry>();							//creo arraylist contenente le mie entries
		root=trie.insertVertex(0);													//creo la root
		/**Inizializzo l'array list */
		for (String x: SignatureList){												//per ogni stringa nella lista delle signature
			lista.add(new MyEntry(x,root));												//creo una nuova entry stringa e che punta a root e la aggiungo all'array lista
		}
		Edge<String> y=null;
		/**Computo il trie */
		for(MyEntry x: lista){														//per ogni entry nella lista
			boolean flag=false;
			boolean tobreak=false;
			while(true){																//ciclo infinito
				flag=false;
				tobreak=false;
				String signature=x.getSignature();										//mi faccio dare la signature di quella stringa
				String arclabel=signature.substring(0,2);								//prendo i primi due caratteri
				x.setSignature(signature.substring(2,signature.length()));				//aggiorno la signature (vecchia signature-ultimi due caratteri)
				signature=x.getSignature();												//prendo la nuova signature
				
				System.out.println("signature: "+signature+"; arclabel: "+ arclabel);
				
				if(trie.haveOutgoingEdges(x.getVertice(), arclabel)){
					y=trie.getOutgoingEdges(x.getVertice(), arclabel);										//se ha già un arco uscente uguale ai primi due caratteri presi dalla signature
					if(!trie.nextNode(x.getVertice(), y).element().equals("0")){				//e il nodo a cui punta tale arco è diverso da 0 (ovvero le due signature sono uguali cambia solo $i)
						Vertex<Integer> temp=trie.nextNode(x.getVertice(), y);
						int val=temp.element();
						String value=Integer.toString(val);												//prendo il valore di tale noodo						
							
						if(signature.substring(0,1).equals("$")){										//se la signature rimanente è del tipo $qualcosa
							String nomefoglia= signature.substring(1,signature.length());					//prendo ciò che c'è dopo la signature
							value+=nomefoglia;															//al valore aggiungo il valore della mia foglia
							System.out.println(value);
							val= Integer.parseInt(value);
							trie.replace(temp, val);														//modifico il valore
							System.out.println("modifico foglia");
							tobreak=true;
																										//brekko passo alla prossima entry
						}
						
						x.setVertice(trie.nextNode(x.getVertice(), y));								//setto come vertice nella entry il vertice in cui entra quell'arco	
						System.out.println("attacco l'arco al nodo che già abbiamo: "+ x.getVertice().element() + " con arco entrante: "+ arclabel);
						flag=true;																	//assegno la flag a true per evitare inserimenti sbagliati
					}
					
					
				}
				
				if(tobreak){
					tobreak=false;
					break;
				}
				
				if(signature.substring(0,1).equals("$")){										//se la signature rimanente è del tipo $qualcosa
					String nomefoglia= signature.substring(1,signature.length());				//prendo ciò che c'è dopo la signature
					int val=Integer.parseInt(nomefoglia);
					Vertex<Integer> foglia= trie.insertVertex(val);						//creo una foglia con il nome uguale a ciò che abbiamo preso sopra
					trie.insertEdge(x.getVertice(), foglia,arclabel);							//inserisco l'arco tra il vertice nella entry e la nuova foglia creata
					System.out.println("ho inserito una nuova foglia "+ foglia.element()+ " con arco entrante: "+ arclabel );
					y=trie.getOutgoingEdges(x.getVertice(), arclabel);
					System.out.println("Esecuzione: "+ (trie.nextNode(x.getVertice(), y).element()));
					listafoglie.add(trie.nextNode(x.getVertice(), y));
					break;
				}else{
					if (!flag){																	//se la flag è false ovvero non è stato aggioranto il campo vertex di entry
						Vertex<Integer> nodo= trie.insertVertex(0);								//creo un nuovo nodo intermedio
						trie.insertEdge(x.getVertice(), nodo,arclabel);							//inserisco l'arco tra il vertice nella entry e il nuovo nodo
						x.setVertice(nodo);															//setto come nodo della entry il nodo creato
						System.out.println("ho inserito una nuovo nodo "+ nodo.element()+ " con arco entrante: "+ arclabel );
					}
				}
			}
		}
		
	}
	
	
	public void MinimizingLocalAutomata(){
		while(!(listafoglie.isEmpty())){
			Vertex<Integer> x=listafoglie.remove(0);
			Vertex<Integer> y=null;
			try{
				y=listafoglie.remove(0);
			}catch(IndexOutOfBoundsException e){
				System.out.println("sono dispari");
				break;
			}
			//System.out.println("foglia 1:"+ x.element());
			//System.out.println("foglia 2:"+ y.element());
			if(this.depth(x)!=this.depth(y))
				break;
			Vertex<Integer> automx=mappanodi.get(x.element());					//prendo il nodo(nell'automa) che ha la stessa chiave uguale alla foglie su cui sto lavorando nel trie
			Vertex<Integer> automy=mappanodi.get(y.element());					//serve per calcolare Im(ai)
			//System.out.println("Automx:"+automx.element()+"Automy:"+automy.element());
			for(int i=alfabeto.size()-1;i>=0;i--){
				if(x.element()>=10 || y.element()>=10)
					break;
				if(automata.haveOutgoingEdge(automx, alfabeto.get(i)) && automata.haveOutgoingEdge(automy, alfabeto.get(i))){
					System.out.println("la foglia:" + x.element() +" e la foglia:" + y.element() +" appartengono ad Im("+alfabeto.get(i)+")");
					Edge<String> edgex=null;
					Edge<String> edgey=null;
					try {
						edgex=automata.getOutgoingEdge(automx, alfabeto.get(i));
						edgey=automata.getOutgoingEdge(automy, alfabeto.get(i));
						
					} catch (NullEdgeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Vertex<Integer> classx=automata.nextNode(automx, edgex);
					Vertex<Integer> classy=automata.nextNode(automy, edgey);
					System.out.println("Class di x:"+ classx.element() +" Class di y:"+ classy.element());
					//System.out.println("Size("+ alfabeto.get(i) + "," + classx.element()+")="+this.size(alfabeto.get(i), classx.element().toString()));
					//System.out.println("Size("+ alfabeto.get(i) + "," + classy.element()+")="+this.size(alfabeto.get(i), classy.element().toString()));
					if(this.size(alfabeto.get(i), classx.element().toString())<=this.size(alfabeto.get(i), classy.element().toString())){
						System.out.println("MERGE("+alfabeto.get(i)+","+classx.element() +","+classy.element()+")");
						this.Merge(alfabeto.get(i),classx.element().toString(), classy.element().toString());
					}
					else{
						System.out.println("MERGE("+alfabeto.get(i)+","+classy.element() +","+classx.element()+")");
						this.Merge(alfabeto.get(i),classy.element().toString(), classx.element().toString());
					}
					
					
				}else
					System.out.println("la foglia:" + x.element() +" e la foglia:" + y.element() +" non appartengono ad Im("+alfabeto.get(i)+")");
			}
			
		}
	}
	
	public void Merge(String a,String p ,String q){
	/**	if(p.equals(q)){
			System.out.println("p e q sono uguali non faccio niente");
			return;
		}*/
		PositionList<Vertex<Integer>> listaarchi= this.arc(a, p);
		for(Vertex<Integer> x: listaarchi){
			//System.out.println("Vertex:"+ x.element());
			if(!(trie.haveOutgoingEdges(x, a+q))){
				try {
					Edge<String> s=trie.getOutgoingEdges(x, a+p);
					
					
							System.out.println("Cambio il label di:"+ s.element()+ " in: "+a+q); //cancello quello vecchio e inserisco quello nuovo per via della situazione mappa
							/**Vertex<Integer> dacuiesce=trie.endVertices(s)[0];
							Vertex<Integer> incuientra=trie.endVertices(s)[1];
							Edge<String> rimozione=s;
							trie.removeEdge(rimozione);
							trie.insertEdge(dacuiesce, incuientra, a+q);
							 */
							trie.replace(s, a+q);
						
							//System.out.println(s.element());
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					Edge<String> e=trie.getOutgoingEdges(x, a+p);
					Edge<String> f=trie.getOutgoingEdges(x, a+q);
					Vertex<Integer> x1=trie.nextNode(x, e);
					Vertex<Integer> x2=trie.nextNode(x, f);
					//if(!(e.equals(f))){
							
							Vertex<Integer> incuientra=trie.endVertices(e)[1];
							if(incuientra.element()==0)	{							//cancello l'arco solo quando poi la chiama di dopo di fusion non è su due foglie
								System.out.println("Cancello l'arco:"+e.element());	//perchè quando chiama fusion su due foglie rimuove automaticametne il nodo e l'arco entrate
								trie.removeEdge(e);
							}
					//}
					
					System.out.println("chiamo Fusion("+x1.element()+","+x2.element()+")");
					System.out.println("rispettivamente destinazione di:"+e.element()+" e di:"+f.element());
					this.Fusion(x1, x2);
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
					
			
		}
		
		
	}
	
	
	public void Fusion(Vertex<Integer> x1,Vertex<Integer> x2){
		if(x1.equals(x2))
			return;
		if(x1.element()!=0 && x1.element()!=0){
			System.out.println("Sono foglie");
			System.out.println("Fondo la foglia: "+x1.element()+"nella foglia: "+x2.element());
			int label=x1.element()*10+x2.element();
			trie.replace(x2, label);
			trie.removeVertex(x1);
			System.out.println("Nuova foglia:"+x2.element());
		}
		else{
			System.out.println("Non sono foglie");
			Iterable<Edge<String>> archi=trie.OutgoingEdges(x1);
			for(Edge<String> e: archi){
				if(!(trie.haveOutgoingEdges(x2, e.element()))){
					System.out.println("Trasferisco:"+e.element()+"da succ(x1) a succ(x2)");
					Vertex<Integer> incuientra=trie.endVertices(e)[1];				//il secondo vertice è quello in cui entra
					trie.removeEdge(e);
					trie.insertEdge(x2, incuientra, e.element());
				}else{
					Vertex<Integer> y1=trie.nextNode(x1, e);
					Edge<String> f=null;
					try {
						f = trie.getOutgoingEdges(x2, e.element());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Vertex<Integer> y2=trie.nextNode(x2, f);
					//trie.removeVertex(x1);
					System.out.println("chiamo Fusion("+y1.element()+","+y2.element()+")");
					this.Fusion(y1, y2);
					trie.removeVertex(x1); //rimuovo x1, perchè x1 e x2 sono i "genitori" dei nodi che devono essere fusi 
					//ed essendo che durante la fusione y1 figlio di(x1) verrà cancellato canecello anche il suo genitore
					
				}
			}
		}
	}
	
	
	
	public Automata getMinimizedAutomata(){
		Automata auto= new Automata();
		Iterable<Vertex<Integer>> verticestrie=trie.vertices();
		ArrayList<Vertex<Integer>> listanodiautom=new ArrayList<Vertex<Integer>> ();
		ArrayList<Vertex<Integer>> listafoglietrie=new ArrayList<Vertex<Integer>> ();
		for(Vertex<Integer> x: verticestrie){
			if(x.element()!=0){
				listanodiautom.add(auto.insertVertex(x.element()));
				listafoglietrie.add(x);
			}
		}
		
		int i=0;
		for(Vertex<Integer> x: listafoglietrie){
			Vertex<Integer> nodo=listanodiautom.get(i);
			inserisciArchi(auto,nodo,x);
			i++;
		}
		
		return auto;
		
	}
	
	public void inserisciArchi(Automata auto,Vertex<Integer> nodo,Vertex<Integer> x){
		if(x.equals(root))
			return;
		Iterable<Edge<String>> archi=trie.IngoingEdges(x);
		for(Edge<String> s: archi){
			Iterable<Vertex<Integer>> nodiautoma=auto.vertices();
			for(Vertex<Integer> n: nodiautoma){
				String label=n.element().toString();
				if(label.indexOf(s.element().charAt(1))!=-1)
					auto.insertEdge(nodo, n, s.element().charAt(0)+"");
					
			}
		}
		inserisciArchi(auto,nodo,trie.parent(x));
	}
	
	
	
	
	

	public int numVertex(){
		return trie.numVertices();
	}
	
	public int numEdge(){
		return trie.numEdges();
	}
	
	public String StampaVertici(){
		String stringa="";
		Iterable<Vertex<Integer>> lista=trie.vertices();
		for(Vertex<Integer> x: lista){
			stringa+=x.element()+",";
		}
		return stringa;
	}
	
	public String StampaArchi(){
		String stringa="";
		Iterable<Edge<String>> lista=trie.edges();
		for(Edge<String> x: lista){
			stringa+=x.element()+",";
			//System.out.println("Edge:"+x.element()+"esce da:"+trie.endVertices(x)[0].element()+"entra in:"+trie.endVertices(x)[1].element());
		}
		return stringa;
	}
	
	public int size(Vertex<Integer> x){
		
		return trie.size(x);
	}
	
	public int size(String a, String p){
		return trie.size(a, p);
	}
	
	public PositionList<Vertex<Integer>> arc(String a, String p){
		return trie.arc(a, p);
	}
	
	public int depth(Vertex<Integer> x){
		if(x.equals(root))
			return 0;
		//System.out.println("foglia:"+x.element());
		return 1+ depth(trie.parent(x));
	}
	
	
	public ArrayList<Vertex<Integer>> getFoglie(){
		return listafoglie;
	}
	
	public TreeTrie getTreeTrie(){
		return trie;
	}
	
}
