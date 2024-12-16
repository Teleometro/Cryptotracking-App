package major_project.view;

import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import major_project.model.Currency;
import major_project.presenter.Presenter;

public class ListingWindow {
    Stage stage;
    VBox tableBox;
    TableView<Currency> list;
    ImageView loadingImage;
    public ListingWindow() {
        stage = new Stage();
    }
    
    public void display(Presenter presenter) {
        
        Label dataLabel = new Label("Currencies");
        Button addCurrency = new Button("Add Currency");
        
        list = new TableView<>();
        
        list.setOnMousePressed((event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                presenter.add(list.getSelectionModel().getSelectedItem());              
            }
        }));
        
        loadingImage = new ImageView("load.gif");
        loadingImage.setScaleX(0.3);
        loadingImage.setScaleY(0.3);

        TableColumn<Currency, String> nameCollumn = new TableColumn<>("name");
        nameCollumn.setMinWidth(100);
        nameCollumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("name"));
 
        TableColumn<Currency, String> symbolCollumn = new TableColumn<>("symbol");
        symbolCollumn.setMinWidth(100);
        symbolCollumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("symbol"));
 
        list.setItems(null);
        list.getColumns().addAll(Arrays.asList(nameCollumn, symbolCollumn));

        tableBox = new VBox(10, list);
        tableBox.setAlignment(Pos.TOP_CENTER);
        VBox vb = new VBox(10, dataLabel, tableBox, addCurrency);

        vb.setAlignment(Pos.TOP_CENTER);
        vb.setSpacing(1);
        addCurrency.setOnAction(event -> {
            presenter.add(list.getSelectionModel().getSelectedItem());
        });
        
        Scene scene = new Scene(vb, 300, 400);
        scene.getStylesheets().add("test.css");
        stage.setScene(scene);
        stage.show();
    }

    public void setTable(List<Currency> cur) {
        ObservableList<Currency> currencies = FXCollections.observableArrayList(cur);
        list.setItems(currencies);
    }

    public void hideTable() {
        tableBox.getChildren().remove(list);
        tableBox.getChildren().add(loadingImage);
    }

    public void showTable() {
        tableBox.getChildren().add(list);
        tableBox.getChildren().remove(loadingImage); 
    }

    public void hide() {
        stage.hide();
    }
}

