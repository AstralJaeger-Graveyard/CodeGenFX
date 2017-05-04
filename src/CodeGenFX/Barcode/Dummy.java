package CodeGenFX.Barcode;

import CodeGenFX.Controller;
import CodeGenFX.IBarcode;
import CodeGenFX.Main;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.beans.EventHandler;

/**
 * A dummy barcode implementation
 */
public class Dummy implements IBarcode {
	/**
	 * runs the current barcode generator
	 * @return generated barcode as Image
	 */
	@Override
	public Image runGenerator() {
		
		return new Image("https://www.wpclipart.com/signs_symbol/business/barcodes/barcode_UPC-A.png");
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
		
		return "Dummy";
	}
	// TODO: implement Interface
}
