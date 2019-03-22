package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import model.Discovery;
import model.User;
import util.ConnectionProvider;

public class DiscoveryDAOImpl implements DiscoveryDAO {

    private static final String CREATE_DISCOVERY =
            "INSERT INTO discovery (name,description,url,user_id,date,up_vote,down_vote, num_of_comments) "
                    +"VALUES (:name,:description,:url,:user_id,:date,:up_vote,:down_vote, :num_of_comments);";

    private static final String READ_ALL_DISCOVERIES =
            "SELECT user.user_id,username,email,is_active,password,discovery_id,name,description,url,date,up_vote,down_vote, num_of_comments "
                    +"FROM discovery LEFT JOIN user ON discovery.user_id=user.user_id;";

    private static final String READ_ALL_USER_DISCOVERIES =
            "SELECT user.user_id,username,email,is_active,password,discovery_id,name,description,url,date,up_vote,down_vote, num_of_comments "
                    +"FROM discovery LEFT JOIN user ON discovery.user_id=user.user_id WHERE user.user_id=:user_id;";

    private static final String READ_DISCOVERY =
            "SELECT user.user_id, username, email, is_active, password, discovery_id, name, description, url, date, up_vote, down_vote, num_of_comments "
                    + "FROM discovery LEFT JOIN user ON discovery.user_id=user.user_id WHERE discovery_id=:discovery_id;";

    private static final String UPDATE_DISCOVERY =
            "UPDATE discovery SET name=:name,description=:description,url=:url,user_id=:user_id,date=:date,up_vote=:up_vote,down_vote=:down_vote, num_of_comments=:num_of_comments "
                    +"WHERE discovery_id=:discovery_id;";

    private static final String DELETE_DISCOVERY =
            "DELETE FROM discovery WHERE discovery_id=:discovery_id;";

    private static final String DELETE_DISCOVERY_BY_USER_ID =
            "DELETE FROM discovery WHERE user_id=:user_id;";

    private NamedParameterJdbcTemplate template;

    public DiscoveryDAOImpl() {
        template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
    }

    @Override
    public Discovery create(Discovery newObject) {
        Discovery resultDiscovery = new Discovery(newObject);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String,Object>paramMap = new HashMap<String,Object>();
        paramMap.put("name",newObject.getName());
        paramMap.put("description",newObject.getDescription());
        paramMap.put("url", newObject.getUrl());
        paramMap.put("user_id", newObject.getUser().getId());
        paramMap.put("date",newObject.getTimestamp());
        paramMap.put("up_vote", newObject.getUpVote());
        paramMap.put("down_vote",newObject.getDownVote());
        paramMap.put("num_of_comments",newObject.getNumberOfComments());
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        int update = template.update(CREATE_DISCOVERY, paramSource,keyHolder);
        if (update>0) {
            resultDiscovery.setId(keyHolder.getKey().longValue());
        }

        return resultDiscovery;
    }

    @Override
    public Discovery read(Long primaryKey) {
        SqlParameterSource paramSource = new MapSqlParameterSource("discovery_id",primaryKey);
        Discovery discovery = null;
        discovery = template.queryForObject(READ_DISCOVERY,paramSource,new DiscoveryRowMapper());
        return discovery;
    }

    @Override
    public boolean update(Discovery updateObject) {
        boolean result = false;
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("discovery_id", updateObject.getId());
        paramMap.put("name", updateObject.getName());
        paramMap.put("description", updateObject.getDescription());
        paramMap.put("url", updateObject.getUrl());
        paramMap.put("user_id", updateObject.getUser().getId());
        paramMap.put("date", updateObject.getTimestamp());
        paramMap.put("up_vote", updateObject.getUpVote());
        paramMap.put("down_vote", updateObject.getDownVote());
        paramMap.put("num_of_comments",updateObject.getNumberOfComments());
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        int update = template.update(UPDATE_DISCOVERY, paramSource);
        if (update>0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean delete(Long key) {
        boolean result = false;
        SqlParameterSource parameterSource = new MapSqlParameterSource("discovery_id",key);
        int delete = template.update(DELETE_DISCOVERY,parameterSource);
        if (delete > 0)
            result = true;
        return result;
    }

    @Override
    public List<Discovery> getAll() {
        List<Discovery> discoveries = template.query(READ_ALL_DISCOVERIES, new DiscoveryRowMapper());
        return discoveries;
    }

    @Override
    public List<Discovery> getAllUserDiscoveries(Long userId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("user_id",userId);
        List <Discovery> discoveries = template.query(READ_ALL_USER_DISCOVERIES,parameterSource,new DiscoveryRowMapper());
        return discoveries;
    }

    @Override
    public boolean deleteDiscoveryByUserId(long userId) {
        boolean result = false;
        SqlParameterSource parameterSource  = new MapSqlParameterSource("user_id",userId);
        int delete = template.update(DELETE_DISCOVERY_BY_USER_ID,parameterSource);
        if (delete >= 0)
            result = true;
        return result;
    }

    private class DiscoveryRowMapper implements RowMapper <Discovery>{

        @Override
        public Discovery mapRow(ResultSet arg0, int arg1) throws SQLException {
            Discovery discovery = new Discovery();
            discovery.setId(arg0.getLong("discovery_id"));
            discovery.setName(arg0.getString("name"));
            discovery.setDescription(arg0.getString("description"));
            discovery.setUrl(arg0.getString("url"));
            discovery.setUpVote(arg0.getInt("up_vote"));
            discovery.setDownVote(arg0.getInt("down_vote"));
            discovery.setTimestamp(arg0.getTimestamp("date"));
            discovery.setNumberOfComments(arg0.getInt("num_of_comments"));
            User user = new User();
            user.setId(arg0.getLong("user_id"));
            user.setUsername(arg0.getString("username"));
            user.setPassword(arg0.getString("password"));
            user.setEmail(arg0.getString("email"));
            discovery.setUser(user);

            return discovery;
        }

    }

}
