/**
 * 
 */
package pl.im.gmd;

/**
 * @author Igor
 *
 */
public class Coordinates {
	
	private double borderN = 0;
	private double borderS = 0;
	private double borderW = 0;
	private double borderE = 0;
	
	public void setBorderN(String coordinate) throws WrongCoordinatesException, NumberFormatException {
		borderN = Double.parseDouble(coordinate);
		if(borderN > 90 || borderN < -90) {
			borderN = 0;
			throw new WrongCoordinatesException("Latitude boundary: -90\u00B0 to 90\u00B0.");
		}
		
	}
	
	public void setBorderS(String coordinate) throws WrongCoordinatesException, NumberFormatException {
		borderS = Double.parseDouble(coordinate);
		if(borderS > 90 || borderS < -90) {
			borderS = 0;
			throw new WrongCoordinatesException("Latitude boundary: -90\u00B0 to 90\u00B0.");
		}
		checkIfNIsGreaterThanSAndSwap();
	}
	
	private void checkIfNIsGreaterThanSAndSwap() {
		if(borderS > borderN) {
			double temp = borderS;
			borderS = borderN;
			borderN = temp;
		}
	}

	public void setBorderW(String coordinate) throws WrongCoordinatesException, NumberFormatException {
		borderW = Double.parseDouble(coordinate);
		if(borderW > 180 || borderW < -180) {
			borderW = 0;
			throw new WrongCoordinatesException("Longitude boundary: -180\u00B0 to 180\u00B0.");
		}
	}
	
	public void setBorderE(String coordinate) throws WrongCoordinatesException, NumberFormatException {
		borderE = Double.parseDouble(coordinate);
		if(borderE > 180 || borderE < -180) {
			borderE = 0;
			throw new WrongCoordinatesException("Longitude boundary: -180\u00B0 to 180\u00B0.");
		}
		checkIfEIsGreaterThanWAndSwap();
	}

	private void checkIfEIsGreaterThanWAndSwap() {
		if(borderW > borderE) {
			double temp = borderW;
			borderW = borderE;
			borderE = temp;
		}
	}

	public double getBorderN() {
		return borderN;
	}

	public double getBorderS() {
		return borderS;
	}

	public double getBorderW() {
		return borderW;
	}

	public double getBorderE() {
		return borderE;
	}
}
