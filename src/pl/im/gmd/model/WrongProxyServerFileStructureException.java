/**
 * 
 */
package pl.im.gmd.model;

/**
 * @author Igor
 * 
 */
public class WrongProxyServerFileStructureException extends Exception {

	private String message = null;

	public WrongProxyServerFileStructureException() {
		this(null);
	}

	public WrongProxyServerFileStructureException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
