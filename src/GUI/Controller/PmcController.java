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
import java.util.Calendar;
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
    private String oldMovies = "";
    private boolean detectOldMovie;

    @Override
    public void setup() throws Exception {
        pmcModel = new PmcModel();
        updateMovieList();
        search();
        disableButtons();
        checkOldMovie();
        alertOldMovie();
    }

    private void disableButtons() {
        btnPlay.setDisable(true);
        btnRate.setDisable(true);
        btnDelete.setDisable(true);

    }

    private void checkOldMovie() {
        //Get this day 2 years ago
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -2);
        Date oldDate = calendar.getTime();

        for (int i = 0; i < lstMovie.getItems().size(); i++) {
            //Get last view and rating
            Movie movie = (Movie) lstMovie.getItems().get(i);
            Date date = (Date) clnLastView.getCellObservableValue(movie).getValue();
            Float rating = (Float) clnPersonalRating.getCellObservableValue(movie).getValue();
            String title = (String) clnTitle.getCellObservableValue(movie).getValue();

            //Display alert if rating is under 6 and is older than this day 2 years ago
            if (lstMovie.getItems().size()-1 == i && rating < 6.0 && date.before(oldDate)) {
                oldMovies = oldMovies + title + ".";
                detectOldMovie = true;
            } else if (rating < 6.0 && date.before(oldDate)) {
                oldMovies = oldMovies + title + ", ";
                detectOldMovie = true;
            }
        }
    }

    private void alertOldMovie() {
        if (detectOldMovie) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Old Movie!");
            alert.setHeaderText("You have a movie that haven't been watched for over two years with a rating below 6.0.\n" + "Old movie: " + oldMovies);
            alert.showAndWait();
        }
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
            alert.setContentText("Rating must be between 1.0 and 10.0\neg 6.9");
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
        alert.setHeaderText("Delete movie");
        alert.setContentText("Continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Movie removedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();
            pmcModel.reMovie(removedMovie);
        }
    }

    public void onClickMovie(MouseEvent mouseEvent) {
        Movie selectedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();

        txtTitle.setText(selectedMovie.getName());
        txtPersonalRating.setText(String.valueOf(selectedMovie.getRating()));
        if (selectedMovie.getLastview() != null) {
            txtLastView.setText(String.valueOf(selectedMovie.getLastview()));
        } else {
            txtLastView.setText("Never seen");
        }
        txtCategory.setText(selectedMovie.getCategories().toString());

        btnRate.setDisable(false);
        btnPlay.setDisable(false);
        btnDelete.setDisable(false);
    }

    private void search(){
        tfSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                PmcModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();

            }
        });
    }
}
