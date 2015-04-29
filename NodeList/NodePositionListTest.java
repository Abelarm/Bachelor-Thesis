package NodeList;

import position.Position;

public class NodePositionListTest {

	public static void main(String[] args) {
	
		NodePositionList<Integer> S = new NodePositionList<Integer>();
		
		System.out.println(S.isEmpty());
		
		S.addFirst(5);
		S.addFirst(4);
		S.addFirst(3);
		S.addFirst(2);
		S.addFirst(1);
		S.addLast(6);
		
		System.out.println(S.isEmpty());
		System.out.println(S);
		System.out.println(S.size());

		
		Position<Integer> p5=((DNode<Integer>)S.last()).getPrev();
		
		if(p5==null)
			System.out.println("è vuota");
		
		S.remove(p5);
		
		
		System.out.println(S);
		System.out.println(S.size());
		
		
		

	}

}
