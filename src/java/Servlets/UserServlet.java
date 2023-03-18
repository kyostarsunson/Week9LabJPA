/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import dataaccess.RoleDB;
import dataaccess.UserDB;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Role;
import models.User;

/**
 *
 * @author 886152
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDB myUserDb = new UserDB();
        List<User> users = myUserDb.getAll();
        RoleDB myRoleDb = new RoleDB();
        List<Role> roles = myRoleDb.getAll();
        request.setAttribute("users", users);
        request.setAttribute("roles", roles);

        System.out.println("Servlets.UserServlet.doGet():roles=" + roles.toString());

        String email = request.getParameter("email");
        if (email != null) {
            email = email.replaceAll(" ", "+");
            email = email.replaceAll("'", "");
            User user = myUserDb.getByEmail(email);

            request.setAttribute("selectuser", user);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String actionString = request.getParameter("action");
        UserDB myUserDB = new UserDB();
        RoleDB myRoleDB = new RoleDB();

        // if add
        if (actionString.equals("insert")) {
            try {
                String strValue = request.getParameter("roleInput");
                User user = new User(request.getParameter("emailInput"), request.getParameter("firstInput"), request.getParameter("lastInput"), request.getParameter("passwordInput"));
                Role role = myRoleDB.getRoleById(Integer.parseInt(request.getParameter("roleIdInput")));
                user.setRole(role);

                myUserDB.updateByEmail(user);
                myUserDB.add(user);
                request.setAttribute("message", "insert");

            } catch (Exception e) {
                request.setAttribute("message", "error");
            }
            //System.out.println("in Servlets.UserServlet.doPost()," + "email=" + email);
        }
        // if delete
        if (actionString.equals("delete")) {
            String email = request.getParameter("email");
            email = email.replaceAll(" ", "+");
            email = email.replaceAll("'", "");

            try {
                User user = myUserDB.getByEmail(email);
                myUserDB.del(user);
                request.setAttribute("message", "deleted");

            } catch (Exception e) {
                request.setAttribute("message", "error");
                // System.out.println(e.getMessage());
            }
            //System.out.println("in Servlets.UserServlet.doPost()," + "email=" + email);
        }
        // if update
        if (actionString.equals("update")) {
            String email = request.getParameter("email");
            email = email.replaceAll(" ", "+");
            email = email.replaceAll("'", "");

            try {
                //myUserDB.updateByEmail(email, , , ,);

                User user = new User(request.getParameter("emailInput"), request.getParameter("firstInput"), request.getParameter("lastInput"), request.getParameter("passwordInput"));
                Role role = myRoleDB.getRoleById(Integer.parseInt(request.getParameter("roleId")));
                user.setRole(role);

                myUserDB.updateByEmail(user);

                request.setAttribute("message", "updated");

            } catch (Exception e) {

                request.setAttribute("message", "error");
            }

        }

        // after db action
        try {
            UserDB myUserDb = new UserDB();
            List<User> users = myUserDb.getAll();
            RoleDB myRoleDb = new RoleDB();
            List<Role> roles = myRoleDb.getAll();
            request.setAttribute("users", users);
            request.setAttribute("roles", roles);

        } catch (Exception e) {
            request.setAttribute("message", "error");
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

}
