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
    @SqlUpdate("insert into menus (name, user_id, created_at, period, description) values (:name, :userId, now(), :period, :description)")
    abstract protected Long _addMenu(@BindBean Menu menu);
    
    @SqlBatch("insert into menu_recipes (menu_id, recipe_id) values (:menuId, :recipeId)")
    abstract protected void addRecipesForMenu(@Bind("menu_id") Long menuId, @Bind("tag") List<Long> recipeIds);
    
    @Mapper(MenuMapper.class)
    @SqlQuery("select * from menus, menu_recipes where menus.id = :id and menu_recipes.menu_id = :id")
    abstract protected List<Menu> getMenuById(@Bind("id") Long id);

    @SqlUpdate("insert into menu_recipes (recipe_id, menu_id) "
            + "values (:recipe_id, :menu_id)")
    abstract public void createRecipe( @Bind("recipe_id") Long recipeId,
        @Bind("menu_id") Long menuId);

    public Menu getMenu(Long menuId){
        List<Menu> res = getMenuById(menuId);

            if (res == null || res.isEmpty()) {
                return null;
            }

            return res.get(0);
    }

    public void addMenu(Menu menu) {
        Long id = _addMenu(menu);
        menu.setId(id);
        menu.createRecipes(this);
    }}