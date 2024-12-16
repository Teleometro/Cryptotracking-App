package major_project.model.httpHandler;

public interface CryptoHttpHandler {
    
    public String getListing(String key) throws Exception;

    public String getMetaData(String key, int id) throws Exception;

    public String getConversion(String key, int from, int to, double amount) throws Exception;
}
