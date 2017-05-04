package CodeGenFX;

import CodeGenFX.Barcode.DummyBarcode;
import CodeGenFX.Barcode.Dummy;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
	
	
	//region Private fields
	
	private ObservableList<IBarcode> barcodeTypes;
	private IBarcode iBarcode;
	
	//endregion
	
	//region GUI Objects
	
	// MenuBar
	
	// Menu - File
	@FXML private MenuItem newFile;
	@FXML private MenuItem saveFile;
	@FXML private MenuItem saveAsFile;
	@FXML private MenuItem quitFile;
	
	// Menu - Edit
	// TODO: Evaluate needed functions and controls for this menu
	
	// Menu - Help
	@FXML private MenuItem about;
	
	
	// SplitPane
	
	// Accordion
	// TitledPane - Barcode
	@FXML	private ComboBox<IBarcode> iBarcodeComboBox;
	@FXML private Button generate;
	
	// TitledPane - Configuration
	@FXML private AnchorPane configuration;
	
	// TitledPane - Export Settings
	// TODO: Evaluate needed functions and controls for this menu
	
	// ImageView - Barcode PreView
	@FXML private ImageView iBarcodePreview;
	
	//endregion
	
	
	/**
	 * Contructor w/o args
	 */
	public Controller(){
	
	}

	public void setData(){
		
		barcodeTypes = FXCollections.observableArrayList(
				new DummyBarcode(),
		      new Dummy()
		                                                );
		
		iBarcodeComboBox.setItems(barcodeTypes);
		iBarcodeComboBox.getSelectionModel().select(0);
		
		iBarcode = iBarcodeComboBox.getValue();
		
		iBarcodeComboBox.valueProperty().addListener(new ChangeListener<IBarcode>() {
			@Override
			public void changed(ObservableValue<? extends IBarcode> observable,
			                    IBarcode oldValue, IBarcode newValue) {
				
				iBarcode = newValue;
				
				configuration.getChildren().clear();
				configuration.getChildren().add(iBarcode.mandatoryProperties());
				
				System.out.println("Current iBarcode: " + newValue);
			}
		});
		
		generate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				iBarcodePreview.setImage(iBarcode.runGenerator());
			}
		});
		
		
	}
	
	
	
	/**
	 * Called to initialize a controller after its root element has been
	 * completely processed.
	 *
	 * @param location  The location used to resolve relative paths for the root
	 *                    object, or
	 *                  <tt>null</tt> if the location is not known.
	 * @param resources The resources used to localize the root object, or
	 *                    <tt>null</tt> if
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}
}
