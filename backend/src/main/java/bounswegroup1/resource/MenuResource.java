package bounswegroup1.resource;


import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bounswegroup1.db.RecipeDAO;
import bounswegroup1.db.MenuDAO;
import bounswegroup1.model.AccessToken;
import bounswegroup1.model.Recipe;
import bounswegroup1.model.Menu;
import io.dropwizard.auth.Auth;


@Path("/menu")
@Produces(MediaType.APPLICATION_JSON)
public class MenuResource {
    private final MenuDAO menuDAO;

    public MenuResource(MenuDAO menuDAO) {
        super();
        this.menuDAO = menuDAO;
    }

    @POST
    public Menu addMenu(@Auth AccessToken accessToken, Menu menu) {
        // if id is present, edit, otherwise add.
        
        
        menu.setUserId(accessToken.getUserId());
        menuDAO.addMenu(menu);

        return menu;
    }

    @GET
    public List<Menu> getAllMenus() {
        return menuDAO.getMenus();
    }

    @GET
    @Path("/{id}")
    public Menu getMenuById(@PathParam("id") Long id){
        return menuDAO.getMenu(id);
    }

    @GET
    @Path("/{id}/recipes")
    public List<Recipe> getRecipesForMenu(@PathParam("id") Long id){
    	return menuDAO.getRecipesForMenu(id);
    }
}
