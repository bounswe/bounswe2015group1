package bounswegroup1.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import bounswegroup1.mapper.RatingMapper;
import bounswegroup1.model.Rating;
import bounswegroup1.mapper.RecipesMapper;
import bounswegroup1.model.Recipe;

public abstract class ConsumeDAO{
	
    @SqlUpdate("INSERT INTO user_consumption(user_id, recipe_id, created_at)"+
    "VALUES (:userId, :recipeId, now());"
)
    abstract public void _saveUserConsumption(@Bind("userId") Long userId, @Bind("recipeId") Long recipeId);

    @SqlQuery("select * from recipes,recipe_ingredients,tags,nutrition_recipe,nutritions"+
    " where recipes.id in "+
    " (select user_consumption.recipe_id from users, user_consumption where users.id = user_consumption.user_id and users.id = :id)"+
    " and recipes.id = recipe_ingredients.recipe_id"+
    " and recipes.id = tags.recipe_id"+
    " and nutritions.id = nutrition_recipe.nutrition_id"+
    " and recipes.id = nutrition_recipe.recipe_id"+
    " order by recipes.id,recipe_ingredients.ingredient_id")
    @Mapper(RecipesMapper.class)
    abstract protected List<List<Recipe>> _getConsumedRecipesForUser(@Bind("id") Long id);

	 public void saveUserConsumption(Long userId, Long recipeId){
		_saveUserConsumption(userId, recipeId);
	}	

	public List<Recipe> getConsumedRecipesForUser(Long userId) {
        List<List<Recipe>> res = _getConsumedRecipesForUser(userId);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }
}