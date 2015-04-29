package Map;

import PriorityQueue.Entry;
import PriorityQueue.InvalidKeyException;

/**Il TDA map � un contenitore di oggetti (entry) che sono coppie chiave valore(K,V) NON sono conesentite chiavi uguali**/
public interface Map<K,V> {
	
	/**Restituisce la dimensione della mappa**/
	public int size();
	
	/**Restituisce true se la PQ � vuota altrimenti false**/
	public boolean isEmpty();
	
	/**Inserisce l�entry (k, v) nella mappa M. Se la chiave k non � gi� presente in M, restituisce null; altrimenti restituisce il vecchio valore associato a k [sostituisce]**/
	public V put(K key, V value) throws InvalidKeyException;
	
	/**Se la mappa M ha l�entry con chiave k, restituisce il valore associato, altrimenti null.**/
	public V get(K key) throws InvalidKeyException;
	
	/**Se nella mappa M c�� un entry con chiave k, rimuove l�entry con chiave k e restituisce il valore associato. Altrimenti restituisce null.**/
	public V remove(K key) throws InvalidKeyException;
	
	/**restituisce una collezione iterabile delle chiavi di M**/
	public Iterable<K> keys();
	
	/**restituisce una collezione iterabile dei valori di M**/
	public Iterable<V> values();
	
	/**restituisce una collezione iterabile delle entry (k,v) di M**/
	public Iterable<Entry<K,V>> entries();
}