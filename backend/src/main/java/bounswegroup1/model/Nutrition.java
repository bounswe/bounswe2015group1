package bounswegroup1.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Nutrition{
	private Long id;

	private Float calories;
	private Float carbohydrate;
	private Float fats;
	private Float proteins;
	private Float sodium;
	private Float fiber;
	private Float cholesterol;
	private Float sugars;
	private Float iron;

    public Nutrition(){
        
    }

    public Nutrition(Long id,Float calories,Float carbohydrate,Float fats,Float proteins,Float sodium,
        Float fiber,Float cholesterol,Float sugars,Float iron){
        this.id=id;
        this.calories=calories;
        this.carbohydrate=carbohydrate;
        this.fats=fats;
        this.proteins=proteins;
        this.sodium=sodium;
        this.fiber=fiber;
        this.cholesterol=cholesterol;
        this.sugars=sugars;
        this.iron=iron;

    }

    public String toString(){
        return "calories: "+getCalories();
    }

    @JsonGetter("id")
	public Long getId() {
        return id;
    }

    @JsonSetter("id")
	public void setId(Long id) {
        this.id = id;
    }

    @JsonGetter("calories")
    public Float getCalories(){
    	return calories;
    }

    @JsonSetter("calories")
    public void setCalories(Float calories){
    	this.calories = calories;
    }

    @JsonGetter("carbohydrate")
    public Float getCarbohydrate(){
    	return carbohydrate;
    }

    @JsonSetter("carbohydrate")
    public void setCarbohydrate(Float carbohydrate){
    	this.carbohydrate = carbohydrate;
    }

    @JsonGetter("fats")
    public Float getFats(){
    	return fats;
    }

    @JsonSetter("fats")
    public void setFats(Float fats){
    	this.fats = fats;
    }

    @JsonGetter("proteins")
    public Float getProteins(){
    	return proteins;
    }

    @JsonSetter("proteins")
    public void setProteins(Float proteins){
    	this.proteins = proteins;
    }

    @JsonGetter("sodium")
    public Float getSodium(){
    	return sodium;
    }

    @JsonSetter("sodium")
    public void setSodium(Float sodium){
    	this.sodium = sodium;
    }

    @JsonGetter("fiber")
    public Float getFiber(){
    	return fiber;
    }

    @JsonSetter("fiber")
    public void setFiber(Float fiber){
    	this.fiber = fiber;
    }

    @JsonGetter("cholesterol")
    public Float getCholesterol(){
    	return cholesterol;
    }

    @JsonSetter("cholesterol")
    public void setCholesterol(Float cholesterol){
    	this.cholesterol = cholesterol;
    }

    @JsonGetter("sugars")
    public Float getSugars(){
    	return sugars;
    }

    @JsonSetter("sugars")
    public void setSugars(Float sugars){
    	this.sugars = sugars;
    }

    @JsonGetter("iron")
    public Float getIron(){
    	return iron;
    }

    @JsonSetter("iron")
    public void setIron(Float iron){
    	this.iron = iron;
    }


}