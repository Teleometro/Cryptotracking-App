package SpaceTrader.Dummy;

import java.time.*;
import java.util.HashMap;
import java.util.Map;


import SpaceTrader.HttpHandler;

public class DummyHandler implements HttpHandler {
    Map<String, Account> users = new HashMap<>();
    int nextToken = 1;
    @Override
    public String getStatus() {
        return "spacetraders is currently online and available to play";
    }

    @Override
    public String signIn(String aTok) {
        if (users.containsKey(aTok)) return aTok;
        return null;
    }

    @Override
    public String createAccount(String uname) {
        for (AccountInfo acc : users.values()) {
            if(acc.username.equals(uname)) {
                return "error: Username has already been claimed.";
            }
        }
        Account acc = new Account();
        acc.username = uname;
        acc.JoinDate = LocalDateTime.now().toString();
        users.put(Integer.toString(nextToken), acc);
        nextToken += 1;
        return Integer.toString(nextToken-1);
    }

    @Override
    public AccountInfo getAccount(String aTok) {
        return users.get(aTok);
    }

    @Override
    public String getLoans(String aTok) {
        return "{\"loans\": [{\"amount\": 200000,\n\"collateralRequired\": false, \"rate\": 40,\"termInDays\": 2, \"type\": \"STARTUP\"}, {\"amount\": 200000, \"collateralRequired\": false, \"rate\": 40, \"termInDays\": 2, \"type\": \"Custom\"}]}";
    }

    @Override
    public String obtainLoan(String authTok, String type) {
        if (!users.containsKey(authTok)) return null;
        users.get(authTok).credits = 200000;
        if (type.equals("STARTUP")) return "{\"credits\":200000,\"loan\":{\"id\":\"cl1svpsg968491715s6qk54qq3v\",\"due\":\"2022-04-12T06:02:32.648Z\",\"repaymentAmount\":280000,\"status\":\"CURRENT\",\"type\":\"STARTUP\"}}";
        return "{\"credits\":200000,\"loan\":{\"id\":\"cl1svpsg968491715s6qk54qq3v\",\"due\":\"2022-04-12T06:02:32.648Z\",\"repaymentAmount\":280000,\"status\":\"CURRENT\",\"type\":\"Custom\"}}";
    }

    @Override
    public String getShips(String aTok) {
        return "{ \"shipListings\": [ { \"class\": \"MK-I\", \"flightPlanId\": \"cmyk\", \"manufacturer\": \"Gravager\", \"maxCargo\": 300, \"plating\": 10, \"purchaseLocations\": [ { \"location\": \"OE-PM-TR\", \"price\": 184000 }, { \"location\": \"OE-NY\", \"price\": 184000 } ], \"speed\": 1, \"type\": \"GR-MK-I\", \"weapons\": 5 }, { \"class\": \"MK-I\", \"manufacturer\": \"Jackshaw\", \"maxCargo\": 100, \"plating\": 5, \"purchaseLocations\": [ { \"location\": \"OE-PM-TR\", \"price\": 94000 }, { \"location\": \"OE-NY\", \"price\": 94000 } ], \"speed\": 2, \"type\": \"JW-MK-I\", \"weapons\": 5 }, { \"class\": \"MK-I\", \"manufacturer\": \"Electrum\", \"maxCargo\": 100, \"plating\": 5, \"purchaseLocations\": [ { \"location\": \"OE-NY\", \"price\": 302400 } ], \"speed\": 2, \"type\": \"EM-MK-I\", \"weapons\": 10 } ] }";
    }

    @Override
    public String purchaseShip(String authTok, String loc, String type) {
        if(authTok != null) {
            users.get(authTok).ShipCount += 1;
            users.get(authTok).credits -= 184000;
        }
        return "{}";
    }

    @Override
    public String getMyLoans(String aTok) {
        return "{ \"loans\": [ { \"id\": \"cl1stzuoq55424515s699dh9xgc\", \"due\": \"2022-04-12T05:14:22.872Z\", \"repaymentAmount\": 280000, \"status\": \"CURRENT\", \"type\": \"STARTUP\" } ] }";
    }

    @Override
    public String getMyShips(String aTok) {
        return "{ \"ships\": [ { \"id\": \"cl1sv9lvv64843215s6vtvc6sr2\", \"flightPlanId\": \"cmyk\",  \"location\": \"OE-PM-TR\", \"x\": -20, \"y\": 5, \"cargo\": [ { \"good\": \"FUEL\", \"quantity\": 20, \"totalVolume\": 20 } ], \"spaceAvailable\": 50, \"type\": \"JW-MK-I\", \"class\": \"MK-I\", \"maxCargo\": 50, \"loadingSpeed\": 25, \"speed\": 1, \"manufacturer\": \"Jackshaw\", \"plating\": 5, \"weapons\": 5 } ] }";
    }

    @Override
    public String getMarket(String AuthTok, String loc) {
        return "{ \"marketplace\":  [ { \"pricePerUnit\": 4, \"quantityAvailable\": 406586, \"symbol\": \"METALS\", \"volumePerUnit\": 1 }, { \"pricePerUnit\": 231, \"quantityAvailable\": 5407, \"symbol\": \"MACHINERY\", \"volumePerUnit\": 4 }, { \"pricePerUnit\": 8, \"quantityAvailable\": 406586, \"symbol\": \"CHEMICALS\", \"volumePerUnit\": 1 }, { \"pricePerUnit\": 1, \"quantityAvailable\": 462806, \"symbol\": \"FUEL\", \"volumePerUnit\": 1 }, { \"pricePerUnit\": 127, \"quantityAvailable\": 19961, \"symbol\": \"SHIP_PLATING\", \"volumePerUnit\": 2 }, { \"pricePerUnit\": 30, \"quantityAvailable\": 403, \"symbol\": \"DRONES\", \"volumePerUnit\": 2 }, { \"pricePerUnit\": 1283, \"quantityAvailable\": 8738, \"symbol\": \"SHIP_PARTS\", \"volumePerUnit\": 5 } ] }";
    }

    @Override
    public String purchaseCargo(String AuthTok, String shipId, String good, int num) {
        return "{}";
    }

    @Override
    public String getNearLocations(String authTok) {
        return "{ \"locations\": [ { \"name\": \"Carth\", \"symbol\": \"OE-CR\", \"type\": \"PLANET\", \"x\": 16, \"y\": 17 }, { \"name\": \"Koria\", \"symbol\": \"OE-KO\", \"type\": \"PLANET\", \"x\": -48, \"y\": -7 }, { \"name\": \"Ucarro\", \"symbol\": \"OE-UC\", \"type\": \"PLANET\", \"x\": -75, \"y\": 82 }, { \"name\": \"Prime\", \"symbol\": \"OE-PM\", \"type\": \"PLANET\", \"x\": 20, \"y\": -25 } ] }";
    }

    @Override
    public String setFlightRoute(String authTok, String dest, String fId) {
        return "{}";
    }

    @Override
    public String getFlight(String authTok, String fId) {
        return "{ \"flightPlan\": { \"arrivesAt\": \"2021-03-08T06:41:19.658Z\", \"departure\": \"OE-PM-TR\", \"destination\": \"OE-PM\", \"distance\": 1, \"fuelConsumed\": 1, \"fuelRemaining\": 19, \"id\": \"ckm07t6ki0038060jv7b2x5gk\", \"shipId\": \"ckm07ezq50354ti0j1drcey9v\", \"terminatedAt\": \"2021-03-08T06:41:18.752Z\", \"timeRemainingInSeconds\": 0 } }";
    }

    @Override
    public String sellCargo(String AuthTok, String shipId, String good, int num) {
        return "{}";
    }
    
}
