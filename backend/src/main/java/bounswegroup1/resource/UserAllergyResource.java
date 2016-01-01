package bounswegroup1.resource;


import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import bounswegroup1.db.UserAllergyDAO;
import bounswegroup1.model.AccessToken;
import bounswegroup1.model.UserAllergy;


import io.dropwizard.auth.Auth;


@Path("/allergy")
@Produces(MediaType.APPLICATION_JSON)
public class UserAllergyResource {

    private final UserAllergyDAO userAllergyDAO;


    public UserAllergyResource(UserAllergyDAO userAllergyDAO) {
        super();
        this.userAllergyDAO = userAllergyDAO;
    }

    @POST
    public UserAllergy addUserAllergy(@Auth AccessToken accessToken, UserAllergy userAllergy) {

        userAllergy.setUserId(accessToken.getUserId());
        userAllergyDAO.addUserAllergy(userAllergy);

        return userAllergy;
    }

    @GET
    @Path("/user/{id}")
    public List<UserAllergy> getAllergiesForUser(@PathParam("id") Long id){
        return userAllergyDAO.getAllergiesForUser(id);
    }


}