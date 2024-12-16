package major_project.view;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import major_project.model.Currency;


public class ImageCell extends TableCell<Currency, String>  {
    @Override 
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty) {
            if (item != null) {
                ImageView image = new ImageView(item);
                image.setFitWidth(100);
                image.setFitHeight(100);
                setGraphic(image);

            }
            else {
                setGraphic(null);
            }
        }
        else {
            setGraphic(null);
        }
    }
}
