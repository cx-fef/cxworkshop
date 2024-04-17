import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Demo {


    public static void main(String[] args) {
        String url = "http://example.com/api/data"; // Replace with your API endpoint URL

        try {
            // Create a URL object with the API endpoint
            URL apiUrl = new URL(url);

            // Open a connection to the API endpoint
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();


            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();
    

            // Check if the request was successful (response code 200)
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Create a BufferedReader to read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                // Read the response line by line
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Close the reader
                reader.close();

                // Display the response to the user
                System.out.println(response.toString());
                
            } else {
                // Display an error message if the request was not successful
                System.out.println("Error: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}