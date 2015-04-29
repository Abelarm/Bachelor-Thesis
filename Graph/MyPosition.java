package Graph;

import Map.HashTableMap;

/**E' una position, che ha un elemento e una mappa associata ad esso,
 * implementa decorable position per avere questa struttura ed estende hashtable map, per avaere 
 * i metodi della mappa già implementati**/
public class MyPosition<T> extends HashTableMap<Object, Object>  implements DecorablePosition<T> {
	protected T element;
	
	@Override
	public T element() {
		return element;
	}
	
	/** Imposta l'elemento in questa posizione. */
	public void setElement(T o) {
	element = o;
	}

}
