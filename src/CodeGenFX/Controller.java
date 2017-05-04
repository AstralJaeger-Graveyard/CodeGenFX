package CodeGenFX;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Controller {
	
	
	
	
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
	
	// TitledPane - Configuration
	@FXML private AnchorPane configuration;
	
	// TitledPane - Export Settings
	// TODO: Evaluate needed functions and controls for this menu
	
	// ImageView - Barcode PreView
	@FXML private ImageView iBarcodePreview;
	
	
	/**
	 * Contructor w/o args
	 */
	public Controller(){
	
	}
	
	/**
	 * Inizialise fct for JavaFX Controller class
	 */
	@FXML
	public void inizialise(){
	
	
	}
	
	
	
	
}
