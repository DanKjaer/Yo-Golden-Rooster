package DAL.DB;

import BE.Category;

public interface ICategoryDatabaseAccess {
    public Category CreateCategory(String category);

    public void removeCategory(Category category);

    Category getCategories();

}
