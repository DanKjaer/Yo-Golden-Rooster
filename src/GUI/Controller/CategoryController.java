package GUI.Controller;

import BE.Category;
import GUI.Model.CategoryModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CategoryController extends BaseController{
    @FXML
    private TableColumn clnCategory;
    @FXML
    private TextField tfCategory;
    @FXML
    private Button btnAdd;
    @FXML
    private TableView lstCategory;

    private CategoryModel categoryModel;

    @Override
    public void setup() {
        categoryModel = getModel().getCategoryModel();

        updateCategoryList();
        disableAdd();
    }

    /**
     * Adds a new category with the name provided by the user and clears the textfield.
     */
    @FXML
    private void handleAdd() {
        try {
            String category = tfCategory.getCharacters().toString();
            categoryModel.addCategory(category);
            tfCategory.clear();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    /**
     * Deletes selected category.
     */
    @FXML
    private void handleDelete() {
        try {
            Category selectedCategory = (Category) lstCategory.getSelectionModel().getSelectedItem();
            categoryModel.removeCategory(selectedCategory);
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }

    /**
     * Adds the list of categories from the database.
     */
    private void updateCategoryList() {
        categoryModel = getModel().getCategoryModel();

        clnCategory.setCellValueFactory(new PropertyValueFactory<>("name"));

        lstCategory.getColumns().addAll();
        lstCategory.setItems(categoryModel.getObservableCategories());
    }

    /**
     * Disables the add button, if nothing has been written in the text field.
     */
    private void disableAdd(){
        tfCategory.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isTextFieldEmpty = newValue.trim().isEmpty();
            btnAdd.setDisable(isTextFieldEmpty);
        });
    }
}
