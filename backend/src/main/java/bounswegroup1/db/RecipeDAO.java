package bounswegroup1.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import bounswegroup1.mapper.RecipeMapper;
import bounswegroup1.mapper.RecipesMapper;
import bounswegroup1.model.Recipe;
import bounswegroup1.model.Nutrition;

public abstract class RecipeDAO {
    @SqlQuery("select * from recipes, recipe_ingredients,tags,nutrition_recipe,nutritions"+
    " where recipes.id =:id"+
    " and recipes.id = recipe_ingredients.recipe_id"+
    " and recipes.id = tags.recipe_id"+
    " and nutritions.id = nutrition_recipe.nutrition_id"+
    " and recipes.id = nutrition_recipe.recipe_id"+
    " order by recipes.id,recipe_ingredients.ingredient_id")
    @Mapper(RecipeMapper.class)
    abstract protected List<Recipe> _getRecipe(@Bind("id") Long recipeId);

    @SqlQuery("select * from recipes, recipe_ingredients,tags,nutrition_recipe,nutritions"+
    " where recipes.id = recipe_ingredients.recipe_id"+
    " and recipes.id = tags.recipe_id"+
    " and nutritions.id = nutrition_recipe.nutrition_id"+
    " and recipes.id = nutrition_recipe.recipe_id"+
    " order by recipes.id,recipe_ingredients.ingredient_id")
    @Mapper(RecipesMapper.class)
    abstract protected List<List<Recipe>> _getRecipes();

    @SqlQuery("select * from recipes, recipe_ingredients,tags,nutrition_recipe,nutritions"+
    " where recipes.user_id = :userId"+
    " and recipes.id = recipe_ingredients.recipe_id"+
    " and recipes.id = tags.recipe_id"+
    " and nutritions.id = nutrition_recipe.nutrition_id"+
    " and recipes.id = nutrition_recipe.recipe_id"+
    " order by recipes.id,recipe_ingredients.ingredient_id")
    @Mapper(RecipesMapper.class)
    abstract protected List<List<Recipe>> _getRecipesForUser(@Bind("userId") Long userId);

    @SqlQuery("select tags.tag from recipes ,tags where recipes.user_id = :userId "+
            " and recipes.id = tags.recipe_id order by recipes.id")
    abstract protected List<String> _getTagsForUser(@Bind("userId") Long userId);

    @GetGeneratedKeys
    @SqlUpdate("insert into recipes (name, user_id, description,created_at,rating)"+
    " values (:name, :userId, :description,:createdAt,:rating)")
    abstract protected Long _createRecipe(@BindBean Recipe recipe);

    @SqlUpdate("insert into recipe_ingredients (recipe_id, ingredient_name, ingredient_id, amount, unit) "
            + "values (:id, :name, :ingredient_id, :amount, '')")
    abstract public void createIngredient(@Bind("id") Long id, @Bind("name") String name,
            @Bind("ingredient_id") String ingredientId, @Bind("amount") Long amount);
    
    @SqlQuery("select tag from tags where recipe_id = :id")
    abstract public List<String> getTagsForRecipe(@Bind("id") Long recipeId);
    
    @SqlBatch("insert into tags (recipe_id, tag) values (:id, :tag)")
    abstract public void insertTagsForRecipe(@Bind("id") Long recipeId, @Bind("tag") List<String> tag);

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO nutritions(calories, carbohydrate, fats, proteins, sodium, fiber, cholesterol,"+
       "sugars, iron) values (:calories,:carbohydrate,:fats,:proteins,:sodium,:fiber,:cholesterol,:sugars,:iron)")
    abstract public Long _createNutrition(@Bind("calories") Float calories,@Bind("carbohydrate") Float carbohydrate,
        @Bind("fats") Float fats,@Bind("proteins") Float proteins,@Bind("sodium") Float sodium,
        @Bind("fiber") Float fiber,@Bind("cholesterol") Float cholesterol,@Bind("sugars") Float sugars,
        @Bind("iron") Float iron);

    @SqlUpdate("insert into nutrition_recipe (nutrition_id, recipe_id) values (:nutritionId, :recipeId)")
    abstract public void createNutritionRecipe(@Bind("nutritionId") Long nutritionId,@Bind("recipeId") Long recipeId);


    @SqlQuery("select * from recipes, recipe_ingredients,tags,nutrition_recipe,nutritions"+
    " where recipes.id = recipe_ingredients.recipe_id"+
    " and recipes.id = tags.recipe_id"+
    " and nutritions.id = nutrition_recipe.nutrition_id"+
    " and recipes.id = nutrition_recipe.recipe_id"+
    " and recipes.id in"+
    "    ("+
    "        (select distinct(rec3.id) from recipes rec1,recipes rec3,tags tg1,tags tg2"+
    "        where rec1.id = :recId"+
    "        and rec3.id != :recId"+   
    "        and tg2.recipe_id = rec3.id"+
    "        and tg1.recipe_id = rec1.id"+
    "       and (lower(tg1.tag) like lower('%'||tg2.tag||'%')"+
    "            or lower(tg2.tag) like lower('%'||tg1.tag||'%'))"+
    "        )"+
    "        union"+
    "        (select distinct(rec2.id) from recipes rec1,recipes rec2"+
    "        where rec1.id = :recId"+
    "        and rec2.id != :recId"+
    "        and (lower(rec2.name) like lower('%'||rec1.name||'%')"+
    "        or lower(rec2.description) like lower('%'||rec1.description||'%')"+
    "        or lower(rec1.name) like lower('%'||rec2.name||'%')"+
    "        or lower(rec1.description) like lower('%'||rec2.description||'%')))"+
    "    )"+
    " order by recipes.id,recipe_ingredients.ingredient_id")
    @Mapper(RecipesMapper.class)
    abstract protected List<List<Recipe>> _getRecommendedRecipeByRecipeId(@Bind("recId") Long recipeId);

     @SqlQuery("with user_consumed_recipes as ( "+
                "    select * from recipes "+
                "    where id in( "+
                "        select recipe_id from user_consumption "+
                "        where user_id = :userId "+
                "    ) "+
                ") "+
                " select * from recipes, recipe_ingredients,tags,nutrition_recipe,nutritions "+
                " where recipes.id = recipe_ingredients.recipe_id "+
                " and recipes.id = tags.recipe_id "+
                " and nutritions.id = nutrition_recipe.nutrition_id "+
                " and recipes.id = nutrition_recipe.recipe_id "+
                " and recipes.id in "+
                "     ( "+
                "         ( "+ //--recipes where tags are similar
                "         select distinct(rec3.id) from recipes rec1,recipes rec3,tags tg1,tags tg2 "+
                "         where rec1.user_id = :userId "+
                "         and rec3.user_id != :userId "+
                "         and tg2.recipe_id = rec3.id "+
                "         and tg1.recipe_id = rec1.id "+
                "         and (lower(tg1.tag) like lower('%'||tg2.tag||'%') "+
                "             or lower(tg2.tag) like lower('%'||tg1.tag||'%')) "+
                "         ) "+
                "         union "+
                "         ("+ //--recipes where name or descriptions are similar 
                "         select distinct(rec2.id) from recipes rec1,recipes rec2 "+
                "         where rec1.user_id = :userId "+
                "         and rec2.user_id != :userId "+
                "         and (lower(rec2.name) like lower('%'||rec1.name||'%') "+
                "         or lower(rec2.description) like lower('%'||rec1.description||'%') "+
                "         or lower(rec1.name) like lower('%'||rec2.name||'%') "+
                "         or lower(rec1.description) like lower('%'||rec2.description||'%'))) "+
                "         union "+
                "         ( "+ //--recipes where user consumed tags are similar
                "         select distinct(rec3.id) from user_consumed_recipes rec1,recipes rec3,tags tg1,tags tg2 "+
                "         where rec1.user_id = :userId "+
                "         and rec3.user_id != :userId "+
                "         and tg2.recipe_id = rec3.id "+
                "         and tg1.recipe_id = rec1.id "+
                "         and (lower(tg1.tag) like lower('%'||tg2.tag||'%') "+
                "         or lower(tg2.tag) like lower('%'||tg1.tag||'%')) "+
                "         ) "+
                "         union "+
                "         ("+ //--recipes user consumed where name or descriptions are similar 
                "         select distinct(rec2.id) from user_consumed_recipes rec1,recipes rec2 "+
                "         where rec1.user_id = :userId "+
                "         and rec2.user_id != :userId "+
                "         and (lower(rec2.name) like lower('%'||rec1.name||'%') "+
                "         or lower(rec2.description) like lower('%'||rec1.description||'%') "+
                "         or lower(rec1.name) like lower('%'||rec2.name||'%') "+
                "         or lower(rec1.description) like lower('%'||rec2.description||'%')) "+
                "         ) "+
                "     )"+
                " order by recipes.id,recipe_ingredients.ingredient_id") 
    @Mapper(RecipesMapper.class)
    abstract protected List<List<Recipe>> _getRecommendedRecipeForUser(@Bind("userId") Long userId);

    @SqlUpdate("delete from recipe_ingredients where recipe_id = :recipeId")
    abstract public void deleteRecipeIngredients(@Bind("recipeId") Long recipeId);

    @SqlUpdate("delete from tags where recipe_id = :recipeId")
    abstract public void deleteRecipeTags(@Bind("recipeId") Long recipeId);

    @SqlUpdate("UPDATE recipes"+
                " SET name = :name, description = :description"+
                " WHERE id = :id")
    abstract public void _updateRecipe(@BindBean Recipe recipe);

    public void createRecipe(Recipe recipe) {
        Long id = _createRecipe(recipe);
        recipe.setId(id);
        recipe.createIngredients(this);
        recipe.createTags(this);
        if (recipe.getNutritions() != null) {
            recipe.createNutrition(this);
        }
        
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

    public List<Recipe> getRecommendedRecipeByRecipeId(Long recipeId) {
        List<List<Recipe>> res = _getRecommendedRecipeByRecipeId(recipeId);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }

    public List<Recipe> getRecommendedRecipeForUser(Long userId) {
        List<List<Recipe>> res = _getRecommendedRecipeForUser(userId);

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

    public List<String> getTagsForUser(Long userId) {
        List<String> res = _getTagsForUser(userId);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res;
    }

    public Recipe updateRecipe(Recipe recipe){
        deleteRecipeTags(recipe.getId());
        deleteRecipeIngredients(recipe.getId());
        _updateRecipe(recipe);

        recipe.createIngredients(this);
        recipe.createTags(this);
        if (recipe.getNutritions() != null) {
            recipe.createNutrition(this);
        }

        return recipe;
    }
}
