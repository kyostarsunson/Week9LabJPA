<%--
    Document   : users
    Created on : 5-Mar-2023, 9:58:14 PM
    Author     : 886152
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Users</title>
    </head>
    <body>
        <h1>Manage Users</h1>

        <h2 style="color:red">

            <c:if test="${message eq 'insert'}">Success inserted.</c:if>
            <c:if test="${message eq 'updated'}">Success updated.</c:if>
            <c:if test="${message eq 'deleted'}">Success deleted.</c:if>
            <c:if test="${message eq 'error'}">error.</c:if>
            </h2>
            <br>

            <table  border="1">
                <tr><th>EMAIL</th><th>FIRST NAME</th><th>LAST NAME</th><th>ROLE</th><th></th><th></th></tr>


            <c:forEach items="${users}" var="tt">
                <tr>
                    <td>${tt.email} </td>
                    <td>${tt.firstName} </td>
                    <td>${tt.lastName} </td>
                    <td>${tt.role.roleName}</td>
                    <td><a href="UserServlet?action=update&email='${tt.email}'" >Edit</a> <td><a href="UserServlet?action=delete&email='${tt.email}'" >Delete</a>  </td>
                </tr>
            </c:forEach>
        </table>


        <c:if test="${selectuser eq null}">


            <form method="post" action="UserServlet">
                ${email}
                <h1>Add user</h1>

                Email:<input type='email' name='emailInput'><br>
                First Name<input type='textbox' name='firstInput'><br>
                Last Name<input type='textbox' name='lastInput'><br>
                Password:<input type='password' name='passwordInput'><br>
                Role:<select name='roleIdInput' >
                    <c:forEach items="${roles}" var="roles">
                        <option value=${roles.roleId}>${roles.roleName}</option>
                    </c:forEach>

                </select><br>
                <input type="hidden" name='action' value='insert'>
                <input type='submit' value='Add User'><br>


            </form>

        </c:if>


        <c:if test="${selectuser ne null}">
            <h1>Edit user</h1>
            ${selectuser.firstName}
            <form method="post" action="UserServlet">
                Email:<input type='email' name='emailInput' value='${selectuser.email}'><br>
                First Name<input type='textbox' name='firstInput' value="${selectuser.firstName}"><br>
                Last Name<input type='textbox' name='lastInput' value="${selectuser.lastName}"><br>
                Password:<input type='password' name='passwordInput' value="${selectuser.password}"><br>
                Role:${selectuser.role.roleName}<br>
                <select name="roleId">
                    <c:forEach items="${roles}" var="roles">
                        <option value=${roles.roleId}>${roles.roleName}</option>
                    </c:forEach>
                </select><br>
                <input type="hidden" name='email' value='${selectuser.email}'>
                <input type="hidden" name='action' value=update>
                <input type='submit' value='update'>
            </form>
        </c:if>

        <form method="post" action="UserServlet">
            <c:if test="${selectuser ne null }">
                <input type="hidden" name='email' value='${selectuser.email}'>
                <input type="hidden" name='action' value=delete>
                <input type='submit' value='delete'>
            </c:if>


        </form>
        <a href="/">cancel edit</a>
    </body>
</html>
