package CodeGenFX.Barcode;

import CodeGenFX.IBarcode;
import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * Generates a not so common EAN8 barcode
 */
public class EAN8 implements IBarcode{
	
	private String data;
	
	private int digits;
	
	private boolean retardMode;
	
	private boolean stictMode;
	
	/**
	 * runs the current barcode generator
	 *
	 * @return generated barcode as Image
	 */
	@Override
	public Image runGenerator() throws BarcodeException {
		
		// TODO: Check if data is valid
		
		// TODO: if applicable calculate checksum
		
		// TODO: generate raw data string
		
		// TODO: redner barcode
		
		return null;
	}
	
	//region Internal methods
	
	/**
	 * Collects the needed information from the corresponding controls
	 */
	private void collectSettings(){
	
	
	}
	
	/**
	 * Resets the data textfield
	 */
	private void resetSettings(){
	
	}
	
	/**
	 * Checks weather the given data is valid or invalid
	 * @param data to check
	 * @return valid
	 */
	private boolean isValid(String data){
		
		return false;
	}
	
	/**
	 * calculates checksum for EAN8
	 * @param data to calulate checksum from
	 * @return checksum
	 */
	private int checksum(String data){
		
		
		return 0;
	}
	
	/**
	 * Generates raw data of style "1010011001[...]101"
	 * @param data to generate raw from
	 * @return raw data
	 */
	private String generateRaw(String data){
		
		
		return "";
	}
	
	/**
	 * renders Barcode
	 * @param raw data to generate barcode from
	 * @return barcode image
	 */
	private Image renderBarcode(String raw){
		
		
		return null;
	}
	
	//endregion
	
	/**
	 * gets the properties mandatory to be implemented
	 *
	 * @return A anchorPane that contains controls which set the properties
	 * (examples in EAN13 & EAN8 class)
	 */
	@Override
	public Node mandatoryProperties() {
		
		Insets small = new Insets(5, 5, 5, 5);
		
		VBox pane = new VBox();
		pane.setPadding(new Insets( 10, 10, 10, 10));
		pane.getChildren().add(new Label("Data"));
		
		TextField data = new TextField();
		pane.getChildren().add(data);
		
		//region Digit & Mode settings
		
		//region Digit settings
		
		TitledPane digitPane = new TitledPane();
		digitPane.setCollapsible(false);
		digitPane.setText("Digts");
		digitPane.setPadding(small);
		
		ToggleGroup digits = new ToggleGroup();
		RadioButton calcCheck_7DG = new RadioButton("7 Digits");
		RadioButton calcCheck_8DG = new RadioButton("8 Digits");
		calcCheck_7DG.setToggleGroup(digits);
		calcCheck_8DG.setToggleGroup(digits);
		
		calcCheck_7DG.setSelected(true);
		
		VBox digitVBox = new VBox(calcCheck_7DG, calcCheck_8DG);
		digitVBox.setPadding(small);
		digitPane.setContent(digitVBox);
		//endregion
		
		//region Mode settings
		TitledPane modePane = new TitledPane();
		modePane.setCollapsible(false);
		modePane.setText("Digts");
		modePane.setPadding(small);
		
		ToggleGroup mode = new ToggleGroup();
		RadioButton strict = new RadioButton("Strict mode");
		RadioButton ignore = new RadioButton("Ignore mode");
		strict.setToggleGroup(mode);
		ignore.setToggleGroup(mode);
		
		ignore.setSelected(true);
		
		VBox modeVBox = new VBox(strict, ignore);
		modeVBox.setPadding(small);
		modePane.setContent(modeVBox);
		//endregion
		
		HBox radioBtnPanes = new HBox(digitPane, modePane);
		HBox.setMargin(digitPane, new Insets(5, 0, 5, 0));
		HBox.setMargin(modePane, new Insets(5, 5, 5, 0));
		
		pane.getChildren().add(radioBtnPanes);
		
		//endregion
		
		TitledPane optionals = new TitledPane();
		optionals.setPadding(small);
		optionals.setText("Optional Settigns");
		optionals.setCollapsible(false);
		
		VBox optVBox = new VBox();
		
		CheckBox retardMode = new CheckBox("Strike trough");
		CheckBox debugMode = new CheckBox("Debug mode");
		optVBox.setPadding(small);
		optVBox.getChildren().addAll(retardMode, debugMode);
		optionals.setContent(optVBox);
		
		pane.getChildren().add(optionals);
		
		return pane;
	}
	
	@Override
	public String toString(){
		
		return "EAN 8";
	}
}
