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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.io.File;
import java.io.IOException;
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
     * Disables buttons to prevent to use buttons
     */
    private void disableButtons() {
        btnPlay.setDisable(true);
        btnRate.setDisable(true);
        btnDelete.setDisable(true);
    }

    /**
     * Checks the DB when the program starts for old movies with less than 6.0 rating
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
            displayError(e);
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

    /**
     * Opens movie in default mediaplayer
     * @param actionEvent
     */
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
            displayError(e);
        }
    }

    @FXML
    private void handleRate(ActionEvent actionEvent) {
        try {
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
            pmcModel = getModel();

            clnTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
            clnCategory.setCellValueFactory(new PropertyValueFactory<>("categories"));
            clnPersonalRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
            clnLastView.setCellValueFactory(new PropertyValueFactory<>("lastview"));

            lstMovie.getColumns().addAll();
            lstMovie.setItems(pmcModel.getObservableMovies());
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    public void reMovieAlert() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete that shit yo");
            alert.setHeaderText("Delete movie");
            alert.setContentText("Continue?");
            Optional<ButtonType> result = alert.showAndWait();
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
     * @param mouseEvent
     * @throws IOException
     */
    public void onClickMovie(MouseEvent mouseEvent) throws IOException {
        //Gets the selected movie and scrapes imdb page for their rating and poster
        Movie selectedMovie = (Movie) lstMovie.getSelectionModel().getSelectedItem();
        //String URL = selectedMovie;
        //Document doc = Jsoup.connect(URL).get();
        //String rating = doc.select("span.sc-7ab21ed2-1.eUYAaq").text();
        //String poster = doc.select("img.ipc-image").attr("src");

        //Adds all the additional info
        txtTitle.setText(selectedMovie.getName());
        txtPersonalRating.setText(String.valueOf(selectedMovie.getRating()));
        if (selectedMovie.getLastview() != null) {
            txtLastView.setText(String.valueOf(selectedMovie.getLastview()));
        } else {
            txtLastView.setText("Never seen");
        }
        txtCategory.setText(selectedMovie.getCategories());
        //txtRating.setText(rating.substring(0,3));
        //imgMovie.setImage(new Image(poster));

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
