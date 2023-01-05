package GUI.Controller;

import GUI.Model.CategoryModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CategoryController extends BaseController{
    public TableColumn clnCategory;
    @FXML
    private TextField tfCategory;
    @FXML
    private Button btnAdd, btnDelete;
    @FXML
    private TableView lstCategory;

    private CategoryModel categoryModel;

    @Override
    public void setup() {
        categoryModel = getModel().getCategoryModel();

        updateCategoryList();
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

    private void updateCategoryList() {
        categoryModel = getModel().getCategoryModel();

        clnCategory.setCellValueFactory(new PropertyValueFactory<>("name"));

        lstCategory.getColumns().addAll();
        lstCategory.setItems(categoryModel.getObservableCategories());
    }
}
