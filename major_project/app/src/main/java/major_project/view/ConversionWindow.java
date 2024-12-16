package major_project.view;

import java.util.Arrays;
import java.util.List;

import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import major_project.model.*;
import major_project.presenter.Presenter;

public class ConversionWindow {
    private Stage stage;
    private Currency selected;
    private List<Currency> curList;
    private VBox tableBox;
    private TableView<Currency> list;
    private ImageView loadingImage;
    
    public ConversionWindow(Currency s, List<Currency> l) {
        stage = new Stage();
        selected = s;
        curList = l;
    }
    
    public void display(Presenter presenter) {
        
        Label dataLabel = new Label("Currencies");
        Label tfLabel = new Label("enter amount to convert");
        TextField amount = new TextField();
        TextField email = new TextField();
        
        Button addCurrency = new Button("Convert to Currency");
        loadingImage = new ImageView("load.gif");
        
        list = new TableView<>();

        list.setOnMousePressed((event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                presenter.convert(selected, list.getSelectionModel().getSelectedItem(), amount.getText(), email.getText()); 
            }
        }));
        
        ObservableList<Currency> currencies = FXCollections.observableArrayList(curList);

        TableColumn<Currency, String> nameCollumn = new TableColumn<>("name");
        nameCollumn.setMinWidth(100);
        nameCollumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("name"));
 
        TableColumn<Currency, String> symbolCollumn = new TableColumn<>("symbol");
        symbolCollumn.setMinWidth(100);
        symbolCollumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("symbol"));
 
        list.setItems(currencies);
        list.getColumns().addAll(Arrays.asList(nameCollumn, symbolCollumn));

        tableBox = new VBox(list);
        HBox am = new HBox(new Label("amount to convert:"), amount);
        HBox em = new HBox(new Label("email to send to", email));
        VBox vb = new VBox(10, dataLabel, tableBox, tfLabel, am, em, addCurrency);
        vb.setSpacing(10);
        addCurrency.setOnAction(event -> {
            presenter.convert(selected, list.getSelectionModel().getSelectedItem(), amount.getText(), email.getText());
        });
        
        Scene scene = new Scene(vb, 300, 400);
        scene.getStylesheets().add("test.css");
        stage.setScene(scene);
        stage.show();
    }

    public void showReport(String text) {
        Alert alert = new Alert(AlertType.INFORMATION);

        DialogPane dialog = alert.getDialogPane();
        dialog.getStylesheets().add("test.css");

        alert.setHeaderText("Report");
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void showLoading() {
        tableBox.getChildren().remove(list);
        tableBox.getChildren().add(loadingImage);
    }

    public void hideLoading() {
        tableBox.getChildren().add(list);
        tableBox.getChildren().remove(loadingImage); 
    }



    public void hide() {
        stage.hide();
    }
}
