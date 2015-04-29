package PriorityQueue;



import java.util.Iterator;

import defaultComparator.DefaultComparator;

import NodeList.NodePositionList;
import NodeList.PositionList;

import position.Position;
/**Implementazione della PQ
 * attraverso una PositionList non ordinata inerimento O(1) cancellazione/min O(n)**/
public class NotSortedListPriorityQueue<K, V> implements PriorityQueue<K, V> {
	private PositionList<Entry<K,V>> lista;
	private DefaultComparator<K> comp= new DefaultComparator<K>();
	private DefaultComparator<V> compValue= new DefaultComparator<V>();
	
	
	/**Costruttore**/
	public NotSortedListPriorityQueue(){
		lista= new NodePositionList<Entry<K,V>>();
	
	}
	
	/**Restituisce il numero di elementi contenuti nella PriorityQueue**/
	public int size() {
		return lista.size();				//Come contantore di elementi utilizzo il metodo .size della PositionList
	}

	/**Restituisce true se la PQ è vuota altrimenti false**/
	public boolean isEmpty() {
		if(this.size()!=0)
			return false;
		else
			return true;
	}
	
	/**Restituisce la entry con la chiave più piccola**/
	public Entry<K, V> min() throws EmptyPriorityQueueException {
		Entry<K, V> min=lista.first().element();				//Creo una variabile d'appoggio che conterrà il minimo della nostra lista, e all'inizio gli assegno il primo elemento
		for(Entry<K, V> x: lista){								//(for generico) per ogni Entry che c'è nella lista
			if(comp.compare(x.getKey(), min.getKey())<0)		//Confronto se la key dell'entry è minore della key del minimo
				min=x;												//se è vero assegno a min la nuova entry 
		}
		return min;
		}
	
	/**Crea e inserisce una nuova Entry con i parametri passati in input**/
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		MyEntry<K,V> temp=new MyEntry<K,V>(key,value);				//Creo e istanzio una MyEntry con i parametri pasati in input
		lista.addLast(temp);										//aggiungo alla fine(potrei farlo anche all'inizio) la Entry prima creata
		return temp;												//la restituisco
	}

	/**Rimuove la Entry con la chiave più piccola**/
	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		if(this.isEmpty())											//Se la PQ è vuota lancio l'eccezione
			throw new EmptyPriorityQueueException("PQ vuota");
			return this.remove(this.min());							//mi rifaccio al metodo remove (accessorio) richiamato sull minimo
}
	
	/**Crea un iteratore di entry**/
	public Iterator<Entry<K,V>> entryes(){
		return lista.iterator();								//Invoca un iteratore sulla lista di entrys
	}
	
	/**Metodo per la stampa**/
	public String toString()throws EmptyPriorityQueueException{
		if(this.isEmpty())											//Se la PQ è vuota lancio l'eccezione
			throw new EmptyPriorityQueueException("PQ vuota");
		String stringa="";											//Crea e inizializza una stringa
		Iterator<Entry<K,V>> stampa=this.entryes();							//crea un iteratore di Entry e gli assegna il metodo .entryes(che restituisce un iteratore di Entry)
		while(stampa.hasNext())										//finché l'interatore ha un successivo
			stringa=stringa+ " " + stampa.next().getValue();					//alla stringa assegna il valore contentuto nella stringa + il valore della entry corrente
		return stringa;												//restituisce la stringa
	}
	
	/**Metodo accessorio per la rimozione di una entry passata in input**/
	public Entry<K,V> remove(Entry<K,V> e){
		Entry<K,V> temp=null;
		Iterable<Position<Entry<K,V>>> iter=lista.positions();				//crea un iteratore di position
		for(Position<Entry<K,V>> x: iter){									//per ogni position contentuo nell'iteratore
			if(x.element().equals(e)){										//confornto se l'elemento di quella position (la entry) è uguale alla key passata in input
				temp=lista.remove(x);										//rimuovo quell'elemento e inserisco ciò che mi ritorna dentro una variabile
				return temp;												//ritorno la variabile
			}
		}
		return temp;
	}
	
	
	public void azzeraKey(V value){
		Entry<K,V> temp=null;
		Iterable<Position<Entry<K,V>>> iter=lista.positions();
		for(Position<Entry<K,V>> x: iter){
			if(compValue.compare(x.element().getValue(), value)==0){
				temp=this.remove(x.element());
				this.insert(lista.first().element().getKey(), temp.getValue()); 
		}
		}
	}
}
