/**
 * 
 */
package pl.im.gmd.model;

/**
 * @author Igor
 *
 */
public class ProxyServer {
	
	private String ip;
	private int port;

	public ProxyServer(String ip, String port) {
		this.ip = ip;
		this.port = Integer.parseInt(port);
	}

	public int getServerPort() {
		return port;
	}

	public String getServerAddress() {
		return ip;
	}

}
