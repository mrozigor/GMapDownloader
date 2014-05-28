/**
 * 
 */
package pl.im.gmd.model;

/**
 * @author Igor
 * 
 */
public class WrongProxyServerFileStructureException extends Exception {

	private static final long serialVersionUID = 28022075402647321L;
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
