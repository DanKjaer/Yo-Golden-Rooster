package GUI.Controller;

import GUI.Model.PmcModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class AddMovieController extends BaseController {

    public TextField tfUrl;
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
            //Creates a list of categories to be shown and allows the user to select multiple.
            lstCategory.setItems(model.getCategories());
            lstCategory.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }

    }

    /**
     * Saves a movie to our DB, with the name, filepath, website url and category of the movie.
     */
    @FXML
    private void handleSave() {
        String name = tfTitle.getText();
        String filePath = tfFilePath.getText();
        ObservableList categories = lstCategory.getSelectionModel().getSelectedItems();
        String website = tfUrl.getText();
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
        try{
            model.createMovie(name,filePath, categories, website);
        } catch (Exception e) {
            e.printStackTrace();
            displayError(e);
        }
    }

    /**
     * Cancel button closes the AddMovieController stage.
     * the method "Cancel" that's called, is used to close the stage
     * btnCancel refers to the cancel button
     */
    @FXML
    private void handleCancel() {
        cancel(btnCancel);
    }

    /**
     * FileChooser enables the choose button to open a local filepath.
     */
    @FXML
    private void handleChoose() {
        try {
            //create new stage for picking files
            FileChooser fileChooser = new FileChooser();
            Stage stage = (Stage) btnChoose.getScene().getWindow();
            fileChooser.setTitle("Select a Movie");
            //Add valid filetypes
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Movie Files (*.mp4, *.mpeg4)", "*.mp4", "*.mpeg4"),
                    new FileChooser.ExtensionFilter("MP4 Files (*.mp4)", "*.mp4"),
                    new FileChooser.ExtensionFilter("MPEG4 Files (*.mpeg4)", "*.mpeg4")
            );
            //Put selected file into textfield
            File file = fileChooser.showOpenDialog(stage);
            tfFilePath.setText(file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            displayError(e);
        }
    }
}
