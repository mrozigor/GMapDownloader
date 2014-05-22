/**
 * 
 */
package pl.im.gmd.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * @author Igor
 *
 */
public class ServerLoader {
	
	private List<ProxyServer> servers;

	public ServerLoader(String path) throws WrongProxyServerFileStructureException, FileNotFoundException, IOException {
		parseFile(path);
	}

	private void parseFile(String path) throws WrongProxyServerFileStructureException, FileNotFoundException, IOException {
		File file = new File(path);
		BufferedReader input = new BufferedReader(new FileReader(file));
		String line;
		while((line = input.readLine()) != null) {
			
		}
	}

	public List<ProxyServer> getProxyServers() {
		return servers;
	}
}
