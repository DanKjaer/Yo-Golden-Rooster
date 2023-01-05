import GUI.Controller.PmcController;
import GUI.Model.PmcModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GUI/View/PmcView.fxml"));
        Parent root = loader.load();

        PmcController controller = loader.getController();
        controller.setModel(new PmcModel());
        controller.setUp();

        primaryStage.setTitle("Private Movie Collection");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}