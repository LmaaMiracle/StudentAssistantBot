package database.dao;

import database.entity.Group;

import java.util.List;

public interface GroupDAO {

    public void save(Group group);

    public void update(Group group);

    public void delete(Group group);

    public Group findByName(String groupName);

    public List<Group> findAll();

    public void saveOrUpdate(Group group);
}
