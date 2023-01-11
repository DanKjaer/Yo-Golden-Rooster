package GUI.Model;

import BE.Category;
import BE.Movie;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class PmcModel {

    private static ObservableList<Movie> moviesToBeViewed;
    private final CategoryModel categoryModel;
    private final ObservableList<Category> categoryToBeViewed;
    private static MovieManager mManager;

    public PmcModel() throws Exception {
        categoryModel = new CategoryModel();
        mManager = new MovieManager();

        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(mManager.getMovies());

        categoryToBeViewed = FXCollections.observableArrayList();
        categoryToBeViewed.addAll(mManager.getCategories());
    }

    public static void searchMovie(String query) throws Exception {
        List<Movie> searchResults = mManager.searchMovies(query);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(searchResults);
    }

    public ObservableList<Movie> getObservableMovies() throws Exception {
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(mManager.getMovies());
        return moviesToBeViewed;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

<<<<<<< Updated upstream
    public void createMovie(String name, String fileLink, List<Category> categories) throws Exception{
        Movie m = mManager.createMovie(name, fileLink, categories);
=======

    public void createMovie(String name, String fileLink, List<Category> categories, String website) throws Exception{
        Movie m = mManager.createMovie(name, fileLink, categories, website);
>>>>>>> Stashed changes

        moviesToBeViewed.add(m);
    }

    public void reMovie(Movie removedMovie) throws Exception {
        mManager.reMovie(removedMovie);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(mManager.getMovies());
    }

    public ObservableList<Category> getCategories() {
        return categoryToBeViewed;
    }

    public void rateMovie (Movie ratedMovie,float rating) throws Exception {
            mManager.rateMovie(ratedMovie, rating);
    }

    public void updateDateOnMovie (Movie movie) throws Exception {
            mManager.updateDateOnMovie(movie);
    }
}