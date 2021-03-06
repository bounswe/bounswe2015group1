package bounswegroup1.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import bounswegroup1.model.Recipe;
import bounswegroup1.model.Nutrition;

import org.joda.time.DateTime;

public class RecipesMapper implements ResultSetMapper<List<Recipe>> {

    List<Recipe> res;
    Recipe curr;
    long lastId;
    String lastIngredientId;
    String lastTag;

    public RecipesMapper() {
        res = new ArrayList<Recipe>();
        lastId = -1;
        lastIngredientId = "-1";
        lastTag = "";
    }

    @Override
    public List<Recipe> map(int idx, ResultSet rs, StatementContext ctx) throws SQLException {
        if (rs.getLong("id") != lastId) {
            curr = new Recipe(rs.getLong("id"), rs.getLong("user_id"), rs.getString("name"),
                    rs.getString("description"),new DateTime(rs.getTimestamp("created_at").getTime()),
                    rs.getFloat("rating"));
            
            lastId = rs.getLong("id");

            curr.addNutritions(new Nutrition(rs.getLong("nutrition_id"),rs.getFloat("calories"),
                rs.getFloat("carbohydrate"),rs.getFloat("fats"),rs.getFloat("proteins"),
                rs.getFloat("sodium"),rs.getFloat("fiber"),rs.getFloat("cholesterol"),
                rs.getFloat("sugars"),rs.getFloat("iron")));

            lastIngredientId = "-1";
            res.add(curr);
        }

        if (!rs.getString("ingredient_id").equals(lastIngredientId)) {
            curr.addIngredient(rs.getString("ingredient_id"), rs.getString("ingredient_name"),
                rs.getLong("amount"));

            lastIngredientId = rs.getString("ingredient_id");
        }

        String tag = rs.getString("tag");
        if(tag != null){
            if(!curr.isContainTag(rs.getString("tag"))){
                curr.addTags(rs.getString("tag"));
            }
        }

        return res;
    }

}
