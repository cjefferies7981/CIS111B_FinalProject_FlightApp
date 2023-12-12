import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;

public class FlightTrackingAppController implements Initializable
{
   // Variables from FXML file using annotations 
   @FXML
   private Label flightTracker;
   
   @FXML
   private TextField flightInput;
   
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
   
   // Gather API data   
   private HttpClient client;
   
   // Top-level class that saves GSON processed JSON data
   Root root;

   // Flight data
   Data data;
   
   // An enum that represents what format to display the time
   private enum Time { NORMAL, MILITARY };
   private Time time;
   
   // Keeps track of last time the flight data was updated
   private Date updateTime;
   
   // Key to persist time format preference
   public static final String TIME_FORMAT = "time_format_key";
   
   // Action to perform when the refresh button is pressed
   @FXML 
   protected void showFlightStatus(ActionEvent event)
   {
      updateFlightData();       
   }
   
   // Action to refresh time
   @FXML
   protected void updateDates(ActionEvent event)
   {
      updateFlightData();
   }
   
   // Actions to perform when the format radio buttons are pushed
   @FXML
   protected void timeFormatChangeRadioButton(ActionEvent event)
   {
       
      // Update units based on which radio button was pressed
      if(event.getSource() == normalTime)
         this.time = Time.NORMAL;
      else if(event.getSource() == militaryTime)
         this.time = Time.MILITARY;
      
      // Save time format selection
      Preferences z = Preferences.userNodeForPackage(FlightTrackingAppController.class);
      z.put(TIME_FORMAT, this.time.toString() );
      
      // Reflect changes in user-interface
      updateUI();
   }
   
   // This method parses the JSON and creates POJO
   protected void processFlightData(String inputJSON)
   {
            
      // Save the time this data was retrieved to be displayed in the GUI
      this.updateTime = new Date();
      
      // Some debugging text for the console. Allows us to view returned JSON
      System.out.println(inputJSON);      
      
      // Use GSON to convert the JSON to a POJO
      // If JSON is not valid then just return
      Gson gson = new Gson();

      Root root = gson.fromJson(inputJSON, Root.class);

      this.data = root.data[0];

      // Schedule UI updates on the GUI thread
      Platform.runLater( new Runnable()
      {
         public void run()
            {
               updateUI();
            }
      });
   }

     // Update values in each property
     protected void updateUI()
     {
        if(this.data != null)
        {
           // Update flight status
           flightStatus.setText(this.data.flight_status);
           
           // Set the airline
           airline.setText(this.data.airline.name);
           
           // Set the aircraft model
           aircraftModel.setText(this.data.aircraft);
           
           // Update the departure time
           departureDateTime.setText(this.data.departure.estimated);
           
           // Update the departing airport gate
           departureAirportGate.setText(this.data.arrival.estimated);
           
           // Update the arrival date
           arrivalDateTime.setText(this.data.arrival.gate);
           
           // Update the arriving airport gate
           arrivalAirportGate.setText(this.data.departure.gate);
        }
      }
   
   // This method runs when the user pushes the show flight status button and once the app is initialized
   protected void updateFlightData()
   {
      if(this.client == null)
         this.client = HttpClient.newHttpClient();

      // LocalDate flightDate = LocalDate.now();
      // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      // String formattedDate = flightDate.format(formatter);

      try
      {
         HttpRequest request = HttpRequest.newBuilder()
                                          .uri(new URI("https://api.aviationstack.com/v1/flights?access_key=" + System.getenv("APIKEY") + "&limit=1&flight_iata=AA6383")) // + flightInput))
                                          .GET()
                                          .build();
                                          
         client.sendAsync(request, BodyHandlers.ofString())
                 .thenApply(HttpResponse::body)
                 .thenAccept(this::processFlightData);
      }
      catch(URISyntaxException e)
      {
         System.out.println("Issue with request");
      }
   }
    
   // "Waking up" applicatoin and setting preserved preferences
   @Override
   public void initialize(URL location, ResourceBundle resources)
   {

      // Passes the time format to set
      // App defaults to normal time format
      Preferences z = Preferences.userNodeForPackage(FlightTrackingAppController.class);
      this.time = Time.valueOf( z.get(TIME_FORMAT, Time.MILITARY.toString() ) );
      
       if(this.time == Time.NORMAL)
         this.normalTime.setSelected(true);
       else
         this.militaryTime.setSelected(true);
         
      updateFlightData();
    }
      
}