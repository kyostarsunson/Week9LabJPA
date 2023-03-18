/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import models.Role;
import models.User;

/**
 *
 * @author 886152
 */
public class RoleDB {

    public List<Role> getAll() {
//        List<Role> roles = new ArrayList<Role>();
//        ConnectionPool myConnectionPool = ConnectionPool.getInstance();
//        Connection myConnecton = myConnectionPool.getConnection();
//        PreparedStatement myPS = null;

//        try {
//            myPS = myConnecton.prepareStatement("select *from role");
//            ResultSet myRecordSet = myPS.executeQuery();
//
//            while (myRecordSet.next()) {
//                Role role = new Role(myRecordSet.getString("role_id"), myRecordSet.getString("role_name"));
//                roles.add(role);
//            }
//
//        } catch (SQLException e) {
//            System.err.println(e.getSQLState());
//
//        } finally {
//            DBUtil.closePreparedStatement(myPS);
//            myConnectionPool.freeConnection(myConnecton);
//        }
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        List<Role> roles = em.createNamedQuery("Role.findAll").getResultList();
        return roles;

    }

    public Role getRoleById(int id) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        Role role = em.find(Role.class, id);
        return role;

    }
}
