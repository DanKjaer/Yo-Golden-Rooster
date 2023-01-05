package BLL;

import BE.Category;
import BE.Movie;
import DAL.DB.ICategoryDatabaseAccess;
import DAL.DB.IMovieDatabaseAccess;
import DAL.DB.MovieDAO;

import java.util.List;
public class MovieManager {

    private ICategoryDatabaseAccess categoryDAO;
    private IMovieDatabaseAccess movieDAO;

    public MovieManager() {
        movieDAO = new MovieDAO();
    }

    public List<Movie> getMovies() throws Exception{
        return movieDAO.getMovies();
    }
    public Category createCategory(String category) throws Exception {
        return categoryDAO.createCategory(category);
    }

    public void removeCategory(Category deletedCategory) throws Exception {
        categoryDAO.removeCategory(deletedCategory);
    }

    public Category getCategories() {
        return categoryDAO.getCategories();
    }
    
    public Movie createMovie(String name, String fileLink) throws Exception {
        return movieDAO.createMovie(name, fileLink);
    }

    public void reMovie(Movie removedMovie) throws Exception{
        movieDAO.reMovie(removedMovie);
    }
}

