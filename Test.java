import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

//Potential add on idea, combine this with puddle farm api to give character feedback

public class Test {
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";
    //Key might eexpire or be old

    public static void main(String[] args) {
        //ADD USER INPUT
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to ask deepseek?: ");
        String prompt = scanner.nextLine();
        String response = sendPromptToAI(prompt);
        System.out.println("AI Response: " + response);
    }

    private static String sendPromptToAI(String prompt) {
        try {
            // Create the JSON payload
            String jsonPayload = String.format(
                "{\"model\": \"openai/gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}",
                prompt
            );

            // Create the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .header("Accept", "application/json")
                .POST(BodyPublishers.ofString(jsonPayload))
                .build();

            System.out.println("Sending request to: " + API_URL);
            System.out.println("Request payload: " + jsonPayload);

            // Send the request and get the response
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            //Comment out status of repsonse and response technical details
            //System.out.println("Response status code: " + response.statusCode());
            //System.out.println("Response body: " + response.body());

            // Parse the response
            return parseAIResponse(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    private static String parseAIResponse(String response) {
        // Simple JSON parsing (for demonstration purposes)
        int startIndex = response.indexOf("\"content\":\"") + 11;
        int endIndex = response.indexOf("\"", startIndex);
        return response.substring(startIndex, endIndex);
    }
}
