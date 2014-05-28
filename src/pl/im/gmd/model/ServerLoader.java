/**
 * 
 */
package pl.im.gmd.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor
 * 
 */
public class ServerLoader {

	private List<ProxyServer> servers;

	public ServerLoader(String path)
			throws WrongProxyServerFileStructureException,
			FileNotFoundException, IOException {
		servers = new ArrayList<ProxyServer>();
		parseFile(path);
	}

	private void parseFile(String path)
			throws WrongProxyServerFileStructureException,
			FileNotFoundException, IOException {
		File file = new File(path);
		BufferedReader input = new BufferedReader(new FileReader(file));
		String line;
		String[] serverData;
		int lineNumber = 0;
		while ((line = input.readLine()) != null) {
			++lineNumber;
			serverData = line.split(":", 2);
			if (!isValidIp4Address(serverData[0])
					|| !isValidPort(serverData[1])) {
				input.close();
				throw new WrongProxyServerFileStructureException(
						"Wrong file structure on line " + lineNumber);
			}
			servers.add(new ProxyServer(serverData[0], serverData[1]));
		}
		input.close();
	}

	private boolean isValidPort(String portToValidate) {
		try {
			int port = Integer.parseInt(portToValidate);
			if(port < 0 || port > 65535) {
				return false;
			}
			return true;
		} catch (NumberFormatException error) {
			return false;
		}
	}

	public boolean isValidIp4Address(String ipToValidate) {
		try {
			if (ipToValidate == null || ipToValidate.isEmpty()) {
				return false;
			}

			String[] parts = ipToValidate.split("\\.");
			if (parts.length != 4) {
				return false;
			}

			for (String s : parts) {
				int i = Integer.parseInt(s);
				if ((i < 0) || (i > 255)) {
					return false;
				}
			}
			if (ipToValidate.endsWith(".")) {
				return false;
			}

			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public List<ProxyServer> getProxyServers() {
		return servers;
	}
}
