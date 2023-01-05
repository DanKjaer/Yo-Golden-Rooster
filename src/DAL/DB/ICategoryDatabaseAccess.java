package DAL.DB;

import BE.Category;

import java.sql.SQLException;
import java.util.List;

public interface ICategoryDatabaseAccess {
    Category createCategory(String category) throws SQLException;

    void removeCategory(Category category) throws SQLException;

    List<Category> getCategories() throws SQLException;

}
