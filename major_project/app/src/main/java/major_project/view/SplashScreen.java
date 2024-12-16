package major_project.view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import major_project.presenter.Presenter;

public class SplashScreen {
    Scene scene;
    Stage stage;
    ProgressBar loadingBar;
    public SplashScreen() {
        stage = new Stage();
    }
    
    public void display(Presenter presenter) {
        
        ImageView splash = new ImageView("splosh.png");
        loadingBar = new ProgressBar(0);
        loadingBar.setMaxWidth(640);
        loadingBar.setMinWidth(640);
        loadingBar.setMaxHeight(20);
        Button skip = new Button("skip load");
        skip.setMaxHeight(20);
        skip.setMaxWidth(100);
        skip.minWidth(80);

        skip.setOnAction(e -> loadingBar.setProgress(1));

        HBox hb = new HBox(2, loadingBar, skip);


        VBox vb = new VBox(0, splash, hb);

        
        scene = new Scene(vb, 720, 500);
        scene.getStylesheets().add("test.css");
        stage.setScene(scene);
        stage.show();
    }

    public void addProgress(double d) {
        loadingBar.setProgress(loadingBar.getProgress()+d);
    }

    public double getProgress() {
        return loadingBar.getProgress();
    }

    public Scene getScene() {
        return scene;
    }

    public void hide() {
        stage.hide();
    }
}
