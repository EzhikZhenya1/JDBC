package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.createNativeQuery("CREATE TABLE IF NOT EXISTS users " +
                                "(id SERIAL PRIMARY KEY NOT NULL, " +
                                "name VARCHAR(255), " +
                                "last_name VARCHAR(255), " +
                                "age INT)")
                        .executeUpdate();
                transaction.commit();
            } catch (Exception e) {

                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }

                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
                transaction.commit();
            } catch (Exception e) {

                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }

                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                User user = new User(name, lastName, age);
                session.save(user);
                transaction.commit();
            } catch (Exception e) {

                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }

                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                User user = session.get(User.class, id);
                session.delete(user);
                transaction.commit();
            } catch (Exception e) {

                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }

                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                users = session.createQuery("from User", User.class).list();
                transaction.commit();
            } catch (Exception e) {

                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }

                throw new RuntimeException(e);
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.createNativeQuery("DELETE FROM users").executeUpdate();
                transaction.commit();
            } catch (Exception e) {

                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }

                throw new RuntimeException(e);
            }
        }
    }
}