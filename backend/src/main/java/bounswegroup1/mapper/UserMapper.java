package bounswegroup1.mapper;

import bounswegroup1.model.User;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.joda.time.DateTime;

import org.skife.jdbi.v2.StatementContext;

public class UserMapper implements ResultSetMapper<User> {
    public User map(int row, ResultSet rs, StatementContext ctx) throws SQLException {
        final Long id = rs.getLong("id");
        final String email = rs.getString("email");
        final String passwordHash = rs.getString("password_hash");
        final String passwordSalt = rs.getString("password_salt");
        final String fullName = rs.getString("full_name");
        final String location = rs.getString("location");
        final DateTime dateOfBirth;
        if(rs.getTimestamp("date_of_birth") != null)
            dateOfBirth = new DateTime(rs.getTimestamp("date_of_birth"));
        else
            dateOfBirth = null;
        final Boolean isRestaurant = rs.getBoolean("is_restaurant");

        return new User(id, email, passwordHash, passwordSalt, fullName, location, dateOfBirth, isRestaurant);
    }
}