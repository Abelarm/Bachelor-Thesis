package PriorityQueue;

/**Tipo di dato che è fatto da una coppia di valori
 * **/
public interface Entry<K,V> {
	
	/**Restituisce il valore della key**/
	public K getKey();
	
	/**Restituisce il valore**/
	public V getValue();

	
}
