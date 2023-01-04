package GUI.Controller;

import GUI.Model.PmcModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public abstract class BaseController {

    private PmcModel model;

    public void setModel(PmcModel model) {
        this.model = model;
    }

    public PmcModel getModel() {
        return model;
    }

    public abstract void setUp();

    public void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    public void cancel(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
}
