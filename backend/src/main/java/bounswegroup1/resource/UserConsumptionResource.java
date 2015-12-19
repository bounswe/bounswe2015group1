package bounswegroup1.resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
    public Long consume(@Auth AccessToken accessToken, Long recipeId) {
    	consumeDAO.saveUserConsumption(accessToken.getUserId(), recipeId);
    	
    	return recipeId;

        //rating.setUserId(accessToken.getUserId());
        //ratingDAO.deleteRating(rating);
        //ratingDAO.addRating(rating);

        //return rating;
    }
}
