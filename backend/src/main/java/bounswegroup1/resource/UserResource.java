package bounswegroup1.resource;

import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.io.CharStreams;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import bounswegroup1.model.AccessToken;
import bounswegroup1.model.User;
import io.dropwizard.auth.Auth;
import bounswegroup1.db.UserDAO;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private UserDAO dao;

    public UserResource(UserDAO dao) {
        this.dao = dao;
    }

    @GET
    public List<User> getUsers() {
        return dao.getUsers();
    }

    @POST
    public User addUser(@Valid User user) {
        Long id = dao.addUser(user);
        user.setId(id);
        user.setRating(0.0f);
        
        return user;
    }

    @POST
    @Path("/update")
    public User updateUser(@Auth AccessToken token, User user) {
        if (token.getUserId() == user.getId()) {
            dao.updateUser(user);
        }

        return user;
    }

    @POST
    @Path("/avatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void avatarUpload(@Auth AccessToken token, @FormDataParam("file") InputStream file, @FormDataParam("file") FormDataContentDisposition contentDispositionHeader){
        System.out.println(contentDispositionHeader.getFileName());
        
        try {
            System.out.println(CharStreams.toString(new InputStreamReader(file)));
        } catch (IOException e) {
            System.out.println("wat??");

            e.printStackTrace();
        }
    }

    @GET
    @Path("/{id}")
    public User getUser(@PathParam("id") Long id) {
        return dao.getUserById(id);
    }
}
