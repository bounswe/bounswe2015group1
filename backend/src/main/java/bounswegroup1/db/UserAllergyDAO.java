package bounswegroup1.db;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;


import bounswegroup1.model.UserAllergy;
import bounswegroup1.mapper.UserAllergyMapper;

public abstract class UserAllergyDAO {

    @SqlUpdate("INSERT INTO user_allergy(user_id, ingredient_id) VALUES "+
     "(:userId, :ingredientId)")
    abstract protected void _createUserAllergy(@BindBean UserAllergy userAllergy);

    @SqlQuery("select * from user_allergy "+
            " where user_id = :userId")
    @Mapper(UserAllergyMapper.class)
    abstract protected List<List<UserAllergy>> _getTagsForUser(@Bind("userId") Long userId);

	public UserAllergy addUserAllergy(UserAllergy userAllergy){
		_createUserAllergy(userAllergy);
		return userAllergy;
	}

	public List<UserAllergy> getAllergiesForUser(Long userId){
		List<List<UserAllergy>> res = _getTagsForUser(userId);

		if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
	}
}
