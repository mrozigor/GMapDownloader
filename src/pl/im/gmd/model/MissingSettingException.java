/**
 * 
 */
package pl.im.gmd.model;

/**
 * @author Igor
 * 
 */
public class MissingSettingException extends Exception {

	private static final long serialVersionUID = 5920730836830149627L;
	private String message = null;

	public MissingSettingException() {
		this(null);
	}

	public MissingSettingException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
