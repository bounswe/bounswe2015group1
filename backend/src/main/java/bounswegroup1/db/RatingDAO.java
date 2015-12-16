package bounswegroup1.db;

import java.util.Date;
import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import bounswegroup1.mapper.RatingsMapper;
import bounswegroup1.mapper.RatingMapper;


import bounswegroup1.model.Rating;

public abstract class RatingDAO {

	@GetGeneratedKeys
	@SqlUpdate("insert into ratings (type, parent_id, user_id, rating, created_at) values (:type, :parentId, :userId, :rating, now())")
	abstract protected Long _addRating(@BindBean Rating rating);

	@SqlUpdate("delete from ratings"+
		" where ratings.user_id = :userId"+
		" and ratings.parent_id = :parentId")
	abstract protected void _deleteRating(@BindBean Rating rating);

	@SqlQuery("select * from ratings,menus"+
	" where ratings.parent_id = :id"+
	" and menus.id = :id")
	@Mapper(RatingsMapper.class)
	abstract protected List<List<Rating>> _getRatingsForMenu(@Bind("id") Long id);

	@SqlQuery("select * from ratings,recipes"+
	" where ratings.parent_id = :id"+
	" and recipes.id = :id")
	@Mapper(RatingsMapper.class)
	abstract protected List<List<Rating>> _getRatingsForRecipe(@Bind("id") Long id);

	@SqlQuery("select * from ratings,users"+
	" where ratings.parent_id = :id"+
	" and users.id = :id")
	@Mapper(RatingsMapper.class)
	abstract protected List<List<Rating>> _getRatingsForUser(@Bind("id") Long id);

    @SqlQuery("select * from ratings,menus,users"+
	" where ratings.parent_id = :menuId"+
	" and menus.id = :menuId"+
	" and ratings.user_id = :raterId")
	@Mapper(RatingMapper.class)
	abstract protected List<Rating> _getRatingByUserForMenu(@Bind("menuId") Long menuId, @Bind("raterId") Long userId);

    @SqlQuery("select * from ratings,recipes,users"+
	" where ratings.parent_id = :recipeId"+
	" and recipes.id = :recipeId"+
	" and ratings.user_id = :raterId")
	@Mapper(RatingMapper.class)
	abstract protected List<Rating> _getRatingByUserForRecipe(@Bind("recipeId") Long menuId, @Bind("raterId") Long userId);

    @SqlQuery("select * from ratings,users"+
	" where ratings.parent_id = :userId"+
	" and users.id = :userId"+
	" and ratings.user_id = :raterId")
	@Mapper(RatingMapper.class)
	abstract protected List<Rating> _getRatingByUserForUser(@Bind("userId") Long menuId, @Bind("raterId") Long userId);

	public void addRating(Rating rating){
		Float fRating = rating.getRating();
		String.format("%.1f", fRating);
		rating.setRating(fRating);
		Long id = _addRating(rating);
		rating.setId(id);
	}

	public void deleteRating(Rating rating){
		_deleteRating(rating);
	}

	public Float getAverageRatingForParent(Long parentId, String type) {
		
		List<List<Rating>> res;

		if(type.toLowerCase().equals("menu")) res = _getRatingsForMenu(parentId);
		else if(type.toLowerCase().equals("recipe")) res = _getRatingsForRecipe(parentId);
		else if(type.toLowerCase().equals("user")) res = _getRatingsForUser(parentId);
		else return 0.0f;

        Float totalRating = 0.0f;
        Float avgRating;
        if (res == null || res.isEmpty()) {
            return null;
        }

        for (Rating rating : res.get(0) ) {
        	totalRating += rating.getRating();
        }

        avgRating = totalRating/res.size();
        String.format("%.1f", avgRating);
        return avgRating;
    }

	public Rating getRatingByUserForMenu(Long menuId, Long commenterId) {
        List<Rating> res = _getRatingByUserForMenu(menuId, commenterId);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }

	public Rating getRatingByUserForRecipe(Long recipeId, Long commenterId) {
        List<Rating> res = _getRatingByUserForRecipe(recipeId, commenterId);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }

	public Rating getRatingByUserForUser(Long userId, Long commenterId) {
        List<Rating> res = _getRatingByUserForUser(userId, commenterId);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }

}

