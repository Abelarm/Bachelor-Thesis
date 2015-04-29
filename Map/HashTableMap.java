package Map;

import NodeList.NodePositionList;
import NodeList.PositionList;
import PriorityQueue.Entry;
import PriorityQueue.InvalidKeyException;

/**Implementazione attraverso un array gestito in maniera circolare+ una funziona hash,
 * le collisioni vengono gestite con il linear probing cioè quando avviene una collisione(chiavi diverse che hanno la stessa funzione hash)
 * la chiave cosi trovata si inserisce all'iterno della prima cella libera disponibile**/
public class HashTableMap<K,V> implements Map<K, V> {
	protected Entry<K,V> AVAILABLE = new HashEntry<K,V>(null, null); // marker 
	protected int n = 0; 	// dimensione
	protected int prime, capacity; // capacità del bucket array e il valore prime (p) 
	protected Entry<K,V>[] bucket; // bucket array 
	protected long scale, shift; // fattori di shift (b) e di scala (a) //a,
	
	/** Crea una tabella hash con prime factor 109345121 e capacitaè 1024. **/ 
	public HashTableMap(){ 
		this(109345121,1024); 
		}
	
	/** Creates a hash table with prime factor=109345121 and given initial capacity */
	public HashTableMap(int cap) { 
		this(109345121, cap); 
		}
	
	/** Creates a hash table with prime p and the given capacity. */
	@SuppressWarnings("unchecked")
	public HashTableMap(int p, int cap) {
		prime = p; 				//numero primo pasatto, per la teoria dei numeri altrimenti c'è alta possibilitè di collisioni
		capacity = cap; 		//capacitè dell array bucket
		bucket = (Entry<K,V>[]) new Entry[capacity]; 	// safe cast 
		java.util.Random rand = new java.util.Random(); 
		scale = rand.nextInt(prime-1) + 1; 			// scale sarè >0 e <prime 
		shift = rand.nextInt(prime); 				// shift sarè >=0 e <prime
	}
	
	/**Metodo per lanciare l'eccezione qual'ora la chiave risultasse nulla**/
	protected void checkKey(K k) {
		if (k == null) 
			throw new InvalidKeyException("Invalid key: null.");
		}
	
	/** Calcolo del valore hash mediante il metodo MAD applicato al default hash code. */
	public int hashValue(K key) {
	return (int) ((Math.abs(key.hashCode()*scale + shift) % prime) % capacity);
	}
	
	/**Restituisce la dimensione della mappa**/
	public int size() { 
		return n;
		}
	
	/**Restituisce true se la mappa è vuota altrimenti false**/
	public boolean isEmpty() { 
		return (n == 0); 
		}
	
	// Metodo di ricerca ausiliario: restituisce l'indice della chiave key, se viene trovata,  
	// oppure il valore negativo -(avail + 1), se key non c'è, dove avail è l'ndice della prima cella trovata 
	// vuota o AVAILABLE (ci potrà servire dopo) 
	protected int findEntry(K key) throws InvalidKeyException { 
		int avail = -1; 
		checkKey(key); 
		int i = hashValue(key); 		//tramite MAD 
		int j = i; 
		do { 
			Entry<K,V> e = bucket[i]; 			//in una entry d'appoggio inseriamo il valore contenuto in [i]
			if ( e == null) 					//se e è diverso da null
			{ 
				if (avail < 0) 						//s avail è uguale a -1
					avail = i; 							// key non c'è: i coincide con la prima cella disponibile (lo si ritorna)
				break; 
			} 
			if (key.equals(e.getKey())) 			// se la key passata in input è uguale alla key dell'entry d'appoggio
				return i; 								//ritorniamo i(posizione dove si trova la key)
			
			if (e == AVAILABLE) { 				// Se la entry d'appoggio è uguale ad AVAILABLE 
				if (avail < 0) 
					avail = i; 					// la cella i è la prima cella disponibile 
				} 
			i = (i + 1) % capacity; 			// continuiamo la ricerca 
		} while (i != j); 
		
		return -(avail + 1); 		// avail è la prima cella vuota o AVAILABLE; entry non trovata 
	}


	public V put (K key, V value) throws InvalidKeyException { 
		int i = findEntry(key); 				//trova l'appropriata cella per questa entry e la mette dentro i
		if (i >= 0) 								// se i>0 allora vuol dire che i è l'indice della key, altrimenti  la posizione della prima libera
			return ((HashEntry<K,V>) bucket[i]).setValue(value); 		// settiamo il nuovo value
		if (n >= capacity/2) {
			rehash();									 // rehash per mantenere il load factor <= 0.5 
			i = findEntry(key); 						//trova di nuovo lèappropriata cella per questa entry
		}
		bucket[-i-1] = new HashEntry<K,V>(key, value); 		//si posiziona in -i-1 perchè i contiene un numero negativo(visto che non è stato trovato da findkey)
		n++;
		return null; 		// non cèera giè lèentry.
	}
	
	/** Raddoppia la taglia del bucket array e fa il rehash delle entry.*/ 
	@SuppressWarnings("unchecked")
	protected void rehash() { 
		capacity = 2*capacity; 													//raddoppia la capacity
		Entry<K,V>[] old = bucket; 												//ad un array di entruy d'appoggio assegna bucket
		bucket = (Entry<K,V>[]) new Entry[capacity]; 							// istanza il nuovo bucket (con la dimensione raddoppiata)
		java.util.Random rand = new java.util.Random();
		scale = rand.nextInt(prime-1) + 1; 										// nuovo fattore di scala
		shift = rand.nextInt(prime); 											// nuovo fattore di shift
		//riempiemento del nuovo bucket con i vecchi elementi
		for (int i=0; i<old.length; i++) {		
			Entry<K,V> e = old[i];												//in una Entry d'appoggio inserisco un elemento del vecchio bucket
			if ((e != null) && (e != AVAILABLE)) { 								//se la entry d'appoggio è diversa da null e diversa da AVAILABLE
				int j = - 1 - findEntry(e.getKey());							//in j assegnamo -1 - findEntry(e.getKey())...j è positivo perchè calcolato sul nuovo bucket che sicuramente non ha quell'Entry all'interno(quindi verra restituito sempre un numero negativo da findkey)
				bucket[j] = e;													//in bucket[j] assegnamo il nostro elemento
			}
		}
	}

	/**Se la mappa M ha lèentry con chiave k, restituisce il valore associato, altrimenti null.**/
	public V get (K key) throws InvalidKeyException {
		int i = findEntry(key);
			if (i < 0) 
				return null; 				// non c'è la key e quindi non c'è il value associato.
		return bucket[i].getValue(); 		// restituisce il value della entry con chiave key
		}

	public V remove (K key) throws InvalidKeyException {
		int i = findEntry(key); 								// troviamo lèentry
		if (i < 0) 
			return null; 							// se non cèè, niente da rimuovere
		V toReturn = bucket[i].getValue();			//In una variabile d'appoggio V inseriamo il valore contenuto nella posizione [i]	
		bucket[i] = AVAILABLE;						// marchiamo la cella come disponibile
		n--;										//diminuiamo la dimensione dell'array
		return toReturn;							//ritorniamo il valore
		}

	/**restituisce una collezione iterabile delle chiavi della Map**/
	public Iterable<K> keys() {
		PositionList<K> keys = new NodePositionList<K>();				//Creo un Position List d'appoggio
		for (int i=0; i<capacity; i++)									//finche il mio contatore i è minore della dimensione dell'array
			if ((bucket[i] != null) && (bucket[i] != AVAILABLE))			//se nell'array alla posizione [i] c'è un valore diverso da null e diverso da AVAILABLE
				keys.addLast(bucket[i].getKey());								//nella mia lista aggiungo alla fine la chiave(key) dell'entry corrente
		return keys;
	}

	/**restituisce una collezione iterabile dei valori della Map**/
	public Iterable<V> values() {
		PositionList<V> keys = new NodePositionList<V>();				//Creo un Position List d'appoggio
		for (int i=0; i<capacity; i++)									//finche il mio contatore i è minore della dimensione dell'array
			if ((bucket[i] != null) && (bucket[i] != AVAILABLE))			//se nell'array alla posizione [i] c'è un valore diverso da null e diverso da AVAILABLE
				keys.addLast(bucket[i].getValue());								//nella mia lista aggiungo alla fine il valore(value) dell'entry corrente
		return keys;
	}

	/**restituisce una collezione iterabile delle entry (k,v) della Map**/
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> keys = new NodePositionList<Entry<K,V>>();				//Creo un Position List d'appoggio
		for (int i=0; i<capacity; i++)													//finche il mio contatore i è minore della dimensione dell'array
			if ((bucket[i] != null) && (bucket[i] != AVAILABLE))							//se nell'array alla posizione [i] c'è un valore diverso da null e diverso da AVAILABLE
				keys.addLast(bucket[i]);															//nella mia lista aggiungo alla fine la entry
		return keys;
	}
	
	/**Metodo simile al toString per stampare le entry**/
	public void ShowBucket(){
		for (int i=0; i<capacity; i++)
		System.out.println("bucket[" + i + "]= " + bucket[i]);
	}
	
	/**Metodo simile al toString che stampa tutti il valore hash di ogni singola entry**/
	public void ShowHashVaue(){
		for (K k :keys())
		System.out.println("hashValue("+ k + ")=" + hashValue(k));
	}
	
	/**Metodo per contare quante Entry anno lo stesso valore hash di key**/
	public int ClusterSize(K key){
		int conta=0;
		for (int i=0; i<capacity; i++){
			if(this.hashValue(bucket[i].getKey())==this.hashValue(key))			//chiama hashValue sulla key di ogni Entry contenuta nel bucket array e la confronta con il valore ottenuto dalla funzione hash calcolata sulla key passata in input
				conta++;																//se è uguale incrementa variabile di conteggio
			}
		return conta;
	}

}
