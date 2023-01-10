package GUI.Controller;

import GUI.Model.PmcModel;
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
import java.rmi.server.ExportException;

public class AddMovieController extends BaseController {

    private PmcModel model;
    @FXML
    private Button btnSave, btnChoose, btnCancel;
    @FXML
    private TextField tfFilePath;
    @FXML
    public TextField tfTitle;
    @FXML
    private ListView lstCategory;

    @Override
    public void setup() {
        try {
            model = new PmcModel();
            lstCategory.setItems(model.getCategories());
            lstCategory.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }

    }

    /**
     * Saves a movie to our movie table, with the name, filepath and category of the movie.
     * @param actionEvent
     */
    @FXML
    private void handleSave(ActionEvent actionEvent) {
        String name = tfTitle.getText();
        String filePath = tfFilePath.getText();
        ObservableList categories = lstCategory.getSelectionModel().getSelectedItems();
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();

        try{
            model.createMovie(name,filePath, categories);
        } catch (Exception e) {
            e.printStackTrace();
            displayError(e);
        }
    }

    /**
     * Cancel button closes the AddMovieController stage.
     * the method "Cancel" that's called, is used to close the stage
     * btnCancel refers to the cancel button
     * @param actionEvent
     */

    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        cancel(btnCancel);
    }

    /**
     * FileChooser enables the choose button to open a local filepath.
     * f is the name of our file, and fc stands for filechooser.
     * @param actionEvent
     */
    @FXML
    private void handleChoose(ActionEvent actionEvent) {
        //create new stage for picking files
        FileChooser fc = new FileChooser();
        Stage stage = (Stage) btnChoose.getScene().getWindow();
        fc.setTitle("Select a Movie");
        //add valid filetypes
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Movie Files (*.mp4, *.mpeg4)", "*.mp4", "*.mpeg4"),
                new FileChooser.ExtensionFilter("MP4 Files (*.mp4)", "*.mp4"),
                new FileChooser.ExtensionFilter("MPEG4 Files (*.mpeg4)", "*.mpeg4")
        );
        //put selected file into textfield
        File f = fc.showOpenDialog(stage);
        tfFilePath.setText(f.getPath());
    }
}
