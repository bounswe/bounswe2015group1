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
    private List<Recipe> recipes;
    private Date createdAt; */

public interface MenuDAO {
    @SqlUpdate("insert into menus (name, user_id, created_at) values (:name, :userId, now())")
    public Long addMenu(@BindBean Menu menu);
    
    @SqlBatch("insert into menu_recipes (menu_id, recipe_id) values (:menuId, :recipeId)")
    public void addRecipesForMenu(@Bind("menu_id") Long menuId, @Bind("tag") List<Long> recipeIds);
    
    @Mapper(MenuMapper.class)
    @SqlQuery("select * from menus, menu_recipes where menus.id = :id and menu_recipes.menu_id = :id")
    public List<Menu> getMenuById(@Bind("id") Long id);
    
}
