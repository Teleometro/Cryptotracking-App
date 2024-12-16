package major_project.model;


public class Currency {
    String name;
    String symbol;
    int id;
    String logoURL;
    String description;
    String dateLaunched;
    String website;
    Boolean hasMetadata;

    public Currency() {
        this.hasMetadata = false;
    }

    public Currency(String name, String symbol, int id) {
        this.name = name;
        this.symbol = symbol;
        this.id = id;
        this.hasMetadata = false;
    }

    public String getName() {
        return name;
    }
    public String getSymbol() {
        return symbol;
    }
    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
    public String getDateLaunched() {
        return dateLaunched;
    }

    public String getWebsite() {
        return website;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String uri) {
        logoURL = uri;
    }

    public boolean hasMetadata() {
        return hasMetadata;
    }
     
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Currency)) return false;
        
        Currency other = (Currency)o;
        if (name.equals(other.getName()) 
            && symbol.equals(other.getSymbol()) 
            && id == other.getId()) {
            return true;
        }

        return false;
    }
}
