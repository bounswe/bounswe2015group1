package bounswegroup1.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import bounswegroup1.model.Recipe;
import bounswegroup1.model.Nutrition;

import org.joda.time.DateTime;

public class RecipeMapper implements ResultSetMapper<Recipe> {
    private Recipe recipe;
    String lastIngredientId;

    public RecipeMapper(){
        lastIngredientId = "-1";
    }

    @Override
    public Recipe map(int idx, ResultSet rs, StatementContext ctx) throws SQLException {
        if (idx == 0) {
            // first row, create the recipe object
            recipe = new Recipe(rs.getLong("id"), rs.getLong("user_id"), rs.getString("name"),
                    rs.getString("description"),new DateTime(rs.getTimestamp("created_at").getTime()),
                    rs.getFloat("rating"));


            recipe.addNutritions(new Nutrition(rs.getLong("nutrition_id"),rs.getFloat("calories"),
                rs.getFloat("carbohydrate"),rs.getFloat("fats"),rs.getFloat("proteins"),
                rs.getFloat("sodium"),rs.getFloat("fiber"),rs.getFloat("cholesterol"),
                rs.getFloat("sugars"),rs.getFloat("iron")));
        }

        if (!rs.getString("ingredient_id").equals(lastIngredientId)) {
            recipe.addIngredient(rs.getString("ingredient_id"), rs.getString("ingredient_name"),
                rs.getLong("amount"));
            
            lastIngredientId = rs.getString("ingredient_id");
        }


        String tag = rs.getString("tag");
        if(tag != null){
            if(!recipe.isContainTag(rs.getString("tag"))){
                recipe.addTags(rs.getString("tag"));
            }
        }


        return recipe;
    }


}
