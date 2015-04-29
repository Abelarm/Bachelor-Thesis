package NodeList;

@SuppressWarnings("serial")
public class NoSuchElementException extends RuntimeException {

	public NoSuchElementException(){
		super();
	}
	
	public NoSuchElementException(String message){
		super(message);
	}
}
