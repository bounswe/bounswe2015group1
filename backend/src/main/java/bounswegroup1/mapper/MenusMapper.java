package bounswegroup1.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import bounswegroup1.model.Recipe;
import bounswegroup1.model.Menu;

import org.joda.time.DateTime;


public class MenusMapper implements ResultSetMapper<List<Menu>> {

    List<Menu> res;
    Menu curr;
    long lastId;
    long lastRecipeId;

    public MenusMapper() {
        res = new ArrayList<Menu>();
        lastId = -1;
        lastRecipeId = -1;
    }

    @Override
    public List<Menu> map(int idx, ResultSet rs, StatementContext ctx) throws SQLException {
        if (rs.getLong("id") != lastId) {
            List<Long> recipeIds = new ArrayList<Long>();
            List<String> recipeNames = new ArrayList<String>();

            curr = new Menu(rs.getLong("id"), rs.getString("menu_name"), rs.getLong("user_id"), recipeIds, new DateTime(rs.getDate("created_at"));
            lastId = rs.getLong("id");

            curr.setPeriod(rs.getString("period"));
            curr.setDescription(rs.getString("description"));
            curr.setRecipeNames(recipeNames);
            lastRecipeId = -1;
            res.add(curr);
        }

        if (rs.getLong("recipe_id") != lastRecipeId) {
            curr.addRecipe(rs.getLong("recipe_id"));
            curr.addRecipeName(rs.getString("recipe_name"));
            lastRecipeId = rs.getLong("recipe_id");
        }


        return res;
    }

}
