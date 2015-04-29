package NodeList;

import defaultComparator.DefaultComparator;

public class TestMergeSort {
	public static void main(String[] args) {
		DefaultComparator<String> comp = new DefaultComparator<String>();
		NodePositionList<String> S = new NodePositionList<String>();
		S.addLast("a6b2c5");
		S.addFirst("a2b4");
		S.addFirst("a4b2");
		S.addLast("a4d3");
		S.addLast("a4d1");
		System.out.print("Ecco gli elementi nella lista input");
		System.out.println(S.toString());
		System.out.println("Facciamo il mergesort");
		@SuppressWarnings("unused")
		NodePositionList<Integer> output = new NodePositionList<Integer>();
		NodePositionList.mergeSort(S, comp);
		System.out.print("Ecco gli elementi nella lista ordinata ");
		System.out.println(S.toString());
	}
}