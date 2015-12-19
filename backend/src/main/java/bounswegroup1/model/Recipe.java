package bounswegroup1.model;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

import bounswegroup1.db.RecipeDAO;

public class Recipe {
    private Long id;

    private Long userId;
    private String name;

    private List<Ingredient> ingredients;
    private String description;
    
    private List<String> tags;

    private DateTime createdAt;

    private Nutrition nutritions;

    public Nutrition getNutritions(){
        return nutritions;
    }

    public void setNutritions(Nutrition nutritions){
        this.nutritions = nutritions;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    } 

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTags(List<String> tags){
        this.tags = tags;
    }

    public List<String> getTags(){
        return tags;
    }
    
    public Recipe() {
    }

    public Recipe(Long id, Long userId, String name, String description,DateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.ingredients = new ArrayList<Ingredient>();
        this.tags = new ArrayList<String>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addIngredient(String ingredientId, String name, Long amount) {
        ingredients.add(new Ingredient(ingredientId, name, amount));
    }

    public void addTags(String tag) {
        tags.add(tag);
    }

    public void addNutritions(Nutrition nutritions) {
        this.nutritions = nutritions;
    }

    public void createIngredients(RecipeDAO dao) {
        for (Ingredient ingredient : ingredients) {
            dao.createIngredient(id, ingredient.getName(), ingredient.getIngredientId(),
                    ingredient.getAmount());
        }
    }
    
    public void createTags(RecipeDAO dao){
        if(tags != null){
            dao.insertTagsForRecipe(id, tags);
        }
    }

    public void createNutrition(RecipeDAO dao){
        if(nutritions != null){
            Long id = dao._createNutrition(nutritions.getCalories(),
                nutritions.getCarbohydrate(),nutritions.getFats(),nutritions.getProteins(),
                nutritions.getSodium(),nutritions.getFiber(),nutritions.getCholesterol(),
                nutritions.getSugars(),nutritions.getIron());
            nutritions.setId(id);
            if(nutritions.getId() != null){
                dao.createNutritionRecipe(nutritions.getId(),this.getId());
            }
        }
    }

    public boolean isContainTag(String tag){
        ArrayList<String> tags = new ArrayList<String>(this.getTags());

        for(int i=0;i<tags.size(); i++){
            if(tags.get(i).equals(tag)){
                return true;
            }
        }
        return false;
    }

}
