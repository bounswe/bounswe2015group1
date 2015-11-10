package bounswegroup1.model;	

public class Ingredient {
	private String ingredientId;
	private String name;
	private Long amount;
	private String unit;
	
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
	
	public void setIngredientId(String ingredientId) {
		this.ingredientId = ingredientId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public Ingredient(){ } 
	
	public Ingredient(String ingredientId, String name, Long amount, String unit) {
		this.ingredientId = ingredientId;
		this.name = name;
		this.amount = amount;
		this.unit = unit;
	}
}