package bounswegroup1.resource;

import javax.ws.rs.core.MediaType;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import bounswegroup1.model.User;
import bounswegroup1.db.UserDAO;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private UserDAO dao;

    public UserResource(UserDAO dao){
        this.dao = dao;
    }

    @GET
    public List<User> getUsers() {
        return dao.getUsers();
    }

    @POST
    public User addUser(@Valid User user){
        Long id = dao.addUser(user);
        user.setId(id);
        return user;
    }
}
