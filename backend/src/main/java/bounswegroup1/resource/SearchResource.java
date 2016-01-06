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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MultivaluedMap;


import bounswegroup1.model.AccessToken;
import io.dropwizard.auth.Auth;
import bounswegroup1.db.SearchDAO;
import bounswegroup1.model.Recipe;
import bounswegroup1.model.Menu;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource{
	private SearchDAO dao;

	public SearchResource(SearchDAO dao) {
        this.dao = dao;
    }

    @GET
    @Path("/recipe/{q}")
    public List<Recipe> getRecipeResults(@PathParam("q") String q) {
        return dao.getRecipeResults(q);
    }

    @GET
    @Path("/menu/{q}")
    public List<Menu> getMenuResults(@PathParam("q") String q) {
        return dao.getMenuResults(q);
    }

    @GET
    @Path("/advancedSearch/recipe/{q}")
    public List<Recipe> getAdvancedRecipeResults(@PathParam("q") String q,
                                             @Context UriInfo uriInfo) {
        MultivaluedMap<String, String> map = uriInfo.getQueryParameters();

        System.out.println("CONTEXT URIINFO CONTENT:" + uriInfo.getQueryParameters());

        return dao.getAdvancedRecipeResults(q, map);
    }

    @GET
    @Path("/advancedSearch/menu/{q}")
    public List<Menu> getAdvancedMenuResults(@PathParam("q") String q,
                                             @Context UriInfo uriInfo) {
        MultivaluedMap<String, String> map = uriInfo.getQueryParameters();
        
        System.out.println("CONTEXT URIINFO CONTENT:" + uriInfo.getQueryParameters());

        return dao.getAdvancedMenuResults(q, map);
    }
}