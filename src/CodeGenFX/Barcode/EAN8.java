package CodeGenFX.Barcode;

import CodeGenFX.IBarcode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;
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
	private Color foreground = Color.BLACK;
	private Color background = Color.WHITE;
	private Color debugMarker = Color.PURPLE;
	
	private TextField dataInput;
	private ToggleGroup digitsTG;
	private RadioButton digits7;
	private RadioButton digits8;
	private ToggleGroup modeTG;
	private RadioButton strict;
	private RadioButton ignore;
	private CheckBox retardMode;
	private CheckBox debugMode;
	private ColorPicker foregroundColor;
	private ColorPicker backgroundColor;
	
	private static float SCALE = 1.00f;
	private static final float MINSCALE = 0.80f;
	private static final float MAXSCALE = 2.00f;
	private static final float WIDTH = 22.11f;
	private static final float MARGIN = 4.62f;
	private static final float HEIGTH = 21.31f;
	
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
	
	//region Render barcode
		
		Image barcode = renderBarcode(rawData);
		
	//endregion
		
		return barcode;
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
		pane.setPadding(small);
		pane.getChildren().add(new Label("Data"));
		
		dataInput = new TextField();
		pane.getChildren().add(dataInput);
		
		//region Digit, Mode, Scale, Color & Optional settings
		
		GridPane settingsGrid = new GridPane();
		settingsGrid.setHgap(5);
		settingsGrid.setVgap(5);
		
		//region Digit settings
		
		TitledPane digitPane = new TitledPane();
		digitPane.setCollapsible(false);
		digitPane.setText("Digts");
		digitPane.setPadding(small);
		
		GridPane digitGrid = new GridPane();
		digitGrid.setHgap(5);
		digitGrid.setVgap(5);
		
		digits7 = new RadioButton("7 Digits");
		digitGrid.add(digits7, 0, 0);
		
		digits8 = new RadioButton("8 Digits");
		digitGrid.add(digits8, 0, 1);
		
		digitsTG = new ToggleGroup();
		digitsTG.getToggles().addAll(digits7, digits8);
		digits7.setSelected(true);
		
		digitPane.setContent(digitGrid);
		settingsGrid.add(digitPane, 0, 0);
		//endregion
		
		//region Mode settings
		
		TitledPane modePane = new TitledPane();
		modePane.setCollapsible(false);
		modePane.setText("Digts");
		modePane.setPadding(small);
		
		GridPane modeGrid = new GridPane();
		modeGrid.setHgap(5);
		modeGrid.setVgap(5);
		
		ignore = new RadioButton("Ignore");
		modeGrid.add(ignore, 0, 0);
		
		strict = new RadioButton("Strict");
		modeGrid.add(strict, 0, 1);
		
		modeTG = new ToggleGroup();
		modeTG.getToggles().addAll(ignore, strict);
		modeTG.getToggles().get(0).setSelected(true);
		
		modePane.setContent(modeGrid);
		settingsGrid.add(modePane, 1,0);
		
		//endregion
		
		//region Size settings

		TitledPane sizeSettings = new TitledPane();
		sizeSettings.setText("Size");
		sizeSettings.setCollapsible(false);
		sizeSettings.setPadding(small);
		
		GridPane sizeGrid = new GridPane();
		
		List<Double> scales = new ArrayList<>();
		
		double stp = 0.05;
		
		for(double d = MINSCALE; d <= (MAXSCALE + stp); d += stp){
			
			scales.add(Math.round(d * 100.0) / 100.0);
		}
		
		sizeGrid.add(new Label("Scale: "), 0, 0);
		
		ComboBox<Double> scaleSelector = new ComboBox<>(FXCollections.observableList(scales));
		scaleSelector.setValue(1.00d);
		
		sizeGrid.add(scaleSelector, 1, 0);
		sizeSettings.setContent(sizeGrid);
		settingsGrid.add(sizeSettings, 0, 1);
		
		//endregion
		
		//region Color settings
		
		TitledPane colorSettings = new TitledPane();
		colorSettings.setText("Color");
		colorSettings.setCollapsible(false);
		colorSettings.setPadding(small);
		
		GridPane colorGrid = new GridPane();
		colorGrid.setHgap(5);
		colorGrid.setVgap(5);
		
		colorGrid.add(new Label("Foreground: "), 0, 0);
		colorGrid.add(new Label("Background"), 0, 1);
		
		foregroundColor = new ColorPicker(Color.BLACK);
		backgroundColor = new ColorPicker(Color.WHITE);
		
		Circle foregroundDemonstrator = new Circle(25, foregroundColor.getValue());
		Circle backgroundDemonstrator = new Circle(25, backgroundColor.getValue());
		foregroundDemonstrator.setStroke(Color.BLACK);
		backgroundDemonstrator.setStroke(Color.BLACK);
		
		foregroundColor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				foregroundDemonstrator.setFill(foregroundColor.getValue());
			}
		});
		
		backgroundColor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				backgroundDemonstrator.setFill(backgroundColor.getValue());
			}
		});
		
		colorGrid.add(foregroundColor, 1, 0);
		colorGrid.add(backgroundColor, 1, 1);
		
		colorGrid.add(foregroundDemonstrator, 2, 0);
		colorGrid.add(backgroundDemonstrator, 2, 1);
		
		colorSettings.setContent(colorGrid);
		settingsGrid.add(colorSettings, 1, 1);
		
		//endregion
		
		//region Optionals
		TitledPane optionals = new TitledPane();
		optionals.setPadding(small);
		optionals.setText("Optional Settigns");
		optionals.setCollapsible(false);
		
		GridPane optionalsGrid = new GridPane();
		optionalsGrid.setVgap(5);
		optionalsGrid.setHgap(5);
		
		retardMode = new CheckBox("Strike trough");
		optionalsGrid.add(retardMode, 0, 0);
		
		debugMode = new CheckBox("Debug mode");
		optionalsGrid.add(debugMode, 0, 1);
		
		optionals.setContent(optionalsGrid);
		
		settingsGrid.add(optionals, 0, 2);
		
		//endregion
		
		pane.getChildren().add(settingsGrid);
		
		//endregion
		
		return pane;
	}
	
	@Override
	public String toString(){
		
		return "EAN 8";
	}
	
	//region size definition
//
//	private static final Map<Double, EANDim> dimensions = new TreeMap<Double, EANDim>(){{
//
//		put(0.80, new EANDim(17.69, 21.38, 17.05));
//		put(0.85, new EANDim(18.79, 22.72, 18.11));
//		put(0.90, new EANDim(19.90, 24.06, 19.18));
//		put(0.95, new EANDim(21.00, 25.39, 20.24));
//		put(1.00, new EANDim(22.11, 26.73, 21.31));
//		put(1.05, new EANDim(23.22, 28.73, 22.38));
//		put(1.10, new EANDim(24.32, 29.40, 23.44));
//		put(1.15, new EANDim(25.43, 30.74, 24.51));
//		put(1.20, new EANDim(26.53, 30.74, 25.57));
//		put(1.25, new EANDim(27.64, 33.41, 26.64));
//		put(1.30, new EANDim(00.00, 00.00, 00.00));
//		put(1.35, new EANDim(00.00, 00.00, 00.00));
//		put(1.40, new EANDim(00.00, 00.00, 00.00));
//		put(1.45, new EANDim(00.00, 00.00, 00.00));
//		put(1.50, new EANDim(00.00, 00.00, 00.00));
//		put(1.55, new EANDim(00.00, 00.00, 00.00));
//		put(1.65, new EANDim(00.00, 00.00, 00.00));
//		put(1.70, new EANDim(00.00, 00.00, 00.00));
//		put(1.75, new EANDim(00.00, 00.00, 00.00));
//		put(1.80, new EANDim(00.00, 00.00, 00.00));
//		put(1.85, new EANDim(00.00, 00.00, 00.00));
//		put(1.90, new EANDim(00.00, 00.00, 00.00));
//		put(1.95, new EANDim(00.00, 00.00, 00.00));
//		put(2.00, new EANDim(00.00, 00.00, 00.00));
//	}};
//
//	private static class EANDim {
//
//		public final double MARGIN;
//		public final double HEIGTH;
//		public final double WIDTH;
//
//		public EANDim(double margin, double width, double heigth, boolean isFinal){
//
//			this.MARGIN = margin;
//			this.HEIGTH = heigth;
//			this.WIDTH = width;
//		}
//
//		public EANDim(double w, double wm, double h){
//
//			this.MARGIN = wm - w ;
//			this.WIDTH = w;
//			this.HEIGTH = h;
//		}
//	}
//endregion
}
