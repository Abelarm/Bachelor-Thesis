package NodeList;

import java.util.Comparator;
import java.util.Iterator;


import position.Position;
	

/**Implementazione dellla position List attraverso una lista doppiamente linkata che fa uso della position DNode che hanno un puntatore a successivo e uno al precendente**/

public class NodePositionList<E> implements PositionList<E> {
	protected int num_elementi;
	protected DNode<E> header,trailer;		//Nodi sentienalla per la gestione della lista, header si trova prima del primo nodo, trailer dopo l'ultimo
	
	/**Costruttore senza parametri**/
	public NodePositionList(){ 	
		num_elementi=0;
		header= new DNode<E>(null,null,null);		
		trailer=new DNode<E>(header,null,null);		//crea il trailer e come puntatore al precedente gli passa header
		header.setNext(trailer);					//Setta il puntatore al successivo di header a trailer
	}	
	
	/**Metodo per controllare la validtà di una position, e castarla  DNode**/
	public DNode<E> checkPosition(Position<E> p){		
		if(p==null) throw new InvalidPositionException("Position null passata alla nodeList");
		if(p==header) throw new InvalidPositionException("Header non è una position valida");
		if(p==trailer) throw new InvalidPositionException("Trailer non è una position valida");
		
		try{
			DNode<E> temp = (DNode<E>) p;  //Cast necessario per controllare se la position appartiene al tipo di dato NodePositionList (ritorna una position castata a DNode)

		if((temp.getPrev()==null)||(temp.getNext()==null))		//se uno dei riferimenti a DNode è vuoto lancia l'eccezione "InvalidPositionException"
			throw new InvalidPositionException("Position non appartiene ad una NodeList valida");
		return temp;
	}
		catch(ClassCastException e){		//Se il cast da errore cattura l'eccezione e ne lancia una diversa InvalidPositionException""
			throw new InvalidPositionException("Position di tipo sbagliato per questo tipo di lista");
		}
	}

	/**Crea un position con l'elemento passato e lo inserisce all'interno della struttura dopo la position passta in input**/
	public void addAfter(Position<E> p, E e) throws InvalidPositionException {			
		DNode<E> pcastato=this.checkPosition(p);							//Chiamao check position(su p passato in input), sia per controllare la validità e sia per avere un un oggetto(DNode) su cui poter lavorare
		
		DNode<E> temp= new DNode<E>(pcastato,pcastato.getNext(),e);			//Creiamo un DNode che ha come puntatore al precedente la position che ci viene data in input(però castata, quindi un DNode) 
																//come successivo il successivo della position passato in input, e come elemento e(cioè si inserirà tra l'elemento passato in input e il suo successivo)
		
		pcastato.getNext().setPrev(temp); 									//al successivo della position passata in input modificamo il puntatore al precedente assegnandogli DNode creato precedentemente	
		pcastato.setNext(temp);												//nella position passsata in input modifichiamo il puntatore al successivo e gli assegnamo il DNode creato in precedenza
		num_elementi++;
	
		}

	/**Crea un position con l'elemento passato e lo inserisce all'interno della struttura prima della position passta in input**/
	public void addBefore(Position<E> p, E e) throws InvalidPositionException { 	
		DNode<E> pcastato=this.checkPosition(p);							//Chiamao check position(su p passato in input), sia per controllare la validità e sia per avere un un oggetto(DNode) su cui poter lavorar
		
		DNode<E> temp= new DNode<E>(pcastato.getPrev(),pcastato,e);			//Creiamo un DNode che ha come puntatore al precedente il precedente della position che ci viene data in input, 
															//e come successivo la position passata in input(però castata, quindi un DNode), e come elemento e(cioè si inserirà tra l'elemento passato in input e il suo precedente)
		
		pcastato.getPrev().setNext(temp);									//al precedente della position passata in input modifichiamo il puntatore al successivo assegnandogli il DNode creato precedentemente
		pcastato.setPrev(temp);												//nella position passata in input modifichiamo in puntatore al precedente e gli assegniamo il DNode creato in precedenza
		num_elementi++;
	}

	/**Crea una position con l'elemento passato e lo inserisce all'interno della struttura in prima posizione**/
	public void addFirst(E e) {			
		DNode<E> newFirst= new DNode<E>(header,header.getNext(),e);			//Crea un DNode avente come puntatore al precedente l'header e come successivo il successivo dell'header(cioè il vecchio primo)
		header.setNext(newFirst);											//modifichiamo il puntatore al successivo di header e gli assegniamo il DNode prima creato
		newFirst.getNext().setPrev(newFirst);								//al successivo del DNode creato (cioè il vecchio primo) modifichiamo il puntatore al precedente e gli assegniamo il DNode prima creato
		num_elementi++;
		}

	/**Crea una position con l'elemento passato e lo inserisce all'interno della struttura in ultima posizione**/
	public void addLast(E e) {			
		DNode<E> newLast= new DNode<E>(trailer.getPrev(),trailer,e);	//Crea un DNode avente come puntatore al precedente il precedente di trailer(cioè il vecchio ultimo), e come successivo trailer
		trailer.setPrev(newLast);										//modifichiamo il puntatore al precedente di trailer e gli assegniamo il DNode prima creato
		newLast.getPrev().setNext(newLast);								//Al precedente del DNode creato(il vecchio ultimo) modifichiamo il puntatore al successivo e gli assegniamo il DNode prima creato
		num_elementi++;
		
	}

	/**Restituisce la prima Position della struttura**/
	public Position<E> first() throws EmptyListException {		//Ritorna il primo elemento
		return (Position<E>)header.getNext();
	}

	/**Controlla se è vuota**/
	public boolean isEmpty() {
		if(num_elementi==0)
			return true;
		else
			return false;
	}
	
	/**Restituisce l'ultima Position della struttura**/
	public Position<E> last() throws EmptyListException {			//Ritorna l'ultimo elemento
		return (Position<E>)trailer.getPrev();
	}

	/**Restituisce la position successiva a quella passata in input**/
	public Position<E> next(Position<E> p) throws InvalidPositionException,BoundaryViolationException {
		DNode<E> pcastato=this.checkPosition(p);			//Chiamao check position(su p passato in input), sia per controllare la validità e sia per avere un un oggetto(DNode) su cui poter lavorar
		Position<E> temp= pcastato.getNext();				//su questo nodo richiamo il metodo getNext(dell'interfaccia di Position) che mi restituisce l'elemento contenuto nel puntatore al successivo(cioè il successivo)
		return temp;
	}

	/**Restituisce la position precednetea a quella passata in input**/
	public Position<E> prev(Position<E> p) throws InvalidPositionException,		
			BoundaryViolationException {
		DNode<E> pcastato=this.checkPosition(p);		//Chiamao check position(su p passato in input), sia per controllare la validitïà e sia per avere un un oggetto(DNode) su cui poter lavorare
		Position<E> temp= pcastato.getPrev();			//su questo nodo richiamo il metodo getPrev(dell'interfaccia di Position) che mi restituisce l'elemento contenuto nel puntatore al precedente(cioè il precendete)
		return temp;
	}


	/**Rimuove la position passata in input e restituisce l'elemento precedentemente contenuto**/
	public E remove(Position<E> p) throws InvalidPositionException {			
		DNode<E> pcastato=this.checkPosition(p);			//Chiamao check position(su p passato in input), sia per controllare la validità e sia per avere un un oggetto(DNode) su cui poter lavorare	
		pcastato.getPrev().setNext(pcastato.getNext());		//Nella position precedente a p, modifico il puntatore al successivo assegnandogli la position successiva a p
		pcastato.getNext().setPrev(pcastato.getPrev());		//Nella position successiva a p, modifico il puntatore al precedente assegnandogli la position precedente a p
		num_elementi--;
		return p.element();
	}


	/**Sovrascrive l'elemento della position passata, ma ritorna il vecchio elemento contenuto**/
	public E set(Position<E> p,E e) throws InvalidPositionException {			
		DNode<E> pcastato=this.checkPosition(p);	//Chiamao check position(su p passato in input), sia per controllare la validità e sia per avere un un oggetto(DNode) su cui poter lavorare
		E temp=pcastato.element();					//in una variabile d'appoggio metto l'elemento contenuto all'interno della position
		pcastato.setElement(e);						//Setto l'elemento contenuto nella position con quello passato in input
		return temp;								//restituisco il vecchio temp
	}


	/**Restituisce la dimensione della struttura**/
	public int size() {		//Restituisce il numero degli elementi
		return num_elementi;
	}
	
	/**Metodo per stampare gli elementi contenuti nella PositionList**/
	public String toString(){			
		String stringa=("Gli elementi sono: ");
		DNode<E>temp=(DNode<E>)this.first();		//creiamo un DNode d'appoggio e gli assegniamo la prima position della lista(che verra castata perchè la variabile è DNode) 
		int j=this.size();
		for(int i=0;i<j;i++){			//il for gira fino all numero degli elementi
			stringa=stringa+ temp.element().toString() +" ";	//facciamo il toString dell'elemento contenuto in temp
			temp=temp.getNext();								//a temp assegniamo il succecssivo di temp(in questo modo avanziamo)
		}
		return stringa;
	}
	
	/**Restituisce un nuovo oggetto iterato creato attraverso il costruttore ELementIterator(che prende come parametro la nostra lista)**/
	public Iterator<E> iterator() {		
		return new ElementIterator<E>(this);
	}

	/**Metodo che restituisce una lista Iterable di positions**/
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> P = new NodePositionList<Position<E>>();					//creo una PositionList di Position 
		if (!isEmpty()) {																	//se non è vuota
			Position<E> p = first();															//in una position inserisco il primo elemento
			while (true) {																	//while(infinito) si fermerà quando finisce la lista
				P.addLast(p); 																	// aggiunge la position p come ultimo elemento di P
				if (p == this.last()) 																//se p è l'ultimo elemento 
					break;																				//esce dal while
				p = next(p);																		//altrimenti a p assegna il sucessivo di p
			}
		}
		return P; 																			// restituisce P come oggetto (collezione) Iterabile
	}

	/**Metodo per la fusione**/
	private static <E> void merge(NodePositionList<E> in1, NodePositionList<E> in2, Comparator <E> c, NodePositionList<E> in){
		while((!in1.isEmpty())&&(!in2.isEmpty())){												//finchè entrambe le liste non sono vuote
			
			if (c.compare(in1.first().element(), in2.first().element())<=0)							//confrontiamo il primo elmenento della lista uno con il primo elemento della lista due
				in.addLast(in1.remove(in1.first()));													//se l'elemento della lista uno è più piccolo togliamo il primo e lo inseriamo nella lista
			else
				in.addLast(in2.remove(in2.first()));													//togliamo il primo dalla lista due e lo inseriamo nella lista
		}
		while(!in1.isEmpty())																	//while per inserire gli elementi rimasti dalla lista uno
			in.addLast(in1.remove(in1.first()));
		
		while(!in2.isEmpty())																	//while per inserire gli elementi rimasti dalla lista uno
			in.addLast(in2.remove(in2.first()));
	}
	
	
	
	/**MergeSort per l'ordinamento di due stringhe**/
	public static <E> void mergeSort(PositionList<E> in, Comparator<E> c){
		int n=in.size();																			//in una variabile inseriamo la dimensione
		if(n<2) 																					//se la dimensione è minore di 2
			return;																						//chiude il metodo
		PositionList<E> in1=new NodePositionList<E>();												//crea due liste d'appoggio
		PositionList<E> in2=new NodePositionList<E>();
		
		int i=0;																					//inizializza un contatore a zero
		while(i<(n/2)){																				//finche questo contatore è minore della dimensione della lista/2
			Position<E> temp=in.first();
			in.remove(temp);
			in1.addLast(temp.element());															//togliamo dalla lista il primo elemento e lo inseriamo in una delle due liste d'appoggio create															
			i++;																						//incrementiamo il contatore
		}
		while(i<n){																					//finche il contatore di prima(che sarà arrivato (n/2)2	 è minore della dimensione																	
			Position<E> temp=in.first();
			in.remove(temp);
			in2.addLast(temp.element());															//togliamo dalla lista il primo elemento e lo inseriamo in una delle due liste d'appoggio create															
			i++;																					//incrementiamo il contatore
		}

		
		mergeSort(in1, c);																			//richiamiamo il mergesort sulle liste cosi create
		mergeSort(in2, c);
		merge((NodePositionList<E>)in1, (NodePositionList<E>)in2, c, (NodePositionList<E>)in);		//e invochiamo il merge sulle liste modificate
	}	
	
	
	
	
	/**Metodo per togliere dalla lista le position di indice pari**/
	public void removeOdd(){
		Iterable<Position<E>> iter= this.positions();									//creo un iteratore di position
		int i=0;																		//inizializzo una variabile che sarà il mio contatore
		for(Position<E> x: iter){														//per ognuna di queste position
			if(i%2==0)																		//chiedo se la varibile contatore (che funge da indice) è pari
				this.remove(x);																		//allora rimuovo quella position
		}
	}
	
}
