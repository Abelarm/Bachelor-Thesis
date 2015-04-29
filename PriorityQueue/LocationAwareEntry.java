package PriorityQueue;

import position.Position;
/**Tipo di Entry particolare che estende MyEntry 
 * ed in più ha un elemento al suo intenro che è un puntaotore a position di entry**/
public class LocationAwareEntry<K, V> extends MyEntry<K, V> {
	Position<Entry<K,V>> pos;
	
	/**Costruttore che invoca il costruttore della superclase**/
	public LocationAwareEntry(K k, V v) {
		super(k, v);
	}
	
	/**Metodo per restituire la position all'interno del nostro dato**/
	public Position<Entry<K,V>> getPos(){
		return pos;
	}
	
	/**Metodo per setta la position all'interno del nostro dato**/
	public void SetPos(Position<Entry<K,V>> x){
		pos=x;
	}
	

}
