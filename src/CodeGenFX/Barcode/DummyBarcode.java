package CodeGenFX.Barcode;

import CodeGenFX.IBarcode;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.AnchorPane;

/**
 * A dummy barcode implementation
 */
public class DummyBarcode implements IBarcode {
	/**
	 * runs the current barcode generator
	 * @return generated barcode as Image
	 */
	@Override
	public Image runGenerator() {
		
		return new Image("http://docs.oracle.com/javafx/" +
				               "javafx/images/javafx-documentation.png");
	}
	
	/**
	 * gets the properties mandatory to be implemented
	 * @return A anchorPane that contains controls which set the properties
	 * (examples in EAN13 & EAN8 class)
	 */
	@Override
	public AnchorPane mandatoryProperties() {
		
		return new AnchorPane(new Label("Select valid barcode type"));
	}
	
	@Override
	public String toString(){
		
		return "<Select barcode type>";
	}
	// TODO: implement Interface
}
