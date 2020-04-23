package database.dao;

import database.entity.User;

import java.util.List;

public interface UsersDAO {

    public void save(User user);

    public void update(User user);

    public void delete(User user);

    public List<User> findAllGuests();

    public List<User> findAllStudents();

    public List<User> findAllLecturers();
}