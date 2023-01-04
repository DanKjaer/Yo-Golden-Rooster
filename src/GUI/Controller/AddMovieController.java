package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AddMovieController extends BaseController {
    public Button btnSave;
    public Button btnCancel;
    public TextField tfFilePath;
    public Button btnChoose;
    public ListView lstCategory;

    @Override
    public void setUp() {

    }
    public void handleSave(ActionEvent actionEvent) {
    }

    public void handleCancel(ActionEvent actionEvent) {
        cancel(btnCancel);
    }

    public void handleChoose(ActionEvent actionEvent) {
    }

}
