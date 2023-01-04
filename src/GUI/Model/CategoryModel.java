package GUI.Model;

import BE.Category;
import BLL.MovieManager;
import javafx.collections.ObservableList;

public class CategoryModel {

    private ObservableList<Category> mCategory;

    private MovieManager mManager;




    public void createCategory(String category) throws Exception {
        Category c = mManager.createCategory(category);
        mCategory.add(c);
    }

    public void removeCategory(Category removedCategory) throws Exception {
        mManager.removeCategory(removedCategory);
        mCategory.clear();
        mCategory.addAll(mManager.getCategories());
    }

}
