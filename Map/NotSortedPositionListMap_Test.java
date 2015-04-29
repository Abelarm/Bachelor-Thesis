package Map;

import java.util.Iterator;

public class NotSortedPositionListMap_Test {


	public static void main(String[] args) {
		NotSortedPositionListMap<Integer, Integer> X = new NotSortedPositionListMap<Integer,Integer>();
		
		System.out.println(X.isEmpty());
		System.out.println(X.size());

		X.put(0, 1);
		X.put(1, 2);
		X.put(2,3);
		X.put(3,4);
		
		System.out.println(X.isEmpty());
		System.out.println(X.size());
		
		Iterator<Integer> stampa= X.values().iterator();
		
		while(stampa.hasNext())
			System.out.println(stampa.next());
	
			
		System.out.println(X.put(0,10));
		
		System.out.println(X.get(3));
		
		System.out.println(X.remove(10));
		System.out.println(X.remove(2));
	
	
	
	}
	
	

}
