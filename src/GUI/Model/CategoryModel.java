package GUI.Model;


import BE.Category;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class CategoryModel {
    private ObservableList<Category> categoriesToBeViewed;
    private MovieManager movieManager;

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
