/**
 * 
 */
package pl.im.gmd;

/**
 * @author Igor
 *
 */
public class WrongCoordinatesException extends Exception {
	
	private String message = null;
	
	public WrongCoordinatesException() {
		this(null);
	}
	
	public WrongCoordinatesException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
