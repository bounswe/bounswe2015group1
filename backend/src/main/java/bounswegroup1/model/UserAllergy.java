package bounswegroup1.model;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

public class UserAllergy {

    private Long userId;
    private String ingredientId;
    
    public UserAllergy() {
    }

    public UserAllergy(Long userId, String ingredientId) {
        this.userId = userId;
        this.ingredientId = ingredientId;
    }

    public Long getUserId(){
        return userId;
    }

    public void setUserId(Long userId){
        this.userId = userId;
    }

    public String getIngredientId(){
        return ingredientId;
    }

    public void setIngredientId(String ingredientId){
        this.ingredientId = ingredientId;
    }
}
