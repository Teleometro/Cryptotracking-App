package SpaceTrader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.*;

import SpaceTrader.Dummy.AccountInfo;

public class HttpHandlerImpl implements HttpHandler {
    public static String getRequest(String uri) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .GET()
                    .build();
            
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

    public static String getRequest(String uri, String header) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .GET()
                    .build();
            
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

    public static String postRequest(String uri, String req) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new 
URI(uri))
                    .POST(HttpRequest.BodyPublishers.ofString(req))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception e) {
            System.out.printf("Something went wrong with the request of %s\n", "uri");
            System.out.println(e.getMessage());
            return e.getMessage();
        } 
    }

    @Override
    public String getStatus() {
        String statusString = getRequest("https://api.spacetraders.io/game/status");

        JsonParser parser = new JsonParser();
        JsonObject statusJSON = parser.parse(statusString).getAsJsonObject();

        return statusJSON.get("status").getAsString();
    }

    @Override
    public String signIn(String aTok) {
        JsonObject resp;
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(String.format("https://api.spacetraders.io/my/account?token=%s", 
                                                                                aTok)))
                    .GET()
                    .build();
                
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            resp = new JsonParser().parse(response.body()).getAsJsonObject();
            if (resp.has("user")) return aTok;
            else return null;
        } 
        catch (Exception e) {
            System.out.printf("Something went wrong with the sign in\n");
            System.out.println(e.getMessage());
            return null;
        } 
    }

    @Override
    public String createAccount(String uname) {
        
        String resp = postRequest("https://api.spacetraders.io/users/"+uname+"/claim", "");
        JsonObject response = new JsonParser().parse(resp).getAsJsonObject();
        if (response.has("error")) return "error: " + response.get("error").getAsJsonObject().get("message").getAsString();
        return response.get("token").getAsString();
    }

    @Override
    public AccountInfo getAccount(String aTok) {
        JsonObject resp;
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(String.format("https://api.spacetraders.io/my/account?token=%s", 
                                                                                aTok)))
                    .GET()
                    .build();
                
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            resp = new JsonParser().parse(response.body()).getAsJsonObject().get("user").getAsJsonObject();
            AccountInfo acc = new AccountInfo();
            acc.username = resp.get("username").getAsString();
            acc.credits = resp.get("credits").getAsInt();
            acc.JoinDate = resp.get("joinedAt").getAsString();
            acc.StructureCount = resp.get("structureCount").getAsInt();
            acc.ShipCount = resp.get("shipCount").getAsInt();
            return acc;
        } 
        catch (Exception e) {
            System.out.printf("Something went wrong with the sign in\n");
            System.out.println(e.getMessage());
            return null;
        } 
    }

    @Override
    public String getLoans(String aTok) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(String.format("https://api.spacetraders.io/types/loans?token=%s", 
                                                                                aTok)))
                    .GET()
                    .build();
                
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            return response.body();
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } 
    }

    @Override
    public String obtainLoan(String authTok, String type) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(String.format("https://api.spacetraders.io/my/loans?token=%s&type=%s", 
                                                                                authTok,
                                                                                type)))
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.out.printf("Something went wrong with the request of %s\n", "uri");
            System.out.println(e.getMessage());
            return e.getMessage();
        } 
    }

    @Override
    public String getShips(String aTok) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(String.format("https://api.spacetraders.io/systems/OE/ship-listings?token=%s", 
                                                                                aTok)))
                    .GET()
                    .build();
                
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            return response.body();
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } 
    }

    @Override
    public String purchaseShip(String authTok, String loc, String type) {
        return postRequest(String.format("https://api.spacetraders.io/my/ships?token=%s&location=%s&type=%s", authTok, loc, type), "");
    }

    @Override
    public String getMyLoans(String aTok) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(String.format("https://api.spacetraders.io/my/loans?token=%s", 
                                                                                aTok)))
                    .GET()
                    .build();
                
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            return response.body();
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } 
    }

    @Override
    public String getMyShips(String aTok) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(String.format("https://api.spacetraders.io/my/ships?token=%s", 
                                                                                aTok)))
                    .GET()
                    .build();
                
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            return response.body();
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } 
    }

    @Override
    public String getMarket(String AuthTok, String loc) {
        try {
            HttpRequest request = HttpRequest.newBuilder(new URI(String.format("https://api.spacetraders.io/locations/%s/marketplace?token=%s", 
                                                                                loc, AuthTok)))
                    .GET()
                    .build();
                
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            return response.body();
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } 
    }

    @Override
    public String purchaseCargo(String AuthTok, String shipId, String good, int num) {
        return postRequest(String.format("https://api.spacetraders.io/my/purchase-orders?token=%s&shipId=%s&good=%s&quantity=%d", AuthTok, shipId, good, num), "");
    }

    @Override
    public String getNearLocations(String authTok) {
        return getRequest(String.format("https://api.spacetraders.io/systems/OE/locations?token=%s", authTok));
    }

    @Override
    public String setFlightRoute(String authTok, String dest, String fId) {
        return postRequest(String.format("https://api.spacetraders.io/my/flight-plans?token=%s&destination=%s&shipId=%s", authTok, dest, fId), "");

    }

    @Override
    public String getFlight(String authTok, String fId) {
        return getRequest(String.format("https://api.spacetraders.io/my/flight-plans/%s?token=%s", fId, authTok));
    }

    @Override
    public String sellCargo(String AuthTok, String shipId, String good, int num) {
        return postRequest(String.format("https://api.spacetraders.io/my/sell-orders?token=%s&shipId=%s&good=%s&quantity=%d", AuthTok, shipId, good, num), "");
    }
}
