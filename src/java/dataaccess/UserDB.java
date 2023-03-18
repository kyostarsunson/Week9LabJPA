/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.User;

public class UserDB {

    public boolean add(User user) {
//        ConnectionPool connectionPool = ConnectionPool.getInstance();
//        Connection myConnection = connectionPool.getConnection();
//        PreparedStatement myPS = null;
//
//        try {
//
//            myPS = myConnection.prepareStatement("INSERT INTO `user` (`email`,`first_name`,`last_name`,`password`,`role`)\n"
//                    + "	VALUES (?, ?,?, ?, ?);");
//            myPS.setString(1, user.getEmail());
//            myPS.setString(2, user.getFirst());
//            myPS.setString(3, user.getLast());
//            myPS.setString(4, user.getPassword());
//            myPS.setString(5, user.getRole());
//            return myPS.execute();
//
//        } catch (SQLException e) {
//            return false;
//        } finally {
//
//            connectionPool.freeConnection(myConnection);
//        }

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction traction = em.getTransaction();

        try {

            List<User> users = em.createNamedQuery("User.findAll").getResultList();
            users.add(user);

            traction.begin();
            em.persist(user);
            em.merge(users);

            traction.commit();
        } catch (Exception e) {
            traction.rollback();
            System.out.println("dataaccess.UserDB.add()--roback" + e.getMessage());
        } finally {
            em.close();
        }

        return false;
    }

    public boolean del(User user) {
//        ConnectionPool connectionPool = ConnectionPool.getInstance();
//        Connection myConnection = connectionPool.getConnection();
//        PreparedStatement myPS = null;
//
//        try {
//
//            myPS = myConnection.prepareStatement("delete from user where email=? ");
//            myPS.setString(1, user.getEmail());
//            return myPS.execute();
//
//        } catch (SQLException e) {
//            return false;
//        } finally {
//
//            connectionPool.freeConnection(myConnection);
//        }
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction traction = em.getTransaction();
        try {
            traction.begin();
            List<User> users = em.createNamedQuery("User.findAll").getResultList();
            users.remove(user);
            em.remove(em.merge(user));

            traction.commit();
        } catch (Exception e) {
            traction.rollback();
            System.out.println("dataaccess.UserDB.del()--roback" + e.getMessage());
        } finally {
            em.close();
        }

        return true;
    }

    public int updateByEmail(User user) {
//        ConnectionPool connectionPool = ConnectionPool.getInstance();
//        Connection myConnection = connectionPool.getConnection();
//        try {
//
//            PreparedStatement myPS = myConnection.prepareStatement("update user set email=?,first_name=?,last_name=?,password=? where email=? ");
//            myPS.setString(1, email);
//            myPS.setString(2, first);
//            myPS.setString(3, last);
//            myPS.setString(4, password);
//            myPS.setString(5, selectemail);
//
//            return myPS.executeUpdate();
//
//        } catch (Exception e) {
//            connectionPool.freeConnection(myConnection);
//            return -1;
//        }

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction traction = em.getTransaction();
        try {
            traction.begin();

            em.merge(user);

            traction.commit();
        } catch (Exception e) {
            traction.rollback();
            System.out.println("dataaccess.UserDB.updateByEmail()--roback" + e.getMessage());
        } finally {
            em.close();
        }

        return -1; //test
    }

    public List<User> getAll() {
//        List<User> users = new ArrayList();
//        ConnectionPool connectionPool = ConnectionPool.getInstance();
//        Connection myConnection = connectionPool.getConnection();
//        ResultSet myResultSet;
//
//        try {
//            User user;
//            PreparedStatement myPS = myConnection.prepareStatement("select a.email,a.first_name,a.last_name,b.role_name,a.password from user as a, role as b where a.role=b.role_id ");
//
//            myResultSet = myPS.executeQuery();
//
//            while (myResultSet.next()) {
//                user = new User(myResultSet.getString("email"), myResultSet.getString("first_name"), myResultSet.getString("last_name"), myResultSet.getString("role_name"), myResultSet.getString("password"));
//                System.out.println("in dataaccess.UserDB.getAll():" + user);
//
//                users.add(user);
//            }
//        } catch (SQLException ex) {
//            System.out.println("in UserDB: err" + ex);
//        } finally {
//
//            connectionPool.freeConnection(myConnection);
//        }
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        List<User> users = null;

        users = em.createNamedQuery("User.findAll").getResultList();
        return users;
    }

    public User getByEmail(String email) {
//        User user = new User();
//
//        ConnectionPool connectionPool = ConnectionPool.getInstance();
//        Connection myConnection = connectionPool.getConnection();
//        PreparedStatement myPS = null;
//        ResultSet myResultSet = null;
//
//        try {
//            myPS = myConnection.prepareStatement("select a.email,a.first_name,a.last_name,b.role_name,a.password from user as a, role as b where a.role=b.role_id and a.email=?");
//
//            myPS.setString(1, email);
//            myResultSet = myPS.executeQuery();
//
//            while (myResultSet.next()) {
//                user = new User(myResultSet.getString("email"), myResultSet.getString("first_name"), myResultSet.getString("last_name"), myResultSet.getString("role_name"), myResultSet.getString("password"));
//                System.out.println("dataaccess.UserDB.getByEmail():" + user);
//            }
//        } catch (SQLException ex) {
//            System.out.println("in getByEmail: err:" + ex);
//        } finally {
//            DBUtil.closeRecordSet(myResultSet);
//            DBUtil.closePreparedStatement(myPS);
//            connectionPool.freeConnection(myConnection);
//        }

        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {

            User user = em.find(User.class, email);

            System.out.println("dataaccess.UserDB.getByEmail()" + user.toString());
            return user;

        } finally {
            em.close();
        }

    }

}
