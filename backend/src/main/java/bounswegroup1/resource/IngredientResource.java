package bounswegroup1.resource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

@Path("/ingredient")
@Produces(MediaType.APPLICATION_JSON)
public class IngredientResource {
	private HttpClient httpClient;
	private String appId;
	private String appKey;
	
	public IngredientResource(HttpClient httpClient, String appId, String appKey) {
		super();
		this.httpClient = httpClient;
		this.appId = appId;
		this.appKey = appKey;
	}
	
	@GET
	@Path("/autocomplete/:query")
	public String autocomplete(@PathParam("query") String query) throws URISyntaxException, ClientProtocolException, IOException{
		URI uri = new URIBuilder().setScheme("https")
				.setHost("apibeta.nutritionix.com")
				.setPath("/v2/autocomplete")
				.setParameter("q", query)
				.build();
		
		HttpGet req = new HttpGet(uri);
		HttpResponse resp = httpClient.execute(req);
		
		
		
		return null;
	}
	
	@GET
	@Path("/item/:id")
	public String itemInfo(@PathParam("id") String itemId){
		return null;
	}
	
}
