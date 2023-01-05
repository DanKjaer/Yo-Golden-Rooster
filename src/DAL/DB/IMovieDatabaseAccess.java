package DAL.DB;

import BE.Movie;

import java.sql.SQLException;
import java.util.List;

public interface IMovieDatabaseAccess {

    public List<Movie> getMovies() throws SQLException;

    public Movie createMovie(String name, String fileLink) throws Exception;

    public void reMovie(Movie movie) throws Exception;
}
