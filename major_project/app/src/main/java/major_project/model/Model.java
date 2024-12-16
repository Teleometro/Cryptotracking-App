package major_project.model;

import java.util.ArrayList;
import java.util.List;

import major_project.model.httpHandler.CryptoHttpHandler;
import major_project.model.httpHandler.CryptoHttpHandlerImpl;
import major_project.model.httpHandler.DummyCryptoHttpHandler;
import major_project.model.httpHandler.DummySendgridHttpHandler;
import major_project.model.httpHandler.SendgridHttpHandler;
import major_project.model.httpHandler.SendgridHttpHandlerImpl;

public class Model {
    private String apiKey;
    private String mailKey;
    private String mailAddress;
    private final List<Currency> elements;
    private List<Currency> listing;

    private CryptoHttpHandler cryptoHttp;
    private SendgridHttpHandler sendgridHttp;

    private static final String dbName = "cache.db";
    private static final String dbURL = "jdbc:sqlite:cache.db";
    private DatabaseHandler dbHandler = new DatabaseHandler();
    private boolean isInputOnline;

    private Currency specialDealCurrency;

    public Model(Boolean inputOnline, boolean outputOnline, DatabaseHandler databaseHandler) {
        this.dbHandler = databaseHandler;
        dbHandler.initDB(dbName, dbURL);
        
        isInputOnline = inputOnline;
        if (inputOnline) cryptoHttp = new CryptoHttpHandlerImpl();
        else cryptoHttp = new DummyCryptoHttpHandler();
        if (outputOnline) sendgridHttp = new SendgridHttpHandlerImpl();
        else sendgridHttp = new DummySendgridHttpHandler();


        apiKey = System.getenv("INPUT_API_KEY");
        if (apiKey == null) {
            System.err.println("environment variable INPUT_API_KEY could not be found");
        }
        mailKey = System.getenv("SENDGRID_API_KEY");
        if (mailKey == null) {
            System.err.println("environment variable SENDGRID_API_KEY could not be found");
        }
        mailAddress = System.getenv("SENDGRID_API_EMAIL");
        if (mailAddress == null) {
            System.err.println("environment variable SENDGRID_API_EMAIL could not be found");
        }
        elements = new ArrayList<>();
        listing = null;

    }

    public void setSpecialDealCurrency(Currency cur) {
        specialDealCurrency = cur;
    }

    public boolean apiKeyMissing() {
        return (apiKey == null || mailKey == null || mailAddress == null);
    }

    public void addElement(Currency cur) {
        elements.add(cur);
    }

    public void removeElement(Currency cur) {
        elements.remove(cur);
    }

    public void clear() {
        elements.clear();
    }

    public List<Currency> getElements() {
        return elements;
    }

    public List<Currency> getListing() throws Exception {
        
        listing = JsonConverter.listing(cryptoHttp.getListing(apiKey));
        
        if(isInputOnline)
        {
            for (Currency currency : listing) {
                if (isCached(currency)) {
                    uncache(currency);
                }
                cache(currency);
            }
        }
        
        return listing;
    }

    public Currency getMetadata(Currency cur) throws Exception {
        if (cur == null) {
            throw new Exception("cannot obtain metadata from null");
        }
        cur = JsonConverter.metadata(cryptoHttp.getMetaData(apiKey, cur.id), cur);
        if(isInputOnline)
        {
            if(isCached(cur))
            {
                uncache(cur);
            }
                cache(cur);
            
            
        }
        
        return cur;
    }

    public String convert(Currency from, Currency to, double amount, String emailTo) throws Exception {
        if (from == null || to == null || emailTo == null || amount <= 0) {
            throw new Exception("invalid conversion");
        }
        double finish = JsonConverter.convert(cryptoHttp.getConversion(apiKey, from.getId(), to.getId(), amount), to.getId());

        if (to.equals(specialDealCurrency)) {
            finish *= 1.1;
        }

        String message = String.format("Converting %s to %s. %s %s has Website: %s, Date Launched: %s.  %s %s has Website: %s, Date Launched: %s.  %f %s becomes %f %s. Conversion Rate of %f", 
                        from.getName(), to.getName(), 
                        from.getName(), from.getSymbol(), from.getWebsite(), from.getDateLaunched(),
                        to.getName(), to.getSymbol(), to.getWebsite(), to.getDateLaunched(),
                        amount, from.getName(), finish, to.getName(),
                        finish/amount);
        
        String email = String.format("""
            {\"personalizations\": [{\"to\": [{\"email\": \"%s\"}]}],\"from\": {\"email\": \"%s\"},\"subject\": \"conversion report\",\"content\": [{\"type\": \"text/plain\", \"value\": \"%s\"}]}
            """, 
            emailTo, 
            mailAddress,
            message
            );
        String dbug = sendgridHttp.postEmail(mailKey, email);
        System.out.println(dbug);
        return message;
    }

    public void cache(Currency c) {
        dbHandler.cache(c);
    }

    public void uncache(Currency c) {
        dbHandler.uncache(c);
    }

    public Currency getCatchedMetaData(Currency c) {
        return dbHandler.getCatchedMetaData(c);
    }

    public void clearCache() {
        dbHandler.clearCache();
    }

    public Boolean isCached(Currency c) {
        return dbHandler.isCached(c);
    }

    public Boolean isMetadataCached(Currency c) {
        return dbHandler.isMetadataCached(c);
    }

    public Boolean hasCache() {
        return dbHandler.hasCache();
    }
    public List<Currency> cachedListing() {
        return dbHandler.cachedListing();
    }

    public void injectCryptoHandler(CryptoHttpHandler c) {
        cryptoHttp = c;
    }

    public void injectSendgridHandler(SendgridHttpHandler s) {
        sendgridHttp = s;
    }

    public Boolean isInputOnline() {
        return isInputOnline;
    }

    public String getAPIKey() {
        return apiKey;
    }

    public void setAPIKey(String k) {
        apiKey = k;
    }
}