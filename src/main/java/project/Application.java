package project;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;


@SpringBootApplication
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }







    







    public static void createDB() throws ClassNotFoundException {
        executeSqlStatementWithNoReturn("DROP TABLE IF EXISTS currency");
        executeSqlStatementWithNoReturn("create table currency (currencyName string, value integer);");
    }

    public static void writeIntoDB(String currencyName, String value) throws ClassNotFoundException {
        System.out.println("insert into currency values(\'" + currencyName +"\'," + value +");");
        executeSqlStatementWithNoReturn("insert into currency values(\'" + currencyName +"\'," + value +");");

//                insert into currency values("Dollar", 1.65);
    }

    public static void executeSqlStatementWithNoReturn(String sqlStatement) throws ClassNotFoundException {

        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\database\\currencyDB1.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeQuery(sqlStatement);

        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }
    }



}
