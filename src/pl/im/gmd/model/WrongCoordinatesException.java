/**
 * 
 */
package pl.im.gmd.model;

/**
 * @author Igor
 *
 */
public class WrongCoordinatesException extends Exception {
	
	private static final long serialVersionUID = 7377024707458232174L;
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
