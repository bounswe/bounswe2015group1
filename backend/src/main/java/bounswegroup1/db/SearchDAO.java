package bounswegroup1.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.unstable.BindIn;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import bounswegroup1.mapper.MenusMapper;
import bounswegroup1.mapper.RecipesMapper;
import bounswegroup1.model.Recipe;
import bounswegroup1.model.Menu;
import bounswegroup1.model.Nutrition;
import bounswegroup1.model.Filter;

import javax.ws.rs.core.MultivaluedMap;

@UseStringTemplate3StatementLocator
public abstract class SearchDAO{

	 @SqlQuery("select * from recipes, recipe_ingredients,tags,nutrition_recipe,nutritions"+
				" where recipes.id = recipe_ingredients.recipe_id"+
				" and recipes.id = tags.recipe_id"+
				" and nutritions.id = nutrition_recipe.nutrition_id"+
				" and recipes.id = nutrition_recipe.recipe_id"+
				" and recipes.id in"+
				" ("+
				" (select distinct(id) from recipes"+
				" where lower(name) like lower('%'|| :search ||'%')"+
				"	or lower('%'|| :search ||'%') like lower(name)"+
				"	or lower(description) like lower('%'|| :search ||'%')"+
				"	or lower('%'|| :search ||'%') like lower(description))"+
				" union"+
				" (select distinct(recipes.id) from recipes,tags"+
				" where (lower(tags.tag) like lower('%'|| :search ||'%')"+
				"	or lower('%'|| :search ||'%') like lower(tags.tag) )"+
				"	and recipes.id = tags.recipe_id)"+
				")  order by recipes.id,recipe_ingredients.ingredient_id")
    @Mapper(RecipesMapper.class)
    abstract protected List<List<Recipe>> _getRecipeResults(@Bind("search") String query);

    @SqlQuery("select a.id, a.name as menu_name, a.user_id, a.description, a.created_at, a.period, "+
    		" b.name as recipe_name, b.id, c.recipe_id, c.menu_id,a.rating "+
			" from menus a,recipes b,menu_recipes c "+
			" where b.id = c.recipe_id "+
			" and c.menu_id = a.id "+
			" and a.id in "+
			" ( "+
			"	select distinct(id) from menus "+
			"	where lower(name) like lower('%'|| :search ||'%') "+
			"		or lower('%'|| :search ||'%') like lower(name) "+
			"		or lower(description) like lower('%'|| :search ||'%') "+
			"		or lower('%'|| :search ||'%') like lower(description) "+
			" ) "+
			" order by a.id,c.recipe_id")
    @Mapper(MenusMapper.class)
    abstract protected List<List<Menu>> _getMenuResults(@Bind("search") String query);

    @SqlQuery("with menu_nutritions as ( "+
            "        select menus.id, sum(nut.calories) as calories,sum(nut.carbohydrate) as carbohydrate,"+
            "        sum(nut.fats) as fats, "+
            "        sum(nut.proteins) as proteins,sum(nut.sodium) as sodium,sum(nut.fiber) as fiber, "+
            "        sum(nut.cholesterol) as cholesterol,sum(nut.sugars) as sugars,sum(nut.iron) as iron "+
            "        from menus,menu_recipes,recipes,nutrition_recipe,nutritions nut "+
            "        where menus.id = menu_recipes.menu_id "+
            "        and menu_recipes.recipe_id = recipes.id "+
            "        and recipes.id = nutrition_recipe.recipe_id "+
            "        and nutrition_recipe.nutrition_id = nut.id "+
            "        group by menus.id "+
            "    ) "+
            "    select a.id, a.name as menu_name, a.user_id, a.description, "+
            "    a.created_at, a.period, b.name as recipe_name, b.id, c.recipe_id, c.menu_id,a.rating "+
            "    from menus a,recipes b,menu_recipes c,menu_nutritions nutritions "+
            "    where b.id = c.recipe_id "+
            "    and nutritions.id = a.id "+
            "    and c.menu_id = a.id "+
            "    and (a.id in "+
            "    ( "+
            "        select distinct(id) from menus "+
            "        where lower(name) like lower('%'|| :search ||'%')  "+
            "            or lower('%'|| :search ||'%') like lower(name) "+
            "            or lower(description) like lower('%'|| :search ||'%') "+
            "            or lower('%'|| :search ||'%') like lower(description) "+
            "    ) "+
            "    or b.id in( "+
            "            select recipes.id from recipes, recipe_ingredients,tags "+
            "            where recipes.id = recipe_ingredients.recipe_id "+
            "            and recipes.id = tags.recipe_id "+
            "           and recipes.id in "+
            "                ( "+
            "                (select distinct(id) from recipes "+
            "                where lower(name) like lower('%'|| :search ||'%') "+
            "                    or lower('%'|| :search ||'%') like lower(name) "+
            "                    or lower(description) like lower('%'|| :search ||'%') "+
            "                    or lower('%'|| :search ||'%') like lower(description)) "+
            "                union "+
            "                (select distinct(recipes.id) from recipes,tags "+
            "                where (lower(tags.tag) like lower('%'|| :search ||'%') "+
            "                    or lower('%'|| :search ||'%') like lower(tags.tag) ) "+
            "                    and recipes.id = tags.recipe_id) "+
            "                union "+
            "                (select distinct(recipes.id) from recipes,recipe_ingredients "+
            "                    where recipe_ingredients.ingredient_name in (<wantedIngredientList>) "+
            "                    and recipes.id = recipe_ingredients.recipe_id) "+
            "                ) "+
            "            and recipes.id not in ( "+
            "                    select distinct(recipes.id) from recipes,recipe_ingredients "+
            "                    where recipe_ingredients.ingredient_name in (<notWantedIngredientList>) "+
            "                    and recipes.id = recipe_ingredients.recipe_id "+
            "                ) "+
            "        ) "+
            "    )"+
            "    and a.rating >= :minRating "+
            "    and a.rating \\<= :maxRating "+
            "    and a.period = COALESCE(:period,a.period) "+
            "and nutritions.calories >= :minNutrition.calories "+
            "and nutritions.calories \\<= :maxNutrition.calories "+
            "and nutritions.carbohydrate >= :minNutrition.carbohydrate "+
            "and nutritions.carbohydrate \\<= :maxNutrition.carbohydrate "+
            "and nutritions.fats >= :minNutrition.fats "+
            "and nutritions.fats \\<= :maxNutrition.fats "+
            "and nutritions.proteins >= :minNutrition.proteins "+
            "and nutritions.proteins \\<= :maxNutrition.proteins "+
            "and nutritions.sodium >= :minNutrition.sodium "+
            "and nutritions.sodium \\<= :maxNutrition.sodium "+
            "and nutritions.fiber >= :minNutrition.fiber "+
            "and nutritions.fiber \\<= :maxNutrition.fiber "+
            "and nutritions.cholesterol >= :minNutrition.cholesterol "+
            "and nutritions.cholesterol \\<= :maxNutrition.cholesterol "+
            "and nutritions.sugars >= :minNutrition.sugars "+
            "and nutritions.sugars \\<= :maxNutrition.sugars "+
            "and nutritions.iron >= :minNutrition.iron "+
            "and nutritions.iron \\<= :maxNutrition.iron "+
            "    order by a.id,c.recipe_id")
    @Mapper(MenusMapper.class) 
    abstract protected List<List<Menu>> _getAdvancedMenuResults(@Bind("search") String query, 
        @Bind("minRating") Float minRating, @Bind("maxRating") Float maxRating,
        @BindIn("wantedIngredientList") List<String> wantedIngredientList, @Bind("period") String period,
        @BindIn("notWantedIngredientList") List<String> notWantedIngredientList,
        @BindBean("minNutrition") Nutrition minNutrition, @BindBean("maxNutrition") Nutrition maxNutrition);

    @SqlQuery("select * from recipes, recipe_ingredients,tags,nutrition_recipe,nutritions "+
        "where recipes.id = recipe_ingredients.recipe_id "+
        "and recipes.id = tags.recipe_id "+ 
        "and nutritions.id = nutrition_recipe.nutrition_id "+
        "and recipes.id = nutrition_recipe.recipe_id "+
        "and recipes.id in "+
        "    ( "+
        "    (select distinct(id) from recipes "+
        "    where lower(name) like lower('%'|| :search ||'%') "+
        "        or lower('%'|| :search ||'%') like lower(name) "+
        "        or lower(description) like lower('%'|| :search ||'%') "+
        "        or lower('%'|| :search ||'%') like lower(description)) "+
        "    union"+
        "    (select distinct(recipes.id) from recipes,tags "+
        "    where (lower(tags.tag) like lower('%'|| :search ||'%') "+
        "        or lower('%'|| :search ||'%') like lower(tags.tag) ) "+
        "        and recipes.id = tags.recipe_id) "+
        "    union "+
        "    (select distinct(recipes.id) from recipes,recipe_ingredients "+
        "        where recipe_ingredients.ingredient_name in ( <wantedIngredientList> ) "+
        "        and recipes.id = recipe_ingredients.recipe_id) "+
        "    ) "+
        "and recipes.id not in ( "+
        "        select distinct(recipes.id) from recipes,recipe_ingredients "+
        "        where recipe_ingredients.ingredient_name in ( <notWantedIngredientList> ) "+
        "        and recipes.id = recipe_ingredients.recipe_id "+
        "    ) "+
        "and recipes.rating >= :minRating "+
        "and recipes.rating \\<= :maxRating "+
        "and nutritions.calories >= :minNutrition.calories "+
        "and nutritions.calories \\<= :maxNutrition.calories "+
        "and nutritions.carbohydrate >= :minNutrition.carbohydrate "+
        "and nutritions.carbohydrate \\<= :maxNutrition.carbohydrate "+
        "and nutritions.fats >= :minNutrition.fats "+
        "and nutritions.fats \\<= :maxNutrition.fats "+
        "and nutritions.proteins >= :minNutrition.proteins "+
        "and nutritions.proteins \\<= :maxNutrition.proteins "+
        "and nutritions.sodium >= :minNutrition.sodium "+
        "and nutritions.sodium \\<= :maxNutrition.sodium "+
        "and nutritions.fiber >= :minNutrition.fiber "+
        "and nutritions.fiber \\<= :maxNutrition.fiber "+
        "and nutritions.cholesterol >= :minNutrition.cholesterol "+
        "and nutritions.cholesterol \\<= :maxNutrition.cholesterol "+
        "and nutritions.sugars >= :minNutrition.sugars "+
        "and nutritions.sugars \\<= :maxNutrition.sugars "+
        "and nutritions.iron >= :minNutrition.iron "+
        "and nutritions.iron \\<= :maxNutrition.iron "+
        " order by recipes.id,recipe_ingredients.ingredient_id")
    @Mapper(RecipesMapper.class)
    abstract protected List<List<Recipe>> _getAdvancedRecipeResults(@Bind("search") String query,
        @Bind("minRating") Float minRating, @Bind("maxRating") Float maxRating,
        @BindIn("wantedIngredientList") List<String> wantedIngredientList,
        @BindIn("notWantedIngredientList") List<String> notWantedIngredientList,
        @BindBean("minNutrition") Nutrition minNutrition, @BindBean("maxNutrition") Nutrition maxNutrition);

    public List<Recipe> getRecipeResults(String query){
    	List<List<Recipe>> res = _getRecipeResults(query);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }

    public List<Menu> getMenuResults(String query){
    	List<List<Menu>> res = _getMenuResults(query);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }


    public List<Recipe> getAdvancedRecipeResults(String query, MultivaluedMap<String,String> map){
    	Filter filter = new Filter(map);

        List<List<Recipe>> res = _getAdvancedRecipeResults(query,filter.getMinRating(),filter.getMaxRating(),
            filter.getWantedIngList(),filter.getNotWantedIngList(), filter.getMinNutrition(), filter.getMaxNutrition());

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }

    public List<Menu> getAdvancedMenuResults(String query, MultivaluedMap<String,String> map){
    	Filter filter = new Filter(map);

        List<List<Menu>> res = _getAdvancedMenuResults(query,filter.getMinRating(),filter.getMaxRating(),
            filter.getWantedIngList(),filter.getPeriod(),filter.getNotWantedIngList(), filter.getMinNutrition(), filter.getMaxNutrition());

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }

    

}