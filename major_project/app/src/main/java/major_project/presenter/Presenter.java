package major_project.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import major_project.model.*;
import major_project.view.AppWindow;
import major_project.view.ConversionWindow;
import major_project.view.ListingWindow;
import major_project.view.SplashScreen;

public class Presenter {
    private final Model model;
    private final AppWindow view;
    private final SplashScreen splash;
    private final ListingWindow listing;
    private ConversionWindow conversion;

    private final ExecutorService pool = Executors.newFixedThreadPool(2, runnable -> {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread ;
    });

    
    public Presenter(Model model, AppWindow view) {
        this.model = model;
        this.view = view;
        splash = new SplashScreen();
        this.listing = new ListingWindow();
        this.conversion = null;
    }


    public void start() {
        
        splash.display(this);
        
        Presenter presenter = this;
        Task<Integer> task = new Task<>() {
            @Override
            protected Integer call() {
            while (splash.getProgress() < 1) {
                try {
                    Thread.sleep(75);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                splash.addProgress(0.005);
            }
            Platform.runLater(() -> {
                view.display(presenter);
                splash.hide();
            });
            
            return 0;
        }};
        pool.execute(task);

        if (model.apiKeyMissing()) {
            view.ErrorPopup("Some environment variables appear to be missing.\nProgram will still work if you are in offline mode");
        }
    }

    public void selectSpecialDeal(Currency selectedItem) {
        if (selectedItem == null) {
            view.ErrorPopup("Select a currency!");
            return;
        }
        view.selectSpecialDeal(selectedItem);
        model.setSpecialDealCurrency(selectedItem);
    }

    public Scene getSplashScene() {
        return splash.getScene();
    }

    public void currencyAddPopup() {
        listing.display(this);
        final Boolean useCache;
        if(model.isInputOnline() && model.hasCache()) {
            if (view.YesNoPopup("Cache hit! use it?")) {
                useCache = true;
            } 
            else useCache = false;           
        }
        else useCache = false;
        listing.hideTable();
        Task<Integer> task = new Task<>() {
            List<Currency> cur;
            @Override
            protected Integer call() {
                if(useCache) {
                    cur = model.cachedListing();
                    Platform.runLater(() -> {
                        listing.setTable(cur);
                        listing.showTable();
                    });
                }
                else {
                    try {
                        cur = model.getListing();
                        Platform.runLater(() -> {
                            listing.setTable(cur);
                            listing.showTable();
                        });
                    } catch (Exception e) {
                        view.ErrorPopup("error getting listing");
                        return -1;
                    }
                }
                return 0;
            
        }};
        pool.execute(task);
    }

    public void conversionPopup(Currency selected) {
        if (selected == null) {
            view.ErrorPopup("Select a currency to convert!");
            return;
        }
        List<Currency> l = new ArrayList<>();
        l.clear();
        l.addAll(model.getElements());
        l.remove(selected);
        conversion = new ConversionWindow(selected, l);
        conversion.display(this);
    }

    

    public void add(Currency selectedItem) {
        if (selectedItem == null) {
            view.ErrorPopup("Select a currency!");
            return;
        }

        final Boolean useCache;

        if(model.isInputOnline() && model.isMetadataCached(selectedItem)) {
            if (view.YesNoPopup("Cache hit! use it?")) {
                useCache = true;
            }
            else useCache = false; 
        }
        else useCache = false;
        Currency selectedClone = new Currency(selectedItem.getName() + "(Loading)", selectedItem.getSymbol(), -1);
        selectedClone.setLogoURL("load.gif");
        view.add(selectedClone);
        Task<Integer> task = new Task<>() {
            @Override
            protected Integer call() {
                if(useCache) {
                    Currency cur = model.getCatchedMetaData(selectedItem);
                    model.addElement(cur);
                    Platform.runLater(() -> {
                        selectedClone.setLogoURL(null);
                        view.getList().getItems().remove(selectedClone);
                        view.add(cur);
                        view.DisableClearButton(false);
                        view.DisableRemoveButton(false);
                        if (view.getList().getItems().size() >= 2) {
                            view.DisableConversionButton(false);
                        }
                    });
                }
                else {
                    try {
                        Currency cur = model.getMetadata(selectedItem);
                        model.addElement(cur);
                        Platform.runLater(() -> {
                            selectedClone.setLogoURL(null);
                            view.getList().getItems().remove(selectedClone);
                            view.add(cur);
                            view.DisableClearButton(false);
                            view.DisableRemoveButton(false);
                            if (view.getList().getItems().size() >= 2) {
                                view.DisableConversionButton(false);
                            }
                        });
                    } catch (Exception e) {
                        view.ErrorPopup("error getting metadata");
                        return -1;
                    }
                }
                return 0;
            
        }};
        pool.execute(task);
        
        
        
        listing.hide();
    }

    public void remove(Currency selectedItem) {
        if (selectedItem == null) {
            view.ErrorPopup("Select a currency to remove!");
            return;
        }

        model.removeElement(selectedItem);
        view.getList().getItems().remove(selectedItem);
        if (view.getList().getItems().size() == 0) {
            view.DisableClearButton(true);
            view.DisableRemoveButton(true);
        }
        if (view.getList().getItems().size() < 2) {
            view.DisableConversionButton(true);
        }
    }

    public void clear() {
        view.getList().getItems().clear();
        model.clear();
        view.DisableClearButton(true);
        view.DisableRemoveButton(true);
    }

    public void clearCache() {
        if(view.YesNoPopup("Are you sure you want to clear the cache?")) {
            model.clearCache();
        }
    }

    public void convert(Currency from, Currency to, String amount, String emailTo) {
        final double amountDouble;
        if (to == null) {
            view.ErrorPopup("Select a currency to convert to!");
            return;
        }
        if (from == null) {
            view.ErrorPopup("Select a currency to convert from!");
            return;
        }
        try {
            amountDouble = Double.parseDouble(amount);
        } catch (Exception e) {
            view.ErrorPopup("type an amount to convert!");
            return;
        }
        conversion.showLoading();
        
        Task<Integer> task = new Task<>() {
            String out;
            @Override
            protected Integer call() {
                
                try {
                    out = model.convert(from, to, amountDouble, emailTo);
                } catch (Exception e1) {
                    out = "Failure";
                    view.ErrorPopup("error getting conversion");
                }
                Platform.runLater(() -> {
                    conversion.hideLoading();
                    conversion.hide();
                    conversion.showReport(out);
                });
                
                return 0;
        }};
        pool.execute(task);
        
    }
}
