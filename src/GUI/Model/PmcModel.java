package GUI.Model;

import BE.Movie;
import BLL.MovieManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PmcModel {

    private ObservableList<Movie> moviesToBeViewed;

    private MovieManager mManager;

    public PmcModel() throws Exception {
        moviesToBeViewed = FXCollections.observableArrayList();
        moviesToBeViewed.addAll(mManager.getMovies());
    }

    public ObservableList<Movie> getObservableMovies(){
        return moviesToBeViewed;
    }

    public void createMovie(String name, String fileLink) throws Exception{
        Movie m = mManager.createMovie(name, fileLink);

        moviesToBeViewed.add(m);
    }
    public void reMovie(Movie removedMovie) throws Exception {
        mManager.reMovie(removedMovie);
        moviesToBeViewed.clear();
        moviesToBeViewed.addAll(mManager.getMovies());
    }

}
