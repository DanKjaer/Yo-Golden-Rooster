package GUI.Model;

import BE.Category;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The methods of CategoryModel gets called from the controller, and sends information onwards to the manager in BLL
 * categoriesToBeViewed is an ObservableList containing the categories of our private movie collection.
 */
public class CategoryModel {

    private final ObservableList<Category> categoriesToBeViewed;
    private final MovieManager movieManager;

    public CategoryModel() throws Exception {
        movieManager = new MovieManager();
        categoriesToBeViewed = FXCollections.observableArrayList();
        categoriesToBeViewed.addAll(movieManager.getCategories());
    }

    public void createCategory(String category) throws Exception {
        Category c = movieManager.createCategory(category);
        categoriesToBeViewed.add(c);
    }

    public ObservableList<Category> getObservableCategories() {
        return categoriesToBeViewed;
    }

    public void removeCategory(Category removedCategory) throws Exception {
        movieManager.removeCategory(removedCategory);
        categoriesToBeViewed.clear();
        categoriesToBeViewed.addAll(movieManager.getCategories());
    }

    public void addCategory(String category) throws Exception {
        createCategory(category);
    }
}
