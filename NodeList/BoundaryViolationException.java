package NodeList;

@SuppressWarnings("serial")
public class BoundaryViolationException extends RuntimeException {
		
	public BoundaryViolationException(){
		super();
	}
	
	public BoundaryViolationException(String message){
		super(message);
	}
}
