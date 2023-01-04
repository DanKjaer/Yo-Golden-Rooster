package DAL.DB;

import BE.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieDAO {

    private DatabaseConnector dbCon;





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
