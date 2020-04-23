package database.service;

import database.dao.UsersDAOImpl;
import database.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final UsersDAOImpl usersDAO;

    public UserService() {
        usersDAO = new UsersDAOImpl();
    }

    public User findUserById(long id) {
        for (User user : findAllUsers()) {
            if (id == user.getUserId()) {
                return user;
            }
        }
        return null;
    }

    public User findUserByChatId(long chatId) {
        for (User user : findAllUsers()) {
            if (chatId == user.getChatId()) {
                return user;
            }
        }
        return null;
    }

    public void saveUser(User user) {
        usersDAO.save(user);
    }

    public void deleteUser(User user) {
        usersDAO.delete(user);
    }

    public void updateUser(User user) {
        User updatedUser = findUserByChatId(user.getChatId());
        updatedUser.setBotState(user.getBotState());
        System.out.println(updatedUser);
        usersDAO.update(updatedUser);
    }

    public void saveOrUpdateUser(User user) {
        System.out.println(findUserByChatId(user.getChatId()));
        if (findUserByChatId(user.getChatId()) == null) {
            saveUser(user);
            System.out.println("save");;
        } else {
            updateUser(user);
            System.out.println("update");
        }
    }

    public List<User> findAllGuests() {
        return usersDAO.findAllGuests();
    }

    public List<User> findAllStudents() {
        return usersDAO.findAllStudents();
    }

    public List<User> findAllLecturers() {
        return usersDAO.findAllLecturers();
    }

    public List<User> findAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.addAll(findAllGuests());
        userList.addAll(findAllStudents());
        userList.addAll(findAllLecturers());
        return userList;
    }

    public List<User> findAllMembers() {
        List<User> userList = new ArrayList<>();
        userList.addAll(findAllStudents());
        userList.addAll(findAllLecturers());
        return userList;
    }
}
