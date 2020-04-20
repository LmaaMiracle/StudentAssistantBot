package service;

import dao.UsersDAOImpl;
import entity.User;

import java.util.List;

public class UserService {

    private final UsersDAOImpl usersDAO;

    public UserService() {
        usersDAO = new UsersDAOImpl();
    }

    public User findUserById(long id) {
        return usersDAO.findById(id);
    }

    public void saveUser(User user) {
        usersDAO.save(user);
    }

    public void deleteUser(User user) {
        usersDAO.delete(user);
    }

    public void updateUser(User user) {
        usersDAO.update(user);
    }

    public List<User> findAllUsers() {
        return usersDAO.findAll();
    }

    public void saveOrUpdateUser(User user) {
        usersDAO.saveOrUpdate(user);
    }
}
