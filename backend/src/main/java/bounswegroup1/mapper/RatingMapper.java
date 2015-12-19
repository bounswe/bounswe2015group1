package bounswegroup1.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import org.joda.time.DateTime;

import bounswegroup1.model.Rating;

public class RatingMapper implements ResultSetMapper<Rating> {
    private Rating result;

    public RatingMapper(){
    }

    @Override
    public Rating map(int idx, ResultSet rs, StatementContext ctx) throws SQLException {
        if(result == null){
            result = new Rating(rs.getLong("id"), rs.getLong("user_id"), rs.getString("type"), 
                rs.getLong("parent_id"), rs.getFloat("rating"), new DateTime(rs.getDate("created_at")));
        }
        return result;
    }
}