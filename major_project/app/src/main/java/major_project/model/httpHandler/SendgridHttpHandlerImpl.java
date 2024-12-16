package major_project.model.httpHandler;

import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest.Builder;
import java.util.HashMap;
import java.util.Map;

public class SendgridHttpHandlerImpl implements SendgridHttpHandler {
    private String postRequest(String uri, Map<String, String> header, String data) {
        try {

            Builder builder = HttpRequest.newBuilder(new URI(uri)).POST(HttpRequest.BodyPublishers.ofString(data));
            
            for (String key : header.keySet()) {
                builder.header(key, header.get(key));
            }
            
            HttpRequest request = builder.build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } 
        catch (Exception e) {
            System.out.printf("Something went wrong with the request of %s\n", uri);
            System.out.println(e.getMessage());
            return e.getMessage();
        } 
    }

    public String postEmail(String key, String data) {
        HashMap<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " +  key);
        header.put("Content-Type", "application/json");
        return postRequest("https://api.sendgrid.com/v3/mail/send", 
                          header, 
                          data);
    }
}
