package hospitalappointments;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Ofentse Jabari and Tshepo Moile
 * Create for demonstration purposes only.
 */
public class AppointmentButton extends Button{

    public AppointmentButton(Appointment appointment) {
        
        setPrefWidth(190);
        getStyleClass().add("appointment-button");
        
        GridPane grid = new GridPane();
        grid.setVgap(5);
        grid.setHgap(5);
        
        Patient patient = MainUI.connector.getPatientByID(appointment.getPatientID());
        
        Label name = new Label(patient.getFname()+" "+patient.getLname());
        name.getStyleClass().add("name");
        
        grid.add(name, 0, 0, 2, 1);
        
        
        Label email = new Label(patient.getEmail());
        grid.add(email, 0, 1, 2, 1);
        
        Label date = new Label(appointment.getDate(), new ImageView(new Image(LoginStage.class.getResourceAsStream("res/event_accept.png"))));
        date.setTooltip(new Tooltip("Appointment date"));
        grid.add(date, 0, 2);
        
        setGraphic(grid);
        
        setOnAction((ActionEvent event) -> {
            new UpdateAppointment(appointment);
        });
        
        setTooltip(new Tooltip(appointment.getDescription()));
        
    }
    
    
}
