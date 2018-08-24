package hospitalappointments;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ofentse Jabari and Tshepo Moile
 * Create for demonstration purposes only.
 */
public class AppointmentListItem extends VBox{

    public AppointmentListItem(Appointment appointment) {
        
        setSpacing(2);
        
        Patient patient = MainUI.connector.getPatientByID(appointment.getPatientID());
        
        Label patientName = new Label(patient.getFname()+" "+patient.getLname());
        patientName.setStyle("-fx-font-size:18px; -fx-text-fill: #0D47A1");
        
        HBox hbox = new HBox(5);
        
        Label email = new Label(patient.getEmail());
        email.getStyleClass().add("-fx-text-fill: #607D8B");
        
        Label date = new Label(appointment.getDate(), new ImageView(new Image(LoginStage.class.getResourceAsStream("res/event_accept.png"))));
        date.setTooltip(new Tooltip("Appointment date"));
        
        Region sp1 = new Region();
        HBox.setHgrow(sp1, Priority.ALWAYS);
        
        Region sp2 = new Region();
        HBox.setHgrow(sp2, Priority.ALWAYS);
        
        Button edit = new JFXButton("Edit");
        edit.setPrefWidth(20);
        edit.getStyleClass().addAll("btn", "btn-danger", "btn-xs");
        edit.setOnAction((ActionEvent event) -> {
            new UpdateAppointment(appointment);
        });
        edit.setTooltip(new Tooltip(appointment.getDescription()));
        
        
        Label status = new Label();
        status.setPrefWidth(100);
        
        if(appointment.getStatus().equalsIgnoreCase("0")){//-- closed
            status.setText("CLOSED");
            status.getStyleClass().addAll("btn", "btn-success", "btn-xs");
        }else if(appointment.getStatus().equalsIgnoreCase("1")){
            status.setText("OPEN");
            status.getStyleClass().addAll("btn","btn-default", "btn-xs");
        }else{
            status.setText("CANCELLED");
            status.getStyleClass().addAll("btn", "btn-danger", "btn-xs");
        }
        
        hbox.getChildren().addAll(status, sp1, date, sp2, edit);
        
        
        getChildren().addAll(patientName, email, hbox);
        

        
    }
    
    
}
