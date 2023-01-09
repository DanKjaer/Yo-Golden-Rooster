package GUI.Controller;

import BE.Movie;
import GUI.Model.PmcModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
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
    public void setup() throws Exception {
        pmcModel = new PmcModel();
        updateMovieList();
        search();

    }

    /**
     * Opens a new window to add a new movie.
     *
     * @param actionEvent
     */
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
            stage.setResizable(false);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.showAndWait();
            updateMovieList();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    /**
     * Deletes selected movie from the list.
     *
     * @param actionEvent
     */
    @FXML
    private void handleDelete(ActionEvent actionEvent) throws Exception {
        try {
            reMovieAlert();
            updateMovieList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not delete movie", e);
        }
    }

    /**
     * Opens a new window to add and delete categories.
     *
     * @param actionEvent
     */
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
            stage.setResizable(false);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePlay(ActionEvent actionEvent) {
        try {
            Movie selectedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();
            File file = new File(selectedMovie.getFilelink());
            Desktop.getDesktop().open(file);
            pmcModel.updateDateOnMovie(selectedMovie);
            updateMovieList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRate(ActionEvent actionEvent) throws Exception {
        Movie selectedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();
        float rating = Float.parseFloat(tfRating.getText());

        if (rating >= 1 && rating <= 10) {
            pmcModel.rateMovie(selectedMovie, rating);
            updateMovieList();
            tfRating.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Something went wrong!");
            alert.setHeaderText("Error with rating!");
            alert.setContentText("Rating must be between 1 and 10. eg 6.9");
            tfRating.clear();
            alert.showAndWait();
        }
    }

    /**
     * Updates the list of movies from the database.
     */
    private void updateMovieList() throws Exception {
        pmcModel = getModel();

        clnTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        clnCategory.setCellValueFactory(new PropertyValueFactory<>("categories"));
        clnPersonalRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        clnLastView.setCellValueFactory(new PropertyValueFactory<>("lastview"));

        lstMovie.getColumns().addAll();
        lstMovie.setItems(pmcModel.getObservableMovies());
    }

    public void reMovieAlert() throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete that shit yo");
        alert.setHeaderText("Slå peder ihjel (Slet film)");
        alert.setContentText("Vil du udføre det?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Movie removedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();
            pmcModel.reMovie(removedMovie);
        }
    }

    public void search() {
        tfSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                pmcModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
            }
        });
    }

    public void onclickMovie (MouseEvent mouseEvent){

    }
}
