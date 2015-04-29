package AlphabetArray;

import java.util.ArrayList;
import java.util.Iterator;
import Graph.Edge;
import Map.HashTableMap;
import Map.Map;
import NodeList.NodePositionList;

public class SignatureMap implements Iterable<Edge<String>>{

	private Map<String,ArrayList<Edge<String>>> mappa;
	private int size;
	
	public SignatureMap(){
		mappa=new HashTableMap<String,ArrayList<Edge<String>>>();
	}
	
	public void put(Edge<String> e){
		if(mappa.get(e.element())!= null){
			mappa.get(e.element()).add(e);
			size++;
		}else{
			mappa.put(e.element(), new ArrayList<Edge<String>>());
			mappa.get(e.element()).add(e);
		}
	}
	
	public ArrayList<Edge<String>> get(String s){	
		if(mappa.get(s)==null)
			return new ArrayList<Edge<String>>();
		else{
			return mappa.get(s);
		}
	}
	
	public Edge<String> remove(Edge<String> e){
		if(this.get(e.element()).size()==0)
			return null;
		else{
			Edge<String> toreturn=this.get(e.element()).get(0);
			mappa.get(e.element()).remove(e);
			size--;
			return toreturn;
		}
	}
	
	public int size(){
		return size;
	}
	
	public Iterator<Edge<String>> iterator() {
		NodePositionList<Edge<String>> it= new NodePositionList<Edge<String>>();
		Iterable<ArrayList<Edge<String>>> lista = mappa.values();
		for(ArrayList<Edge<String>> x: lista){
			if(x!=null){
				for(Edge<String> y: x){
					it.addLast(y);
				}
			}
		}
		return it.iterator();
			
	}
}
