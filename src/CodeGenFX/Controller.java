package CodeGenFX;

import CodeGenFX.Barcode.DummyBarcode;
import CodeGenFX.Barcode.DummyInvalid;
import CodeGenFX.Barcode.DummyWorking;
import CodeGenFX.Barcode.EAN8;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
	
	
	//region Private fields
	
	private ObservableList<IBarcode> barcodeTypes;
	private IBarcode iBarcode;
	
	//endregion
	
	//region GUI Objects
	
	// MenuBar
	
	// Menu - File
	@FXML
	private MenuItem newFile;
	@FXML
	private MenuItem saveFile;
	@FXML
	private MenuItem saveAsFile;
	@FXML
	private MenuItem quitFile;
	
	// Menu - Edit
	// TODO: Evaluate needed functions and controls for this menu
	
	// Menu - Help
	@FXML
	private MenuItem about;
	
	
	// SplitPane
	@FXML
	private SplitPane splitPane;
	
	// Accordion
	// TitledPane - Barcode
	@FXML
	private ComboBox<IBarcode> iBarcodeComboBox;
	@FXML
	private Button generate;
	
	// TitledPane - Configuration
	@FXML
	private AnchorPane configuration;
	
	// TitledPane - Export Settings
	// TODO: Evaluate needed functions and controls for this menu
	
	// ImageView - Barcode PreView
	@FXML
	private AnchorPane iBarcodePreviewContainer;
	@FXML
	private ImageView iBarcodePreview;
	
	//endregion
	
	
	/**
	 * Contructor w/o args
	 */
	public Controller() {
	
	}
	
	/**
	 * Sets barcodetypes to combobox and sets events up
	 */
	public void setData() {
		
		barcodeTypes = FXCollections.observableArrayList(
				new DummyBarcode(),
				// TODO: Add barcode types here (must implement iBarcode)
//				new DummyWorking(),
//				new DummyInvalid(),
		      new EAN8()
		                                                );
		
		iBarcodeComboBox.setItems(barcodeTypes);
		iBarcodeComboBox.getSelectionModel().select(0);
		
		// Prepair for first time
		iBarcode = iBarcodeComboBox.getValue();
		configuration.getChildren().clear();
		configuration.getChildren().add(iBarcode.mandatoryProperties());
		generate.setDisable(true);
		
		iBarcodeComboBox.valueProperty().addListener(new ChangeListener<IBarcode>() {
			@Override
			public void changed(
					ObservableValue<? extends IBarcode> observable,
					IBarcode oldValue, IBarcode newValue) {
				
				iBarcode = newValue;
				
				if(newValue.toString().equals("<Select barcode type>")) {
					
					generate.setDisable(true);
				}
				else {
					
					generate.setDisable(false);
				}
				
				Node properties = iBarcode.mandatoryProperties();
				
				configuration.getChildren().clear();
				configuration.getChildren().add(properties);
				
				System.out.println("Current iBarcode: " + newValue);
			}
		});
		
		generate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				try {
					
					Image barcode = iBarcode.runGenerator();
					iBarcodePreview.setImage(barcode);
				}
				catch(Exception ex) {
					
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("An exception has occurred");
					alert.setHeaderText("An exception has occurred when attempted to generate" +
							                    " a barcode of type: " + iBarcode);
					alert.setContentText(ex.toString());
					alert.show();
				}
				
			}
		});
		
		iBarcodePreview.fitHeightProperty().bind(iBarcodePreviewContainer.heightProperty());
		iBarcodePreview.fitWidthProperty().bind(iBarcodePreviewContainer.heightProperty());
		
	}
	
	/**
	 * Called to initialize a controller after its root element has been
	 * completely processed.
	 *
	 * @param location  The location used to resolve relative paths for the root
	 *                  object, or
	 *                  <tt>null</tt> if the location is not known.
	 * @param resources The resources used to localize the root object, or
	 *                  <tt>null</tt> if
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}
}
