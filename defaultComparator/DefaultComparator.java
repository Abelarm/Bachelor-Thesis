package defaultComparator;

import java.util.Comparator;

public class DefaultComparator<E> implements Comparator<E> {
	
	public DefaultComparator(){}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int compare(E a, E b) throws ClassCastException{
		return ((Comparable)a).compareTo(b);
	}

}