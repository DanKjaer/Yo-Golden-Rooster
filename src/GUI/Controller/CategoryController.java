package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CategoryController extends BaseController{
    @FXML
    private TextField tfCategory;
    @FXML
    private Button btnAdd, btnDelete;
    @FXML
    private TableView lstCategory;

    public void handleAdd(ActionEvent actionEvent) {
    }

    public void handleDelete(ActionEvent actionEvent) {
    }

    @Override
    public void setUp() {

    }
}
