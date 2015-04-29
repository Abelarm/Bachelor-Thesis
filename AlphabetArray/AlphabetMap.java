package AlphabetArray;

import java.util.Iterator;
import Graph.Edge;
import Map.HashTableMap;
import Map.Map;
import NodeList.NodePositionList;

public class AlphabetMap implements Iterable<Edge<String>> {

	private Map<String,Edge<String>> mappa;
	
	
	public AlphabetMap(){
		
		mappa=new HashTableMap<String,Edge<String>>();
	}
	
	public void put(Edge<String> e){
		mappa.put(e.element(), e);
		
	}
	
	public Edge<String> get(String s){	
		if(mappa.get(s)==null)
			return null;
		else{
			return mappa.get(s);
		}
	}
	
	public Edge<String> remove(Edge<String> e){
		if(this.get(e.element())==null)
			return null;
		else{
			Edge<String> toreturn=this.get(e.element());
			mappa.remove(e.element());
			return toreturn;
		}
	}
	
	public int size(){
		return mappa.size();
	}
	
	public Iterator<Edge<String>> iterator() {
		NodePositionList<Edge<String>> it= new NodePositionList<Edge<String>>();
		Iterable<Edge<String>> list= mappa.values();
		for(Edge<String> x: list){
			
			if(x!=null)
				it.addLast(x);
		}
		return it.iterator();
			
	}
	
}
