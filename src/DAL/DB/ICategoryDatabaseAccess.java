package DAL.DB;

import BE.Category;

public interface ICategoryDatabaseAccess {
    Category createCategory(String category) throws Exception;

    void removeCategory(Category category) throws Exception;

    Category getCategories();

}
