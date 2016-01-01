package bounswegroup1.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import org.joda.time.DateTime;

import bounswegroup1.model.UserAllergy;

public class UserAllergyMapper implements ResultSetMapper<List<UserAllergy>> {
    
    private List<UserAllergy> res;

    private UserAllergy cur;

    public UserAllergyMapper(){
        res = new ArrayList<UserAllergy>();
    }

    @Override
    public List<UserAllergy> map(int idx, ResultSet rs, StatementContext ctx) throws SQLException {
            cur = new UserAllergy( rs.getLong("user_id"), rs.getString("ingredient_id"));
            res.add(cur);
            return res;
    }
}