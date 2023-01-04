package GUI.Controller;

import GUI.Model.PmcModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/AddMovieView.fxml"));
            AnchorPane pane = null;
            pane = (AnchorPane) loader.load();

            AddMovieController controller = loader.getController();
            controller.setModel(super.getModel());
            controller.setUp();

            stage.setScene(new Scene(pane));
            stage.setTitle("Add Movie");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    public void handleDelete(ActionEvent actionEvent) {
    }

    public void handleEditCategory(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CategoryView.fxml"));
            AnchorPane pane = null;
            pane = (AnchorPane) loader.load();

            CategoryController controller = loader.getController();
            controller.setModel(super.getModel());
            controller.setUp();

            stage.setScene(new Scene(pane));
            stage.setTitle("Category");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            displayError(e);
            e.printStackTrace();
        }

    }

    public void handleSearch(InputMethodEvent inputMethodEvent) {
    }

    public void handlePlay(ActionEvent actionEvent) {
    }

    public void handleRate(ActionEvent actionEvent) {
    }

}
