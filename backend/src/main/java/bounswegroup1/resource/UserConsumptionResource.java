package bounswegroup1.resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;

import bounswegroup1.db.CommentDAO;
import bounswegroup1.db.RecipeDAO;
import bounswegroup1.db.MenuDAO;
import bounswegroup1.db.UserDAO;
import bounswegroup1.db.ConsumeDAO;
import bounswegroup1.db.RatingDAO;
import bounswegroup1.model.AccessToken;
import bounswegroup1.model.Comment;
import bounswegroup1.model.Recipe;
import bounswegroup1.model.Menu;
import bounswegroup1.model.User;
import bounswegroup1.model.Rating;
import bounswegroup1.model.Nutrition;

import io.dropwizard.auth.Auth;


@Path("/consume")
@Produces(MediaType.APPLICATION_JSON)
public class UserConsumptionResource {
	private final ConsumeDAO consumeDAO;

    public UserConsumptionResource(ConsumeDAO consumeDAO) {

        super();
        this.consumeDAO = consumeDAO;
    }
 
    @POST
    public Recipe consume(@Auth AccessToken accessToken, Recipe recipe) {

        Long recipeId = recipe.getId();
        consumeDAO.saveUserConsumption(accessToken.getUserId(), recipeId);

        return recipe;
    }

    @GET
    @Path("/{id}")
    public List<Recipe> getConsumedRecipesForUser(@PathParam("id") Long id){
        return consumeDAO.getConsumedRecipesForUser(id);
    }

    @GET
    @Path("/daily/average/{id}")
    public Nutrition getDailyAverageNutritionConsumptionForUser(@PathParam("id") Long id){
        return consumeDAO.getDailyAverageNutritionConsumptionForUser(id);
    }


}
