package database.service;

import database.dao.UsersDAOImpl;
import database.entity.Lecturer;
import database.entity.LecturerData;
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
        if (user != null) {
            User thisUserInDatabase = findUserByChatId(user.getChatId());
            if (thisUserInDatabase != null) {
                user.setUserId(thisUserInDatabase.getUserId());
                usersDAO.delete(user);
            }
        }
    }

    public void updateUser(User user) {
        User thisUserInDatabase = findUserByChatId(user.getChatId());
        user.setUserId(thisUserInDatabase.getUserId());
        if (!user.equals(thisUserInDatabase)) {
            usersDAO.update(user);
        }
    }

    public void saveOrUpdateUser(User user) {
        if (findUserByChatId(user.getChatId()) == null) {
            saveUser(user);
        } else {
            updateUser(user);
        }
    }

    public List<User> findAllGuests() {
        return usersDAO.findAllGuests();
    }

    public List<User> findAllStudents() {
        return usersDAO.findAllStudents();
    }

    public List<Lecturer> findAllLecturers() {
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

    public LecturerData findLecturerDataByLogin(String login) {
        for (LecturerData lecturerData : usersDAO.findAllLecturerData()) {
            if (login.equals(lecturerData.getLogin())) {
                return lecturerData;
            }
        }
        return null;
    }

    public LecturerData findLecturerDataBySecondName(String login) {
        for (LecturerData lecturerData : usersDAO.findAllLecturerData()) {
            if (login.equals(lecturerData.getSecondName())) {
                return lecturerData;
            }
        }
        return null;
    }

    public List<LecturerData> findAllLecturerData() {
        return usersDAO.findAllLecturerData();
    }
}
