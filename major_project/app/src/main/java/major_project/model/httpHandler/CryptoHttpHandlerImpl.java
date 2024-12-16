package major_project.model.httpHandler;

import java.net.*;
import java.net.http.*;
import java.net.http.HttpRequest.Builder;
import java.util.HashMap;
import java.util.Map;

public class CryptoHttpHandlerImpl implements CryptoHttpHandler{
    

    private String getRequest(String uri, Map<String, String> header, String parameters) throws Exception {
            URI uriWithParams;
            if (parameters.equals("")) uriWithParams = new URI(uri);
            else uriWithParams = new URI(String.format("%s?%s", uri, parameters));

            Builder builder = HttpRequest.newBuilder(uriWithParams).GET();
            
            for (String key : header.keySet()) {
                builder.header(key, header.get(key));
            }
            
            HttpRequest request = builder.build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
    }

    public String getListing(String key) throws Exception {
        HashMap<String, String> header = new HashMap<>();
        header.put("X-CMC_PRO_API_KEY", key);
        header.put("Accept", "application/json");
        return getRequest("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest", 
                          header, 
                          "");
    }

    public String getMetaData(String key, int id) throws Exception {
        HashMap<String, String> header = new HashMap<>();
        header.put("X-CMC_PRO_API_KEY", key);
        header.put("Accept", "application/json");
        return getRequest("https://pro-api.coinmarketcap.com/v1/cryptocurrency/info", 
                          header, 
                          "id="+Integer.toString(id));
    }

    public String getConversion(String key, int from, int to, double amount) throws Exception {
        HashMap<String, String> header = new HashMap<>();
        header.put("X-CMC_PRO_API_KEY", key);
        header.put("Accept", "application/json");
        return getRequest("https://pro-api.coinmarketcap.com/v1/tools/price-conversion", 
                          header, 
                          String.format("amount=%f&id=%d&convert_id=%d", amount, from, to));
    }
}
