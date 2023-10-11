import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Scanner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherAlertApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your location: ");
        String location = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        DatabaseHandler databaseHandler = new DatabaseHandler();

        // Store user information in the database
        databaseHandler.insertUser(username, location, email);

        // Schedule weather alerts
        Timer timer = new Timer();
        timer.schedule(new WeatherAlert(databaseHandler, location, email), 0, 3600000); // Run every hour
    }

    private static class WeatherAlert extends TimerTask {
        private final DatabaseHandler databaseHandler;
        private final String location;
        private final String email;

        public WeatherAlert(DatabaseHandler databaseHandler, String location, String email) {
            this.databaseHandler = databaseHandler;
            this.location = location;
            this.email = email;
        }

        @Override
        public void run() {
            // Retrieve user data
            // You can modify this part to retrieve user data and location from the database

            // Here, we use the provided 'location' and 'email' from the constructor
            String username = "YourUsername"; // You may retrieve it from the database

            // Retrieve weather data (replace with your weather data retrieval logic)
            double temperature = getWeatherData(location);

            // Determine the appropriate weather alert message
            String alertMessage = getWeatherAlert(temperature);

            // Send an email alert
            EmailNotifier.sendEmail(email, "Weather Alert for " + location, alertMessage);
        }

        private double getWeatherData(String location) {
            try {
                // Replace 'YOUR_API_KEY' with your actual API key
                String apiKey = "YOUR_API_KEY";
                String apiUrl = "https://api.example.com/weather?location=" + location + "&apikey=" + apiKey;

                // Create a URL object
                URL url = new URL(apiUrl);

                // Open a connection to the URL
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Set the request method to GET
                connection.setRequestMethod("GET");

                // Get the response code
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Parse the JSON response
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(response.toString());

                    // Assuming the temperature is stored in the 'temperature' field
                    double temperature = (double) json.get("temperature");

                    return temperature;
                } else {
                    // Handle error cases
                    System.out.println("Error: Unable to fetch weather data.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Return a default value in case of an error
            return 0.0;
        }

        private String getWeatherAlert(double temperature) {
            if (temperature > 30) {
                return "It's going to be hot. Stay hydrated and drink water.";
            } else if (temperature < 20) {
                return "It's going to be cold. Don't forget to wear a jacket.";
            } else {
                return "The weather should be comfortable. Enjoy your day!";
            }
        }
    }
}
