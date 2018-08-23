package hospitalappointments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author ofentse and moile
 */
public class LoginStage extends Application {
    
    //-- 
    public static Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        this.stage = stage;
        
        Parent root = FXMLLoader.load(getClass().getResource("loginView.fxml"));
        
        //-- set stage icon --
        stage.getIcons().add(new Image(LoginStage.class.getResourceAsStream("res/clinic.png")));
        
        //-- set stage title --
        stage.setTitle("BITRI Hospital");
        
        //-- Disable screen resizing --
        stage.setResizable(false);
        
        Scene scene = new Scene(root);
                
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
