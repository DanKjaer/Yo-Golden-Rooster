package GUI.Controller;

import GUI.Model.AddMovieModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddMovieController extends BaseController {

    private AddMovieModel aMM;

    @FXML
    private Button btnSave, btnChoose, btnCancel;
    @FXML
    private TextField tfFilePath;
    @FXML
    public TextField tfTitle;
    @FXML
    private ListView lstCategory;

    @Override
    public void setup() throws Exception {
        aMM = new AddMovieModel();
        lstCategory.setItems(aMM.getCategories());
        lstCategory.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Saves a movie to our movie table, with the name, filepath and category of the movie.
     * @param actionEvent
     */
    public void handleSave(ActionEvent actionEvent) {
        String name = tfTitle.getText();
        String filePath = tfFilePath.getText();
        String category = lstCategory.getSelectionModel().getSelectedItems().toString();

        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();

        try{
            aMM.createMovie(name,filePath, category);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cancel button closes the AddMovieController stage.
     * the method "Cancel" that's called, is used to close the stage
     * btnCancel refers to the cancel button
     * @param actionEvent
     */

    public void handleCancel(ActionEvent actionEvent) {
        cancel(btnCancel);
    }

    /**
     * FileChooser enables the choose button to open a local filepath.
     * f is the name of our file, and fc stands for filechooser.
     * @param actionEvent
     */
    public void handleChoose(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        Stage stage = (Stage) btnChoose.getScene().getWindow();
        File f = fc.showOpenDialog(stage);
        tfFilePath.setText(f.getPath());
    }
}
