package DAL.DB;

import BE.Category;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements ICategoryDatabaseAccess {

    private DatabaseConnector dbCon;
    public CategoryDAO() throws IOException {
        dbCon = new DatabaseConnector();
    }

    public List<Category> getCategories() throws SQLException {

        //Make a list called allCategories, to store categories in, and return in the end
        ArrayList<Category> allCategories = new ArrayList<>();

        //Try with resources to connect to DB
        try (Connection conn = dbCon.getConnection()){

            String sql = "SELECT * FROM Category;";

            Statement statement = conn.createStatement();
            ResultSet rSet = statement.executeQuery(sql);

            while(rSet.next()){
                int id = rSet.getInt("id");
                String name = rSet.getString("name");

                Category category = new Category(id, name);
                allCategories.add(category);
            }
            return allCategories;
        } catch (SQLException e){
            throw new RuntimeException("Could not get categories from database", e);
        }
    }

    public Category createCategory(String category) throws SQLException {
        String sql = "INSERT INTO Category (name) VALUES (?);";

        int id = 0;

        try(Connection con = dbCon.getConnection()) {
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, category);

            statement.executeUpdate();
            ResultSet rSet = statement.getGeneratedKeys();

            if(rSet.next());{
                id = rSet.getInt(1);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("Could not create category", e);
        }
        Category mCat = new Category(id, category);
        return mCat;

    }
    public void removeCategory(Category category) throws SQLException {
        try(Connection con = dbCon.getConnection()){
            String sql = "DELETE FROM Category WHERE id = ?;";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1, category.getId());

            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("Could not remove Category", e);
        }
    }

}

//Action, animation, comedy, crime, drama, film noir, horror, thriller, war, western.