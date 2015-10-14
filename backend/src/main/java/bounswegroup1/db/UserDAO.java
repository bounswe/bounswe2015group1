package bounswegroup1.db;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.BeanMapper; 
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import bounswegroup1.model.User;
import bounswegroup1.mapper.UserMapper;
import java.util.List;

@RegisterMapper(UserMapper.class)
public interface UserDAO {
    @SqlQuery("select * from users")
    List<User> getUsers();

    @SqlUpdate("insert into users values (:username, :password)")
    void addUser(@BindBean User user);
}
