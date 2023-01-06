package GUI.Controller;

import BE.Movie;
import GUI.Model.PmcModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

public class PmcController extends BaseController {

    @FXML
    private TextField tfSearch;
    @FXML
    private TableView lstMovie;
    @FXML
    private TableColumn clnTitle, clnCategory, clnLastView;
    @FXML
    private TableColumn clnPersonalRating;
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
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
        updateMovieList();
    }

    @FXML
    private void handleDelete(ActionEvent actionEvent) throws Exception {
        try{
            reMovieAlert();
        } catch (Exception e){
            e.printStackTrace();
            throw new Exception("Could not delete movie", e);
        }
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

        clnTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        //clnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        clnPersonalRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        clnLastView.setCellValueFactory(new PropertyValueFactory<>("lastview"));

        lstMovie.getColumns().addAll();
        lstMovie.setItems(pmcModel.getObservableMovies());
    }

    public void reMovieAlert() throws Exception{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete that shit yo");
        alert.setHeaderText("Slå peder ihjel (Slet film)");
        alert.setContentText("Vil du udføre det?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            Movie removedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();
            pmcModel.reMovie(removedMovie);
        }
    }
}