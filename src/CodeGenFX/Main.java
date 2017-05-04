package CodeGenFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
     
    	try {
	      FXMLLoader sceneLoader = new FXMLLoader();
	      
	      Parent root = sceneLoader.load(getClass().getResource("MainWindow.fxml"));
	  	  
	      primaryStage.setTitle("CodeGen FX");
	      primaryStage.setScene(new Scene(root, 300, 275));
	      primaryStage.show();
	      
      }catch(Exception ex){
	  
	      Alert exceptionAlert = new Alert(Alert.AlertType.ERROR);
	      exceptionAlert.setTitle("Not able to load gui");
	      exceptionAlert.setHeaderText("A exception occurred when attempted " +
			                             "to load \"MainWindow.fxml\" resource file");
    		exceptionAlert.setContentText(ex.toString());
    		exceptionAlert.showAndWait();
      }
    }


    public static void main(String[] args) {
        
        launch(args);
    }
}
