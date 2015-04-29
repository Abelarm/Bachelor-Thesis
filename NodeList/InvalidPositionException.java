package NodeList;

@SuppressWarnings("serial")
public class InvalidPositionException extends RuntimeException {
		
	public InvalidPositionException(){
		super();
	}
	
	public InvalidPositionException(String message){
		super(message);
	}
}
