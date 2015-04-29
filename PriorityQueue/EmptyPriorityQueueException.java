package PriorityQueue;

@SuppressWarnings("serial")
public class EmptyPriorityQueueException extends RuntimeException {
	
	public EmptyPriorityQueueException(){
		super();
	}
	
	public EmptyPriorityQueueException(String x){
		super(x);
	}

}
