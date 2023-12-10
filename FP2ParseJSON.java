/**
 * This class demonstrates using GSON to parse JSON data returned by the AviationStack API.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;

public class FP2ParseJSON
{

    public static void main(String[] args) throws IOException
    {
        // Use a file in the same directory with saved flight JSON data.
        // We are doing this because we can't query specific flights under the "free" plan through the AviationStack API,
        // so we get tons of data back, and pasting it all here would make this class huge.
        Path fileName = Path.of("flights.json");
        String fileContent = Files.readString(fileName);

        // Use GSON to parse the JSON data.
        Gson gson = new Gson();
        Flight flight = new Flight();
        try
        {
            flight = gson.fromJson(fileContent, Flight.class);
        }
        catch (Exception e)
        {
            System.out.println("Unable to parse data");
        }

        System.out.println("Demo finished!");
    }
}