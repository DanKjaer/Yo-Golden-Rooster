package DAL.DB;

import BE.Category;
import BE.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

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
                float rating = rs.getFloat("rating");
                Date lastview = rs.getDate("lastview");
                List<Category> categories = getMovieCategories(id, conn);

                //Create Movie and add to list created in the beginning
                Movie movie = new Movie(id,name,rating,filelink,lastview, categories);
                allMovies.add(movie);

            }
        } catch (Exception e){
            throw e;
        }
        return allMovies;
    }

    public Movie createMovie(String name, String fileLink, List<Category> categories) throws Exception{
        String sql = "INSERT INTO Movie (name, fileLink, lastview)VALUES (?,?,GETDATE());";
        int id = 0;

        try(Connection con = dbCon.getConnection()){
            PreparedStatement statement = con.prepareStatement(sql, RETURN_GENERATED_KEYS);

            statement.setString(1, name);
            statement.setString(2, fileLink);

            statement.executeUpdate();

            ResultSet rSet = statement.getGeneratedKeys();

            if(rSet.next());{
                id = rSet.getInt(1);
            }
            Movie movie = new Movie(id, name, fileLink, categories);
            for(Category category: categories){
                addCategoryToMovie(category, movie, con);
            }
            return movie;
        } catch (Exception e) {
            throw new Exception(e);
        }
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

    @Override
    public void rateMovie(Movie ratedMovie, float rating) throws Exception {
        try (Connection conn = dbCon.getConnection()) {
            String sql = "UPDATE Movie SET rating = ? WHERE id = ?;";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setFloat(1, rating);
            stmt.setInt(2, ratedMovie.getId());

            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new Exception("Could not rate song", e);
        }
    }

    public void updateDateOnMovie(Movie movie) throws Exception {
        String sql = "UPDATE Movie " +
                "SET lastview = GETDATE() " +
                "WHERE id = " + movie.getId() + ";";

        try(Connection con = dbCon.getConnection()){
            PreparedStatement statement = con.prepareStatement(sql);

            statement.executeUpdate();
        }
        catch(Exception e){
            throw new Exception(e);
        }
    }

    private void addCategoryToMovie(Category category, Movie movie, Connection con)throws Exception{
        //try(Connection con = dbCon.getConnection()){

            String sql = "INSERT INTO CatMovie(CategoryId, MovieId) VALUES (?,?);";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1, category.getId());
            statement.setInt(2, movie.getId());

            statement.executeUpdate();
        //} catch(Exception e){
           // throw new Exception(e);
       // }
    }
    private List<Category> getMovieCategories(int movieId, Connection con) throws SQLException {
        ArrayList<Category> movieCategories = new ArrayList<>();

        String sql = "SELECT Category.* FROM CatMovie JOIN Category ON CategoryId = Category.Id WHERE movieId =?;";

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1,movieId);
        ResultSet rSet = statement.executeQuery();

        while(rSet.next()){

            int categoryId = rSet.getInt("id");
            String name = rSet.getString("name");

            Category category = new Category(categoryId, name);
            movieCategories.add(category);
        }
        return movieCategories;
    }

}
