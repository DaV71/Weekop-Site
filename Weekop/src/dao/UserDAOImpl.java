package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import model.User;
import util.ConnectionProvider;

public class UserDAOImpl implements UserDAO {

    private static final String CREATE_USER =
            "INSERT INTO user(username, email, password, is_active) VALUES (:username,:email,:password,:active)";

    private static final String READ_USER =
            "SELECT user_id,username,email,password,is_active FROM user WHERE user_id = :id";

    private static final String READ_USERS =
            "SELECT user_id,username,email,password,is_active FROM user";

    private static final String READ_USER_BY_USERNAME =
            "SELECT user_id,username,email,password,is_active FROM user WHERE username = :username";

    private static final String DELETE_USER =
            "DELETE FROM user WHERE user_id=:user_id";

    private static final String DELETE_USER_FROM_USER_ROLE =
            "DELETE FROM user_role WHERE username=:username";


    private NamedParameterJdbcTemplate template;

    public UserDAOImpl() {
        template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
    }

    @Override
    public User create(User newObject) {
        User resultUser = new User(newObject);
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(newObject);
        int update = template.update(CREATE_USER, paramSource,holder);
        if (update > 0) {
            resultUser.setId(holder.getKey().longValue());
            setPrivigiles(resultUser);
        }

        return resultUser;
    }

    private void setPrivigiles(User resultUser) {
        final String userRoleQuery = "INSERT INTO user_role(username) VALUES (:username)";
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(resultUser);
        template.update(userRoleQuery,paramSource);

    }

    private void setIsActive(User resultUser) {
        String isActiveString = null;
        if (resultUser.isActive()) {
            isActiveString = "1";
        } else isActiveString = "0";
        final String userRoleQuery = "UPDATE user SET is_active = :isActiveString WHERE user_id = :id";
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("isActiveString",isActiveString);
        paramSource.addValue("id",resultUser.getId());
        template.update(userRoleQuery,paramSource);

    }

    @Override
    public User read(Long primaryKey) {
        User resultUser = null;
        SqlParameterSource paramSource = new MapSqlParameterSource("id",primaryKey);
        resultUser = template.queryForObject(READ_USER,paramSource, new UserRowMapper());
        return resultUser;
    }

    @Override
    public boolean update(User updateObject) {
        User user = null;
        SqlParameterSource parameterSource = new MapSqlParameterSource("id",updateObject.getId());
        user = template.queryForObject(READ_USER,parameterSource,new UserRowMapper());
        if (user != null){
            setIsActive(updateObject);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long key) {
        boolean result = false;
        SqlParameterSource parameterSource = new MapSqlParameterSource("user_id",key);
        int delete = template.update(DELETE_USER,parameterSource);
        if (delete > 0)
            result = true;
        return result;
    }

    @Override
    public List<User> getAll() {
        List<User> users = template.query(READ_USERS,new UserRowMapper());
        return users;
    }

    @Override
    public User getUserByUsername(String username) {
        User resultUser = null;
        SqlParameterSource paramSource = new MapSqlParameterSource("username",username);
        resultUser = template.queryForObject(READ_USER_BY_USERNAME, paramSource, new UserRowMapper());
        return resultUser;
    }

    @Override
    public boolean deleteUserFromUserRole(String username) {
        boolean result = false;
        SqlParameterSource parameterSource = new MapSqlParameterSource("username",username);
        int delete = template.update(DELETE_USER_FROM_USER_ROLE,parameterSource);
        if (delete > 0)
            result = true;
        return result;
    }

    private class UserRowMapper implements RowMapper<User>{

        private static final String READ_USER_ROLE =
                "SELECT role_name FROM user_role WHERE username = :username";


        @Override
        public User mapRow(ResultSet arg0, int arg1) throws SQLException {
            User user = new User();
            SqlParameterSource parameterSource = new MapSqlParameterSource("username",arg0.getString("username"));
            String  roleName = template.queryForObject(READ_USER_ROLE,parameterSource, new RowMapper<String>(){
                public String mapRow (ResultSet resultSet, int rowNum) throws SQLException{
                    return resultSet.getString("role_name");
                }
            });
            user.setId(arg0.getLong("user_id"));
            user.setUsername(arg0.getString("username"));
            user.setEmail(arg0.getString("email"));
            user.setPassword(arg0.getString("password"));
            boolean isActive = arg0.getString("is_active").equals("1")? true:false;
            user.setActive(isActive);
            user.setRole(roleName);
            return user;
        }

    }

}
