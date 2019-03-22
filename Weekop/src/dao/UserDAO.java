package dao;

import java.util.List;

import model.User;

public interface UserDAO extends GenericDAO <User,Long> {
    List <User> getAll();
    User getUserByUsername(String username);
    boolean deleteUserFromUserRole (String username);

}
