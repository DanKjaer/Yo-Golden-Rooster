package GUI.Model;

import BE.Category;
import BE.Movie;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PmcModel {

    private ObservableList<Movie> moviesToBeViewed;
    private CategoryModel categoryModel;

    private ObservableList<Category> categoryToBeViewed;
    private MovieManager mManager;

    public PmcModel() throws Exception {
        categoryModel = new CategoryModel();
        mManager = new MovieManager();

        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(mManager.getMovies());

        categoryToBeViewed = FXCollections.observableArrayList();
        categoryToBeViewed.addAll(mManager.getCategories());
    }

    public ObservableList<Movie> getObservableMovies() throws Exception {
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(mManager.getMovies());
        return moviesToBeViewed;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void createMovie(String name, String fileLink, String category) throws Exception{
        Movie m = mManager.createMovie(name, fileLink);

        moviesToBeViewed.add(m);
    }

    public void reMovie(Movie removedMovie) throws Exception {
        mManager.reMovie(removedMovie);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(mManager.getMovies());
    }

    public ObservableList<Category> getCategories() throws Exception{
        return categoryToBeViewed;
    }

    public void rateMovie(Movie ratedMovie, float rating) throws Exception {
        mManager.rateMovie(ratedMovie, rating);
    }
}
