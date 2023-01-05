package BLL;


import BE.Category;
import BE.Movie;
import DAL.DB.CategoryDAO;
import DAL.DB.ICategoryDatabaseAccess;
import DAL.DB.IMovieDatabaseAccess;

import java.util.List;

public class MovieManager {
    private ICategoryDatabaseAccess categoryDAO;
    private IMovieDatabaseAccess movieDAO;





    public List<Movie> getMovies() throws Exception{
        return movieDAO.getMovies();
    }
    public Category createCategory(String category) throws Exception {
        return categoryDAO.CreateCategory(category);
    }

    public void removeCategory(Category deletedCategory) throws Exception {
        categoryDAO.removeCategory(deletedCategory);
    }

    public Category getCategories() {
        return categoryDAO.getCategories();

    }
}
