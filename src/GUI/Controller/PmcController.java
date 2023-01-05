package GUI.Controller;

import GUI.Model.PmcModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Text;

public class PmcController extends BaseController {

    @FXML
    private TextField tfSearch;
    @FXML
    private TableView lstMovie;
    @FXML
    private TableColumn clnTitle, clnCategory, clnPersonalRating, clnLastView;
    @FXML
    private Button btnPlay, btnRate, btnCategory, btnDelete, btnAdd;
    @FXML
    private TextField tfRating;
    @FXML
    private ImageView imgMovie;
    @FXML
    private Text txtTitle, txtLastView, txtCategory, txtPersonalRating, txtRating;
    private PmcModel pmcModel;

    @Override
    public void setUp() {

    }

    public void handleAdd(ActionEvent actionEvent) {
    }

    public void handleDelete(ActionEvent actionEvent) {
    }

    public void handleEditCategory(ActionEvent actionEvent) {
    }

    public void handleSearch(InputMethodEvent inputMethodEvent) {
    }

    public void handlePlay(ActionEvent actionEvent) {
    }

    public void handleRate(ActionEvent actionEvent) {
    }

}
