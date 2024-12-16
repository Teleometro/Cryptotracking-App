package major_project.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;
import major_project.model.Currency;
import javafx.scene.web.*;


public class URLCell extends TableCell<Currency, String>  {
    @Override 
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        Hyperlink link = new Hyperlink(item);
        if (item != null)
        {
            link.setOnAction((event) -> {
                try {
                    Stage stage = new Stage();
   
                    WebView webView = new WebView();
        
                    WebEngine webEngine = webView.getEngine();
        
                    webEngine.load(link.getText());
        
                    Scene scene = new Scene(webView, webView.getPrefWidth(), 
                                            webView.getPrefHeight());
                    scene.getStylesheets().add("test.css");
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                } 
    
            }); 
        }
         
        setGraphic(empty ? null : link);
    }
}
