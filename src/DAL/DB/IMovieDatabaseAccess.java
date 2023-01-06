package DAL.DB;

import BE.Movie;

import java.sql.SQLException;
import java.util.List;

public interface IMovieDatabaseAccess {

    List<Movie> getMovies() throws SQLException;

    Movie createMovie(String name, String fileLink) throws Exception;

    void reMovie(Movie movie) throws Exception;

    void updateDateOnMovie(Movie movie) throws Exception;
}
