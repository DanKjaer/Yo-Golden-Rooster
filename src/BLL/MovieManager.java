package BLL;

import BE.Category;
import BE.Movie;
import BLL.util.Searcher;
import DAL.DB.CategoryDAO;
import DAL.DB.ICategoryDatabaseAccess;
import DAL.DB.IMovieDatabaseAccess;
import DAL.DB.MovieDAO;

import java.io.IOException;
import java.util.List;
public class MovieManager {

    private ICategoryDatabaseAccess categoryDAO;
    private IMovieDatabaseAccess movieDAO;
    private Searcher searcher;

    public MovieManager() throws IOException {
        movieDAO = new MovieDAO();
        categoryDAO = new CategoryDAO();
        searcher = new Searcher();
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


    public void rateMovie(Movie ratedMovie, float rating) throws Exception {
        movieDAO.rateMovie(ratedMovie, rating);
    }

    public void updateDateOnMovie(Movie movie) throws Exception {
        movieDAO.updateDateOnMovie(movie);
    }

    public List<Movie> searchMovies(String query) throws Exception{
        List<Movie> allMovies = getMovies();
        List<Movie> searchResult = searcher.search(allMovies, query);
        return searchResult;
    }
}

