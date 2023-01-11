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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.List;

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
    private Text txtTitle, txtLastView, txtCategory, txtPersonalRating, txtRating;
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
            oldMovieList();
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
     * Checks the DB when the program starts for old movies with less than 6.0 rating,
     * and prepares a string to be show to the user if any is found.
     */
    private void checkOldMovie(List<Movie> list) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {

            //Add to oldMovies string and add a comma if it isn't the last one
            if(i == list.size()-1){
                sb.append(list.get(i).getName());
            }else{
                sb.append(list.get(i).getName() + ", ");
            }
            oldMovies = sb.toString();
            detectOldMovie = true;
        }
    }

    /**
     * Checks for old movies and adds them to a list
     */
    private void oldMovieList() {

        //Get this date 2 years ago
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -2);
        Date oldDate = calendar.getTime();
        List<Movie> list = new ArrayList<>();

        //Go through the list of movies and check for old movies shit ratings under 6
        for (int i = 0; i < lstMovie.getItems().size(); i++) {
            Movie movie = (Movie) lstMovie.getItems().get(i);
            Date date = (Date) clnLastView.getCellObservableValue(movie).getValue();
            Float rating = (Float) clnPersonalRating.getCellObservableValue(movie).getValue();
            String title = (String) clnTitle.getCellObservableValue(movie).getValue();

            if (date.before(oldDate) && rating < 6.0) {
                list.add(movie);
            }
        }
        checkOldMovie(list);
    }

    /**
     * Displays an alert if detectOldMovie is true with the old movies listed.
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
     * Rates the selected movie.
     */
    @FXML
    private void handleRate() {
        try {
            //Selects a movie. Parses the textfield from a string to a float.
            Movie selectedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();
            float rating = Float.parseFloat(tfRating.getText());

            //Checks if the value is between 1 and 10, then updates the movie with the rating and the list of movies.
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
            // It uses the getters from the object, to retrieve the values
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
            alert.setHeaderText("You are about to delete a movie");
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

    /**
     * Adds extra info of the movie from the DB
     */
    public void onClickMovie() {
        try {
            //Gets the selected movie and scrapes imdb page for their rating and poster
            Movie selectedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();
            String URL = selectedMovie.getWebsite();
            Document doc = Jsoup.connect(URL).get();
            String rating = doc.select("span.sc-7ab21ed2-1.eUYAaq").text();
            String poster = doc.select("img.ipc-image").attr("src");

            //Adds all the additional info
            txtTitle.setText(selectedMovie.getName());
            txtPersonalRating.setText(String.valueOf(selectedMovie.getRating()));
            txtLastView.setText(String.valueOf(selectedMovie.getLastview()));
            txtCategory.setText(selectedMovie.getCategories());
            txtRating.setText(rating.substring(0,3));
            imgMovie.setImage(new Image(poster));

            //Enables the rate, play and delete buttons
            btnRate.setDisable(false);
            btnPlay.setDisable(false);
            btnDelete.setDisable(false);
        } catch (IOException e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    /**
     * Enables the search function, making you able to search for either the title, category or rating in real time.
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

    /**
     * Opens the url of the movie on your standard browser.
     */
    public void handleImdb() {
        try {
            Movie selectedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();
            Desktop.getDesktop().browse(URI.create(selectedMovie.getWebsite()));
        } catch (IOException e) {
            displayError(e);
            e.printStackTrace();
        }
    }
}
