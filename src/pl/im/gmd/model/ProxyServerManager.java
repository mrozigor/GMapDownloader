/**
 * 
 */
package pl.im.gmd.model;

// TODO Add downloading few times, from different proxy's before tile downloading error

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

/**
 * @author Igor
 * 
 */
public class ProxyServerManager {
	private List<ProxyServer> serverList = null;
	private int totalDownloadsSoFar = 0;

	public ProxyServerManager(String path)
			throws WrongProxyServerFileStructureException,
			FileNotFoundException, IOException {
		if (path == "none") {
			serverList = null;
		} else {
			ServerLoader loader = new ServerLoader(path);
			serverList = loader.getProxyServers();
		}
	}

	public URL getConnection(String tileUrl) throws MalformedURLException {
		++totalDownloadsSoFar;
		URL connection;
		if (serverList == null) {
			connection = new URL(tileUrl);
			return connection;
		} else {
			Random generator = new Random();
			int x;
			ProxyServer temp;
			int usabilityFactor = totalDownloadsSoFar/ serverList.size() + serverList.size() / 2;
			do {
				x = generator.nextInt(serverList.size());
				temp = serverList.get(x);
			}
			while (temp.getUsedSoFar() > usabilityFactor);
			connection = new URL("http", temp.getServerAddress(), temp.getServerPort(), tileUrl);
			return connection;
		}
	}
}
