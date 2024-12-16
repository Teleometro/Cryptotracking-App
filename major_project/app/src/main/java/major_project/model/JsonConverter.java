package major_project.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;


public class JsonConverter {
    private static JsonParser parser = new JsonParser();

    public static List<Currency> listing(String raw) {
        ArrayList<Currency> arr = new ArrayList<>(); 
        if (raw == null) {
            return arr;
        }
        JsonElement parsedRaw = parser.parse(raw);
        if (!parsedRaw.isJsonObject()) {
            return arr;
        } 
        JsonObject json = parsedRaw.getAsJsonObject();
        
        JsonElement dataElement = json.get("data");
        if (dataElement == null) {
            return arr;
        }
        if (!dataElement.isJsonArray()) {
            return arr;
        }
        JsonArray dataArray = dataElement.getAsJsonArray();
        
        for (JsonElement cur : dataArray) {
            Currency c = new Currency();
            JsonElement idElement = cur.getAsJsonObject().get("id");
            JsonElement nameElement = cur.getAsJsonObject().get("name");
            JsonElement symbolElement = cur.getAsJsonObject().get("symbol");
            if (idElement == null || nameElement == null || symbolElement == null) {
                continue;
            }

            try {
                c.id = idElement.getAsInt();
                c.name = nameElement.getAsString();
                c.symbol = symbolElement.getAsString();
            } catch (Exception e) {
                continue;
            }
            if (c.name == null || c.symbol == null) {
                continue;
            }
            arr.add(c);
        }
        return arr; 
    }
    public static Currency metadata(String raw, Currency cur) {
        if (raw == null) {
            return cur;
        }
        if (cur == null) {
            return cur;
        }

        JsonElement parsedRaw = parser.parse(raw);
        if (!parsedRaw.isJsonObject()) {
            return cur;
        } 
        JsonObject json = parsedRaw.getAsJsonObject();
        parsedRaw = json.get("data");
        if (parsedRaw == null) {
            return cur;
        }
        json = parsedRaw.getAsJsonObject();

        try {
            json = json.get(Integer.toString(cur.getId())).getAsJsonObject();
        } 
        //catch will only occur when dummy is in use 
        catch (Exception e) {
            json = json.entrySet().iterator().next().getValue().getAsJsonObject();
        }

        try {
            cur.setLogoURL(json.get("logo").getAsString());
        } catch (Exception e) {
            
        }
        if (json.get("date_launched").isJsonNull()) cur.dateLaunched = null;
        else cur.dateLaunched = json.get("date_launched").getAsString();
        if (json.get("description").isJsonNull()) cur.description = null;
        else cur.description = json.get("description").getAsString();
        try {
            if (json.get("urls").getAsJsonObject().get("website").isJsonNull()) cur.website = null;
            else cur.website = json.get("urls").getAsJsonObject().get("website").getAsJsonArray().get(0).getAsString();
        } catch (Exception e) {
            cur.website = null;
        }
         
        cur.hasMetadata = true;
        return cur;
    }

    public static double convert(String raw, int id) throws Exception {
        if (raw == null) {
            throw new Exception("did not recieve conversion data");
        }

        JsonElement parsedRaw = parser.parse(raw);
        if (!parsedRaw.isJsonObject()) {
            throw new Exception("did not recieve conversion data");
        } 
        JsonObject json = parser.parse(raw).getAsJsonObject();
        json = json.get("data").getAsJsonObject();
        try {
            json = json.get("quote").getAsJsonObject()
                   .get(Integer.toString(id)).getAsJsonObject();    
        } 
        //will only occur on dummy
        catch (Exception e) {
            json = json.get("quote").getAsJsonObject().entrySet().iterator().next().getValue().getAsJsonObject();
        }
        return json.get("price").getAsDouble();
    }
}
