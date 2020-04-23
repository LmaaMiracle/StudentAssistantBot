package database.dao;

import database.entity.Group;
import database.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public class GroupDAOImpl implements GroupDAO {

    @Override
    public Group findByName(String groupName) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Group group = session.get(Group.class, groupName);
        session.close();
        return group;
    }

    @Override
    public void save(Group group) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(group);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Group group) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(group);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Group group) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(group);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Group> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Group> groupList = session.createQuery("FROM Group").list();
        session.close();
        return groupList;
    }

    @Override
    public void saveOrUpdate(Group group) {
        if (findByName(group.getGroupName()) == null) {
            save(group);
        } else {
            update(group);
        }
    }
}
