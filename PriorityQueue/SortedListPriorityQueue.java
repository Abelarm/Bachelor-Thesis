package PriorityQueue;

import java.util.Comparator;
import java.util.Iterator;

import NodeList.NodePositionList;
import NodeList.PositionList;

import defaultComparator.DefaultComparator;

import position.Position;

/** Utilizaz una lista di position per immagazzinare Entry<K,V> è ordinata in modo crescente rispetto le K
 * inerimento O(n) cancellazione/min O(1)**/
public class SortedListPriorityQueue<K,V> implements PriorityQueue<K, V> {
	protected PositionList<Entry<K,V>> entries; 
	protected Comparator<K> c;
	
	/**Costruttore senza parametri**/
	public SortedListPriorityQueue() {
		entries = new NodePositionList<Entry<K,V>>();		
		c = new DefaultComparator<K>();					//utilizza un comparatore di default
		}
	
	/**Costruttore parametrico gli viene passato un comparator**/
	public SortedListPriorityQueue (Comparator<K> comp) {		
		entries = new NodePositionList<Entry<K,V>>();			
		c = comp;										//utilizza il comparatore passato in input
		}
	
	/**Restituisce la dimensione della nostra SLPQ**/
	public int size() {
		return entries.size();
	}

	/**Restituisce true se la PQ è vuota altrimenti false**/
	public boolean isEmpty() {
		if(this.size()==0)
			return true;
		else
			return false;
	}

	/**Restituisce la entry con la chiave più piccola**/
	public Entry<K,V> min () throws EmptyPriorityQueueException {
		if (entries.isEmpty()) 
			throw new EmptyPriorityQueueException("PQ vuota");
		else 
			return entries.first().element();							//Ritorna il primo elemento perché la lista è ordinata e ogni inserimento è coerente con la dimensione della chiave
	}

	/**Crea e inserisce una nuova Entry con i parametri passati in input**/
	public Entry<K, V> insert(K k, V v) throws InvalidKeyException {
		checkKey(k); 											// metodo per controllare se la chiave è valida
		Entry<K,V> entry = new MyEntry<K,V>(k, v);				//Creo la mia entry con i parametri passati in input
		insertEntry(entry); 									// richiamo il metodo ausiliario per l'inserimento
		return entry;
	}

	
	/**Metodo ausiliario per l'inserimento**/
	protected void insertEntry(Entry<K,V> e) { 
		if (this.isEmpty()) 					//se la la nostra PQ è vuota
			entries.addFirst(e); 					//inseriscel 'elemento all'inzio (perché sicuramente è la più piccola entry,(visto che è vuoto))
		else 
				if (c.compare(e.getKey(), entries.last().element().getKey()) > 0) 			//Se la entry è maggiore dell'ultimo
					entries.addLast(e); 																	// inserisce alla fine della lista
	else { 
		Position<Entry<K,V>> curr = entries.first(); 								//ad una Entry d'appoggio inserisce il primo elemento della lista
		while (c.compare(e.getKey(), curr.element().getKey())> 0) { 				//finche la chiave del nostro elemento è maggiore della chiave della Entry d'appoggio
			curr = entries.next(curr); 													// alla nostra entry d'appoggio assegnamo la prossima entry
			} 
		entries.addBefore(curr, e); 							//arrivati a questo punto la entry d'appoggio sara maggiore della nostra entry passata in input(altrimenti non si fermava il while), quindi la inseriamo prima di quest'ultima 
		}
	}

	/**Controlla se la key è valida e può essere comparata**/
	private void checkKey(K key)throws InvalidKeyException{
	try {
			c.compare(key,key);				//comprara due volte la key, se questa key non puïò essere comparata allora lancia l'eccezione
	}
	catch (Exception e){				//che viene catturata 
			throw new InvalidKeyException("Invalid key");			//e al suo posto viene mandata una eccezione diversa cioé invalidKeyException
		}
	}
	
	
	/**Rimuove la Entry più piccola **/
	public Entry<K,V> removeMin() throws EmptyPriorityQueueException {
		if (this.isEmpty()) 									//se la nostra PQ è vuota lancia l'eccezione 
			throw new EmptyPriorityQueueException("PQ vuota");
		else return entries.remove(entries.first());		//invoca il remove della PositionList(che vuole una position in input) passandogli come parametro entries.first(cioè la position contenuta nella prima posizione)
	}
	
	/**Crea un iteratore di entry**/
	public Iterator<Entry<K,V>> entryes(){
		return entries.iterator();								//Invoca un iteratore sulla lista di entrys
	}
	
	/**Metodo per la stampa**/
	public String toString()throws EmptyPriorityQueueException{
		if(this.isEmpty())											//Se la PQ è vuota lancio l'eccezione
			throw new EmptyPriorityQueueException("PQ vuota");
		String stringa="";											//Crea e inizializza una stringa
		Iterator<Entry<K,V>> stampa=this.entryes();							//crea un iteratore di Entry e gli assegna il metodo .entryes(che restituisce un iteratore di Entry)
		while(stampa.hasNext())										//finchïé l'interatore ha un successivo
			stringa=stringa+ " " + stampa.next().getValue();					//alla stringa assegna il valore contentuto nella stringa + il valore della entry corrente
		return stringa;												//restituisce la stringa
	}
	
	
	

}
