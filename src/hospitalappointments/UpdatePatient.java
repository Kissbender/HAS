package hospitalappointments;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author ofentse
 */
public class UpdatePatient extends Stage{

    public UpdatePatient(Patient patient) {
        
        BorderPane root = new BorderPane();
        
        VBox left = new VBox();
        left.setStyle("-fx-border-color:#cfd8dc; -fx-border-width:0 1 0 0;-fx-padding: 0 10 10 0");
        left.setAlignment(Pos.TOP_CENTER);
        ImageView ic = new ImageView(new Image(LoginStage.class.getResourceAsStream("res/user.png")));
        
        left.getChildren().add(ic);
        root.setLeft(left);
        
        GridPane grid = new GridPane();
        grid.getStyleClass().add("form");
        grid.setVgap(5);
        grid.setHgap(5);
        
        
        TextField fname = new TextField();
        fname.setPrefWidth(280);
        fname.setPromptText("First Name");
        
        grid.add(new Label("First Name: "), 0, 0);
        grid.add(fname, 1, 0);
        
        TextField lname = new TextField();
        lname.setPrefWidth(280);
        lname.setPromptText("Last Name");
        
        grid.add(new Label("Last Name: "), 0, 1);
        grid.add(lname, 1, 1);
        
        TextField email = new TextField();
        email.setPrefWidth(280);
        email.setPromptText("Email");
        
        grid.add(new Label("Email: "), 0, 2);
        grid.add(email, 1, 2);
        
        TextField cell = new TextField();
        cell.setPrefWidth(280);
        cell.setPromptText("Cell Phone");
        
        grid.add(new Label("Cell Phone: "), 0, 3);
        grid.add(cell, 1, 3);
        
        TextField occupation = new TextField();
        occupation.setPrefWidth(280);
        occupation.setPromptText("Occupation");
        
        grid.add(new Label("Occupation: "), 0, 4);
        grid.add(occupation, 1, 4);
        
        HBox gendr = new HBox(10);
        gendr.setPadding(new Insets(3, 0, 3, 0));
        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");
        
        ToggleGroup tg = new ToggleGroup();
        male.setToggleGroup(tg);
        female.setToggleGroup(tg);
        tg.selectToggle(male);
        
        gendr.getChildren().addAll(male, female);
        gendr.setAlignment(Pos.CENTER_LEFT);
        
        grid.add(new Label("Gender: "), 0, 5);
        grid.add(gendr, 1, 5);
        
        TextArea postal = new TextArea();
        postal.setPrefRowCount(3);
        postal.setPrefWidth(280);
        postal.setPromptText("Postal Address");
        
        grid.add(new Label("Postal Address: "), 0, 6);
        grid.add(postal, 1, 6);
        
        
        TextArea physical = new TextArea();
        physical.setPrefRowCount(3);
        physical.setPrefWidth(280);
        physical.setPromptText("Phyical Address");
        
        grid.add(new Label("Phyical Address: "), 0, 7);
        grid.add(physical, 1, 7);
        
        root.setCenter(grid);
        
        
        Button save = new Button("Save Changes");
        save.getStyleClass().add("custom-button");
        save.setOnAction((ActionEvent event) -> {
            
            if(patient != null){ //-- Update --
               
                //-- form validation --
                if(!fname.getText().trim().equalsIgnoreCase("") &&
                    !lname.getText().trim().equalsIgnoreCase("") &&
                    !cell.getText().trim().equalsIgnoreCase("")){
               
                    patient.setCell(cell.getText().trim());
                    patient.setEmail(email.getText().trim());
                    patient.setFname(fname.getText().trim());
                    patient.setLname(lname.getText().trim());
                    patient.setOccupation(occupation.getText().trim());
                    patient.setPhysicalAddress(physical.getText().trim());
                    patient.setPostalAddress(postal.getText().trim());
                  
                    String gndr = (male.isSelected())?"male":"female";
                    patient.setGender(gndr);
                  
                    if(MainUI.connector.updatePatient(patient)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Successful Update");
                        alert.setHeaderText(null);
                        alert.setContentText("Patient profile has been updated successfully.");
                        alert.show();
                      
                        close();
                        PatientsProfile.ps.restart();
                      
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Update Failed");
                        alert.setHeaderText(null);
                        alert.setContentText("Exception encountered while trying to update patient profile");
                        alert.show();
                    }
                  
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please ensure that First Name, Last Name and Cell Phone fields are not empty.");
                    alert.show();
               }
               
            }else{ //-- New patient --
                if(!fname.getText().trim().equalsIgnoreCase("") &&
                  !lname.getText().trim().equalsIgnoreCase("") &&
                  !cell.getText().trim().equalsIgnoreCase("")){
                  
                    String gndr = (male.isSelected())?"male":"female";
                   
                    Patient pat = new Patient("", fname.getText().trim(), lname.getText().trim(),
                          cell.getText().trim(), email.getText().trim(), 
                          gndr, physical.getText().trim(), postal.getText().trim(),
                          occupation.getText().trim());
                  
                  
                    if(MainUI.connector.updatePatient(pat)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Successful Update");
                        alert.setHeaderText(null);
                        alert.setContentText("New patient profile has been added successfully.");
                        alert.show();
                        
                        close();
                        PatientsProfile.ps.restart();
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Update Failed");
                        alert.setHeaderText(null);
                        alert.setContentText("Exception encountered while trying to create new patient profile");
                        alert.show();
                    }
                  
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please ensure that First Name, Last Name and Cell Phone fields are not empty.");
                    alert.show();
               }
           }
           
        });
        
        Button cancel = new Button("Exit Window");
        cancel.getStyleClass().add("custom-button");
        cancel.setOnAction((ActionEvent event) -> {
            close();
        });
        
        HBox ctrl = new HBox(10);
        ctrl.getStyleClass().add("toolbar");
        ctrl.setAlignment(Pos.CENTER_RIGHT);
        ctrl.setPadding(new Insets(5, 0, 5, 0));
        
        ctrl.getChildren().addAll(cancel, save);
        grid.add(ctrl, 0, 8, 2, 1);
        
        
        //--- 
        if(patient == null){
            setTitle("Add New Patient");
        }else{
            setTitle("Update Patient");
            fname.setText(patient.getFname());
            lname.setText(patient.getLname());
            cell.setText(patient.getCell());
            email.setText(patient.getEmail());
            postal.setText(patient.getPostalAddress());
            physical.setText(patient.getPhysicalAddress());
            occupation.setText(patient.getOccupation());
            
            if(patient.getGender().equalsIgnoreCase("male")){ male.setSelected(true);}
            else{ female.setSelected(true);}
            
        }
        
        Scene scene = new Scene(root, 550, 380);
        scene.getStylesheets().add(MainUI.class.getResource("res/style.css").toExternalForm());
        
        //-- set stage icon --
        getIcons().add(new Image(LoginStage.class.getResourceAsStream("res/clinic.png")));
        setScene(scene);
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);
        show();
    }
    
}
