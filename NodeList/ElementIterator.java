package NodeList;

import java.util.Iterator;

import position.Position;

/**Iteratore di una positionList(cioè una classe che si scorre la nostra position list)**/
public class ElementIterator<E> implements Iterator<E> {		
	protected PositionList<E> list;		// la lista di appoggio
	protected Position<E> cursor;		//il cursore che rappresenta il next element
	
	/**Costruttore dell'iteratore**/
	public ElementIterator(PositionList<E> L){				//Costruttore prende come parametro una PositionList
		list=L;												//Nella lista di appoggio assegna la lista passata in input
		cursor = (list.isEmpty())? null : list.first();		//Se list.isEmpty() ritorna true setta il cursor=null altrimenti setta il cursor=list.first() (primo elemento)
	}
		
	
	/**Metodo per controllare se il cursore è uguale a null(cioè non vi sono più elementi)**/
	public boolean hasNext() {		//Restituisce true se cursor è diverso da null altrimenti restituisce false
		return (cursor!=null);
	}

	/**Metodo per restituire l'elemento puntato e far avanzare il cursore**/
	public E next()	throws NoSuchElementException {		//Restituisce l'elemento contenuto nel cursore e avanza il cursore al successivo
		if(cursor==null)     throw new NoSuchElementException("no next element");		//Se il cursore è uguale lancia l'ececzione	
		E toReturn =cursor.element();		//in una variabile d'appoggio inserisce l'elemento contenuto nella position puntata da cursor
		cursor=(cursor==list.last()? null : list.next(cursor));		//Avanzamento di cursor //Se cursor è uguale a l'ultimo elemento restituisce null, altrimenti il cursore va all'elemento successivo
		return toReturn;	//ritorna l'elemento messo nella variabile d'appoggio
	}

	/**Metodo che fa parte dell'interfaccia Iterator, ma che non non implementiamo**/
	public void remove() {
		// TODO Auto-generated method stub

	}

}
