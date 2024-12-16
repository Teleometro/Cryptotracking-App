package major_project.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import major_project.model.*;
import major_project.presenter.Presenter;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class AppWindow {
    Stage stage;
    private Scene scene = null;
    private TableView<Currency> selectedCurrencies = null;
    private TableView<Currency> specialCurrency;
    
    private Button removeSelected;
    private Button clearList;
    private Button conversion;
    MediaPlayer player;

    public AppWindow(Stage pStage) {
        stage = pStage;
        selectedCurrencies = new TableView<>();
        TableColumn<Currency, String> logoCollumn = new TableColumn<>("logo");
        logoCollumn.setMinWidth(100);
        logoCollumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("logoURL"));
        logoCollumn.setCellFactory(new Callback<TableColumn<Currency, String>, TableCell<Currency, String>>() {
            @Override 
            public TableCell<Currency, String> call(TableColumn<Currency, String> list) {
                return new ImageCell();
            }
        });
        TableColumn<Currency, String> nameCollumn = new TableColumn<>("name");
        nameCollumn.setMinWidth(100);
        nameCollumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("name"));
 
        TableColumn<Currency, String> symbolCollumn = new TableColumn<>("symbol");
        symbolCollumn.setMinWidth(100);
        symbolCollumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("symbol"));

        TableColumn<Currency, String> websiteCollumn = new TableColumn<>("website");
        websiteCollumn.setMinWidth(150);
        websiteCollumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("website"));
        websiteCollumn.setCellFactory(new Callback<TableColumn<Currency, String>, TableCell<Currency, String>>() {
            @Override 
            public TableCell<Currency, String> call(TableColumn<Currency, String> list) {
                return new URLCell();
            }
        });

        TableColumn<Currency, String> dateLaunchedCollumn = new TableColumn<>("date launched");
        dateLaunchedCollumn.setMinWidth(200);
        dateLaunchedCollumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("dateLaunched"));

        TableColumn<Currency, String> descriptionCollumn = new TableColumn<>("description");
        descriptionCollumn.setMinWidth(800);
        descriptionCollumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("description"));

        selectedCurrencies.getColumns().addAll(Arrays.asList(logoCollumn, nameCollumn, symbolCollumn, websiteCollumn, dateLaunchedCollumn, descriptionCollumn));
    
        
    }

    public void display(Presenter presenter) {
        selectedCurrencies.setOnMousePressed((event -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                presenter.conversionPopup(selectedCurrencies.getSelectionModel().getSelectedItem());              
            }
        }));
        File file = new File("src/main/resources/to_the_mooooon.wav");
        if(!file.exists()) {
            ErrorPopup("Music File missing! did you remove it?");
        }
        else {
            try {
                Media media = new Media(new File("src/main/resources/to_the_mooooon.wav").toURI().toString());
                player = new MediaPlayer(media);  
                player.play();
                player.setOnEndOfMedia(() -> {
                        player.seek(Duration.ZERO);
                        player.play();
                    
                }); 
            } catch (Exception e) {
                ErrorPopup("Error playing music. This is likely a codec issue");
            }
           
        }

        

        Label dataLabel = new Label("Currencies");
        Button addCurrency = new Button("Add Currency");
        removeSelected = new Button("Remove Selected");
        removeSelected.setDisable(true);
        clearList = new Button("Clear List");
        Button clearCache = new Button("Clear Cache");
        clearCache.setStyle("-fx-text-fill: #ff7737;");
        clearCache.setOnAction(e -> presenter.clearCache());
        clearList.setDisable(true);
        conversion = new Button("currency conversion");
        conversion.setDisable(true);



        Label specialDealLabel = new Label("Special Deal on:");

        specialCurrency = new TableView<>();
        specialCurrency.setMaxHeight(290);
        TableColumn<Currency, String> logoCollumn = new TableColumn<>("logo");
        logoCollumn.setMinWidth(100);
        logoCollumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("logoURL"));
        logoCollumn.setCellFactory(new Callback<TableColumn<Currency, String>, TableCell<Currency, String>>() {
            @Override 
            public TableCell<Currency, String> call(TableColumn<Currency, String> list) {
                return new ImageCell();
            }
        });
        TableColumn<Currency, String> nameCollumn = new TableColumn<>("name");
        nameCollumn.setMinWidth(100);
        nameCollumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("name"));
 
        TableColumn<Currency, String> symbolCollumn = new TableColumn<>("symbol");
        symbolCollumn.setMinWidth(100);
        symbolCollumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("symbol"));

        specialCurrency.getColumns().addAll(Arrays.asList(logoCollumn, nameCollumn, symbolCollumn));
        specialCurrency.getItems().add(new Currency("None", "N/A", 0));
        Button specialDealButton = new Button("set Special Deal");
        specialDealButton.setOnAction(event -> presenter.selectSpecialDeal(selectedCurrencies.getSelectionModel().getSelectedItem()));
        VBox specialDealBox = new VBox(specialDealLabel, specialCurrency, specialDealButton);
        specialDealBox.setSpacing(10);


        Button muteBut = new Button("music: on");
        muteBut.setOnAction(event -> {
            if(muteBut.getText().equals("music: on")) {
                muteBut.setText("music: off");
                player.setMute(true);
            }
            else {
                muteBut.setText("music: on");
                player.setMute(false);
            }
        });

        
        VBox vb = new VBox(10,
                            dataLabel, 
                            selectedCurrencies, 
                            new HBox(addCurrency, 
                            removeSelected, 
                            clearList, 
                            conversion,
                            clearCache),
                            new HBox(400, muteBut, 
                            specialDealBox));
        vb.setPadding(new Insets(40));

        addCurrency.setOnAction(e -> presenter.currencyAddPopup());
        removeSelected.setOnAction(e -> presenter.remove(selectedCurrencies.getSelectionModel().getSelectedItem()));
        clearList.setOnAction(e -> presenter.clear());
        conversion.setOnAction(e -> presenter.conversionPopup(selectedCurrencies.getSelectionModel().getSelectedItem()));
        scene = new Scene(vb, 1000, 600);
        scene.getStylesheets().add("test.css");
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
    }

    public void selectSpecialDeal(Currency cur) {
        specialCurrency.getItems().clear();
        specialCurrency.getItems().add(cur);
    }

    public void rebuildList(Collection<Currency> elements)
    {
        selectedCurrencies.getItems().setAll(elements);
    }

    public Scene getScene() {
        return scene;
    }

    public TableView<Currency> getList() {
        return selectedCurrencies;
    }

    public void add(Currency cur) {
        
        selectedCurrencies.getItems().add(cur);
    }

    public void DisableRemoveButton(boolean b) {
        removeSelected.setDisable(b);
    }

    public void DisableClearButton(boolean b) {
        clearList.setDisable(b);
    }

    public void DisableConversionButton(boolean b) {
        conversion.setDisable(b);
    }

    public void ErrorPopup(String str) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(str);
        DialogPane dialog = alert.getDialogPane();
        dialog.getStylesheets().add("test.css");
        alert.showAndWait();
    }

    public boolean YesNoPopup(String str) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        
        DialogPane dialog = alert.getDialogPane();
        dialog.getStylesheets().add("test.css");

        alert.setContentText(str);
        alert.getButtonTypes().setAll(new ButtonType("no", ButtonData.NO), new ButtonType("yes", ButtonData.YES));

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            return result.get().getButtonData() == ButtonData.YES;
        }
        return false;
    }

    public void hide() {
        stage.hide();
    }
}
