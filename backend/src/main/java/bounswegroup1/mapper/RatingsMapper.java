package bounswegroup1.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import org.joda.time.DateTime;

import bounswegroup1.model.Rating;

public class RatingsMapper implements ResultSetMapper<List<Rating>> {

    List<Rating> res;
    Rating curr;
    long lastId;

    public RatingsMapper(){
        res = new ArrayList<Rating>();
        lastId = -1;
    }

    @Override
    public List<Rating> map(int idx, ResultSet rs, StatementContext ctx) throws SQLException {
        if (rs.getLong("id") != lastId) {
            curr = new Rating(rs.getLong("id"), rs.getLong("user_id"), rs.getString("type"), 
                rs.getLong("parent_id"), rs.getFloat("rating"), new DateTime(rs.getDate("created_at"));
            lastId = rs.getLong("id");
            res.add(curr);
        }
        return res;
    }
}