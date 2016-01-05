package bounswegroup1.db;

import java.util.Date;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import bounswegroup1.mapper.MenuMapper;
import bounswegroup1.mapper.MenusMapper;
import bounswegroup1.mapper.RecipeMapper;
import bounswegroup1.mapper.RecipesMapper;
import bounswegroup1.model.Menu;
import bounswegroup1.model.Recipe;



/* 
    private Long id;
    private String name;
    private Long userId;
    private List<Recipe> recipeIds;
    private Date createdAt; */

public abstract class MenuDAO {

    @GetGeneratedKeys
    @SqlUpdate("insert into menus (name, user_id, created_at, period, description, rating) values (:name, :userId, :createdAt, :period, :description, :rating)")
    abstract protected Long _addMenu(@BindBean Menu menu);
    
    @SqlQuery("select a.id, a.name as menu_name, a.user_id, a.description, a.created_at, a.period, a.rating, b.name as recipe_name, b.id, c.recipe_id, c.menu_id"+
    " from menus a,recipes b,menu_recipes c"+
    " where a.id = c.menu_id"+
    " and b.id = c.recipe_id"+
    " order by a.id,c.recipe_id")
    @Mapper(MenusMapper.class)
    abstract protected List<List<Menu>> _getMenus();

    @SqlBatch("insert into menu_recipes (menu_id, recipe_id) values (:menuId, :recipeId)")
    abstract protected void addRecipesForMenu(@Bind("menu_id") Long menuId, @Bind("tag") List<Long> recipeIds);
    
    @SqlQuery("select * from recipes, recipe_ingredients,tags,nutrition_recipe,nutritions"+
    " where recipes.id in "+
    " (select menu_recipes.recipe_id from menus, menu_recipes where menus.id = menu_recipes.menu_id and menus.id = :id)"+
    " and recipes.id = recipe_ingredients.recipe_id"+
    " and recipes.id = tags.recipe_id"+
    " and nutritions.id = nutrition_recipe.nutrition_id"+
    " and recipes.id = nutrition_recipe.recipe_id"+
    " order by recipes.id,recipe_ingredients.ingredient_id")
    @Mapper(RecipesMapper.class)
    abstract protected List<List<Recipe>> _getRecipesForMenu(@Bind("id") Long id);

    @Mapper(MenuMapper.class)
    @SqlQuery("select a.id, a.name as menu_name, a.user_id, a.description, a.created_at, a.period, a.rating, b.name as recipe_name, b.id, c.recipe_id, c.menu_id"+
    " from menus a,recipes b,menu_recipes c"+
    " where a.id = :id"+
    " and b.id = c.recipe_id"+
    " and c.menu_id = :id"+
    " order by a.id,c.recipe_id")
    abstract protected List<Menu> _getMenuById(@Bind("id") Long id);

    @SqlQuery("select a.id, a.name as menu_name, a.user_id, a.description, a.created_at, a.period, a.rating, b.name as recipe_name, b.id, c.recipe_id, c.menu_id"+
    " from menus a,recipes b,menu_recipes c"+
    " where a.id = c.menu_id"+
    " and b.id = c.recipe_id"+
    " and a.user_id = :id"+
    " order by a.id,c.recipe_id")
    @Mapper(MenusMapper.class)
    abstract protected List<List<Menu>> _getMenusForUser(@Bind("id") Long id);

    @SqlUpdate("insert into menu_recipes (recipe_id, menu_id) "
            + "values (:recipe_id, :menu_id)")
    abstract public void createRecipe( @Bind("recipe_id") Long recipeId,
        @Bind("menu_id") Long menuId);

    public Menu getMenu(Long menuId){
        List<Menu> res = _getMenuById(menuId);

            if (res == null || res.isEmpty()) {
                return null;
            }

            return res.get(0);
    }

    public void addMenu(Menu menu) {
        Long id = _addMenu(menu);
        menu.setId(id);
        menu.createRecipes(this);
    }

    public List<Menu> getMenus() {
        List<List<Menu>> res = _getMenus();

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }

    public List<Recipe> getRecipesForMenu(Long menuId) {
        List<List<Recipe>> res = _getRecipesForMenu(menuId);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }

    public List<Menu> getMenusForUser(Long userId) {
        List<List<Menu>> res = _getMenusForUser(userId);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }

}