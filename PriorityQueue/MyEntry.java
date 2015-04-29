package PriorityQueue;

/**Implementazione dell'interfaccia entry**/
public class MyEntry<K, V> implements Entry<K, V> {
	protected K key;			 // key
	protected V value; 		// value
	
	/**Costruttore**/
	public MyEntry(K k, V v) { 
		key = k; 
		value = v; }
	
	/**Reistuisce la chiave della entry su cui è invocata**/
	public K getKey() {
		return key;
	}

	/**Reistuisce il valore della entry su cui è invocata**/
	public V getValue() {
		return value;
	}
	
	/**Metodo per stampare il contenuto della entry**/
	public String toString() { 
		return "(" + key + "," + value + ")"; 
	}

}
