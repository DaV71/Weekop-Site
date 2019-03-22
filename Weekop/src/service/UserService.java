package service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;

import dao.DAOFactory;
import dao.UserDAO;
import model.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;

public class UserService {
    public void addUser (String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        String md5Pass = encryptPassword(password);
        user.setPassword(md5Pass);
        user.setActive(false);
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDao = factory.getUserDAO();
        userDao.create(user);
    }

    public void setIsActive (long id, boolean isActive){
        User user = getUserById(id);
        user.setActive(isActive);
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDao = factory.getUserDAO();
        userDao.update(user);
    }

    public String encryptPassword(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        digest.update(password.getBytes());
        String md5Password = new BigInteger(1,digest.digest()).toString(16);
        return md5Password;
    }

    public User getUserById(long userId) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDao = factory.getUserDAO();
        User user = userDao.read(userId);
        return user;
    }

    public User getUserByUsername (String username) {
        DAOFactory factory = DAOFactory.getDAOFactory();
        UserDAO userDao = factory.getUserDAO();
        User user = userDao.getUserByUsername(username);
        return user;
    }

    public boolean deleteUser (long userId){
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        UserDAO userDAO = daoFactory.getUserDAO();
        return userDAO.delete(userId);
    }

    public boolean deleteUserFromUserRole (String username){
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        UserDAO userDAO = daoFactory.getUserDAO();
        return userDAO.deleteUserFromUserRole(username);
    }

    public List<User> getUsers(){
        DAOFactory daoFactory = DAOFactory.getDAOFactory();
        UserDAO userDAO = daoFactory.getUserDAO();
        return userDAO.getAll();
    }

    public void sendEmail (String username){
        User user = getUserByUsername(username);
        String id = String.valueOf(user.getId());


        final String login = "weekop@weekop.com";
        final String password = "****";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login, password);
                    }
                });


        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("my.project.email2121@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail()));
            message.setSubject("Potwierdzenie rejestracji Weekop");
            String stringMessage = "Witaj\n\nPotwierdź założenie konta klikając w link:\nhttp://192.168.0.108:8080/Weekop/auth?id="+id;
            message.setText(stringMessage);

            Transport.send(message);

            System.out.println("Sending email done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
