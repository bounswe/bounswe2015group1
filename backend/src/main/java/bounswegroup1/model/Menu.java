package bounswegroup1.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Menu {
    private Long id;
    private String name;
    private Long userId;
    private List<Recipe> recipes;
    private Date createdAt;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Menu(Long id, String name, Long userId, List<Recipe> recipes, Date createdAt) {
        super();
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.recipes = recipes;
        this.createdAt = createdAt;
    }

    public Menu(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public void addRecipe(Recipe id){
        recipes.add(id);
    }
}
