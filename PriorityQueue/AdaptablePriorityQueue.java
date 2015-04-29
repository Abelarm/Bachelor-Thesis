package PriorityQueue;
/**TDA estensione della Priority Queue 
 * che oltre ad avere i metodi interni della PQ ha anche alcuni metodi per modificare le entry**/
public interface AdaptablePriorityQueue<V, K> extends PriorityQueue<K, V> {

	/**sostituisce con k la chiave di e**/
	public void replaceKey(Entry<K,V> entry,K key); 
	
	/**sostituisce il valore di e con x**/
	public void replaceValue(Entry<K,V> entry, V x); 
	
	/**elimina dalla PQ l’entry e (e la restituisce)**/
	public Entry<K,V> remove(Entry<K,V> e); 
}
