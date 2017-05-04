package CodeGenFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Main extends Application {

	public static Controller ctrl;
	
    @Override
    public void start(Stage primaryStage) throws Exception{
     
    	try {
	      FXMLLoader loader = new FXMLLoader(Main.class.getResource("/CodeGenFX/MainWindow.fxml"));//getClass().getResource("MainWindow.fxml"));
	      Parent root = loader.load();
	      ctrl = loader.getController();
	      
	      primaryStage.setTitle("CodeGen FX");
	      primaryStage.setScene(new Scene(root, 1080, 720));
	      primaryStage.show();
	  
	      ctrl.setData();
	      
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
        
        launch("void");
    }
}
