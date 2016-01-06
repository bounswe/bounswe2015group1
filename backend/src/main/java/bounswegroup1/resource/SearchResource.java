package bounswegroup1.resource;

import java.io.FileInputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import net.didion.jwnl.data.*;
import net.didion.jwnl.dictionary.Dictionary;
import net.didion.jwnl.*;


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

    @GET
    @Path("/s/{q}")
    public void semanticSearch(@PathParam("q") String q) {
        //dao.semanticSearch(q);

        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher = pattern.matcher(q);
        boolean found = matcher.find();

        List<String> queryWords;

        if(found){
          System.out.println("matche");
          String[] strArr = q.split("\\s+");
          queryWords = new ArrayList<String>(Arrays.asList(strArr));
        }else{
          System.out.println("do not match");
          ArrayList<String> list = new ArrayList<String>();
          list.add(q);
          queryWords = list;
        }

        for(int i = 0; i< queryWords.size(); i++){
          System.out.println(queryWords.get(i));
        }

    
        try{

        JWNL.initialize(new FileInputStream("src/main/resources/JWNLProperties.xml"));     
        final Dictionary dictionary = Dictionary.getInstance();
        System.out.println("1 ");

        IndexWord indexWord = dictionary.getIndexWord(POS.NOUN, q);

        System.out.println("2 ");
        System.out.println(indexWord.toString());
        Synset[] senses = indexWord.getSenses();

        System.out.println("3 ");

        for (Synset set : senses) {
            System.out.println("4 ");
            System.out.println(indexWord + ": " + set.getGloss());

            System.out.println("\nPOINTERS OF THIS SYNSET:");
            for(Pointer p : set.getPointers()){
                System.out.println(p.getTargetSynset()); 
            }

            System.out.println("\n SEMANTIC WORDS:");
            for(Word word : set.getWords()){
                System.out.println("The semantic word is " + word.getLemma());
            }
        }
      
        }catch(Exception e){
            System.out.println("HATA ALDI: "+e.getMessage());
        }
    }
}