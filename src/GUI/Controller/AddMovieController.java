package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AddMovieController extends BaseController {
    @FXML
    private Button btnSave, btnChoose, btnCancel;
    @FXML
    private TextField tfFilePath;
    @FXML
    private ListView lstCategory;

    @Override
    public void setup() {

    }
    public void handleSave(ActionEvent actionEvent) {
    }

    public void handleCancel(ActionEvent actionEvent) {
        cancel(btnCancel);
    }

    public void handleChoose(ActionEvent actionEvent) {
    }

}
