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

    public abstract void setup() throws Exception;

    /**
     * Method used to display errors in the other controller classes
     * @param t - the error that have been caught
     */
    public void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    /**
     * Method called in other controller classes, used to close windows in case of cancel button used.
     * @param btn - the button pressed in the method.
     */
    public void cancel(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }
}
