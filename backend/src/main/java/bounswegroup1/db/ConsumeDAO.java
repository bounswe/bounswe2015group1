package bounswegroup1.db;

import java.util.List;
import java.util.ArrayList;

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
import bounswegroup1.model.Nutrition;


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

    @SqlQuery("select count(a.*) as totalEat from "+
	" (select * from user_consumption"+
	" where user_id = :userId and recipe_id = :recipeId"+
	" ) a")
    abstract protected Long getConsumptionCountOfRecipeForUser(@Bind("userId") Long userId, @Bind("recipeId") Long recipeId);

    @SqlQuery("select count(a.*) as diffDay from"+
	" (select distinct(created_at) from user_consumption"+
    " where user_id = :id"+
	") a")
    abstract protected Long getDifferentConsumptionDayCount(@Bind("id") Long id);


	 public void saveUserConsumption(Long userId, Long recipeId){
		_saveUserConsumption(userId, recipeId);
	}	

	public List<Recipe> getConsumedRecipesForUser(Long userId) {
        List<List<Recipe>> res = _getConsumedRecipesForUser(userId);

        if (res == null || res.isEmpty()) {
            return new ArrayList<Recipe>();
        }

        return res.get(0);
    }

    public Nutrition getDailyAverageNutritionConsumptionForUser(Long userId) {
    	List<List<Recipe>> res = _getConsumedRecipesForUser(userId);
    	Long totalDays = getDifferentConsumptionDayCount(userId);

        Nutrition n = new Nutrition(new Long(-1), 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);

        if (res == null || res.isEmpty()) {
            return n;
		}

		Float totalCal = 0.0f;
		Float totalCar = 0.0f;
		Float totalFat = 0.0f;
		Float totalPro = 0.0f;
		Float totalSod = 0.0f;
		Float totalFib = 0.0f;
		Float totalCho = 0.0f;
		Float totalSug = 0.0f;
		Float totalIro = 0.0f;
		
		for (Recipe recipe : res.get(0) ) {
			Long count = getConsumptionCountOfRecipeForUser(userId, recipe.getId());
			totalCal += count*recipe.getNutritions().getCalories();
			totalCar += count*recipe.getNutritions().getCarbohydrate();
			totalFat += count*recipe.getNutritions().getFats();
			totalPro += count*recipe.getNutritions().getProteins();
			totalSod += count*recipe.getNutritions().getSodium();
			totalFib += count*recipe.getNutritions().getFiber();
			totalCho += count*recipe.getNutritions().getCholesterol();
			totalSug += count*recipe.getNutritions().getSugars();
			totalIro += count*recipe.getNutritions().getIron();
        }

        Float avgCal = totalCal/totalDays;
        Float avgCar = totalCar/totalDays;
        Float avgFat = totalFat/totalDays;
        Float avgPro = totalPro/totalDays;
        Float avgSod = totalSod/totalDays;
        Float avgFib = totalFib/totalDays;
        Float avgCho = totalCho/totalDays;
        Float avgSug = totalSug/totalDays;
        Float avgIro = totalIro/totalDays;


        n= new Nutrition(new Long(-1), avgCal, avgCar, avgFat, avgPro, avgSod, avgFib, avgCho, avgSug, avgIro);
        return n;

    }
}