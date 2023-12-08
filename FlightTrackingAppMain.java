import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.*;

public class FlightTrackingAppMain extends Application
{
   public static void main (String [] args)
   {
      launch(args);
   }
   
   @Override
   public void start(Stage stage) throws Exception
   {
      // Load Flight App GUI
      Parent root = FXMLLoader.load(getClass().getResource("FlightTrackingApp.fxml"));
      
      //Create "scene"
      Scene scene = new Scene(root);
      // Give window border a title
      stage.setTitle("Flight Tracking Application");
      // Set the scene
      stage.setScene(scene);
      // Present the completed stage
      stage.show();
   }
   
   @Override
   public void stop()
   {
      System.out.println("The program has been stopped.");
   }
}