package bounswegroup1.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import bounswegroup1.model.Recipe;

public class RecipesMapper implements ResultSetMapper<List<Recipe>> {

	List<Recipe> res;
	Recipe curr;
	long lastId;
	
	public RecipesMapper(){
		res = new ArrayList<Recipe>();
		lastId = -1;
	}
	
	@Override
	public List<Recipe> map(int idx, ResultSet rs, StatementContext ctx) throws SQLException {
		if(rs.getLong("id") != lastId){
			curr = new Recipe(rs.getLong("id"), rs.getLong("user_id"), rs.getString("name"), rs.getString("description"));
			lastId = rs.getLong("id");
			res.add(curr);
		}
		
		curr.addIngredient(rs.getString("ingredient_id"), rs.getString("ingredient_name"), 
				rs.getLong("amount"), rs.getString("unit"));
		
		return res;
	}

}
