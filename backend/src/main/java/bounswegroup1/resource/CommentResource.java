package bounswegroup1.resource;


import java.util.List;

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
import bounswegroup1.model.AccessToken;
import bounswegroup1.model.Comment;
import bounswegroup1.model.Recipe;
import bounswegroup1.model.Menu;
import bounswegroup1.model.User;
import io.dropwizard.auth.Auth;


@Path("/comment")
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {

    private final CommentDAO commentDAO;
    private final UserDAO userDAO;


    public CommentResource(CommentDAO commentDAO, UserDAO userDAO) {
        super();
        this.commentDAO = commentDAO;
        this.userDAO = userDAO;
    }

    @POST
    public Comment addComment(@Auth AccessToken accessToken, Comment comment) {
        // if id is present, edit, otherwise add.
        
        
        comment.setUserId(accessToken.getUserId());
        comment.setUserFullName(userDAO.getUserById(accessToken.getUserId()).getFullName());
        commentDAO.addComment(comment);

        return comment;
    }

 //    @POST
 //    @Path("/comment/delete/{id}")
	// public void deleteComment(@PathParam("id") Long id){
 //        commentDAO.deleteComment(id);
 //    }

    @GET
    @Path("/menu/{id}")
    public List<Comment> getCommentsForMenu(@PathParam("id") Long id){
        return commentDAO.getCommentsForMenu(id);
    }

    @GET
    @Path("/recipe/{id}")
    public List<Comment> getCommentsForRecipe(@PathParam("id") Long id){
        return commentDAO.getCommentsForRecipe(id);
    }   

    @GET
    @Path("/user/{id}")
    public List<Comment> getCommentsForUser(@PathParam("id") Long id){
        return commentDAO.getCommentsForUser(id);
    }  

    @GET
    @Path("/menu/{id}/{commenterId}")
    public List<Comment> getCommentsByUserForMenu(@PathParam("id") Long id, @PathParam("commenterId") Long commenterId){
        return commentDAO.getCommentsByUserForMenu(id, commenterId);
    } 

    @GET
    @Path("/recipe/{id}/{commenterId}")
    public List<Comment> getCommentsByUserForRecipe(@PathParam("id") Long id, @PathParam("commenterId") Long commenterId){
        return commentDAO.getCommentsByUserForRecipe(id, commenterId);
    } 

    @GET
    @Path("/user/{id}/{commenterId}")
    public List<Comment> getCommentsByUserForUser(@PathParam("id") Long id, @PathParam("commenterId") Long commenterId){
        return commentDAO.getCommentsByUserForUser(id, commenterId);
    } 
}