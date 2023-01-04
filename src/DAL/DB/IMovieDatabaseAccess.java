package DAL.DB;

import BE.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.util.List;

public interface IMovieDatabaseAccess {

    public List<Movie> getMovies() throws SQLServerException;

    public Movie createMovie(String name, String fileLink) throws Exception;

    public void reMovie(Movie movie) throws Exception;
}
