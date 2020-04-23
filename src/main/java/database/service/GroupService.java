package database.service;

import database.dao.GroupDAOImpl;
import database.entity.Group;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupService {

    private final GroupDAOImpl groupsDAO;

    public GroupService() {
        groupsDAO = new GroupDAOImpl();
    }

    public Group findGroupByName(String groupName) {
        return groupsDAO.findByName(groupName);
    }

    public void saveGroup(Group group) {
        groupsDAO.save(group);
    }

    public void deleteGroup(Group group) {
        groupsDAO.delete(group);
    }

    public void updateGroup(Group group) {
        groupsDAO.update(group);
    }

    public List<Group> findAllGroups() {
        return groupsDAO.findAll();
    }

    public Set<String> getGroupNameSet() {
        Set<String> set = new HashSet<>();
        findAllGroups().forEach(group -> set.add(group.getGroupName()));
        return set;
    }

    public void saveOrUpdateGroup(Group group) {
        groupsDAO.saveOrUpdate(group);
    }
}
