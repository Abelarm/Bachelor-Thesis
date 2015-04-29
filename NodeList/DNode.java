package NodeList;

import position.Position;

/**Position particolari che al loro interno oltre ad avere l'elemento hanno due puntatori a DLNode che saranno il successivo è il precedente**/
public class DNode<E> implements Position<E> {
	DNode<E> prev,next;		//Puntatore al nodo successivo e precedente
	E element;				//Elemento nel nodo/position

	/**Costruttore parametrico**/
	public DNode(DNode<E> newPrev, DNode<E> newNext, E eleme){  //Crea un DNode con puntatore al DNode successivo e a quello precedente
		prev=newPrev;
		next=newNext;
		element=eleme;
		
	}
	
	/**Unico metodo dell'interfaccia Position, se uno dei puntatori dell'elemento su cui è invocato è null lancia l'eccezione**/
	public E element() { //Metodo di Position unica differenza con DLNode
		if((prev==null)||(next==null)){			//Controlla i puntatori se sono vuoti vuol dire che l'elemento non è in nessuna lista
			throw new InvalidPositionException("Questa position non è in una lista");
		}
		return element;
	}
	
	/**Restituisce il puntatore al successivo**/
	public DNode<E> getNext(){
		return next;
	}
	
	/**Restituisce il puntatore al precedente**/
	public DNode<E> getPrev(){
		return prev;
	}
	
	/**Setta il puntatore al successivo, con quello passato in input**/
	public void setNext(DNode<E> newNext){ 
		next=newNext;
	}
	
	/**Setta il puntatore al precednte, con quello passato in input**/
	public void setPrev(DNode<E> newPrev){
		prev=newPrev;
	}
	
	/**Setta l'elemento all'interno del DNode**/
	public void setElement(E newElement){   
		element=newElement;
	}
	
	

}
