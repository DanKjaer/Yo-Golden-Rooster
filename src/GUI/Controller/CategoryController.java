package GUI.Controller;

import GUI.Model.CategoryModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CategoryController extends BaseController{
    @FXML
    private TextField tfCategory;
    @FXML
    private Button btnAdd, btnDelete;
    @FXML
    private TableView lstCategory;

    private CategoryModel categoryModel;

    @Override
    public void setUp() {
        categoryModel = getModel().getCategoryModel();
    }

    public void handleAdd(ActionEvent actionEvent) {
        try {
            String category = tfCategory.getCharacters().toString();
            categoryModel.addCategory(category);
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    public void handleDelete(ActionEvent actionEvent) {
    }

}
