import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import java.util.ResourceBundle;
import java.util.Date;
import java.util.prefs.Preferences;
import java.text.SimpleDateFormat;
import javafx.application.Platform;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FlightTrackingAppController //implements Initializable
{
   // Variables from FXML file using annotations 
   @FXML
   private Label flightTracker;
   
   @FXML
   private Label flightInput;
   
   @FXML
   private Button btnFlightStatus;
   
   @FXML
   private Label messageArea;
   
   @FXML
   private Label flightStatus;
   
   @FXML
   private Label airline;
   
   @FXML
   private Label aircraftModel;
   
   @FXML
   private Label departureDateTime;
   
   @FXML
   private Label departureAirportGate;
   
   @FXML
   private Label arrivalDateTime;
   
   @FXML
   private Label arrivalAirportGate;
   
   @FXML
   private RadioButton normalTime;
   
   @FXML
   private RadioButton militaryTime;
   
   // Used to retrieve data from the API   
   private HttpClient client;
   
   // Top-level class that saves GSON processed JSON data
   private Flight flight;
   
   // An enum that represents what format to display the time
   private enum Time { NORMAL, MILITARY };
   private Time time;
   
   // Key to persist time format preference
   public static final String TIME_FORMAT = "time_format_key";
   
   // Actions to perform when the format radio buttons are pushed
   @FXML
   protected void handleTimeConversionRadioButtonAction(ActionEvent event) {
       
      // Update units based on which radio button was pressed
      if(event.getSource() == normalTime)
         this.time = Time.NORMAL;
      else if(event.getSource() == militaryTime)
         this.time = Time.MILITARY;
      
      // Save time format selection
      Preferences z = Preferences.userNodeForPackage(FlightTrackingAppController.class);
      z.put(TIME_FORMAT, this.time.toString() );
   }
   
   /**   
   // This method implements the Initializable interface
   // This is how we can respond to the scene "waking up"
   @Override
   public void initialize(URL location, ResourceBundle resources) {

      // Passes the time format to set
      // App defaults to normal time format
      Preferences z = Preferences.userNodeForPackage(FlightTrackingAppController.class);
      this.unit = Unit.valueOf( z.get(TIME_FORMAT, Unit.MILITARY.toString() ) );
      
       if(this.unit == Unit.NORMAL)
         this.normalTime.setSelected(true);
       else
         this.militaryTime.setSelected(true);
      */
}