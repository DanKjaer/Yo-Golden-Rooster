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


    /**
     * Retrieves movies from database and makes a list of them
     * @return an arraylist of movies from the database
     * @throws SQLException - Throws an exception, if there is a communication mishap with the database.
     */
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


    /**
     * Creates a new movie in the database
     * @param name - The name of the movie
     * @param fileLink - The local file path, used to find the mp4 and mpeg.
     * @param categories - The categories of the movie.
     * @return newly created movie
     * @throws Exception - Throws an exception if you could not create a movie.
     */

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


    /**
     * Removes movie from database
     * @param movie - Movie refers to a table in our database.
     * @throws Exception - Throws an exception if deleting the movie failed.
     */

    public void reMovie(Movie movie) throws Exception{
        try(Connection con = dbCon.getConnection()){
            deleteMovieCategoryLink(movie, con);
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

    /**
     * Removes movie connection to categories
     * @param movie - The movie connection to the category
     * @param con - Connection, used to connect to the database, so we can skip the try/catch
     * @throws SQLException - Throws an exception, if there is a communication mishap with the database.
     */

    private void deleteMovieCategoryLink(Movie movie, Connection con) throws SQLException {
        String sql = "DELETE FROM CatMovie WHERE MovieId = ?;";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setInt(1,movie.getId());

        statement.executeUpdate();
    }


    /**
     * Updates movie in database with a personal rating
     * @param ratedMovie - References the movie that have been rated.
     * @param rating - The rating given to a specified movie.
     * @throws Exception - Throws an exception if rating the song failed.
     */

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


    /**
     * Updates the date of the last view of the movie in the database
     * @param movie - Uses a row in our movie table to find a specified id.
     * @throws Exception - Throws an exception if the movie does not update.
     */

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


    /**
     * Connects a movie and category
     * @param category - Uses the id from our category table.
     * @param movie - Uses the id from our movie table.
     * @param con - Used to connect to the database, without having to use a try/catch
     * @throws Exception - Throws an exception.
     */

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


    /**
     * Gets the catagories connected to a movie and puts them into a list
     * @param movieId - references the movieId column in our movie table.
     * @param con - Used to connect to the database, without having to use a try/catch
     * @return an arraylist of catagories
     * @throws SQLException - Throws an exception, if there is a communication mishap with the database.
     */

    private List<Category> getMovieCategories(int movieId, Connection con) throws SQLException {
        //Makes an arraylist with movie categories.
        ArrayList<Category> movieCategories = new ArrayList<>();
        //An SQL string that selects everything from category, and searches for a specified
        //Id in category and Movie, then joins it in the CatMovie table.
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
