package NodeList;

import position.Position;

/**TDA per immagazinare Position(di tipo DNode(doppiamente linkata)) e che estende iterable(quindi avrà il metodo .iterator())**/
public interface PositionList<E> extends Iterable<E>{
	
	/**Restituisce la dimensione della struttura**/
	public int size();   
	
	/**Controlla se è vuota**/
	public boolean isEmpty(); //
	
	/**Restituisce la prima Position della struttura**/
	public Position<E> first() throws EmptyListException;		
	
	/**Restituisce l'ultima Position della struttura**/
	public Position<E> last() throws EmptyListException;		
	
	/**Restituisce la position successiva a quella passata in input**/
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException;		
	
	/**Restituisce la position precedente a quella passata in input**/
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException;		
	
	/**Crea una position con l'elemento passato e lo inserisce all'interno della struttura in prima posizione**/
	public void addFirst(E e);	
	
	/**Crea una position con l'elemento passato e lo inserisce all'interno della struttura in ultima posizione**/
	public void addLast(E e);	//
	
	/**Crea un position con l'elemento passato e lo inserisce all'interno della struttura dopo la position passta in input**/
	public void addAfter(Position<E> p,E e) throws InvalidPositionException;	
	
	/**Crea un position con l'elemento passato e lo inserisce all'interno della struttura prima della position passta in input**/
	public void addBefore(Position<E> p,E e) throws InvalidPositionException;	
	
	/**Rimuove la position passata in input e restituisce l'elemento precedentemente contenuto**/
	public E remove(Position<E> p) throws InvalidPositionException; 	
	
	/**Sovrascrive l'elemento della position passata, ma ritorna il vecchio elemento contenuto**/
	public E set(Position<E> p, E e) throws InvalidPositionException;	

	/**Metodo che restituisce una lista Iterable di positions**/
	public Iterable<Position<E>> positions();
}
