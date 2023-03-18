/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.*;
import java.util.LinkedList;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author 886152
 */
public class ConnectionPool {

    private static ConnectionPool pool = null;
    private static DataSource dataSource = null;
    private static final LinkedList<Connection> CONNECTIONS_LIST = new LinkedList<Connection>();

    private ConnectionPool() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/userdb");

            for (int i = 0; i < 4; i++) {
                CONNECTIONS_LIST.add(dataSource.getConnection());
            }

        } catch (SQLException | NamingException e) {
            System.err.println(e);
        }
    }

    ;

    public static synchronized ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    public Connection getConnection() {
        Connection connection = null;

        if (CONNECTIONS_LIST.isEmpty()) {
        } else {
            connection = CONNECTIONS_LIST.removeFirst();
        }
        return connection;
    }

    public void freeConnection(Connection c) {

        CONNECTIONS_LIST.add(c);

    }

}
