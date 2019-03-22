package service;

import dao.CommentDAO;
import dao.DAOFactory;
import dao.DiscoveryDAO;
import model.Comment;
import model.Discovery;
import model.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class CommentService {
    public void addComment (String content, User user, Discovery discovery){
        Comment comment = createCommentObject(content,user,discovery);
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = daoFactory.getCommentDAO();
        commentDAO.create(comment);
        DiscoveryDAO discoveryDAO = daoFactory.getDiscoveryDAO();
        Discovery updateDiscovery = new Discovery(discovery);
        updateDiscovery.setNumberOfComments(discovery.getNumberOfComments()+1);
        discoveryDAO.update(updateDiscovery);

    }

    private Comment createCommentObject (String content, User user, Discovery discovery){
        Comment comment = new Comment();
        comment.setContent(content);
        User copyUser = new User(user);
        comment.setUser(copyUser);
        Discovery copyDiscovery = new Discovery(discovery);
        comment.setDiscovery(copyDiscovery);
        comment.setDate(new Timestamp(new Date().getTime()));
        return comment;
    }

    public List<Comment> getAllDiscoveryComments (Long id){
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = daoFactory.getCommentDAO();
        List<Comment> comments = commentDAO.getAll(id);
        return comments;
    }

    public boolean deleteCommentsByDiscoveryId (long discoveryId){
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = daoFactory.getCommentDAO();
        return commentDAO.deleteByDiscoveryId(discoveryId);
    }

    public boolean deleteCommentsByUserId (long userId){
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = daoFactory.getCommentDAO();
        return commentDAO.deleteByUserId(userId);
    }

    public boolean deleteComment (long voteId){
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = daoFactory.getCommentDAO();
        return commentDAO.delete(voteId);
    }

    public Comment getComment (long commentId){
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        CommentDAO commentDAO = daoFactory.getCommentDAO();
        return commentDAO.read(commentId);
    }
}
