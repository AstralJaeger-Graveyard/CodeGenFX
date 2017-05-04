package CodeGenFX.Barcode;

import CodeGenFX.IBarcode;
import javafx.scene.Node;
import javafx.scene.image.Image;

/**
 * Generates a not so common EAN8 barcode
 */
public class EAN8 implements IBarcode{
	
	
	
	
	/**
	 * runs the current barcode generator
	 *
	 * @return generated barcode as Image
	 */
	@Override
	public Image runGenerator() throws BarcodeException {
		
		return null;
	}
	
	/**
	 * gets the properties mandatory to be implemented
	 *
	 * @return A anchorPane that contains controls which set the properties
	 * (examples in EAN13 & EAN8 class)
	 */
	@Override
	public Node mandatoryProperties() {
		
		return null;
	}
}
