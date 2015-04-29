package Tesi;

import Graph.Vertex;


/**Classe che viene utilizzat per la creazione del trie */
public class MyEntry {
	
	private String signature;							//signature della entry
	private Vertex<Integer> vertice;					//vertice a cui punta la entry
	
	
	public MyEntry(String s, Vertex<Integer> v){
		signature=s;
		vertice=v;
	}
	
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public Vertex<Integer> getVertice() {
		return vertice;
	}
	public void setVertice(Vertex<Integer> vertice) {
		this.vertice = vertice;
	}

}
