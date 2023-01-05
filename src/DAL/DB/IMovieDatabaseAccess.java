package DAL.DB;

import BE.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.util.List;

public interface IMovieDatabaseAccess {

    List<Movie> getMovies() throws SQLServerException;
}
