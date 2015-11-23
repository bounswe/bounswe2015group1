package bounswegroup1.resource;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;


@Path("/ingredient")
@Produces(MediaType.APPLICATION_JSON)
public class IngredientResource {
    private Client httpClient;
    private String appId;
    private String appKey;
    private URI infoUri;
    private URI autocompleteUri;
    private URI searchUri;

    public IngredientResource(Client httpClient, String appId, String appKey) {
        super();
        this.httpClient = httpClient;
        this.appId = appId;
        this.appKey = appKey;

        this.infoUri = UriBuilder.fromUri("https://api.nutritionix.com/v1_1/item").build();
        this.autocompleteUri = UriBuilder.fromUri("https://apibeta.nutritionix.com/v2/autocomplete")
                .build();
        this.searchUri = UriBuilder.fromUri("https://api.nutritionix.com/v1_1/search").build();

    }

    @GET
    @Path("/autocomplete/{query}")
    public String autocomplete(@PathParam("query") String query) {
        System.out.println("girdi mi");
        
        WebTarget target = httpClient.target(autocompleteUri);
        String response = target.queryParam("q", query).request()
                .header("X-APP-ID", appId).header("X-APP-KEY", appKey).get(String.class);

        return response;
    }

    @GET
    @Path("/item/{id}")
    public String itemInfo(@PathParam("id") String itemId) {
        WebTarget target = httpClient.target(infoUri);
        String response = target.queryParam("id", itemId).queryParam("appId", appId)
                .queryParam("appKey", appKey).request().get(String.class);

        // todo: cache this result
        return response;
    }
    
    @GET
    @Path("/search/{query}")
    public String search(@PathParam("query") String query) {
        WebTarget target = httpClient.target(searchUri).path(query);
        String response = target.queryParam("appId", appId).queryParam("appKey", appKey)
                .request().get(String.class);
        
        // cache this one as well
        return response;
    }

}
