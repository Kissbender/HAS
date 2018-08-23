package hospitalappointments;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author ofentse
 */
public class LoginViewController implements Initializable {
    
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField username;

    @FXML
    private Button login;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        login.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event) {
                //-- Authenticate the user --
                if(username.getText().trim().equals("bitrihospital") && password.getText().trim().equals("bitrihospital")){
                    //-- open a new stage (maiin UI) --
                    new MainUI();
                    
                    //-- hide login screen --
                    LoginStage.stage.hide();
                    
                }else{
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Authentication failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Please confirm your username and password combination and try again.");

                    
                    //-- Get the Stage. --
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    
                    //-- Add a custom icon. --
                    stage.getIcons().add(new Image(LoginStage.class.getResourceAsStream("res/clinic.png")));

                    alert.showAndWait();
                }
                
                
            }
        });
    }    
    
}
