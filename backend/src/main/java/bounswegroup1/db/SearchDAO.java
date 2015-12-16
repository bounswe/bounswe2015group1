package bounswegroup1.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import bounswegroup1.mapper.MenusMapper;
import bounswegroup1.mapper.RecipesMapper;
import bounswegroup1.model.Recipe;
import bounswegroup1.model.Menu;

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
    		" b.name as recipe_name, b.id, c.recipe_id, c.menu_id "+
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

}