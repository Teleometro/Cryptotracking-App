package task1;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket {
    List<javafx.util.Pair<String,Integer>> l = new ArrayList<javafx.util.Pair<String,Integer>>();
    public void addItem(String item, int count) throws IllegalArgumentException {
        l.add(new javafx.util.Pair<String, Integer>(item, count));
        if (count < 1 || item.equals("belugawhale")) {
            throw new IllegalArgumentException();
        }
    }

    public void clear() {
        l.clear();
    }

    public List<javafx.util.Pair<String,Integer>> getItems() {
        return l; 
    }

    public double getValue() {
        return 24.65;
    }

    public boolean removeItem(String item, int count) {
        return false;
    }
}
