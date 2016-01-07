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
    @Path("/semanticSearch/recipe/{q}")
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

    
        List recipes = new ArrayList<Recipe>();

        for (Iterator itrQuery = queryWords.iterator(); itrQuery.hasNext();) {
            String queryWord = (String) itrQuery.next();

            List allNyms = new ArrayList<String>();
            List hypernyms = new ArrayList<String>();
            List hyponyms = new ArrayList<String>();
            List hyperHyponyms = new ArrayList<String>();
            List synonyms = new ArrayList<String>();
            List meronyms = new ArrayList<String>();

            try{
                JWNL.initialize(new FileInputStream("JWNLProperties.xml"));     
                final Dictionary dictionary = Dictionary.getInstance();

                IndexWord indexWord = dictionary.getIndexWord(POS.NOUN, queryWord);
                //IndexWord indexWord = dictionary.getRandomIndexWord(POS.NOUN);


                Synset[] senses = indexWord.getSenses();
                System.out.println("All related words for: " + q);

                for (Synset set : senses) {
                    System.out.println("A synset for :" + set.toString());

                    Word[] synWords = set.getWords();

                    for(Word w : synWords){
                        synonyms.add(w.getLemma());
                    }

                    PointerTargetNodeList hypoTargets;
                    PointerTargetNodeList hyperTargets;
                    PointerTargetNodeList meroTargets;

                    try{
                        hypoTargets = new PointerTargetNodeList(set.getTargets(PointerType.HYPONYM));
                        hyperTargets = new PointerTargetNodeList(set.getTargets(PointerType.HYPERNYM));
                        meroTargets = new PointerTargetNodeList(set.getTargets(PointerType.MEMBER_MERONYM));

                    }
                    catch(Exception e){
                        System.out.println("NO HYPERNYMS OR HYPONYMS");
                        continue;
                    }

                    for (Iterator itr = hyperTargets.iterator(); itr.hasNext();) {

                        PointerTargetNode t = (PointerTargetNode) itr.next();
                        System.out.println("Hyper target :" + t.toString());

                        Word[] hyperWords = t.getSynset().getWords();
                        
                        for(Word w : hyperWords){
                            hypernyms.add(w.getLemma());  
                            System.out.println("\n HYPERNM: " + w.getLemma());

                        }
                    }
                    
                    for (Iterator itr = hypoTargets.iterator(); itr.hasNext();) {
                        PointerTargetNode t = (PointerTargetNode) itr.next();
                        System.out.println("Hypo target :" + t.toString());
                        
                        Word[] hypoWords = t.getSynset().getWords();
                        
                        for(Word w : hypoWords){
                            hyponyms.add(w.getLemma());  
                            System.out.println("\n HYPONM: " + w.getLemma());

                        }
                    }
                    
                    for (Iterator itr = meroTargets.iterator(); itr.hasNext();) {
                        PointerTargetNode t = (PointerTargetNode) itr.next();
                        System.out.println("Mero target :" + t.toString());
                        
                        Word[] meroWords = t.getSynset().getWords();
                        
                        for(Word w : meroWords){
                            meronyms.add(w.getLemma());  
                            System.out.println("\n MERONYM: " + w.getLemma());

                        }
                    }
                }
                
            }
            catch(Exception e){
                System.out.println("HATA ALDI: "+e.getMessage());
                e.printStackTrace();
                return recipes;
            }

            allNyms.addAll(synonyms);
            allNyms.addAll(hypernyms);
            allNyms.addAll(hyponyms);
            allNyms.addAll(meronyms);
            //allNyms.addAll(hyperHyponyms);

            for (Iterator itr = allNyms.iterator(); itr.hasNext();) {
                String nym = (String) itr.next();
                try{
                    recipes.addAll(dao.getRecipeResults(nym));
                    System.out.println(nym + "is found!");
                }
                catch(Exception e){

                }
            }
        }    
        return recipes;
    }

    @GET
    @Path("/semanticSearch/menu/{q}")
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

    
        List menus = new ArrayList<Menu>();

        for (Iterator itrQuery = queryWords.iterator(); itrQuery.hasNext();) {
            String queryWord = (String) itrQuery.next();

            List allNyms = new ArrayList<String>();
            List hypernyms = new ArrayList<String>();
            List hyponyms = new ArrayList<String>();
            List hyperHyponyms = new ArrayList<String>();
            List synonyms = new ArrayList<String>();
            List meronyms = new ArrayList<String>();

            try{
                JWNL.initialize(new FileInputStream("JWNLProperties.xml"));     
                final Dictionary dictionary = Dictionary.getInstance();

                IndexWord indexWord = dictionary.getIndexWord(POS.NOUN, queryWord);
                //IndexWord indexWord = dictionary.getRandomIndexWord(POS.NOUN);


                Synset[] senses = indexWord.getSenses();
                System.out.println("All related words for: " + q);

                for (Synset set : senses) {
                    System.out.println("A synset for :" + set.toString());

                    Word[] synWords = set.getWords();

                    for(Word w : synWords){
                        synonyms.add(w.getLemma());
                    }

                    PointerTargetNodeList hypoTargets;
                    PointerTargetNodeList hyperTargets;
                    PointerTargetNodeList meroTargets;

                    try{
                        hypoTargets = new PointerTargetNodeList(set.getTargets(PointerType.HYPONYM));
                        hyperTargets = new PointerTargetNodeList(set.getTargets(PointerType.HYPERNYM));
                        meroTargets = new PointerTargetNodeList(set.getTargets(PointerType.MEMBER_MERONYM));

                    }
                    catch(Exception e){
                        System.out.println("NO HYPERNYMS OR HYPONYMS");
                        continue;
                    }

                    for (Iterator itr = hyperTargets.iterator(); itr.hasNext();) {

                        PointerTargetNode t = (PointerTargetNode) itr.next();
                        System.out.println("Hyper target :" + t.toString());

                        Word[] hyperWords = t.getSynset().getWords();
                        
                        for(Word w : hyperWords){
                            hypernyms.add(w.getLemma());  
                            System.out.println("\n HYPERNM: " + w.getLemma());

                        }
                    }
                    
                    for (Iterator itr = hypoTargets.iterator(); itr.hasNext();) {
                        PointerTargetNode t = (PointerTargetNode) itr.next();
                        System.out.println("Hypo target :" + t.toString());
                        
                        Word[] hypoWords = t.getSynset().getWords();
                        
                        for(Word w : hypoWords){
                            hyponyms.add(w.getLemma());  
                            System.out.println("\n HYPONM: " + w.getLemma());

                        }
                    }
                    
                    for (Iterator itr = meroTargets.iterator(); itr.hasNext();) {
                        PointerTargetNode t = (PointerTargetNode) itr.next();
                        System.out.println("Mero target :" + t.toString());
                        
                        Word[] meroWords = t.getSynset().getWords();
                        
                        for(Word w : meroWords){
                            meronyms.add(w.getLemma());  
                            System.out.println("\n MERONYM: " + w.getLemma());

                        }
                    }
                }
                
            }
            catch(Exception e){
                System.out.println("HATA ALDI: "+e.getMessage());
                e.printStackTrace();
                return menus;
            }

            allNyms.addAll(synonyms);
            allNyms.addAll(hypernyms);
            allNyms.addAll(hyponyms);
            allNyms.addAll(meronyms);
            //allNyms.addAll(hyperHyponyms);

            for (Iterator itr = allNyms.iterator(); itr.hasNext();) {
                String nym = (String) itr.next();
                try{
                    menus.addAll(dao.getMenuResults(nym));
                    System.out.println(nym + "is found!");
                }
                catch(Exception e){

                }
            }
        }    
        return menus;
    }    
}