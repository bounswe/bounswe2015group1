package bounswegroup1.db;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import bounswegroup1.model.User;
import bounswegroup1.mapper.UserMapper;
import java.util.List;

@RegisterMapper(UserMapper.class)
public interface UserDAO {
    @SqlQuery("select * from users")
    List<User> getUsers();

    @GetGeneratedKeys
    @SqlUpdate("insert into users (email, password_hash, password_salt, "
    		+ "full_name, location, date_of_birth) "
    		+ "values (:email, :passwordHash, :passwordSalt, "
    		+ ":fullName, :location, :dateOfBirth)")
    Long addUser(@BindBean User user);
    
    @SqlQuery("select * from users where id = :id")
    User getUserById(@Bind("id") Long id);
    
    @SqlQuery("select * from users where email = :email")
    User getUserByEmail(@Bind("email") String email);
}
