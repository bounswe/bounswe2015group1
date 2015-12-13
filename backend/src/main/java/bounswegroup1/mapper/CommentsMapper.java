package bounswegroup1.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import bounswegroup1.model.Comment;

public class CommentsMapper implements ResultSetMapper<List<Comment>> {
    List<Comment> res;
    Comment curr;
    long lastId;

    public CommentsMapper(){
        res = new ArrayList<Comment>();
        lastId = -1;
    }

    @Override
    public List<Comment> map(int idx, ResultSet rs, StatementContext ctx) throws SQLException {
        if (rs.getLong("id") != lastId) {
            curr = new Comment(rs.getLong("id"), rs.getLong("user_id"), rs.getString("type"), 
                rs.getLong("parent_id"), rs.getString("body"), rs.getString("user_full_name"), rs.getDate("created_at"));
            lastId = rs.getLong("id");
            res.add(curr);
        }
        return res;
    }
    

}