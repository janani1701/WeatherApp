# WeatherApp


The Weather Alert Application is a Java-based program that enables users to receive weather alerts via email based on their location. The application consists of several components, including user registration, weather data retrieval, email notifications, and a scheduler for periodic weather checks.

## Components

### 1. User Registration (DatabaseHandler)

- The `DatabaseHandler` class is responsible for managing user registration and data storage in a SQLite database.
- The `createTable` method creates the 'users' table if it doesn't exist. This table includes columns for user ID, username, location, and email.
- The `insertUser` method allows the insertion of user data into the 'users' table.
- The `getUserEmail` and `getUserLocation` methods retrieve email and location data for a given username.
- The `closeConnection` method is used to close the database connection.

### 2. Email Notifications (EmailNotifier)

- The `EmailNotifier` class handles the sending of email alerts to users.
- It leverages the JavaMail API to configure and send email messages via Gmail's SMTP server.
- The `sendEmail` method takes the recipient's email address, subject, and message as parameters and sends the email.

### 3. Weather Data Retrieval (WeatherAlert)

- The `WeatherAlert` class is responsible for periodically checking weather conditions and sending email alerts.
- It uses the `Timer` class to schedule weather checks at predefined intervals, such as every hour.
- The `getWeatherData` method retrieves weather data from an external source, such as a weather API. This method should be customized to fetch data from your chosen source.
- The `getWeatherAlert` method generates alert messages based on temperature thresholds.
- The `run` method is executed at each scheduled interval and triggers the weather checks, email generation, and sending of alerts.

## Workflow

Here is the step-by-step explanation of how the application works:

1. User Registration:
   - Users are prompted to enter their username, location, and email when they run the application.
   - The user data is stored in a SQLite database using the `DatabaseHandler` class.

2. Weather Checks:
   - The `WeatherAlert` class, which extends `TimerTask`, is scheduled to run at defined intervals (e.g., every hour).
   - At each interval, the `run` method is executed, triggering the weather checks.

3. Weather Data Retrieval:
   - The `getWeatherData` method in the `WeatherAlert` class is responsible for fetching weather data. You should customize this method to use an appropriate weather data source (e.g., a weather API). The example code assumes a simple HTTP GET request to an API that returns JSON data.

4. Email Alerts:
   - The `getWeatherAlert` method in the `WeatherAlert` class generates alert messages based on the retrieved temperature data.
   - If the temperature crosses predefined thresholds, an email alert is generated using the `EmailNotifier` class and sent to the user's registered email address.

## Customization

To customize the application:

- Replace the weather data retrieval logic in the `getWeatherData` method with your chosen weather data source. Ensure that the data retrieval and parsing are adapted to your specific API or source.

- Modify the `getWeatherAlert` method to define your temperature thresholds and alert messages based on your preferences.

- Adjust the scheduling of weather checks by modifying the timer interval in the `main` function of the `WeatherAlertApp`.

- Customize the user registration process, database schema, and email configuration to meet your project's requirements.

## Contribution

If you'd like to contribute to this project, follow the standard Git contribution workflow:

1. Fork the repository.
2. Make changes in a new branch.
3. Create a pull request on the original repository for review.

Please note that this application serves as a starting point, and you can extend it with additional features, such as user authentication, more sophisticated weather data processing, or support for multiple locations.

