package bounswegroup1.model;

import java.util.ArrayList;
import java.util.*;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;

public class Filter {

    private Float minRating;
    private Float maxRating;
    private Nutrition minNutrition;
    private Nutrition maxNutrition;
    private String period;
    private String wantedIngredients;
    private String notWantedIngredients;
    private List<String> wantedIngList;
    private List<String> notWantedIngList;

    public Filter(MultivaluedMap<String,String> map){
        minNutrition = new Nutrition();
        maxNutrition = new Nutrition();
        wantedIngList = new ArrayList<String>();
        notWantedIngList = new ArrayList<String>();
        editMap(map);
    }

    public void setWantedIngList(List<String> wantedIngList){
        this.wantedIngList = wantedIngList;
    }

    public List<String> getWantedIngList(){
        return this.wantedIngList;
    }

    public void setNotWantedIngList(List<String> notWantedIngList){
        this.notWantedIngList = notWantedIngList;
    }

    public List<String> getNotWantedIngList(){
        return this.notWantedIngList;
    }    

    public void setMinRating(Float minRating){
        this.minRating = minRating;
    }

    public Float getMinRating(){
        return this.minRating;
    }

    public void setMaxRating(Float maxRating){
        this.maxRating = maxRating;
    }

    public Float getMaxRating(){
        return this.maxRating;
    }

    public void setMinNutrition(Nutrition minNutrition){
        this.minNutrition = minNutrition;
    }

    public Nutrition getMinNutrition(){
        return this.minNutrition;
    }
    public void setMaxNutrition(Nutrition maxNutrition){
        this.maxNutrition = maxNutrition;
    }

    public Nutrition getMaxNutrition(){
        return this.maxNutrition;
    }

    public String getPeriod(){
        return period;
    }

    public void setPeriod(String period){
        this.period = period;
    }

    public String getWantedIngredients(){
        return wantedIngredients;
    }

    public void setWantedIngredients(String wantedIngredients){
        this.wantedIngredients = wantedIngredients;
    }

    public String getNotWantedIngredients(){
        return notWantedIngredients;
    }

    public void setNotWantedIngredients(String notWantedIngredients){
        this.notWantedIngredients = notWantedIngredients;
    }


    public void editMap(MultivaluedMap<String,String> map){
        if(map.getFirst("minRating") == null){
            setMinRating(new Float(0));
        }else{
            setMinRating(Float.parseFloat(map.getFirst("minRating")));
        }

        if(map.getFirst("maxRating") == null){
            setMaxRating(new Float(9999999));
        }else{
            setMaxRating(Float.parseFloat(map.getFirst("maxRating")));
        }
        
        if(map.getFirst("period") == null){
            setPeriod("weekly");
        }else{
            setPeriod(map.getFirst("period"));
        }
        
        if(map.getFirst("wantedIngredients") == null){
            //map.addFirst("wantedIngredients","");
            ArrayList<String> myList = new ArrayList<String>();
            myList.add("");
            setWantedIngList(myList);
        }else{
            if(map.getFirst("wantedIngredients").contains(",")){
                String[] ingArray  = map.getFirst("wantedIngredients").split(",");
                setWantedIngList(new ArrayList<String>(Arrays.asList(ingArray)));
            }else{
                ArrayList<String> myList = new ArrayList<String>();
                myList.add(map.getFirst("wantedIngredients"));
                setWantedIngList(myList);
            }
            
        }
        
        if(map.getFirst("notWantedIngredients") == null){
            //map.addFirst("notWantedIngredients","");
            ArrayList<String> myList = new ArrayList<String>();
            myList.add("");
            setNotWantedIngList(myList);
        }else{
            if(map.getFirst("notWantedIngredients").contains(",")){
                String[] ingArray  = map.getFirst("notWantedIngredients").split(",");
                setNotWantedIngList(new ArrayList<String>(Arrays.asList(ingArray)));
            }else{
                ArrayList<String> myList = new ArrayList<String>();
                myList.add(map.getFirst("notWantedIngredients"));
                setNotWantedIngList(myList);
            }
        }
        
        if(map.getFirst("minCalories") == null){
            //map.addFirst("minCalories","0");
            this.minNutrition.setCalories(new Float(0));
        }else{
            //map.addFirst("minCalories",Float.parseFloat("minCalories"));
            this.minNutrition.setCalories(Float.parseFloat(map.getFirst("minCalories")));
        }
        
        if(map.getFirst("maxCalories") == null){
            //map.addFirst("maxCalories","99999999");
            this.maxNutrition.setCalories(new Float(99999999));
        }else{
            //map.addFirst("maxCalories",Float.parseFloat("maxCalories"));
            this.maxNutrition.setCalories(Float.parseFloat(map.getFirst("maxCalories")));
        }
        
        if(map.getFirst("minCarbohydrate") == null){
            //map.addFirst("minCarbohydrate","0");
            this.minNutrition.setCarbohydrate(new Float(0));
        }else{
            //map.addFirst("minCarbohydrate",Float.parseFloat("minCarbohydrate"));
            this.minNutrition.setCarbohydrate(Float.parseFloat(map.getFirst("minCarbohydrate")));
        }
        
        if(map.getFirst("maxCarbohydrate") == null){
            //map.addFirst("maxCarbohydrate","99999999");
            this.maxNutrition.setCarbohydrate(new Float(99999999));
        }else{
            //map.addFirst("maxCarbohydrate",Float.parseFloat("maxCarbohydrate"));
            this.maxNutrition.setCarbohydrate(Float.parseFloat(map.getFirst("maxCarbohydrate")));
        }
        
        if(map.getFirst("minFats") == null){
            //map.addFirst("minFats","0");
            this.minNutrition.setFats(new Float(0));
        }else{
            //map.addFirst("minFats",Float.parseFloat("minFats"));
            this.minNutrition.setFats(Float.parseFloat(map.getFirst("minFats")));
        }
        
        if(map.getFirst("maxFats") == null){
            //map.addFirst("maxFats","99999999");
            this.maxNutrition.setFats(new Float(99999999));
        }else{
            //map.addFirst("maxFats",Float.parseFloat("maxFats"));
            this.maxNutrition.setFats(Float.parseFloat(map.getFirst("maxFats")));
        }
        
        if(map.getFirst("minProteins") == null){
            //map.addFirst("minProteins","0");
            this.minNutrition.setProteins(new Float(0));
        }else{
            //map.addFirst("minProteins",Float.parseFloat("minProteins"));
            this.minNutrition.setProteins(Float.parseFloat(map.getFirst("minProteins")));
        }
        
        if(map.getFirst("maxProteins") == null){
            //map.addFirst("maxProteins","99999999");
            this.maxNutrition.setProteins(new Float(99999999));
        }else{
            //map.addFirst("maxProteins",Float.parseFloat("maxProteins"));
            this.maxNutrition.setProteins(Float.parseFloat(map.getFirst("maxProteins")));
        }
        
        if(map.getFirst("minSodium") == null){
            //map.addFirst("minSodium","0");
            this.minNutrition.setSodium(new Float(0));
        }else{
            //map.addFirst("minSodium",Float.parseFloat("minSodium"));
            this.minNutrition.setSodium(Float.parseFloat(map.getFirst("minSodium")));
        }
        
        if(map.getFirst("maxSodium") == null){
            //map.addFirst("maxSodium","99999999");
            this.maxNutrition.setSodium(new Float(99999999));
        }else{
            //map.addFirst("maxSodium",Float.parseFloat("maxSodium"));
            this.maxNutrition.setSodium(Float.parseFloat(map.getFirst("maxSodium")));
        }
        
        if(map.getFirst("minFiber") == null){
            //map.addFirst("minFiber","0");
            this.minNutrition.setFiber(new Float(0));
        }else{
            //map.addFirst("minFiber",Float.parseFloat("minFiber"));
            this.minNutrition.setFiber(Float.parseFloat(map.getFirst("minFiber")));
        }
        
        if(map.getFirst("maxFiber") == null){
            //map.addFirst("maxFiber","99999999");
            this.maxNutrition.setFiber(new Float(99999999));
        }else{
            //map.addFirst("maxFiber",Float.parseFloat("maxFiber"));
            this.maxNutrition.setFiber(Float.parseFloat(map.getFirst("maxFiber")));
        }
        
        if(map.getFirst("minCholesterol") == null){
            //map.addFirst("minCholesterol","0");
            this.minNutrition.setCholesterol(new Float(0));
        }else{
            //map.addFirst("minCholesterol",Float.parseFloat("minCholesterol"));
            this.minNutrition.setCholesterol(Float.parseFloat(map.getFirst("minCholesterol")));
        }
        
        if(map.getFirst("maxCholesterol") == null){
            //map.addFirst("maxCholesterol","99999999");
            this.maxNutrition.setCholesterol(new Float(99999999));
        }else{
            //map.addFirst("maxCholesterol",Float.parseFloat("maxCholesterol"));
            this.maxNutrition.setCholesterol(Float.parseFloat(map.getFirst("maxCholesterol")));
        }
        
        if(map.getFirst("minSugars") == null){
            //map.addFirst("minSugars","0");
            this.minNutrition.setSugars(new Float(0));
        }else{
            //map.addFirst("minSugars",Float.parseFloat("minSugars"));
            this.minNutrition.setSugars(Float.parseFloat(map.getFirst("minSugars")));
        }
        
        if(map.getFirst("maxSugars") == null){
            //map.addFirst("maxSugars","99999999");
            this.maxNutrition.setSugars(new Float(99999999));
        }else{
            //map.addFirst("maxSugars",Float.parseFloat("maxSugars"));
            this.maxNutrition.setSugars(Float.parseFloat(map.getFirst("maxSugars")));
        }
        
        if(map.getFirst("minIron") == null){
            //map.addFirst("minIron","0");
            this.minNutrition.setIron(new Float(0));
        }else{
            //map.addFirst("minIron",Float.parseFloat("minIron"));
            this.minNutrition.setIron(Float.parseFloat(map.getFirst("minIron")));
        }
        
        if(map.getFirst("maxIron") == null){
            //map.addFirst("maxIron","99999999");
            this.maxNutrition.setIron(new Float(99999999));
        }else{
            //map.addFirst("maxIron",Float.parseFloat("maxIron"));
            this.maxNutrition.setIron(Float.parseFloat(map.getFirst("maxIron")));
        }
        
    }
}
