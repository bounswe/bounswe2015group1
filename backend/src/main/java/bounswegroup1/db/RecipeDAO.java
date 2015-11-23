package bounswegroup1.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import bounswegroup1.mapper.RecipeMapper;
import bounswegroup1.mapper.RecipesMapper;
import bounswegroup1.model.Recipe;

public abstract class RecipeDAO {
    @SqlQuery("select * from recipes, recipe_ingredients where "
            + "recipes.id = :id and recipes.id = recipe_ingredients.recipe_id")
    @Mapper(RecipeMapper.class)
    abstract protected List<Recipe> _getRecipe(@Bind("id") Long recipeId);

    @SqlQuery("select * from recipes, recipe_ingredients where "
            + "recipes.id = recipe_ingredients.recipe_id")
    @Mapper(RecipesMapper.class)
    abstract protected List<List<Recipe>> _getRecipes();

    @SqlQuery("select * from recipes, recipe_ingredients where "
            + "recipes.id = recipe_ingredients.recipe_id" + "and recipes.user_id = :userId")
    @Mapper(RecipesMapper.class)
    abstract protected List<List<Recipe>> _getRecipesForUser(@Bind("userId") Long userId);

    @GetGeneratedKeys
    @SqlUpdate("insert into recipes (name, user_id, description) values (:name, :userId, :description)")
    abstract protected Long _createRecipe(@BindBean Recipe recipe);

    @SqlUpdate("insert into recipe_ingredients (recipe_id, ingredient_name, ingredient_id, amount, unit) "
            + "values (:id, :name, :ingredient_id, :amount, :unit)")
    abstract public void createIngredient(@Bind("id") Long id, @Bind("name") String name,
            @Bind("ingredient_id") String ingredientId, @Bind("amount") Long amount,
            @Bind("unit") String unit);

    public void createRecipe(Recipe recipe) {
        Long id = _createRecipe(recipe);
        recipe.setId(id);
        recipe.createIngredients(this);
    }

    public Recipe getRecipe(Long recipeId) {
        List<Recipe> res = _getRecipe(recipeId);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }

    public List<Recipe> getRecipes() {
        List<List<Recipe>> res = _getRecipes();

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }

    public List<Recipe> getRecipesForUser(Long userId) {
        List<List<Recipe>> res = _getRecipesForUser(userId);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }
}
