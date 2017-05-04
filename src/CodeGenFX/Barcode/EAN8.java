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
	private boolean stict;
	private boolean retard;
	private boolean debug;
	
	private TextField dataInput;
	private ToggleGroup digitsTG;
	private RadioButton digits7;
	private RadioButton digits8;
	private ToggleGroup modeTG;
	private RadioButton strict;
	private RadioButton ignore;
	private CheckBox retardMode;
	private CheckBox debugMode;
	
	/**
	 * runs the current barcode generator
	 *
	 * @return generated barcode as Image
	 */
	@Override
	public Image runGenerator() throws BarcodeException {
		
		collectSettings();
		resetSettings();
		
		// TODO: Check if data is valid
		isValid(data);
		
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
	
		data = dataInput.getText();
		
		//region Digit count
		digits = -1;
		if(digitsTG.getSelectedToggle().equals(digits7)){
			
			digits = 7;
		}
		else if(digitsTG.getSelectedToggle().equals(digits8)){
			
			digits = 8;
		}
		//endregion
		
		//region Mode Selection
		if(modeTG.getSelectedToggle().equals(strict)){
			
			stict = true;
		}
		else if(modeTG.getSelectedToggle().equals(ignore)){
			
			stict = false;
		}
		//endregion
		
		retard = retardMode.isSelected();
		debug = debugMode.isSelected();
		
	}
	
	/**
	 * Resets the data textfield
	 */
	private void resetSettings(){
	
		dataInput.setText("");
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
		
		dataInput = new TextField();
		pane.getChildren().add(dataInput);
		
		//region Digit & Mode settings
		
		//region Digit settings
		
		TitledPane digitPane = new TitledPane();
		digitPane.setCollapsible(false);
		digitPane.setText("Digts");
		digitPane.setPadding(small);
		
		digitsTG = new ToggleGroup();
		digits7 = new RadioButton("7 Digits");
		digits8 = new RadioButton("8 Digits");
		digits7.setToggleGroup(digitsTG);
		digits8.setToggleGroup(digitsTG);
		
		digits7.setSelected(true);
		
		VBox digitVBox = new VBox(digits7, digits8);
		digitVBox.setPadding(small);
		digitPane.setContent(digitVBox);
		//endregion
		
		//region Mode settings
		TitledPane modePane = new TitledPane();
		modePane.setCollapsible(false);
		modePane.setText("Digts");
		modePane.setPadding(small);
		
		modeTG = new ToggleGroup();
		strict = new RadioButton("Strict mode");
		ignore = new RadioButton("Ignore mode");
		strict.setToggleGroup(modeTG);
		ignore.setToggleGroup(modeTG);
		
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
		
		retardMode = new CheckBox("Strike trough");
		debugMode = new CheckBox("Debug mode");
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
