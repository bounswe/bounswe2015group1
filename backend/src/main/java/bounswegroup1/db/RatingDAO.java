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

import org.joda.time.DateTime;


import bounswegroup1.model.Rating;

public abstract class RatingDAO {

	@GetGeneratedKeys
	@SqlUpdate("insert into ratings (type, parent_id, user_id, rating, created_at) values (:type, :parentId, :userId, :rating, :createdAt)")
	abstract protected Long _addRating(@BindBean Rating rating);

	@SqlUpdate("update recipes"+
		" set rating = :rating"+
		" where id = :id")
	abstract protected void _updateRatingForRecipe(@Bind("id") Long id, @Bind("rating") Float rating);

	@SqlUpdate("update menus"+
		" set rating = :rating"+
		" where id = :id")
	abstract protected void  _updateRatingForMenu(@Bind("id") Long id, @Bind("rating") Float rating);

	@SqlUpdate("update users"+
		" set rating = :rating"+
		" where id = :id")
	abstract protected void _updateRatingForUser(@Bind("id") Long id, @Bind("rating") Float rating);

	@SqlUpdate("delete from ratings"+
		" where ratings.user_id = :userId"+
		" and ratings.parent_id = :parentId"+
		" and ratings.type = :type")
	abstract protected void _deleteRating(@BindBean Rating rating);

	@SqlQuery("select * from ratings,menus"+
	" where ratings.parent_id = :id"+
	" and menus.id = :id"+
	" and ratings.type = 'menu'")
	@Mapper(RatingsMapper.class)
	abstract protected List<List<Rating>> _getRatingsForMenu(@Bind("id") Long id);

	@SqlQuery("select * from ratings,recipes"+
	" where ratings.parent_id = :id"+
	" and recipes.id = :id"+
	" and ratings.type = 'recipe'")

	@Mapper(RatingsMapper.class)
	abstract protected List<List<Rating>> _getRatingsForRecipe(@Bind("id") Long id);

	@SqlQuery("select * from ratings,users"+
	" where ratings.parent_id = :id"+
	" and users.id = :id"+
	" and ratings.type = 'user'")
	@Mapper(RatingsMapper.class)
	abstract protected List<List<Rating>> _getRatingsForUser(@Bind("id") Long id);

    @SqlQuery("select * from ratings,menus,users"+
	" where ratings.parent_id = :menuId"+
	" and menus.id = :menuId"+
	" and ratings.user_id = :raterId"+
	" and ratings.type = 'menu'")
	@Mapper(RatingMapper.class)
	abstract protected List<Rating> _getRatingByUserForMenu(@Bind("menuId") Long menuId, @Bind("raterId") Long raterId);

    @SqlQuery("select * from ratings,recipes,users"+
	" where ratings.parent_id = :recipeId"+
	" and recipes.id = :recipeId"+
	" and ratings.user_id = :raterId"+
	" and ratings.type = 'recipe'")
	@Mapper(RatingMapper.class)
	abstract protected List<Rating> _getRatingByUserForRecipe(@Bind("recipeId") Long recipeId, @Bind("raterId") Long raterId);

    @SqlQuery("select * from ratings,users"+
	" where ratings.parent_id = :userId"+
	" and users.id = :userId"+
	" and ratings.user_id = :raterId"+	
	" and ratings.type = 'user'")
	@Mapper(RatingMapper.class)
	abstract protected List<Rating> _getRatingByUserForUser(@Bind("userId") Long userId, @Bind("raterId") Long raterId);

	public void addRating(Rating rating){
		Float fRating = rating.getRating();
		String type = rating.getType();
		Long parentId = rating.getParentId();

		Long id = _addRating(rating);
		rating.setId(id);

		Rating avg = getAverageRatingForParent(parentId, type);
		fRating = avg.getRating();

		if(type.equals("recipe")) _updateRatingForRecipe(parentId, fRating);
		else if(type.equals("menu")) _updateRatingForMenu(parentId, fRating);
		else if(type.equals("user")) _updateRatingForUser(parentId, fRating);

	}

	public void deleteRating(Rating rating){
		_deleteRating(rating);
	}

	public Rating getAverageRatingForParent(Long parentId, String type) {
		
		List<List<Rating>> res;

		if(type.toLowerCase().equals("menu")) res = _getRatingsForMenu(parentId);
		else if(type.toLowerCase().equals("recipe")) res = _getRatingsForRecipe(parentId);
		else if(type.toLowerCase().equals("user")) res = _getRatingsForUser(parentId);
		else return null;

        Float totalRating = 0.0f;
        Float avgRating;
        if (res == null || res.isEmpty()) {
        	Rating rating = new Rating();
        	rating.setRating(0.0f);
        	rating.setParentId(parentId);
        	rating.setType(type);
            return rating;
        }

        for (Rating rating : res.get(0) ) {
        	totalRating += rating.getRating();
        }

        avgRating = totalRating/res.size();
        Rating rating = new Rating(new Long(-1), new Long(-1), type, parentId, avgRating, new DateTime());

        return rating;
    }

	public Rating getRatingByUserForMenu(Long menuId, Long raterId) {
        List<Rating> res = _getRatingByUserForMenu(menuId, raterId);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }

	public Rating getRatingByUserForRecipe(Long recipeId, Long raterId) {
        List<Rating> res = _getRatingByUserForRecipe(recipeId, raterId);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }

	public Rating getRatingByUserForUser(Long userId, Long raterId) {
        List<Rating> res = _getRatingByUserForUser(userId, raterId);

        if (res == null || res.isEmpty()) {
            return null;
        }

        return res.get(0);
    }

}

