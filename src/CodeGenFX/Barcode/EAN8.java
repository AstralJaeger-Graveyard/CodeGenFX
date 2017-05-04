package CodeGenFX.Barcode;

import CodeGenFX.IBarcode;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;
import java.util.TreeMap;

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
	
	private static final String START_MARKER = "101";
	private static final String END_MARKER = "101";
	private static final String SEPARATOR = "01010";
	
	private static final Map<Integer, String> L_CODE = new TreeMap<Integer, String>(){{
		
		put(0, "000000");
		put(1, "001011");
		put(2, "001101");
		put(3, "010011");
		put(4, "010011");
		put(5, "011001");
		put(6, "011100");
		put(7, "010101");
		put(8, "010110");
		put(9, "011010");
	}};
	
	private static final Map<Integer, String> R_CODE = new TreeMap<Integer, String>(){{
		
		put(0, "1110010");
		put(1, "1100110");
		put(2, "1101100");
		put(3, "1000010");
		put(4, "1011100");
		put(5, "1001110");
		put(6, "1010000");
		put(7, "1000100");
		put(8, "1001000");
		put(9, "1110100");
	}};
	
	/**
	 * runs the current barcode generator
	 *
	 * @return generated barcode as Image
	 */
	@Override
	public Image runGenerator() throws BarcodeException {
		
		collectSettings();
		resetSettings();
		
		System.out.println("Starting EAN 8 Generator");
		System.out.println("Information collected: ");
		System.out.println("> Data: " + data);
		System.out.println("> Strict: " + stict);
		System.out.println("> Digits: " + digits);
		
	//region Check if data is valid
		
		int validityLevel = isValid(data);
		System.out.println("> Valid: " + validityLevel);
		
		if(validityLevel != 0){
			
			String msg = "";
			
			switch(validityLevel) {
				case 1:
					msg = "Barcode length is invalid! Length needs to be " + digits + " digits";
					break;
					
				case 2:
					msg = "Barcode length need to be at lest " + digits + "digits";
					break;
					
				case 3:
					msg = "Barcode contains invalid character. EAN 8 may only contain Numbers";
					break;
					
				default:
					break;
			}
		
		if(! msg.equals("")) {
			
			throw new BarcodeException(msg);
		}
	}
	//endregion
	
	//region Calculate checksum if applicable
		String refData = data.substring(0, digits);
		
		if(digits != 8){
			
			refData += checksum(refData);
		}
		
		System.out.println("> Ref. data: " + refData);
	//endregion
	
	//region Generate raw data string
		
		String rawData = generateRaw(refData);
		System.out.println("> Raw: " + rawData);
	//endregion
	
	//region Render Barcode
		// TODO: render barcode
		
	//endregion
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
	 * @return
	 * 0 if valid,
	 * 1 if lenght invalid in strict mode,
	 * 2 if length invalid in ignore mode,
	 * 3 if data contains non numerical character
	 */
	private int isValid(String data){
		
		if(stict && (data.length() != digits)){
			
			return 1;
		}
		
		if((!stict) && (data.length() < digits)){
			
			return 2;
		}
		
		for(char c : data.toCharArray()){
			
			if(!Character.isDigit(c)){
				
				return 3;
			}
		}
		
		return 0;
	}
	
	/**
	 * calculates checksum for EAN8
	 * @param data to calulate checksum from
	 * @return checksum
	 */
	private int checksum(String data){
		
		StringBuilder reversed = new StringBuilder();
		reversed.append(data);
		reversed.reverse();
		data = reversed.toString();
		
		int mul = 3;
		int sum = 0;
		
		for(int i = 0; i < data.length(); i++){
			
			sum += Integer.parseInt("" + data.charAt(i)) * mul;
			mul = (mul == 3) ? 1 : 3;
		}
		
		int nextMulOf10 = (sum + 9) - ((sum + 9) % 10);
		
		return nextMulOf10 - sum;
	}
	
	/**
	 * Generates raw data of style "1010011001[...]101"
	 * @param data to generate raw from
	 * @return raw data
	 */
	private String generateRaw(String data){
		
		StringBuilder rawData = new StringBuilder();
		rawData.append(START_MARKER);
		
		for(int i = 0; i < data.length()/2; i++){
			
			rawData.append(L_CODE.get(Integer.parseInt("" + data.charAt(i))));
		}
		
		rawData.append(SEPARATOR);
		
		for(int i = data.length()/2; i < data.length(); i++){
			
			rawData.append(R_CODE.get(Integer.parseInt("" + data.charAt(i))));
		}
		
		rawData.append(END_MARKER);
		return rawData.toString();
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
