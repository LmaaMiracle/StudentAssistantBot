package database.dao;

import database.entity.User;
import database.util.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UsersDAOImpl implements UsersDAO {

    @Override
    public void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> findAllGuests() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<User> guestList = session.createQuery("FROM Guest").list();
        session.close();
        return guestList;
    }

    @Override
    public List<User> findAllStudents() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<User> studentList = session.createQuery("FROM Student").list();
        session.close();
        return studentList;
    }

    @Override
    public List<User> findAllLecturers() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<User> lecturerList = session.createQuery("FROM Lecturer").list();
        session.close();
        return lecturerList;
    }
}
