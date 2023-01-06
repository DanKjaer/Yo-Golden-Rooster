package GUI.Controller;

import GUI.Model.AddMovieModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
    }

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
    //Cancel button closes the AddMovieController stage.
    public void handleCancel(ActionEvent actionEvent) {
        cancel(btnCancel);

    }

    /**
     * FileChooser enables the choose button to open a local filepath.
     */
    public void handleChoose(ActionEvent actionEvent) {
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
