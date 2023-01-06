package BLL;

import BE.Category;
import BE.Movie;
import DAL.DB.CategoryDAO;
import DAL.DB.ICategoryDatabaseAccess;
import DAL.DB.IMovieDatabaseAccess;
import DAL.DB.MovieDAO;

import java.io.IOException;
import java.util.List;
public class MovieManager {

    private ICategoryDatabaseAccess categoryDAO;
    private IMovieDatabaseAccess movieDAO;

    public MovieManager() throws IOException {
        movieDAO = new MovieDAO();
        categoryDAO = new CategoryDAO();
    }
    public List<Movie> getMovies() throws Exception{
        return movieDAO.getMovies();
    }
    public List<Category> getCategories() throws Exception{
        return categoryDAO.getCategories();
    }
    public Category createCategory(String category) throws Exception {
        return categoryDAO.createCategory(category);
    }

    public void removeCategory(Category deletedCategory) throws Exception {
        categoryDAO.removeCategory(deletedCategory);
    }
    
    public Movie createMovie(String name, String fileLink) throws Exception {
        return movieDAO.createMovie(name, fileLink);
    }

    public void reMovie(Movie removedMovie) throws Exception{
        movieDAO.reMovie(removedMovie);
    }
}

