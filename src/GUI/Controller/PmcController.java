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
    private Button btnPlay, btnRate, btnDelete;
    @FXML
    private TextField tfRating;
    @FXML
    private ImageView imgMovie;
    @FXML
    private Text txtTitle, txtLastView, txtCategory, txtPersonalRating;
    private PmcModel pmcModel;
    private String oldMovies = "";
    private boolean detectOldMovie;

    @Override
    public void setup() {
        try {
            pmcModel = new PmcModel();
            updateMovieList();
            search();
            disableButtons();
            checkOldMovie();
            alertOldMovie();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    /**
     * Disables the play, rate and delete button.
     */
    private void disableButtons() {
        btnPlay.setDisable(true);
        btnRate.setDisable(true);
        btnDelete.setDisable(true);
    }

    /**
     * Checks the DB when the program starts for old movies with less than 6.0 rating
     */
    private void checkOldMovie() {
        //Get this day 2 years ago
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -2);
        Date oldDate = calendar.getTime();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lstMovie.getItems().size(); i++) {
            //Get last view, rating and title
            Movie movie = (Movie) lstMovie.getItems().get(i);
            Date date = (Date) clnLastView.getCellObservableValue(movie).getValue();
            Float rating = (Float) clnPersonalRating.getCellObservableValue(movie).getValue();
            String title = (String) clnTitle.getCellObservableValue(movie).getValue();

            //Add to oldMovies string
            if (date.before(oldDate) && rating < 6.0) {
                sb.append(title);
                if (i < lstMovie.getItems().size() - 2) {
                    sb.append(", ");
                }
                oldMovies = sb.toString();
                detectOldMovie = true;
            }
        }
    }

    /**
     * Displays an alert if detectOldMovie is true
     */
    private void alertOldMovie() {
        if (detectOldMovie) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Old Movie!");
            alert.setHeaderText("You have a movie that haven't been watched for over two years with a rating below 6.0.\n" + "Old movie: " + oldMovies + ".");
            alert.showAndWait();
        }
    }

    /**
     * Opens a new window to add a new movie.
     */
    @FXML
    private void handleAdd(ActionEvent actionEvent) {
        try {
            //Creating a new stage with the add movie view
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/AddMovieView.fxml"));
            AnchorPane pane = null;
            pane = (AnchorPane) loader.load();

            //Setting controller and model for the new view.
            AddMovieController controller = loader.getController();
            controller.setModel(super.getModel());
            controller.setup();

            //Opens the add movie window.
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
     */
    @FXML
    private void handleDelete() {
        try {
            reMovieAlert();
            updateMovieList();
        } catch (Exception e) {
            e.printStackTrace();
            displayError(e);
        }
    }

    /**
     * Opens a new window to add and delete categories.
     */
    @FXML
    private void handleEditCategory(ActionEvent actionEvent) {
        try {
            //Creating a new stage with the edit category view
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CategoryView.fxml"));
            AnchorPane pane = null;
            pane = (AnchorPane) loader.load();

            //Setting controller and model for the new view.
            CategoryController controller = loader.getController();
            controller.setModel(super.getModel());
            controller.setup();

            //Opens the edit category window.
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

    /**
     * Opens movie in default mediaplayer
     */
    @FXML
    private void handlePlay() {
        try {
            //Opens the selected movie on your desktop, using the file link from the database.
            Movie selectedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();
            File file = new File(selectedMovie.getFilelink());
            Desktop.getDesktop().open(file);
            pmcModel.updateDateOnMovie(selectedMovie);
            updateMovieList();
        } catch (Exception e) {
            e.printStackTrace();
            displayError(e);
        }
    }

    /**
     * A button used to rate the selected movie.
     */
    @FXML
    private void handleRate() {
        try {
            //Selects a movie. Parses the textfield from a string to a float, so you can add numbers.
            Movie selectedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();
            float rating = Float.parseFloat(tfRating.getText());

            //Checks if the value is between 1 and 10, if it is, it'll update the movie list.
            if (rating >= 1 && rating <= 10) {
                pmcModel.rateMovie(selectedMovie, rating);
                updateMovieList();
                tfRating.clear();
            }

            //If the value isn't between 1 and 10, an error will pop up.
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Something went wrong!");
                alert.setHeaderText("Error with rating!");
                alert.setContentText("Rating must be between 1.0 and 10.0\neg 6.9");
                tfRating.clear();
                alert.showAndWait();
            }
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    /**
     * Updates the list of movies from the database.
     */
    private void updateMovieList() {
        try {
            //Gives every column a property to look for in given object
            // It uses the get"X" from the object, to retrieve the values
            clnTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
            clnCategory.setCellValueFactory(new PropertyValueFactory<>("categories"));
            clnPersonalRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
            clnLastView.setCellValueFactory(new PropertyValueFactory<>("lastview"));

            //Add the Movies to the list
            lstMovie.getColumns().addAll();
            lstMovie.setItems(pmcModel.getObservableMovies());
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    /**
     * An alert that asks if you're sure you want to delete the movie.
     */
    private void reMovieAlert() {
        try {
            //Opens an alert box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete that shit yo");
            alert.setHeaderText("Delete movie");
            alert.setContentText("Continue?");
            Optional<ButtonType> result = alert.showAndWait();

            //Removes the movie if you press Ok.
            if (result.get() == ButtonType.OK) {
                Movie removedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();
                pmcModel.reMovie(removedMovie);
            }
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    /**+
     * Removes the greyed out fields once you select a movie in the movie table, enabling rate, play and delete.
     */
    @FXML
    private void onClickMovie() {
        Movie selectedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();

        txtTitle.setText(selectedMovie.getName());
        txtPersonalRating.setText(String.valueOf(selectedMovie.getRating()));
        if (selectedMovie.getLastview() != null) {
            txtLastView.setText(String.valueOf(selectedMovie.getLastview()));
        } else {
            txtLastView.setText("Never seen");
        }
        txtCategory.setText(selectedMovie.getCategories());

        btnRate.setDisable(false);
        btnPlay.setDisable(false);
        btnDelete.setDisable(false);
    }

    /**
     * Enables the search function, making you able to search for either the title, category or rating.
     */
    private void search(){
        //Adds a listener to our search bar, making it able to update in real time.
        tfSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                //Parse search query down to model layer.
                PmcModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });
    }
}
