package bounswegroup1.db;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import bounswegroup1.mapper.RatingMapper;
import bounswegroup1.model.Rating;

public abstract class ConsumeDAO{
	
    @SqlUpdate("INSERT INTO user_consumption(user_id, recipe_id, created_at)"+
    "VALUES (:userId, :recipeId, now());"
)
    abstract public void _saveUserConsumption(@Bind("userId") Long userId, @Bind("recipeId") Long recipeId);

	 public void saveUserConsumption(Long userId, Long recipeId){
		_saveUserConsumption(userId, recipeId);
	}	
}