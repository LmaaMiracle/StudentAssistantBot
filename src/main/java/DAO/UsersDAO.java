package DAO;

import Entities.User;

import java.util.List;

public interface UsersDAO {

    public void save(User user);

    public void update(User user);

    public void delete(User user);

    public User findById(long id);

    public List<User> findAll();

    public void saveOrUpdate(User user);
}
