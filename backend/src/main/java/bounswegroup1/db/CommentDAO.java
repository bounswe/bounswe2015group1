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

import bounswegroup1.mapper.CommentsMapper;

import bounswegroup1.model.Comment;

public abstract class CommentDAO {

	@GetGeneratedKeys
	@SqlUpdate("insert into comments (type, parent_id, user_id, body, created_at, user_full_name) values (:type, :parentId, :userId, :body, :createdAt, :userFullName)")
	abstract protected Long _addComment(@BindBean Comment comment);

	@SqlUpdate("delete from comments"+
	" where comments.id = :id")
	abstract protected void _deleteComment(@BindBean Comment comment);

	@SqlQuery("select * from comments,menus"+
	" where comments.parent_id = :id"+
	" and menus.id = :id"+
	" and comments.type = 'menu'")
	@Mapper(CommentsMapper.class)
	abstract protected List<List<Comment>> _getCommentsForMenu(@Bind("id") Long id);
	
	@SqlQuery("select * from comments,recipes"+
	" where comments.parent_id = :id"+
	" and recipes.id = :id"+
	" and comments.type = 'recipe'")
	@Mapper(CommentsMapper.class)
	abstract protected List<List<Comment>> _getCommentsForRecipe(@Bind("id") Long id);

	@SqlQuery("select * from comments,users"+
	" where comments.parent_id = :id"+
	" and users.id = :id"+
	" and comments.type = 'user'")
	@Mapper(CommentsMapper.class)
	abstract protected List<List<Comment>> _getCommentsForUser(@Bind("id") Long id);

    @SqlQuery("select * from comments,menus,users"+
	" where comments.parent_id = :menuId"+
	" and menus.id = :menuId"+
	" and comments.user_id = :commenterId"+
	" and comments.type = 'menu'")
	@Mapper(CommentsMapper.class)
	abstract protected List<List<Comment>> _getCommentsByUserForMenu(@Bind("menuId") Long menuId, @Bind("commenterId") Long userId);
	
	@SqlQuery("select * from comments,recipes,users"+
	" where comments.parent_id = :recipeId"+
	" and recipes.id = :recipeId"+
	" and comments.user_id = :commenterId"+
	" and comments.type = 'recipe'")
	@Mapper(CommentsMapper.class)
	abstract protected List<List<Comment>> _getCommentsByUserForRecipe(@Bind("recipeId") Long menuId, @Bind("commenterId") Long userId);

	@SqlQuery("select * from comments,users"+
	" where comments.parent_id = :userId"+
	" and users.id = :userId"+
	" and comments.user_id = :commenterId"+
	" and comments.type = 'user'")
	@Mapper(CommentsMapper.class)
	abstract protected List<List<Comment>> _getCommentsByUserForUser(@Bind("userId") Long menuId, @Bind("commenterId") Long userId);



	public void addComment(Comment comment){
		Long id = _addComment(comment);
		comment.setId(id);
	}

	public void deleteComment(Comment comment){
		_deleteComment(comment);
	}

	public List<Comment> getCommentsForMenu(Long menuId) {
        List<List<Comment>> res = _getCommentsForMenu(menuId);

        if (res == null || res.isEmpty()) {
            return new ArrayList<Comment>();
        }

        return res.get(0);
    }

    public List<Comment> getCommentsForRecipe(Long recipeId) {
        List<List<Comment>> res = _getCommentsForRecipe(recipeId);

        if (res == null || res.isEmpty()) {
            return new ArrayList<Comment>();
        }

        return res.get(0);
    }

    public List<Comment> getCommentsForUser(Long userId) {
        List<List<Comment>> res = _getCommentsForUser(userId);

        if (res == null || res.isEmpty()) {
            return new ArrayList<Comment>();

        }

        return res.get(0);
    }

    public List<Comment> getCommentsByUserForMenu(Long menuId, Long commenterId) {
        List<List<Comment>> res = _getCommentsByUserForMenu(menuId, commenterId);

        if (res == null || res.isEmpty()) {
            return new ArrayList<Comment>();
        }

        return res.get(0);
    }

    public List<Comment> getCommentsByUserForRecipe(Long recipeId, Long commenterId) {
        List<List<Comment>> res = _getCommentsByUserForRecipe(recipeId, commenterId);

        if (res == null || res.isEmpty()) {
            return new ArrayList<Comment>();
        }

        return res.get(0);
    }

    public List<Comment> getCommentsByUserForUser(Long userId, Long commenterId) {
        List<List<Comment>> res = _getCommentsByUserForUser(userId, commenterId);

        if (res == null || res.isEmpty()) {
            return new ArrayList<Comment>();
        }

        return res.get(0);
    }

}
