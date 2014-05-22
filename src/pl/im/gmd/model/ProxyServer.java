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
	private int usedSoFar;

	public ProxyServer(String ip, String port) {
		this.ip = ip;
		this.port = Integer.parseInt(port);
		usedSoFar = 0;
	}

	public int getServerPort() {
		return port;
	}

	public String getServerAddress() {
		++usedSoFar;
		return ip;
	}
	
	public int getUsedSoFar() {
		return usedSoFar;
	}

}
