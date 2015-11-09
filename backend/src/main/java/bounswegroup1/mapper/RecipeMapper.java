package bounswegroup1.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import bounswegroup1.model.Recipe;

public class RecipeMapper implements ResultSetMapper<Recipe> {
	private Recipe recipe;

	@Override
	public Recipe map(int idx, ResultSet rs, StatementContext ctx) throws SQLException {
		if(idx==0){
			// first row, create the recipe object
			recipe = new Recipe(rs.getLong("id"), rs.getLong("user_id"), rs.getString("name"), rs.getString("description"));
		}
		
		recipe.addIngredient(rs.getString("ingredient_id"), rs.getString("ingredient_name"), rs.getLong("amount"), rs.getString("unit"));
		
		return recipe;
	}

}
