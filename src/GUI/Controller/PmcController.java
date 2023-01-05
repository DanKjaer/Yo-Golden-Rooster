package GUI.Controller;

import GUI.Model.PmcModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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
    public void setup() {
        updateMovieList();
    }

    @FXML
    private void handleAdd(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/AddMovieView.fxml"));
            AnchorPane pane = null;
            pane = (AnchorPane) loader.load();

            AddMovieController controller = loader.getController();
            controller.setModel(super.getModel());
            controller.setup();

            stage.setScene(new Scene(pane));
            stage.setTitle("Add Movie");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            displayError(e);
            e.printStackTrace();
        }
        updateMovieList();
    }

    @FXML
    private void handleDelete(ActionEvent actionEvent) {
        updateMovieList();
    }

    @FXML
    private void handleEditCategory(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CategoryView.fxml"));
            AnchorPane pane = null;
            pane = (AnchorPane) loader.load();

            CategoryController controller = loader.getController();
            controller.setModel(super.getModel());
            controller.setup();

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

    @FXML
    private void handleSearch(InputMethodEvent inputMethodEvent) {
    }

    @FXML
    private void handlePlay(ActionEvent actionEvent) {
    }

    @FXML
    private void handleRate(ActionEvent actionEvent) {
    }

    private void updateMovieList(){
        pmcModel = getModel();

        clnTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        clnCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        clnPersonalRating.setCellValueFactory(new PropertyValueFactory<>("PersonalRating"));
        clnLastView.setCellValueFactory(new PropertyValueFactory<>("LastView"));

        lstMovie.getColumns().addAll();
        lstMovie.setItems(pmcModel.getObservableMovies());
    }
}