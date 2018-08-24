
package hospitalappointments;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author ofentse
 */
public class MainUI extends Stage {
    
    public static MysqlConnector connector = null;
    
    public MainUI() {
        
        //-- Intialize DB connectivity --
        connector = new MysqlConnector();
        
        BorderPane root = new BorderPane();
        
        //-- Create a tabpane view and set it to the center of root --
        TabPane tabpane = new TabPane();
        root.setCenter(tabpane);
        
        //-- create and add tabs to tabpane --
        
        Tab dashboard = new Tab("Dashboard");
        //dashboard.getStyleClass().add("dashboard");
        
        Tab patients = new Tab("Patients Profile");
        //patients.getStyleClass().add("profile");
        
        Tab appointments = new Tab("Appointments");
        //appointments.getStyleClass().add("appointments");
        
        tabpane.getTabs().addAll(dashboard, patients, appointments);
        
        //-- disable the close button on tabs --
        dashboard.setClosable(false);
        patients.setClosable(false);
        appointments.setClosable(false);
        
        //-- set graphics on tabs --
        ImageView dashboard_icon = new ImageView(new Image(LoginStage.class.getResourceAsStream("res/dashboard.png")));
        dashboard_icon.setFitHeight(38);
        dashboard_icon.setPreserveRatio(true);
        dashboard.setGraphic(dashboard_icon);
        
        ImageView patients_icon = new ImageView(new Image(LoginStage.class.getResourceAsStream("res/patient.png")));
        patients_icon.setFitHeight(38);
        patients_icon.setPreserveRatio(true);
        patients.setGraphic(patients_icon);
                
        ImageView appointment_icon = new ImageView(new Image(LoginStage.class.getResourceAsStream("res/appointment.png")));
        appointment_icon.setFitHeight(38);
        appointment_icon.setPreserveRatio(true);
        appointments.setGraphic(appointment_icon);
        
        //-- Create and set tab contents
        PatientsProfile patientProfile = new PatientsProfile();
        patients.setContent(patientProfile);
        
        DoctorAppointments doctorAppointments = new DoctorAppointments();
        appointments.setContent(doctorAppointments);
        
        Dashboard dashboardUI = new Dashboard();
        dashboard.setContent(dashboardUI);
                
        //-- Create a scene with a desired initial width and height --
        Scene scene = new Scene(root, 1100, 550);
        scene.getStylesheets().addAll(MainUI.class.getResource("res/style.css").toExternalForm());
        
        //-- set stage icon --
        getIcons().add(new Image(LoginStage.class.getResourceAsStream("res/clinic.png")));
        setTitle("BITRI Hospital");
        setScene(scene);
        show();
    }

    
}
