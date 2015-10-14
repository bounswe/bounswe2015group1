package bounswegroup1.mapper;

import bounswegroup1.model.User;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;


public class UserMapper implements ResultSetMapper<User> {
    public User map(int row, ResultSet rs, StatementContext ctx) throws SQLException {
        return new User(rs.getString("username"), rs.getString("password"));
    }
}