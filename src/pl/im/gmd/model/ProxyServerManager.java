/**
 * 
 */
package pl.im.gmd.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @author Igor
 * 
 */
public class ProxyServerManager {
	// TODO List of proxy's, list of used proxy's.
	private List<ProxyServer> serverList = null;

	public ProxyServerManager(String path) throws WrongProxyServerFileStructureException, FileNotFoundException, IOException {
		if (path == "none") {
			serverList = null;
		} else {
			ServerLoader loader = new ServerLoader(path);
			serverList = loader.getProxyServers();
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
