package DAL.DB;

import BE.Category;

import java.io.IOException;
import java.sql.*;

public class CategoryDAO implements ICategoryDatabaseAccess{
    private DatabaseConnector dbcon;

    public CategoryDAO() throws IOException {
        dbcon = new DatabaseConnector();
    }

    public Category createCategory(String category) throws Exception {
        String sql = "INSERT INTO Category (name) VALUES (?);";
        int id = 0;

        try(Connection con = dbcon.getConnection()) {
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
            throw new Exception("Could not create category");
        }
        Category mCat = new Category(id, category);
        return mCat;

    }
    public void removeCategory(Category category) throws Exception {
        try(Connection con = dbcon.getConnection()){
            String sql = "DELETE FROM Category WHERE id = ?;";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1, category.getId());

            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new Exception("Could not remove Category", e);
        }
    }

    @Override
    public Category getCategories() {
        return null;
    }


}

//Action, animation, comedy, crime, drama, film noir, horror, thriller, war, western.