package DAL.DB;

import BE.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements IMovieDatabaseAccess {

    private DatabaseConnector dbCon;

    public MovieDAO() throws IOException {
        dbCon = new DatabaseConnector();
    }

    @Override
    public List<Movie> getMovies() throws SQLException {

        //Make a list called allMovies, to store movies in, and return in the end
        ArrayList<Movie> allMovies = new ArrayList<>();

        //Try with resources to connect to DB
        try (Connection conn = dbCon.getConnection()){

            //SQL string, selects all movies from DB
            String sql = "SELECT * FROM Movie;";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            //Loop through rows from database result set
            while (rs.next()){
                //Map DB row to Movie object
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String filelink = rs.getString("filelink");
                double rating = rs.getInt("rating");
                Date lastview = rs.getDate("lastview");

                //Create Movie and add to list created in the beginning
                Movie movie = new Movie(id,name,rating,filelink,lastview);
                allMovies.add(movie);
            }
        } catch (Exception e){
            throw e;
        }
        return allMovies;
    }

    public Movie createMovie(String name, String fileLink) throws Exception{
        String sql = "INSERT INTO Movie (name, fileLink)VALUES (?,?);";
        int id = 0;

        try(Connection con = dbCon.getConnection()){
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, name);
            statement.setString(2, fileLink);

            statement.executeUpdate();

            ResultSet rSet = statement.getGeneratedKeys();

            if(rSet.next());{
                id = rSet.getInt(1);
            }
        }
        Movie movie = new Movie(id, name, fileLink);
        return movie;
    }

    public void reMovie(Movie movie) throws Exception{
        try(Connection con = dbCon.getConnection()){
            String sql = "DELETE FROM Movie WHERE id = ?;";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1,movie.getId());

            statement.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new Exception("Could not delete movie", e);
        }
    }


}
