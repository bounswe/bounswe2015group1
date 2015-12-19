package bounswegroup1.model;

public class Ingredient {
    private String ingredientId;
    private String name;
    private Long amount;

    public String getName() {
        return name;
    }

    public Long getAmount() {
        return amount;
    }


    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }


    public Ingredient() {
    }

    public Ingredient(String ingredientId, String name, Long amount) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.amount = amount;
    }
}