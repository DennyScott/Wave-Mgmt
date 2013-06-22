package exceptions;

public class EmptySetException extends RuntimeException {
	
	public EmptySetException(String msg){
		super(msg);
	}
	public EmptySetException(){
		super("An error has occured in the SQL statement.");
	}
	
	
}
