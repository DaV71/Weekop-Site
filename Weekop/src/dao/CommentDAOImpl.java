package dao;

import model.Comment;
import model.Discovery;
import model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import util.ConnectionProvider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentDAOImpl implements CommentDAO {

    private static final String CREATE_COMMENT =
            "INSERT INTO comment (content,com_date,user_id,discovery_id) "
                    +"VALUES (:content, :com_date, :user_id, :discovery_id);";

    private static final String READ_COMMENT =
            "SELECT user.user_id,username,email,is_active,password,discovery.discovery_id,name,description,url,discovery.date,up_vote,down_vote,comment_id,content,com_date "
        +"FROM comment LEFT JOIN discovery ON comment.discovery_id=discovery.discovery_id "
        +"LEFT JOIN user ON comment.user_id=user.user_id WHERE comment_id=:comment_id;";

    private static final String READ_ALL_DISCOVERY_COMMENTS =
            "SELECT user.user_id,username,email,is_active,password,discovery.discovery_id,name,description,url,discovery.date,up_vote,down_vote,"
                    +"comment_id,content,com_date "
                    +"FROM comment,discovery,user WHERE discovery.discovery_id=comment.discovery_id AND user.user_id=comment.user_id AND discovery.discovery_id = :discovery_id;";

    private static final String DELETE_COMMENTS_BY_DISCOVERY_ID =
            "DELETE FROM comment WHERE discovery_id=:discovery_id";

    private static final String DELETE_COMMENTS_BY_USER_ID =
            "DELETE FROM comment WHERE user_id=:user_id";

    private static final String DELETE_COMMENT =
            "DELETE FROM comment WHERE comment_id=:comment_id";

    private NamedParameterJdbcTemplate template;

    public CommentDAOImpl (){
        template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
    }

    @Override
    public Comment create(Comment newObject) {
        Comment comment = new Comment(newObject);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("content",newObject.getContent());
        paramMap.put("com_date",newObject.getDate());
        paramMap.put("user_id",newObject.getUser().getId());
        paramMap.put("discovery_id",newObject.getDiscovery().getId());
        MapSqlParameterSource parameterSource = new MapSqlParameterSource(paramMap);
        int update = template.update(CREATE_COMMENT,parameterSource,keyHolder);
        if (update > 0){
            comment.setId(keyHolder.getKey().longValue());
        }
        return comment;
    }

    @Override
    public Comment read(Long primaryKey) {
        Comment comment = null;
        SqlParameterSource parameterSource = new MapSqlParameterSource("comment_id",primaryKey);
        comment = template.queryForObject(READ_COMMENT,parameterSource,new CommentRowMapper());
        return comment;
    }

    @Override
    public boolean update(Comment updateObject) {
        return false;
    }

    @Override
    public boolean delete(Long key) {
        boolean result = false;
        SqlParameterSource parameterSource = new MapSqlParameterSource("comment_id",key);
        int delete = template.update(DELETE_COMMENT,parameterSource);
        if (delete > 0)
            result = true;
        return result;
    }

    @Override
    public List<Comment> getAll() {
        return null;
    }

    @Override
    public List<Comment> getAll(Long primaryKey) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("discovery_id",primaryKey);
        List<Comment> comments = template.query(READ_ALL_DISCOVERY_COMMENTS, parameterSource,new CommentRowMapper());
        return comments;
    }

    @Override
    public boolean deleteByDiscoveryId(long discoveryId) {
        boolean result = false;
        SqlParameterSource parameterSource  = new MapSqlParameterSource("discovery_id",discoveryId);
        int delete = template.update(DELETE_COMMENTS_BY_DISCOVERY_ID,parameterSource);
        if (delete >= 0)
            result = true;
        return result;
    }

    @Override
    public boolean deleteByUserId(long userId) {
        boolean result = false;
        SqlParameterSource parameterSource  = new MapSqlParameterSource("user_id",userId);
        int delete = template.update(DELETE_COMMENTS_BY_USER_ID,parameterSource);
        if (delete >= 0)
            result = true;
        return result;
    }

    private class CommentRowMapper implements RowMapper<Comment>{

        @Override
        public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
            Comment comment = new Comment();
            comment.setId(resultSet.getLong("comment_id"));
            comment.setContent(resultSet.getString("content"));
            comment.setDate(resultSet.getTimestamp("com_date"));
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            comment.setUser(user);
            Discovery discovery = new Discovery();
            discovery.setId(resultSet.getLong("discovery_id"));
            discovery.setName(resultSet.getString("name"));
            discovery.setDescription(resultSet.getString("description"));
            discovery.setUrl(resultSet.getString("url"));
            discovery.setUpVote(resultSet.getInt("up_vote"));
            discovery.setDownVote(resultSet.getInt("down_vote"));
            discovery.setTimestamp(resultSet.getTimestamp("date"));
            comment.setDiscovery(discovery);
            return comment;
        }
    }
}
