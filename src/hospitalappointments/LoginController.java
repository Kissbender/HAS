package hospitalappointments;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author ofentse
 */
public class LoginController implements Initializable {
    
    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private TextField username;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //--  TODO -----
        
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //-- Authenticate the user --
                if(username.getText().trim().equals("HAS") && password.getText().trim().equals("HAS")){
                    //-- open a new stage (maiin UI) --
                    new MainUI();
                    
                    //-- hide login screen --
                    LoginStage.stage.hide();
                    
                }else{
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Authentication failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Please confirm your username and password combination and try again.");

                    
                    // Get the Stage.
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    
                    // Add a custom icon.
                     stage.getIcons().add(new Image(LoginStage.class.getResourceAsStream("res/clinic.png")));

                    alert.showAndWait();
                }
                
                
            }
        });
    }    
    
}
