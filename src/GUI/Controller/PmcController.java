package GUI.Controller;

import GUI.Model.PmcModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Text;

public class PmcController extends BaseController {
    public Button btnAdd;
    public Button btnDelete;
    public Button btnCategory;
    public TextField tfSearch;
    public TableView lstMovie;
    public TableColumn clnTitle;
    public TableColumn clnCategory;
    public TableColumn clnPersonalRating;
    public TableColumn clnLastView;
    public Button btnPlay;
    public TextField tfRating;
    public Button btnRate;
    public ImageView imgMovie;
    public Text txtTitle;
    public Text txtRating;
    public Text txtPersonalRating;
    public Text txtCategory;
    public Text txtLastView;
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
