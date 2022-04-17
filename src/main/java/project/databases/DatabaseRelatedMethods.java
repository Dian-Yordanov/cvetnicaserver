package project.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DatabaseRelatedMethods {

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

