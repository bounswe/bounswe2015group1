package bounswegroup1.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import bounswegroup1.db.RecipeDAO;
import bounswegroup1.model.AccessToken;
import bounswegroup1.model.Recipe;
import io.dropwizard.auth.Auth;

@Path("/recipe")
@Produces(MediaType.APPLICATION_JSON)
public class RecipeResource {
    private final RecipeDAO recipeDAO;

    public RecipeResource(RecipeDAO recipeDAO) {
        super();
        this.recipeDAO = recipeDAO;
    }

    @Path("/user/:id")
    @GET
    public List<Recipe> getRecipesForUser(@PathParam("id") Long userId) {
        System.out.println("giriyon mu buraa");
        return recipeDAO.getRecipesForUser(userId);
    }

    @Path("/all")
    @GET
    public List<Recipe> getAllRecipes() {
        return recipeDAO.getRecipes();
    }

    @POST
    public Recipe addRecipe(@Auth AccessToken accessToken, Recipe recipe) {
        recipe.setUserId(accessToken.getUserId());
        recipeDAO.createRecipe(recipe);

        return recipe;
    }

}
