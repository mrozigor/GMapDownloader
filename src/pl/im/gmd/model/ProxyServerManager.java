/**
 * 
 */
package pl.im.gmd.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author Igor
 * 
 */
public class ProxyServerManager {
	// TODO If there isn't list with proxys, download as usual.
	// TODO List of proxy's, list of used proxy's.
	private List<ProxyServer> serverList = null;

	public ProxyServerManager(String path) {
		if (path == "none") {
			serverList = null;
		} else {
			// TODO Loading and parsing file
			// TODO Initializing list
		}
	}

	public URL getConnection(String tileUrl) throws MalformedURLException {
		URL connection;
		if (serverList == null) {
			connection = new URL(tileUrl);
			return connection;
		} else {
			// TODO Return random server from list
			return null;
		}
	}
}
