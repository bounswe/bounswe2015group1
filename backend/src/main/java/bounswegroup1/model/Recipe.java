package bounswegroup1.model;

import java.util.ArrayList;
import java.util.List;

import bounswegroup1.db.RecipeDAO;

public class Recipe {
	private Long id;
	
	private Long userId;
	private String name;
	
	private List<Ingredient> ingredients;
	private String description;


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

	protected class Ingredient {
		private final String ingredientId;
		private final String name;
		private final Long amount;
		private final String unit;
		
		public String getName() {
			return name;
		}
		public Long getAmount() {
			return amount;
		}
		public String getUnit() {
			return unit;
		}
		
		public String getIngredientId() {
			return ingredientId;
		}
		
		public Ingredient(String ingredientId, String name, Long amount, String unit) {
			this.ingredientId = ingredientId;
			this.name = name;
			this.amount = amount;
			this.unit = unit;
		}
	}

	public Recipe(Long id, Long userId, String name, String description) {
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.description = description;
		this.ingredients = new ArrayList<Ingredient>();
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addIngredient(String ingredientId, String name, Long amount, String unit){
		ingredients.add(new Ingredient(ingredientId, name, amount, unit));
	}
	
	public void createIngredients(RecipeDAO dao){
		for(Ingredient ingredient : ingredients){
			dao.createIngredient(id, ingredient.getName(), ingredient.getAmount(), ingredient.getUnit());
		}
	}
	
}
