package Map;

import PriorityQueue.Entry;

/**Implementazione di Entry con l'aggiunta del metodo per controllare se due entry sono uguali**/
public class HashEntry<K,V> implements Entry<K,V> { 
	protected K key; 
	protected V value; 
	
	/**Crea un costruttore, di Entry**/
	public HashEntry(K k, V v) { 
		key = k; 
		value = v; 
		} 
	
	/**Ritorna il valore della entry**/
	public V getValue() { 
		return value; 
		} 
	
	/**Ritorna la key della entry**/
	public K getKey() { 
		return key; 
		} 
	
	/**Setta il nuovo valore e ritorna il vecchio**/
	public V setValue(V val) { 
		V oldValue = value; 
		value = val; 
		return oldValue; 
		} 
	
	/**Metodo per controllare l'uguaglianza tra due entry**/
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) { 
	HashEntry<K,V> ent; 
	try {									//in un blocco try
		ent = (HashEntry<K,V>) o; 					//casta l'object a HashEntry
		} 
	catch (ClassCastException ex) { 		//se viene lanciata l'eccezione il cathc la cattura
		return false; 								//restituisce falso
		} 
	return (ent.getKey() == key) && (ent.getValue() == value);  //altrimenti restituisce vero se la key e il value dell'objecet passato in input (castato) sono uguali alla key e alla value altriemnti false
	}
}