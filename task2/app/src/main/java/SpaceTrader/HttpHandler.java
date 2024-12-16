package SpaceTrader;


import SpaceTrader.Dummy.AccountInfo;

public interface HttpHandler {
    public String getStatus();
    public String signIn(String aTok);
    public String createAccount(String uname);
    public AccountInfo getAccount(String aTok);
    public String getLoans(String aTok);
    public String obtainLoan(String authTok, String type);
    public String getShips(String aTok);
    public String purchaseShip(String authTok, String loc, String type);
    public String getMyLoans(String aTok);
    public String getMyShips(String aTok);
    public String getMarket(String AuthTok, String loc);
    public String purchaseCargo(String AuthTok, String shipId, String good, int num);
    public String sellCargo(String AuthTok, String shipId, String good, int num);
    public String getNearLocations(String authTok);
    public String setFlightRoute(String authTok, String dest, String fId);
    public String getFlight(String authTok, String fId);
}
