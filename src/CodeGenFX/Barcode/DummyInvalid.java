package CodeGenFX.Barcode;

import CodeGenFX.IBarcode;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * A dummy barcode implementation
 */
public class DummyInvalid implements IBarcode {
	
	/**
	 * runs the current barcode generator
	 * @return generated barcode as Image
	 */
	@Override
	public Image runGenerator() throws BarcodeException{
		
		throw new BarcodeException("This barcode-type will always throw this exception");
	}
	
	/**
	 * gets the properties mandatory to be implemented
	 * @return A anchorPane that contains controls which set the properties
	 * (examples in EAN13 & EAN8 class)
	 */
	@Override
	public Node mandatoryProperties() {
		
		VBox pane = new VBox(new Label("Select valid barcode type"));
		
		return pane;
	}
	
	@Override
	public String toString(){
		
		return "Dummy barcode - INVALID";
	}
	// TODO: implement Interface
}
