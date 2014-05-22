/**
 * 
 */
package pl.im.gmd.model;

/**
 * @author Igor
 * 
 */
public class MissingSettingException extends Exception {

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
