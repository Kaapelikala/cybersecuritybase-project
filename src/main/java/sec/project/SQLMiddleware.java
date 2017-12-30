package sec.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public  class SQLMiddleware {

    private static Connection connection;


    public static void setupSQLMiddleware() throws ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:db.db");
            //connection = DriverManager.getConnection("jdbc:sqlite:memory");
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }

    }
    public static Connection getConnection (){
        return connection;
    }




}